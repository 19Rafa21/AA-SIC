/**
 * Licensee: lucas(Universidade do Minho)
 * License Type: Academic
 */
package ormsamples;

import backend.DAOs.*;
import backend.Models.*;
import org.orm.*;
public class DeleteAASICData {
	public void deleteTestData() throws PersistentException {
		PersistentTransaction t = backend.AASICPersistentManager.instance().getSession().beginTransaction();
		try {
			Restaurant lbackendRestaurant = RestaurantDAO.loadRestaurantByQuery(null, null);
			// Delete the persistent object
			RestaurantDAO.delete(lbackendRestaurant);
			Review lbackendReview = ReviewDAO.loadReviewByQuery(null, null);
			// Delete the persistent object
			ReviewDAO.delete(lbackendReview);
			Reply lbackendReply = ReplyDAO.loadReplyByQuery(null, null);
			// Delete the persistent object
			ReplyDAO.delete(lbackendReply);
			User lbackendUser = UserDAO.loadUserByQuery(null, null);
			// Delete the persistent object
			UserDAO.delete(lbackendUser);
			Client lbackendClient = ClientDAO.loadClientByQuery(null, null);
			// Delete the persistent object
			ClientDAO.delete(lbackendClient);
			Owner lbackendOwner = OwnerDAO.loadOwnerByQuery(null, null);
			// Delete the persistent object
			OwnerDAO.delete(lbackendOwner);
			t.commit();
		}
		catch (Exception e) {
			t.rollback();
		}
		
	}
	
	public static void main(String[] args) {
		try {
			DeleteAASICData deleteAASICData = new DeleteAASICData();
			try {
				deleteAASICData.deleteTestData();
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
