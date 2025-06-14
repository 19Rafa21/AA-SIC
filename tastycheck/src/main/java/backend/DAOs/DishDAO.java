package backend.DAOs;

import java.util.List;

import backend.AASICPersistentManager;
import backend.Models.Dish;
import org.orm.PersistentException;
import org.orm.PersistentSession;

public class DishDAO {

    public static boolean save(Dish dish) throws PersistentException {
        try {
            AASICPersistentManager.instance().saveObject(dish);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistentException(e);
        }
    }

    public static void deleteByRestaurantId(String restaurantId) throws PersistentException {
        PersistentSession session = AASICPersistentManager.instance().getSession();
        session.createQuery("DELETE FROM Dish WHERE restaurantId = :id")
                .setParameter("id", restaurantId)
                .executeUpdate();
    }

    public static List<Dish> getByRestaurantId(String restaurantId) throws PersistentException {
        PersistentSession session = AASICPersistentManager.instance().getSession();
        List<Dish> dishes = session.createQuery("FROM Dish WHERE restaurantId = :id")
                .setParameter("id", restaurantId)
                .list();
        return dishes;
    }

    public static Dish getById(String dishId) throws PersistentException {
        PersistentSession session = AASICPersistentManager.instance().getSession();
        return (Dish) session.createQuery("FROM Dish WHERE id = :id")
                .setParameter("id", dishId)
                .uniqueResult();
    }

    public static boolean deleteById(String dishId) throws PersistentException {
        PersistentSession session = AASICPersistentManager.instance().getSession();
        int affected = session.createQuery("DELETE FROM Dish WHERE id = :id")
                .setParameter("id", dishId)
                .executeUpdate();
        return affected > 0;
    }

}
