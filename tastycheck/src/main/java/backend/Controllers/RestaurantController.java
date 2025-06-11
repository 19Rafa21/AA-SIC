package backend.Controllers;

import backend.DTOs.RestaurantDTO;
import backend.Exceptions.UserException;
import backend.Models.Restaurant;
import backend.Services.RestaurantService;
import backend.Services.UserService;
import org.orm.PersistentException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class RestaurantController extends HttpServlet {

    private final RestaurantService restaurantService = new RestaurantService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String name = req.getParameter("name");
        String location = req.getParameter("location");
        String cuisineType = req.getParameter("cuisineType");
        Double rating = Double.valueOf(req.getParameter("raiting"));

        List<Restaurant> results = restaurantService.searchWithAllFilters(name, location, cuisineType, rating);

        StringBuilder json = new StringBuilder();
        json.append("[");

        for (int i = 0; i < results.size(); i++) {
            Restaurant r = results.get(i);
            json.append("{")
                    .append("\"id\":").append(r.getId()).append(",")
                    .append("\"name\":\"").append(r.getName()).append("\",")
                    .append("\"location\":\"").append(r.getLocation()).append("\",")
                    .append("\"cuisineType\":\"").append(r.getCuisineType()).append("\",")
                    .append("\"rating\":").append(r.getRating()).append(",")
                    .append("}");
            if (i < results.size() - 1) json.append(",");
        }

        json.append("]");

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json.toString());
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RestaurantDTO dto = null;
        try {
            dto = parseRestaurantDTO(req);
        } catch (PersistentException | UserException e) {
            throw new RuntimeException(e);
        }

        restaurantService.updateRestaurant(dto);

        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write("{\"message\": \"Restaurante atualizado com sucesso\"}");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        restaurantService.removeRestaurant(id);
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        resp.getWriter().write("{\"message\": \"Restaurante removido com sucesso\"}");
    }

    private RestaurantDTO parseRestaurantDTO(HttpServletRequest req) throws PersistentException, UserException {
        UserService us = new UserService();
        RestaurantDTO dto = new RestaurantDTO();
        dto.setName(req.getParameter("name"));
        dto.setLocation(req.getParameter("location"));
        dto.setCuisineType(req.getParameter("cuisineType"));
        dto.setOwner(us.getOwnerByUsername(req.getParameter("owner")));
        return dto;
    }
}
