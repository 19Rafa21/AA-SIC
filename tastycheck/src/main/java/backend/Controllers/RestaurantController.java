package backend.Controllers;

import backend.DTOs.RestaurantDTO;
import backend.DTOs.RestaurantDetailsDTO;
import backend.DTOs.Review.ReviewDTO;
import backend.Exceptions.UnauthorizedException;
import backend.Exceptions.UserException;
import backend.Models.Restaurant;
import backend.Models.Review;
import backend.Services.ImageService;
import backend.Services.RestaurantService;
import backend.Services.UserService;
import backend.Utils.HttpRequestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@MultipartConfig
public class RestaurantController extends HttpServlet {

    private RestaurantService restaurantService;
    private ImageService imageService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        try {
            restaurantService = new RestaurantService();
            imageService = new ImageService();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        try {
            if (pathInfo == null || pathInfo.equals("/")) {

                String restaurantJson = req.getParameter("restaurant");
                System.out.println("JSON recebido: " + restaurantJson);
                RestaurantDetailsDTO restaurantDTO = new ObjectMapper().readValue(restaurantJson, RestaurantDetailsDTO.class);

                Part coverRestaurantImage = req.getPart("coverImage");
                if (coverRestaurantImage != null && coverRestaurantImage.getSize() > 0){
                    String originalFileName = Paths.get(coverRestaurantImage.getSubmittedFileName()).getFileName().toString();
                    InputStream coverContent = coverRestaurantImage.getInputStream();

                    String coverContentType = coverRestaurantImage.getContentType();
                    if (!coverContentType.startsWith("image/")){
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        resp.getWriter().write("{\"message\": Ficheiro de capa não é uma imagem \"}");
                    }

                    if (coverContent != null){
                        try {
                            String uploadedImage = imageService.uploadImage(originalFileName, coverContent);
                            restaurantDTO.setImage(uploadedImage);
                        } catch (IOException e){
                            throw new UnauthorizedException("Error while adding restaurant cover image: " + e.getMessage());
                        }
                    }

                    //restaurantService.addCoverImage(restaurantDTO, originalFileName, coverContent);
                }

                Collection<Part> menuPart = req.getParts().stream()
                        .filter(p -> p.getName().equals("menuImages"))
                        .toList();

                List<String> uploadedMenuImages = new ArrayList<>();
                for (Part part : menuPart) {
                    if (part.getSize() > 0 && part.getContentType().startsWith("image/")) {
                        String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                        InputStream contentStream = part.getInputStream();
                        if (contentStream != null){
                            try {
                                String uploadedPath = imageService.uploadImage(fileName, contentStream);
                                uploadedMenuImages.add(uploadedPath);
                            } catch (IOException e) {
                                throw new UnauthorizedException("Error while adding restaurant menu image: " + e.getMessage());
                            }
                        }
                    }
                }

                restaurantDTO.setMenuImages(uploadedMenuImages);

                Collection<Part> foodPart = req.getParts().stream()
                        .filter(p -> p.getName().equals("foodImages"))
                        .toList();

                List<String> uploadedFoodImages = new ArrayList<>();
                for (Part part : foodPart){
                    if (part.getSize() > 0 && part.getContentType().startsWith("image/")) {
                        String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                        InputStream contentStream = part.getInputStream();
                        if (contentStream != null){
                            try {
                                String uploadedPath = imageService.uploadImage(fileName, contentStream);
                                uploadedFoodImages.add(uploadedPath);
                            } catch (IOException e) {
                                throw new UnauthorizedException("Error while adding restaurant menu image: " + e.getMessage());
                            }
                        }
                    }
                }

                restaurantDTO.setFoodImages(uploadedFoodImages);

                //RestaurantDetailsDTO dto = null;
                //dto = parseRestaurantDetailsDTO(req);
                restaurantService.createRestaurant(restaurantDTO);
                resp.setContentType("application/json; charset=UTF-8");
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("{\"message\": \"Restaurante criado com sucesso\"}");
            } else {
                String[] parts = pathInfo.split("/");
                if (parts.length >= 2) {
                    String action = parts[1];

                    if ("filters".equals(action)) {
                        JsonObject jsonObject = gson.fromJson(HttpRequestUtils.readBodyJson(req), JsonObject.class);

                        // Extrai os campos
                        String name = jsonObject.get("name").getAsString();

                        String location = jsonObject.get("location").getAsString();

                        String cuisineType = jsonObject.get("cuisineType").getAsString();

                        double rating = jsonObject.get("rating").getAsDouble(); // atenção ao nome correto

                        List<Restaurant> results = restaurantService.searchWithAllFilters(name,cuisineType, location, rating);

                        //Constrói JSON manualmente (podes substituir por Gson se preferires)
                        StringBuilder json = new StringBuilder();
                        json.append("[");

                        for (int i = 0; i < results.size(); i++) {
                            Restaurant r = results.get(i);
                            System.out.println(r);
                            json.append("{")
                                    .append("\"id\":\"").append(r.getId()).append("\",")
                                    .append("\"name\":\"").append(r.getName()).append("\",")
                                    .append("\"location\":\"").append(r.getLocation()).append("\",")
                                    .append("\"cuisineType\":\"").append(r.getCuisineType()).append("\",")
                                    .append("\"rating\":").append(r.getRating()).append(",")
                                    .append("\"image\":\"").append(r.getCoverImage()).append("\"")
                                    .append("}");

                            if (i < results.size() - 1) json.append(",");
                        }

                        json.append("]");

                        //Responde com os resultados
                        resp.setStatus(HttpServletResponse.SC_OK);
                        resp.getWriter().write(json.toString());
                    } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Sub-recurso não encontrado.");
                    }
                } else {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de URL inválido.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno: " + e.getMessage());
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Restaurant> results = restaurantService.searchWithAllFilters("","", "", 0.0);

                StringBuilder json = new StringBuilder();
                json.append("[");

                for (int i = 0; i < results.size(); i++) {
                    Restaurant r = results.get(i);
                    json.append("{")
                            .append("\"id\":\"").append(r.getId()).append("\",")
                            .append("\"name\":\"").append(r.getName()).append("\",")
                            .append("\"location\":\"").append(r.getLocation()).append("\",")
                            .append("\"cuisineType\":\"").append(r.getCuisineType()).append("\",")
                            .append("\"rating\":").append(r.getRating()).append(",")
                            .append("\"image\":").append(r.getCoverImage() != null ? "\"" + r.getCoverImage() + "\"" : "null")
                            .append("}");
                    if (i < results.size() - 1) json.append(",");
                }


                json.append("]");

                //Responde com os resultados
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(json.toString());

            } else {
                // GET /restaurants/rest_001
                String[] parts = pathInfo.split("/");

                if (parts.length == 4 && "user".equals(parts[1]) && "ownedRestaurants".equals(parts[3])) {
                    // código que devolve os restaurantes do utilizador
                    return;
                }

                if (parts.length >= 2) {
                    String id = parts[1];

                    Restaurant restaurant = restaurantService.getRestaurantByOrmID(id);
                    if (restaurant == null) {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Restaurante não encontrado.");
                        return;
                    }

                    if (parts.length == 2) {
                        RestaurantDetailsDTO dto = restaurantService.getRestaurantById(id);

                        StringBuilder json = new StringBuilder();
                        json.append("{");
                        json.append("\"id\":\"").append(dto.getId()).append("\",");
                        json.append("\"name\":\"").append(dto.getName()).append("\",");
                        json.append("\"owner\":\"").append(dto.getOwner()).append("\",");
                        json.append("\"location\":\"").append(dto.getLocation()).append("\",");
                        json.append("\"cuisineType\":\"").append(dto.getCuisineType()).append("\",");
                        json.append("\"rating\":").append(dto.getRating()).append(",");
                        json.append("\"image\":").append(dto.getImage() != null ? "\"" + dto.getImage() + "\"" : "null").append(",");

                        // menuImages
                        json.append("\"menuImages\":[");
                        List<String> menuImages = dto.getMenuImages();
                        for (int i = 0; i < menuImages.size(); i++) {
                            json.append("\"").append(menuImages.get(i)).append("\"");
                            if (i < menuImages.size() - 1) json.append(",");
                        }
                        json.append("],");

                        // foodImages
                        json.append("\"foodImages\":[");
                        List<String> foodImages = dto.getFoodImages();
                        for (int i = 0; i < foodImages.size(); i++) {
                            json.append("\"").append(foodImages.get(i)).append("\"");
                            if (i < foodImages.size() - 1) json.append(",");
                        }
                        json.append("]");

                        json.append("}");

                        resp.setStatus(HttpServletResponse.SC_OK);
                        resp.getWriter().write(json.toString());
                    } else if (parts.length == 3) {
                        String subResource = parts[2];

                        if ("reviews".equals(subResource)) {
                            List<Review> reviewsRestaurant = restaurantService.getAllRestaurantReviews(restaurant);
                            List<ReviewDTO> dtos = reviewsRestaurant.stream()
                                    .map(ReviewDTO::new)
                                    .toList();
                            resp.getWriter().println(gson.toJson(dtos));
                        } else {
                            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Sub-recurso não encontrado.");
                        }
                    }



                } else {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de restaurante em falta.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro no servidor: " + e.getMessage());
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"ID do restaurante em falta na URL\"}");
            return;
        }
        String restaurantId = pathInfo.substring(1);

        Restaurant existing;
        try {
            existing = restaurantService.getRestaurantByOrmID(restaurantId);
            if (existing == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"message\": \"Restaurante não encontrado\"}");
                return;
            }
        } catch (PersistentException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"Erro ao aceder à base de dados\"}");
            return;
        }

