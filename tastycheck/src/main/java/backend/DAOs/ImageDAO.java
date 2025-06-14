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

    public static List<Image> getByRestaurantId(String restaurantId) throws PersistentException {
        PersistentSession session = AASICPersistentManager.instance().getSession();
        List<Image> images = session.createQuery("FROM Image WHERE restaurantId = :id")
                                .setParameter("id", restaurantId)
                                .list();
        return images;
    }
}

