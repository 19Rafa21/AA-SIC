package backend.Controllers;

import backend.DTOs.EditUserDTO;
import backend.DTOs.Review.ReviewDTO;
import backend.DTOs.UserDTO;
import backend.Models.Review;
import backend.Models.User;
import backend.Services.UserService;
import backend.Exceptions.UserException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.orm.PersistentException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UserController extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO dto;
        try {
            dto = parseUserDTO(req);
            userService.registerUser(dto);
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"message\": \"Utilizador criado com sucesso\"}");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EditUserDTO dto;
        try {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do user é obrigatório na URL.");
                return;
            }
            String id = pathInfo.substring(1);

            dto = parseEditUserDTO(req);
            userService.updateUser(dto, id);
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"message\": \"Utilizador atualizado com sucesso\"}");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.split("/").length < 2) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do utilizador não especificado.");
                return;
            }

            String userId = pathInfo.split("/")[1];

            User user = userService.getUserById(userId);
            if (user == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Utilizador não encontrado.");
                return;
            }

            boolean deleted = userService.deleteUser(userId);
            response.setContentType("application/json");
            if (deleted) {
                response.getWriter().println("{\"status\": \"Utilizador removido com sucesso\"}");
            } else {
                response.getWriter().println("{\"status\": \"Erro ao remover o utilizador\"}");
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do utilizador inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno: " + e.getMessage());
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        response.setContentType("application/json");

        try {
            Gson gson = new Gson();

            if (pathInfo == null || pathInfo.equals("/")) {
                // /users → listar todos os users
                List<User> users = userService.listAllUsers();
                List<UserDTO> dtos = users.stream()
                        .map(user -> new UserDTO(user.getUsername(), user.getEmail(), user.getPassword(),user.getDiscriminator()))
                        .toList();
                response.getWriter().println(gson.toJson(dtos));

            } else {
                String[] parts = pathInfo.split("/");
                if (parts.length >= 2) {
                    String userId = parts[1];
                    User user = userService.getUserById(userId);

                    if (user == null) {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Utilizador não encontrado.");
                        return;
                    }

                    if (parts.length == 2) {
                        // /users/{id} → detalhes do user
                        UserDTO userDTO = new UserDTO(user.getUsername(), user.getEmail(), user.getPassword(),user.getDiscriminator());
                        response.getWriter().println(gson.toJson(userDTO));

                    } else if (parts.length == 3) {
                        String subResource = parts[2];

                        if ("reviews".equals(subResource)) {
                            // /users/{id}/reviews → reviews feitas por este user
                            List<Review> reviews = userService.getReviewsByUserId(userId); // ou: reviewService.getReviewsByAuthor(user)
                            List<ReviewDTO> dtos = reviews.stream()
                                    .map(ReviewDTO::new)
                                    .toList();
                            response.getWriter().println(gson.toJson(dtos));
                        } else {
                            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Sub-recurso não encontrado.");
                        }

                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de URL inválido.");
                    }

                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de URL inválido.");
                }
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno: " + e.getMessage());
        }
    }


    private UserDTO parseUserDTO(HttpServletRequest req) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(req.getReader(), UserDTO.class);
    }

    private EditUserDTO parseEditUserDTO(HttpServletRequest req) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(req.getReader(), EditUserDTO.class);
    }

    private JsonObject parseJson(HttpServletRequest req) throws IOException {
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return new Gson().fromJson(sb.toString(), JsonObject.class);
    }
}
