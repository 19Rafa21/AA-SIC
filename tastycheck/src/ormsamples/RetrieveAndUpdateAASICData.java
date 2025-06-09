/**
 * Licensee: lucas(Universidade do Minho)
 * License Type: Academic
 */
package ormsamples;

import backend.Criteria.*;
import backend.DAOs.*;
import backend.Models.*;
import org.orm.*;
public class RetrieveAndUpdateAASICData {
	public void retrieveAndUpdateTestData() throws PersistentException {
		PersistentTransaction t = backend.AASICPersistentManager.instance().getSession().beginTransaction();
		try {
			Restaurant lbackendRestaurant = RestaurantDAO.loadRestaurantByQuery(null, null);
			// Update the properties of the persistent object
			RestaurantDAO.save(lbackendRestaurant);
			Review lbackendReview = ReviewDAO.loadReviewByQuery(null, null);
			// Update the properties of the persistent object
			ReviewDAO.save(lbackendReview);
			Reply lbackendReply = ReplyDAO.loadReplyByQuery(null, null);
			// Update the properties of the persistent object
			ReplyDAO.save(lbackendReply);
			User lbackendUser = UserDAO.loadUserByQuery(null, null);
			// Update the properties of the persistent object
			UserDAO.save(lbackendUser);
			Client lbackendClient = ClientDAO.loadClientByQuery(null, null);
			// Update the properties of the persistent object
			ClientDAO.save(lbackendClient);
			Owner lbackendOwner = OwnerDAO.loadOwnerByQuery(null, null);
			// Update the properties of the persistent object
			OwnerDAO.save(lbackendOwner);
			t.commit();
		}
		catch (Exception e) {
			t.rollback();
		}
		
	}
	
	public void retrieveByCriteria() throws PersistentException {
		System.out.println("Retrieving Restaurant by RestaurantCriteria");
		RestaurantCriteria lbackendRestaurantCriteria = new RestaurantCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//lbackendRestaurantCriteria.id.eq();
		System.out.println(lbackendRestaurantCriteria.uniqueRestaurant());
		
		System.out.println("Retrieving Review by ReviewCriteria");
		ReviewCriteria lbackendReviewCriteria = new ReviewCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//lbackendReviewCriteria.id.eq();
		System.out.println(lbackendReviewCriteria.uniqueReview());
		
		System.out.println("Retrieving Reply by ReplyCriteria");
		ReplyCriteria lbackendReplyCriteria = new ReplyCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//lbackendReplyCriteria.id.eq();
		System.out.println(lbackendReplyCriteria.uniqueReply());
		
		System.out.println("Retrieving User by UserCriteria");
		UserCriteria lbackendUserCriteria = new UserCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//lbackendUserCriteria.id.eq();
		System.out.println(lbackendUserCriteria.uniqueUser());
		
		System.out.println("Retrieving Client by ClientCriteria");
		ClientCriteria lbackendClientCriteria = new ClientCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//lbackendClientCriteria.id.eq();
		System.out.println(lbackendClientCriteria.uniqueClient());
		
		System.out.println("Retrieving Owner by OwnerCriteria");
		OwnerCriteria lbackendOwnerCriteria = new OwnerCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//lbackendOwnerCriteria.id.eq();
		System.out.println(lbackendOwnerCriteria.uniqueOwner());
		
	}
	
	
	public static void main(String[] args) {
		try {
			RetrieveAndUpdateAASICData retrieveAndUpdateAASICData = new RetrieveAndUpdateAASICData();
			try {
				retrieveAndUpdateAASICData.retrieveAndUpdateTestData();
				//retrieveAndUpdateAASICData.retrieveByCriteria();
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