        try {
            // 1. Parse do JSON
            String restaurantJson = req.getParameter("restaurant");
            RestaurantDetailsDTO restaurantDTO = new ObjectMapper().readValue(restaurantJson, RestaurantDetailsDTO.class);

            // 2. Apagar imagens antigas
            restaurantService.deleteAllImagesByRestaurant(restaurantId);
            System.out.println("Imagens antigas apagadas com sucesso.");

            // 3. Upload da imagem de capa
            Part coverImage = req.getPart("coverImage");
            if (coverImage != null && coverImage.getSize() > 0) {
                String fileName = Paths.get(coverImage.getSubmittedFileName()).getFileName().toString();
                String uploadedUrl = imageService.uploadImage(fileName, coverImage.getInputStream());
                restaurantDTO.setImage(uploadedUrl);
                System.out.println("Imagem de capa carregada: " + uploadedUrl);
            }

            // 4. Menu Images
            List<String> uploadedMenuImages = new ArrayList<>();
            for (Part part : req.getParts()) {
                if ("menuImages".equals(part.getName()) && part.getSize() > 0 && part.getContentType().startsWith("image/")) {
                    String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                    String uploadedUrl = imageService.uploadImage(fileName, part.getInputStream());
                    uploadedMenuImages.add(uploadedUrl);
                    System.out.println("Imagem de menu carregada: " + uploadedUrl);
                }
            }
            restaurantDTO.setMenuImages(uploadedMenuImages);

            // 5. Food Images
            List<String> uploadedFoodImages = new ArrayList<>();
            for (Part part : req.getParts()) {
                if ("foodImages".equals(part.getName()) && part.getSize() > 0 && part.getContentType().startsWith("image/")) {
                    String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                    String uploadedUrl = imageService.uploadImage(fileName, part.getInputStream());
                    uploadedFoodImages.add(uploadedUrl);
                    System.out.println("Imagem de comida carregada: " + uploadedUrl);
                }
            }
            restaurantDTO.setFoodImages(uploadedFoodImages);

            // 6. Atualizar restaurante (inclui guardar novas imagens na BD)
            boolean success = restaurantService.updateRestaurant(restaurantId, restaurantDTO);
            if (!success) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"message\": \"Erro ao atualizar o restaurante\"}");
                return;
            }

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"message\": \"Restaurante atualizado com sucesso\"}");

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Erro ao processar o update\"}");
        }
    }



    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo(); // Ex: /rest_001

        if (pathInfo == null || pathInfo.equals("/") || pathInfo.split("/").length < 2) {
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"ID do restaurante em falta na URL.\"}");
            return;
        }

        String id = pathInfo.split("/")[1]; // Ex: "rest_001"

        boolean sucesso = restaurantService.removeRestaurant(id);

        resp.setContentType("application/json; charset=UTF-8");
        if (sucesso) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"message\": \"Restaurante removido com sucesso\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\": \"Restaurante não encontrado ou erro ao remover.\"}");
        }
    }


    private RestaurantDTO parseRestaurantDTO(HttpServletRequest req) throws IOException, PersistentException, UserException {
        //Lê o body do POST → jsonPayload
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String jsonPayload = sb.toString();

        //Converte o JSON → DTO
        Gson gson = new Gson();
        RestaurantDTO dto = gson.fromJson(jsonPayload, RestaurantDTO.class);

        //Preenche o owner usando o username recebido (assumindo que vem dentro do JSON)
        UserService us = new UserService();
        if (dto.getOwner() != null && dto.getOwner().getUsername() != null) {
            dto.setOwner(us.getOwnerByUsername(dto.getOwner().getUsername()));
        }

        return dto;
    }

    public RestaurantDetailsDTO parseRestaurantDetailsDTO(HttpServletRequest req) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(req.getInputStream(), RestaurantDetailsDTO.class);
    }


}
