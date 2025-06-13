package backend.Controllers;

import backend.DTOs.RestaurantDTO;
import backend.DTOs.RestaurantDetailsDTO;
import backend.Exceptions.UserException;
import backend.Models.Restaurant;
import backend.Services.RestaurantService;
import backend.Services.UserService;
import backend.Utils.HttpRequestUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.orm.PersistentException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class RestaurantController extends HttpServlet {

    private RestaurantService restaurantService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        restaurantService = new RestaurantService();
        this.gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                RestaurantDTO dto = null;
                try {
                    dto = parseRestaurantDTO(req);
                } catch (PersistentException | UserException e) {
                    throw new RuntimeException(e);
                }
                restaurantService.createRestaurant(dto);
                resp.setContentType("application/json");
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
                            json.append("{")
                                    .append("\"id\":").append(r.getId()).append(",")
                                    .append("\"name\":\"").append(r.getName()).append("\",")
                                    .append("\"location\":\"").append(r.getLocation()).append("\",")
                                    .append("\"cuisineType\":\"").append(r.getCuisineType()).append("\",")
                                    .append("\"rating\":").append(r.getRating())
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

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Restaurant> results = restaurantService.searchWithAllFilters("","", "", 0.0);

                StringBuilder json = new StringBuilder();
                json.append("[");

                for (int i = 0; i < results.size(); i++) {
                    Restaurant r = results.get(i);
                    json.append("{")
                            .append("\"id\":").append(r.getId()).append(",")
                            .append("\"name\":\"").append(r.getName()).append("\",")
                            .append("\"location\":\"").append(r.getLocation()).append("\",")
                            .append("\"cuisineType\":\"").append(r.getCuisineType()).append("\",")
                            .append("\"rating\":").append(r.getRating()).append("\",")
                            .append("\"image\":").append(r.getCoverImage())
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

                if (parts.length >= 2) {
                    String id = parts[1];

                    RestaurantDetailsDTO dto = restaurantService.getRestaurantById(id);

                    StringBuilder json = new StringBuilder();
                    json.append("{");
                    json.append("\"id\":\"").append(dto.getId()).append("\",");
                    json.append("\"name\":\"").append(dto.getName()).append("\",");
                    json.append("\"owner\":\"").append(dto.getOwner()).append("\",");
                    json.append("\"location\":\"").append(dto.getLocation()).append("\",");
                    json.append("\"cuisineType\":\"").append(dto.getCuisineType()).append("\",");
                    json.append("\"rating\":").append(dto.getRating()).append(",");
                    json.append("\"image\":\"").append(dto.getImage()).append("\",");

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

                    resp.setContentType("application/json");
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().write(json.toString());

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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RestaurantDetailsDTO dto = null;
        try {
            dto = parseRestaurantDetailsDTO(req);
        } catch (PersistentException | UserException e) {
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Erro ao processar o restaurante\"}");
            return;
        }

        // Verifica se o restaurante existe
        Restaurant existing;
        try {
            existing = restaurantService.getRestaurantByOrmID(dto.getId());
            if (existing == null) {
                resp.setContentType("application/json");
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"message\": \"Restaurante não encontrado\"}");
                return;
            }
        } catch (PersistentException e) {
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"Erro ao aceder à base de dados\"}");
            return;
        }

        // Tenta fazer o update
        if (!restaurantService.updateRestaurant(dto)) {
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"message\": \"Erro ao atualizar o restaurante\"}");
            return;
        }

        // Sucesso
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write("{\"message\": \"Restaurante atualizado com sucesso\"}");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1️⃣ Lê o body como JSON
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String jsonPayload = sb.toString();

        // 2️⃣ Extrai o campo "id" do JSON
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonPayload, JsonObject.class);
        String id = jsonObject.get("id").getAsString();

        // 3️⃣ Remove o restaurante
        restaurantService.removeRestaurant(id);

        // 4️⃣ Prepara resposta
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        resp.getWriter().write("{\"message\": \"Restaurante removido com sucesso\"}");
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

    private RestaurantDetailsDTO parseRestaurantDetailsDTO(HttpServletRequest req) throws IOException, PersistentException, UserException {
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
        RestaurantDetailsDTO dto = gson.fromJson(jsonPayload, RestaurantDetailsDTO.class);

        //Preenche o owner usando o username recebido (assumindo que vem dentro do JSON)
        UserService us = new UserService();
        if (dto.getOwner() != null && dto.getOwner() != null) {
            dto.setOwner(us.getOwnerById(dto.getOwner()));
        }

        return dto;
    }

}
