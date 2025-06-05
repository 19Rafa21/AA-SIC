/**
 * Licensee: lucas(Universidade do Minho)
 * License Type: Academic
 */
package ormsamples;

import org.orm.*;
public class DeleteAASICData {
	public void deleteTestData() throws PersistentException {
		PersistentTransaction t = backend.AASICPersistentManager.instance().getSession().beginTransaction();
		try {
			backend.Restaurant lbackendRestaurant = backend.RestaurantDAO.loadRestaurantByQuery(null, null);
			// Delete the persistent object
			backend.RestaurantDAO.delete(lbackendRestaurant);
			backend.Review lbackendReview = backend.ReviewDAO.loadReviewByQuery(null, null);
			// Delete the persistent object
			backend.ReviewDAO.delete(lbackendReview);
			backend.Reply lbackendReply = backend.ReplyDAO.loadReplyByQuery(null, null);
			// Delete the persistent object
			backend.ReplyDAO.delete(lbackendReply);
			backend.User lbackendUser = backend.UserDAO.loadUserByQuery(null, null);
			// Delete the persistent object
			backend.UserDAO.delete(lbackendUser);
			backend.Client lbackendClient = backend.ClientDAO.loadClientByQuery(null, null);
			// Delete the persistent object
			backend.ClientDAO.delete(lbackendClient);
			backend.Owner lbackendOwner = backend.OwnerDAO.loadOwnerByQuery(null, null);
			// Delete the persistent object
			backend.OwnerDAO.delete(lbackendOwner);
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
