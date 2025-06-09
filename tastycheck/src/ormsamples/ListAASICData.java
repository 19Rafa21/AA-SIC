/**
 * Licensee: lucas(Universidade do Minho)
 * License Type: Academic
 */
package ormsamples;

import backend.Criteria.*;
import backend.DAOs.*;
import backend.Models.*;
import org.orm.*;
public class ListAASICData {
	private static final int ROW_COUNT = 100;
	
	public void listTestData() throws PersistentException {
		System.out.println("Listing Restaurant...");
		Restaurant[] backendRestaurants = RestaurantDAO.listRestaurantByQuery(null, null);
		int length = Math.min(backendRestaurants.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(backendRestaurants[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing Review...");
		Review[] backendReviews = ReviewDAO.listReviewByQuery(null, null);
		length = Math.min(backendReviews.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(backendReviews[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing Reply...");
		Reply[] backendReplys = ReplyDAO.listReplyByQuery(null, null);
		length = Math.min(backendReplys.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(backendReplys[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing User...");
		User[] backendUsers = UserDAO.listUserByQuery(null, null);
		length = Math.min(backendUsers.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(backendUsers[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing Client...");
		Client[] backendClients = ClientDAO.listClientByQuery(null, null);
		length = Math.min(backendClients.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(backendClients[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing Owner...");
		Owner[] backendOwners = OwnerDAO.listOwnerByQuery(null, null);
		length = Math.min(backendOwners.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(backendOwners[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
	}
	
	public void listByCriteria() throws PersistentException {
		System.out.println("Listing Restaurant by Criteria...");
		RestaurantCriteria lbackendRestaurantCriteria = new RestaurantCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//lbackendRestaurantCriteria.id.eq();
		lbackendRestaurantCriteria.setMaxResults(ROW_COUNT);
		Restaurant[] backendRestaurants = lbackendRestaurantCriteria.listRestaurant();
		int length =backendRestaurants== null ? 0 : Math.min(backendRestaurants.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(backendRestaurants[i]);
		}
		System.out.println(length + " Restaurant record(s) retrieved."); 
		
		System.out.println("Listing Review by Criteria...");
		ReviewCriteria lbackendReviewCriteria = new ReviewCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//lbackendReviewCriteria.id.eq();
		lbackendReviewCriteria.setMaxResults(ROW_COUNT);
		Review[] backendReviews = lbackendReviewCriteria.listReview();
		length =backendReviews== null ? 0 : Math.min(backendReviews.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(backendReviews[i]);
		}
		System.out.println(length + " Review record(s) retrieved."); 
		
		System.out.println("Listing Reply by Criteria...");
		ReplyCriteria lbackendReplyCriteria = new ReplyCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//lbackendReplyCriteria.id.eq();
		lbackendReplyCriteria.setMaxResults(ROW_COUNT);
		Reply[] backendReplys = lbackendReplyCriteria.listReply();
		length =backendReplys== null ? 0 : Math.min(backendReplys.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(backendReplys[i]);
		}
		System.out.println(length + " Reply record(s) retrieved."); 
		
		System.out.println("Listing User by Criteria...");
		UserCriteria lbackendUserCriteria = new UserCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//lbackendUserCriteria.id.eq();
		lbackendUserCriteria.setMaxResults(ROW_COUNT);
		User[] backendUsers = lbackendUserCriteria.listUser();
		length =backendUsers== null ? 0 : Math.min(backendUsers.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(backendUsers[i]);
		}
		System.out.println(length + " User record(s) retrieved."); 
		
		System.out.println("Listing Client by Criteria...");
		ClientCriteria lbackendClientCriteria = new ClientCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//lbackendClientCriteria.id.eq();
		lbackendClientCriteria.setMaxResults(ROW_COUNT);
		Client[] backendClients = lbackendClientCriteria.listClient();
		length =backendClients== null ? 0 : Math.min(backendClients.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(backendClients[i]);
		}
		System.out.println(length + " Client record(s) retrieved."); 
		
		System.out.println("Listing Owner by Criteria...");
		OwnerCriteria lbackendOwnerCriteria = new OwnerCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//lbackendOwnerCriteria.id.eq();
		lbackendOwnerCriteria.setMaxResults(ROW_COUNT);
		Owner[] backendOwners = lbackendOwnerCriteria.listOwner();
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
