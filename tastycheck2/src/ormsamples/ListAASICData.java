/**
 * Licensee: lucas(Universidade do Minho)
 * License Type: Academic
 */
package ormsamples;

import org.orm.*;
public class ListAASICData {
	private static final int ROW_COUNT = 100;
	
	public void listTestData() throws PersistentException {
		System.out.println("Listing Restaurant...");
		backend.Restaurant[] backendRestaurants = backend.RestaurantDAO.listRestaurantByQuery(null, null);
		int length = Math.min(backendRestaurants.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(backendRestaurants[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing Review...");
		backend.Review[] backendReviews = backend.ReviewDAO.listReviewByQuery(null, null);
		length = Math.min(backendReviews.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(backendReviews[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing Reply...");
		backend.Reply[] backendReplys = backend.ReplyDAO.listReplyByQuery(null, null);
		length = Math.min(backendReplys.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(backendReplys[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing User...");
		backend.User[] backendUsers = backend.UserDAO.listUserByQuery(null, null);
		length = Math.min(backendUsers.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(backendUsers[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing Client...");
		backend.Client[] backendClients = backend.ClientDAO.listClientByQuery(null, null);
		length = Math.min(backendClients.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(backendClients[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing Owner...");
		backend.Owner[] backendOwners = backend.OwnerDAO.listOwnerByQuery(null, null);
		length = Math.min(backendOwners.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(backendOwners[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
	}
	
	public void listByCriteria() throws PersistentException {
		System.out.println("Listing Restaurant by Criteria...");
		backend.RestaurantCriteria lbackendRestaurantCriteria = new backend.RestaurantCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//lbackendRestaurantCriteria.id.eq();
		lbackendRestaurantCriteria.setMaxResults(ROW_COUNT);
		backend.Restaurant[] backendRestaurants = lbackendRestaurantCriteria.listRestaurant();
		int length =backendRestaurants== null ? 0 : Math.min(backendRestaurants.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(backendRestaurants[i]);
		}
		System.out.println(length + " Restaurant record(s) retrieved."); 
		
		System.out.println("Listing Review by Criteria...");
		backend.ReviewCriteria lbackendReviewCriteria = new backend.ReviewCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//lbackendReviewCriteria.id.eq();
		lbackendReviewCriteria.setMaxResults(ROW_COUNT);
		backend.Review[] backendReviews = lbackendReviewCriteria.listReview();
		length =backendReviews== null ? 0 : Math.min(backendReviews.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(backendReviews[i]);
		}
		System.out.println(length + " Review record(s) retrieved."); 
		
		System.out.println("Listing Reply by Criteria...");
		backend.ReplyCriteria lbackendReplyCriteria = new backend.ReplyCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//lbackendReplyCriteria.id.eq();
		lbackendReplyCriteria.setMaxResults(ROW_COUNT);
		backend.Reply[] backendReplys = lbackendReplyCriteria.listReply();
		length =backendReplys== null ? 0 : Math.min(backendReplys.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(backendReplys[i]);
		}
		System.out.println(length + " Reply record(s) retrieved."); 
		
		System.out.println("Listing User by Criteria...");
		backend.UserCriteria lbackendUserCriteria = new backend.UserCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//lbackendUserCriteria.id.eq();
		lbackendUserCriteria.setMaxResults(ROW_COUNT);
		backend.User[] backendUsers = lbackendUserCriteria.listUser();
		length =backendUsers== null ? 0 : Math.min(backendUsers.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(backendUsers[i]);
		}
		System.out.println(length + " User record(s) retrieved."); 
		
		System.out.println("Listing Client by Criteria...");
		backend.ClientCriteria lbackendClientCriteria = new backend.ClientCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//lbackendClientCriteria.id.eq();
		lbackendClientCriteria.setMaxResults(ROW_COUNT);
		backend.Client[] backendClients = lbackendClientCriteria.listClient();
		length =backendClients== null ? 0 : Math.min(backendClients.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(backendClients[i]);
		}
		System.out.println(length + " Client record(s) retrieved."); 
		
		System.out.println("Listing Owner by Criteria...");
		backend.OwnerCriteria lbackendOwnerCriteria = new backend.OwnerCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//lbackendOwnerCriteria.id.eq();
		lbackendOwnerCriteria.setMaxResults(ROW_COUNT);
		backend.Owner[] backendOwners = lbackendOwnerCriteria.listOwner();
		length =backendOwners== null ? 0 : Math.min(backendOwners.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(backendOwners[i]);
		}
		System.out.println(length + " Owner record(s) retrieved."); 
		
	}
	
	public static void main(String[] args) {
		try {
			ListAASICData listAASICData = new ListAASICData();
			try {
				listAASICData.listTestData();
				//listAASICData.listByCriteria();
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
