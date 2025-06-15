package backend.Controllers;

import backend.DTOs.EditUserDTO;
import backend.DTOs.Restaurant.FavRestaurantsDTO;
import backend.DTOs.Review.ReviewDTO;
import backend.DTOs.UserDTO;
import backend.Models.Restaurant;
import backend.Models.Review;
import backend.Models.User;
import backend.Services.ImageService;
import backend.Services.RestaurantService;
import backend.Services.UserService;
import backend.Exceptions.UserException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.orm.PersistentException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

@WebServlet("/users")
@MultipartConfig
public class UserController extends HttpServlet {

    private final UserService userService = new UserService();
    private final RestaurantService restaurantService = new RestaurantService();

    private final ImageService imageService = new ImageService();

    Gson gson = new Gson();

	public UserController() throws IOException {
	}

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                UserDTO dto;
                try {
                    dto = parseUserDTO(req);
                    userService.registerUser(dto);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().write("{\"message\": \"Utilizador criado com sucesso\"}");
                } catch (Exception e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                }
            } else {
                String[] parts = pathInfo.split("/");
                if (parts.length >= 2) {
                    String userId = parts[1];

                    User user = userService.getUserById(userId);
                    if (user == null) {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User não encontrado!");
                        return;
                    }
                    String action = parts[2];

                    if ("favorites".equals(action)) {
                        String restaurantId = parts[3];

                        Restaurant restaurant = restaurantService.getRestaurantByOrmID(restaurantId);
                        if (restaurant == null) {
                            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Restaurante não encontrado!");
                            return;
                        }

                        userService.addFavoriteRestaurant(user, restaurant);
                        resp.setStatus(HttpServletResponse.SC_OK);
                        resp.getWriter().write("{\"message\": \"Restaurante adicionado ao favoritos com sucesso\"}");

                    } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "ação não encontrado.");
                    }

                }
            }
        } catch (IOException | PersistentException | UserException e){
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno: " + e.getMessage());
        }


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        EditUserDTO dto;
        try {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do user é obrigatório na URL.");
                return;
            }
            String id = pathInfo.substring(1);

            String userJson = req.getParameter("user");
            Part file = req.getPart("file");

            boolean imageExist = file != null && file.getSize() > 0;

            if (imageExist){
                String originalFileName = Paths.get(file.getSubmittedFileName()).getFileName().toString();
                InputStream fileContent = file.getInputStream();
                String contentType = file.getContentType();
                if (!contentType.startsWith("image/")){
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("{\"message\": Ficheiro não é uma imagem \"}");
                }

                if (fileContent != null){
                    User user = userService.getUserById(id);
                    String oldFileName = user.getProfilePicture();
                    String uploadedImage = imageService.replaceImage(originalFileName, fileContent, oldFileName);

                    EditUserDTO userdto = gson.fromJson(userJson, EditUserDTO.class);
                    userdto.setUserImage(uploadedImage);

                    userService.updateUserWithImage(userdto, id);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().write("{\"message\": \"Utilizador atualizado com sucesso\"}");

                }
            } else {
                dto = gson.fromJson(userJson, EditUserDTO.class);
                userService.updateUser(dto, id);
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("{\"message\": \"Utilizador atualizado com sucesso\"}");
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.split("/").length < 2) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do utilizador não especificado.");
                return;
            }

            String[] parts = pathInfo.split("/");
            String userId = parts[1];

            User user = userService.getUserById(userId);
            if (user == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Utilizador não encontrado.");
                return;
            }

            if (parts.length == 2) {
                boolean deleted = userService.deleteUser(userId);
                if (deleted) {
                    response.getWriter().println("{\"status\": \"Utilizador removido com sucesso\"}");
                } else {
                    response.getWriter().println("{\"status\": \"Erro ao remover o utilizador\"}");
                }
            } else {
                String action = parts[2];

                if ("favorites".equals(action)){
                    String restaurantId = parts[3];

                    Restaurant restaurant = restaurantService.getRestaurantByOrmID(restaurantId);
                    if (restaurant == null) {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Restaurante não encontrado.");
                        return;
                    }

                    userService.removeFavoriteRestaurant(user, restaurant);
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("{\"message\": \"Restaurante removido dos favoritos com sucesso\"}");
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "ação não encontrado.");
                }
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
                        .map(user -> new UserDTO(user.getUsername(), user.getEmail(), user.getPassword(),user.getDiscriminator(),user.getProfilePicture()))
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
                        UserDTO userDTO = new UserDTO(user.getUsername(), user.getEmail(), user.getPassword(),user.getDiscriminator(),user.getProfilePicture());
                        response.getWriter().println(gson.toJson(userDTO));

                    } else if (parts.length == 3) {
                        String subResource = parts[2];

                        if ("favorites".equals(subResource)) {
                            // /users/{id}/favorites → reviews feitas por este user
                            List<Restaurant> favRestaurants = userService.getFavRestaurantsByUserId(userId); // ou: reviewService.getReviewsByAuthor(user)
                            List<FavRestaurantsDTO> dtos = favRestaurants.stream()
                                    .map(FavRestaurantsDTO::new)
                                    .toList();
                            response.getWriter().println(gson.toJson(dtos));
                        } else if ("ownerRestaurants".equals(subResource)) {
                            // /users/{id}/ownerRestaurantes → restaurantes criados pelo user
                            List<Restaurant> ownedRestaurants = userService.getRestaurantsByOwner(userId);
                            List<FavRestaurantsDTO> dtos = ownedRestaurants.stream()
                                    .map(FavRestaurantsDTO::new)
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
