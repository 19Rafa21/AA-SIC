package backend.Controllers;

import backend.Models.Dish;
import backend.Services.DishService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/dishes")
public class DishController extends HttpServlet {

    private DishService dishService = new DishService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        Dish dish = gson.fromJson(req.getReader(), Dish.class);


        boolean success = dishService.addDish(dish);
        resp.setContentType("application/json; charset=utf-8");
        resp.setStatus(success ? HttpServletResponse.SC_CREATED : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        gson.toJson(success ? "Dish criado com sucesso" : "Erro ao criar dish", resp.getWriter());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();  // e.g. "/restaurant/{restaurantId}" ou "/{dishId}"
        resp.setContentType("application/json; charset=utf-8");
        Gson gson = new Gson();

        if (path == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            gson.toJson("Path em falta", resp.getWriter());
            return;
        }

        String[] parts = path.split("/");
        if (parts.length == 3 && "restaurant".equals(parts[1])) {
            String restaurantId = parts[2];
            List<Dish> dishes = dishService.getDishesByRestaurant(restaurantId);
            gson.toJson(dishes, resp.getWriter());

        } else if (parts.length == 2) {
            String dishId = parts[1];
            Dish dish = dishService.getDishById(dishId);
            if (dish == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                gson.toJson("Dish não encontrado", resp.getWriter());
            } else {
                gson.toJson(dish, resp.getWriter());
            }

        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            gson.toJson("Path inválido", resp.getWriter());
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo(); // ex: "/dish-001"
        resp.setContentType("application/json; charset=utf-8");
        Gson gson = new Gson();

        if (path != null && path.length() > 1) {
            String dishId = path.substring(1); // remove o slash
            boolean success = dishService.deleteDishById(dishId);
            resp.setContentType("application/json; charset=utf-8");
            resp.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            gson.toJson(success ? "Dish apagado com sucesso" : "Erro ao apagar dish", resp.getWriter());
        } else {
            resp.setContentType("application/json; charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            gson.toJson("Parâmetro dishId em falta no path", resp.getWriter());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo(); // "/dish-001"
        resp.setContentType("application/json; charset=utf-8");

        Gson gson = new Gson();

        if (path != null && path.length() > 1) {
            String dishId = path.substring(1);

            Dish payload = gson.fromJson(req.getReader(), Dish.class);

            boolean success = dishService.updateDishPartial(dishId, payload);
            resp.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            gson.toJson(success ? "Dish parcialmente atualizado" : "Erro ao atualizar dish", resp.getWriter());
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            gson.toJson("Path inválido para PUT", resp.getWriter());
        }
    }

}

