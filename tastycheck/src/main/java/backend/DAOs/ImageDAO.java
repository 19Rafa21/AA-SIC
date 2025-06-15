package backend.DAOs;

import backend.AASICPersistentManager;
import backend.Models.Image;
import org.orm.PersistentException;
import org.orm.PersistentSession;

import java.util.List;

public class ImageDAO {

    public static boolean save(Image image) throws PersistentException {
        try {
            AASICPersistentManager.instance().saveObject(image);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new PersistentException(e);
        }
    }

    public static void deleteByRestaurantId(String restaurantId) throws PersistentException {
        PersistentSession session = AASICPersistentManager.instance().getSession();
        session.createQuery("DELETE FROM Image WHERE restaurantId = :id")
                .setParameter("id", restaurantId)
                .executeUpdate();
    }

    public static List<Image> getImagesByRestaurantId(String restaurantId) throws PersistentException {
        PersistentSession session = AASICPersistentManager.instance().getSession();
        List<Image> images = session.createQuery("FROM Image WHERE restaurantId = :id")
                                .setParameter("id", restaurantId)
                                .list();
        return images;
    }

    public static List<String> getImagesByRestaurantAndType(String restaurantId, String type) throws PersistentException {
        try {
            PersistentSession session = AASICPersistentManager.instance().getSession();
            String hql = "SELECT i.url FROM backend.Models.Image i WHERE i.restaurantId = :restaurantId AND i.type = :type";

            List<String> images = session.createQuery(hql)
                .setParameter("restaurantId", restaurantId)
                .setParameter("type", type)
                .list();

            return images;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistentException(e);
        }
    }

    public static List<String> getMenuImages(String restaurantId) throws PersistentException {
        return getImagesByRestaurantAndType(restaurantId, "menu");
    }

    public static List<String> getFoodImages(String restaurantId) throws PersistentException {
        return getImagesByRestaurantAndType(restaurantId, "food");
    }
}

