/**
 * Licensee: lucas(Universidade do Minho)
 * License Type: Academic
 */
package ormsamples;

import backend.DAOs.*;
import backend.Models.*;
import org.orm.*;
public class CreateAASICData {
	public void createTestData() throws PersistentException {
		PersistentTransaction t = backend.AASICPersistentManager.instance().getSession().beginTransaction();
		try {
			Restaurant lbackendRestaurant = RestaurantDAO.createRestaurant();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : id
			RestaurantDAO.save(lbackendRestaurant);
			Review lbackendReview = ReviewDAO.createReview();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : id
			ReviewDAO.save(lbackendReview);
			Reply lbackendReply = ReplyDAO.createReply();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : id
			ReplyDAO.save(lbackendReply);
			User lbackendUser = UserDAO.createUser();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : id
			UserDAO.save(lbackendUser);
			Client lbackendClient = ClientDAO.createClient();
			// Initialize the properties of the persistent object here
			ClientDAO.save(lbackendClient);
			Owner lbackendOwner = OwnerDAO.createOwner();
			// Initialize the properties of the persistent object here
			OwnerDAO.save(lbackendOwner);
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
