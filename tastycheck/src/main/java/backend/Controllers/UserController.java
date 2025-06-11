package backend.Controllers;

import backend.DAOs.UserDAO;
import backend.DTOs.UserDTO;
import backend.Exceptions.UserException;
import backend.Models.User;
import backend.Services.UserService;
import org.orm.PersistentException;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class UserController extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("list".equals(action)) {
                List<User> users = userService.listAllUsers();
                List<UserDTO> dtos = users.stream()
                        .map(u -> new UserDTO(u.getId(), u.getUsername(), u.getEmail()))
                        .collect(Collectors.toList());

                Gson gson = new Gson();
                out.println(gson.toJson(dtos));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Vai aparecer na consola
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno: " + e.getMessage());
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            String id = request.getParameter("id");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            User user = new User(id,username, email, password);
            try {
                userService.registerUser(user);
            } catch (PersistentException e) {
                throw new RuntimeException(e);
            }

            response.setContentType("application/json");
            response.getWriter().println("{\"status\": \"utilizador criado com sucesso\"}");
        } else if ("update".equals(action)) {
            String id = request.getParameter("id");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            User user = new User(id, username, email, password);
            try {
                userService.registerUser(user);
            } catch (PersistentException e) {
                throw new RuntimeException(e);
            }

            response.getWriter().println("{\"status\": \"utilizador atualizado\"}");
    } else if ("delete".equals(action)) {
            String id = request.getParameter("id");

            try {
                User user= userService.getUserById(id);
                userService.deleteUser(user);
                response.getWriter().println("{\"status\": \"utilizador removido\"}");
            } catch (PersistentException | UserException e) {
                throw new RuntimeException(e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ação desconhecida.");
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws IOException, PersistentException {
        List<User> users = userService.listAllUsers();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        for (User user : users) {
            user.setPassword(null);
        }

        Gson gson = new Gson();
        String json = gson.toJson(users);
        response.getWriter().write(json);
    }


    private void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException, PersistentException {
        String id = request.getParameter("id");
        User user = UserDAO.getUserByORMID(id);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (user != null) {
            user.setPassword(null);

            Gson gson = new Gson();
            String json = gson.toJson(user);
            response.getWriter().write(json);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Utilizador não encontrado.");
        }
    }

    private void createUser(HttpServletRequest request, HttpServletResponse response) throws IOException, PersistentException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User novoUser = new User();
        novoUser.setUsername(username);
        novoUser.setEmail(email);
        novoUser.setPassword(password); // Aqui deverias cifrar!

        UserDAO.save(novoUser);
        response.sendRedirect("user?action=list");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException, PersistentException {
        String id = request.getParameter("id");
        User user = UserDAO.getUserByORMID(id);
        if (user != null) {
            user.setUsername(request.getParameter("nome"));
            user.setEmail(request.getParameter("email"));
            UserDAO.save(user);
            response.sendRedirect("user?action=list");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Utilizador não encontrado.");
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException, PersistentException {
        String id = request.getParameter("id");
        User user = UserDAO.getUserByORMID(id);
        UserDAO.delete(user);
        response.sendRedirect("user?action=list");
    }
}
