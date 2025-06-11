package backend.Services;

import backend.DAOs.RestaurantDAO;
import backend.DAOs.ReviewDAO;
import backend.DAOs.UserDAO;
import backend.Exceptions.UserException;
import backend.Models.Restaurant;
import backend.Models.Review;
import backend.Models.User;
import org.orm.PersistentException;

import java.util.ArrayList;
import java.util.List;

public class ReviewService {

	public boolean registerReview(User user, Restaurant restaurant, Review review) throws PersistentException {
		try {
			Review existingReview = ReviewDAO.getReviewByORMID(review.getId());
			if (existingReview != null) {
				throw new IllegalArgumentException("Review com ID: '" + review.getId() + "' já existe!");
			}

			review.setAuthor(user);
			review.setRestaurant(restaurant);

			user.getReviews().add(review);
			restaurant.getReviews().add(review);

			ReviewDAO.save(review);
			UserDAO.save(user);
			RestaurantDAO.save(restaurant);

			System.out.println("Review registado com sucesso!");

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}

	public boolean registerReview(String userId, String restaurantId, Review review) throws PersistentException, UserException {
		try {
			Review existingReview = ReviewDAO.getReviewByORMID(review.getId());
			if (existingReview != null) {
				throw new IllegalArgumentException("Review com ID: '" + review.getId() + "' já existe!");
			}

			UserService userService = new UserService();
			User user = userService.getUserById(userId);
			if (user == null) throw new UserException("User com ID '" + userId + "' não existe.");

			Restaurant restaurant = RestaurantDAO.getRestaurantByORMID(restaurantId);
			if (restaurant == null) throw new PersistentException("Restaurant com ID '" + restaurantId + "' não existe.");

			review.setAuthor(user);
			review.setRestaurant(restaurant);

			user.getReviews().add(review);
			restaurant.getReviews().add(review);

			ReviewDAO.save(review);
			UserDAO.save(user);
			RestaurantDAO.save(restaurant);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}

	public boolean updateReview(Review review) throws  PersistentException {
		return  ReviewDAO.save(review);
	}

	public Review getReviewById(String id) throws PersistentException {
		Review review = ReviewDAO.getReviewByORMID(id);
		if (review == null) {
			throw new IllegalArgumentException("Review com ID '" + id + "' não existe.");
		}
		return review;
	}

	public boolean deleteReview(String reviewId) throws PersistentException {
		try {
			Review review = ReviewDAO.getReviewByORMID(reviewId);
			if (review == null) {
				throw new IllegalArgumentException("Review não existe.");
			}

			User author = review.getAuthor();
			Restaurant restaurant = review.getRestaurant();

			if (author != null) {
				author.getReviews().remove(review);
				UserDAO.save(author); // atualiza a lista de reviews
			}

			if (restaurant != null) {
				restaurant.getReviews().remove(review);
				RestaurantDAO.save(restaurant); // atualiza a lista de reviews
			}

			ReviewDAO.delete(review);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}

	public boolean deleteReview(Review review) throws PersistentException {
		try {
			if (review == null) {
				throw new IllegalArgumentException("Review não existe.");
			}

			User author = review.getAuthor();
			Restaurant restaurant = review.getRestaurant();

			if (author != null) {
				author.getReviews().remove(review);
				UserDAO.save(author); // atualiza a lista de reviews
			}

			if (restaurant != null) {
				restaurant.getReviews().remove(review);
				RestaurantDAO.save(restaurant); // atualiza a lista de reviews
			}

			ReviewDAO.delete(review);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}

	public List<Review> getReviewsByUser(User user) throws PersistentException {
		return new ArrayList<>(user.getReviews());
	}

	public List<Review> getReviewsByUserId(String userId) throws PersistentException, UserException {
		User user = UserDAO.getUserByORMID(userId);
		if (user == null) throw new UserException("User com ID '" + userId + "' não existe.");
		return new ArrayList<>(user.getReviews());
	}

	public List<Review> getReviewsByRestaurant(Restaurant restaurant) throws PersistentException {
		return new ArrayList<>(restaurant.getReviews());
	}

	public List<Review> getReviewsByRestaurantId(String restaurantId) throws PersistentException {
		Restaurant restaurant = RestaurantDAO.getRestaurantByORMID(restaurantId);
		if (restaurant == null) throw new PersistentException("Restaurant com ID '" + restaurantId + "' não existe.");
		return new ArrayList<>(restaurant.getReviews());
	}

	public List<Review> getAllReviews() throws PersistentException {
		Review[] reviews = ReviewDAO.listReviewByQuery(null, null);
		return List.of(reviews);
	}
}
