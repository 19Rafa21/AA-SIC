/**
 * Licensee: lucas(Universidade do Minho)
 * License Type: Academic
 */
package ormsamples;

import org.orm.*;
public class RetrieveAndUpdateAASICData {
	public void retrieveAndUpdateTestData() throws PersistentException {
		PersistentTransaction t = backend.AASICPersistentManager.instance().getSession().beginTransaction();
		try {
			backend.Restaurant lbackendRestaurant = backend.RestaurantDAO.loadRestaurantByQuery(null, null);
			// Update the properties of the persistent object
			backend.RestaurantDAO.save(lbackendRestaurant);
			backend.Review lbackendReview = backend.ReviewDAO.loadReviewByQuery(null, null);
			// Update the properties of the persistent object
			backend.ReviewDAO.save(lbackendReview);
			backend.Reply lbackendReply = backend.ReplyDAO.loadReplyByQuery(null, null);
			// Update the properties of the persistent object
			backend.ReplyDAO.save(lbackendReply);
			backend.User lbackendUser = backend.UserDAO.loadUserByQuery(null, null);
			// Update the properties of the persistent object
			backend.UserDAO.save(lbackendUser);
			backend.Client lbackendClient = backend.ClientDAO.loadClientByQuery(null, null);
			// Update the properties of the persistent object
			backend.ClientDAO.save(lbackendClient);
			backend.Owner lbackendOwner = backend.OwnerDAO.loadOwnerByQuery(null, null);
			// Update the properties of the persistent object
			backend.OwnerDAO.save(lbackendOwner);
			t.commit();
		}
		catch (Exception e) {
			t.rollback();
		}
		
	}
	
	public void retrieveByCriteria() throws PersistentException {
		System.out.println("Retrieving Restaurant by RestaurantCriteria");
		backend.RestaurantCriteria lbackendRestaurantCriteria = new backend.RestaurantCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//lbackendRestaurantCriteria.id.eq();
		System.out.println(lbackendRestaurantCriteria.uniqueRestaurant());
		
		System.out.println("Retrieving Review by ReviewCriteria");
		backend.ReviewCriteria lbackendReviewCriteria = new backend.ReviewCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//lbackendReviewCriteria.id.eq();
		System.out.println(lbackendReviewCriteria.uniqueReview());
		
		System.out.println("Retrieving Reply by ReplyCriteria");
		backend.ReplyCriteria lbackendReplyCriteria = new backend.ReplyCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//lbackendReplyCriteria.id.eq();
		System.out.println(lbackendReplyCriteria.uniqueReply());
		
		System.out.println("Retrieving User by UserCriteria");
		backend.UserCriteria lbackendUserCriteria = new backend.UserCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//lbackendUserCriteria.id.eq();
		System.out.println(lbackendUserCriteria.uniqueUser());
		
		System.out.println("Retrieving Client by ClientCriteria");
		backend.ClientCriteria lbackendClientCriteria = new backend.ClientCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//lbackendClientCriteria.id.eq();
		System.out.println(lbackendClientCriteria.uniqueClient());
		
		System.out.println("Retrieving Owner by OwnerCriteria");
		backend.OwnerCriteria lbackendOwnerCriteria = new backend.OwnerCriteria();
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
