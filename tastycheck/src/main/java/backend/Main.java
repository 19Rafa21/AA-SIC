package backend;

import backend.DAOs.*;
import backend.Exceptions.UserException;
import backend.Models.*;
import backend.Services.*;
import org.orm.PersistentException;

import java.util.Date;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		try{

			UserService userService = new UserService();
			ReviewService reviewService = new ReviewService();
			//RestaurantService restaurantService = new RestaurantService();

			User user = userService.getUserById("u123");
			Restaurant restaurant = RestaurantDAO.getRestaurantByORMID("r1");
			/*Review review = new Review();
			review.setId("rv1");
			review.setRating(4.1);
			review.setText("Bom Restaurante!");
			review.setData(new Date());
			 */

			Review review = reviewService.getReviewById("rv1");
			reviewService.deleteReview(review);

			System.out.println("Review do User '" + user.getUsername() + "': ");
			for (Review r : userService.getReviewsByUserId(user.getId())) {
				System.out.println(" - " + r.getText() + " (rating: " + r.getRating() + ")");
			}

			System.out.println();

			System.out.println("Review do Restaurant '" + restaurant.getName() + "': ");
			for (Review r : restaurant.getReviews()) {
				System.out.println(" - " + r.getText() + " (rating: " + r.getRating() + ")");
			}

			AASICPersistentManager.instance().disposePersistentManager();
		} catch (PersistentException e) {
			e.printStackTrace();
		} catch (UserException e) {
			throw new RuntimeException(e);
		}
	}
}

