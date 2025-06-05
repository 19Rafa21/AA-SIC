/**
 * Licensee: lucas(Universidade do Minho)
 * License Type: Academic
 */
package ormsamples;

import org.orm.*;
public class CreateAASICData {
	public void createTestData() throws PersistentException {
		PersistentTransaction t = backend.AASICPersistentManager.instance().getSession().beginTransaction();
		try {
			backend.Restaurant lbackendRestaurant = backend.RestaurantDAO.createRestaurant();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : id
			backend.RestaurantDAO.save(lbackendRestaurant);
			backend.Review lbackendReview = backend.ReviewDAO.createReview();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : id
			backend.ReviewDAO.save(lbackendReview);
			backend.Reply lbackendReply = backend.ReplyDAO.createReply();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : id
			backend.ReplyDAO.save(lbackendReply);
			backend.User lbackendUser = backend.UserDAO.createUser();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : id
			backend.UserDAO.save(lbackendUser);
			backend.Client lbackendClient = backend.ClientDAO.createClient();
			// Initialize the properties of the persistent object here
			backend.ClientDAO.save(lbackendClient);
			backend.Owner lbackendOwner = backend.OwnerDAO.createOwner();
			// Initialize the properties of the persistent object here
			backend.OwnerDAO.save(lbackendOwner);
			t.commit();
		}
		catch (Exception e) {
			t.rollback();
		}
		
	}
	
	public static void main(String[] args) {
		try {
			CreateAASICData createAASICData = new CreateAASICData();
			try {
				createAASICData.createTestData();
			}
			finally {
				backend.AASICPersistentManager.instance().disposePersistentManager();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
