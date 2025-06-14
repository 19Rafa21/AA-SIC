package backend.Services;

import java.util.List;
import java.util.UUID;

import backend.DAOs.DishDAO;
import backend.Models.Dish;
import org.orm.PersistentException;

public class DishService {

    public boolean addDish(Dish dish) {
        try {
            if (dish.getId() == null || dish.getId().isEmpty()) {
                dish.setId(UUID.randomUUID().toString());
            }
            return DishDAO.save(dish);
        } catch (PersistentException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Dish> getDishesByRestaurant(String restaurantId) {
        try {
            return DishDAO.getByRestaurantId(restaurantId);
        } catch (PersistentException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteDishesByRestaurant(String restaurantId) {
        try {
            DishDAO.deleteByRestaurantId(restaurantId);
            return true;
        } catch (PersistentException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Dish getDishById(String dishId) {
        try {
            return DishDAO.getById(dishId);
        } catch (PersistentException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateDish(Dish dish) {
        try {
            return DishDAO.save(dish);  // save() faz insert ou update
        } catch (PersistentException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDishPartial(String dishId, Dish payload) {
        try {
            // 1. Obtém o prato atual
            Dish existing = DishDAO.getById(dishId);
            if (existing == null) return false;

            // 2. Atualiza apenas campos não-nulos no payload
            if (payload.getName() != null) existing.setName(payload.getName());
            if (payload.getDescription() != null) existing.setDescription(payload.getDescription());
            if (payload.getPrice() != 0) existing.setPrice(payload.getPrice());
            if (payload.getImage() != null) existing.setImage(payload.getImage());
            if (payload.getRestaurantId() != null) existing.setRestaurantId(payload.getRestaurantId());

            // 3. Persiste via DAO
            return DishDAO.save(existing);
        } catch (PersistentException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteDishById(String dishId) {
        if (dishId == null || dishId.isEmpty()) {
            // Não faz sentido tentar apagar sem ID
            return false;
        }
        try {
            return DishDAO.deleteById(dishId);
        } catch (PersistentException e) {
            e.printStackTrace();
            return false;
        }
    }
}

