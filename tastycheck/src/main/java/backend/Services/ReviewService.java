package backend.Services;

import backend.DAOs.RestaurantDAO;
import backend.DAOs.ReviewDAO;
import backend.DAOs.UserDAO;
import backend.DTOs.Review.RegisterReviewDTO;
import backend.DTOs.Review.UpdateReviewDTO;
import backend.Exceptions.UserException;
import backend.Models.Restaurant;
import backend.Models.Review;
import backend.Models.User;
import org.orm.PersistentException;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ReviewService {

	public boolean registerReview(User user, Restaurant restaurant, Review review) throws PersistentException {
		try {
			review.setId(UUID.randomUUID().toString());

			Review existingReview = ReviewDAO.getReviewByORMID(review.getId());
			if (existingReview != null) {
				throw new IllegalArgumentException("Review com ID: '" + review.getId() + "' já existe!");
			}

			review.setData(new Date());
			review.setAuthor(user);
			review.setRestaurant(restaurant);

			restaurant.getReviews().add(review);

			ReviewDAO.save(review);
			UserDAO.save(user);
			RestaurantDAO.save(restaurant);
			RestaurantService restaurantService = new RestaurantService();
			restaurantService.updateRating(restaurant);

			System.out.println("Review registado com sucesso!");

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}

	public boolean registerReview(String userId, String restaurantId, Review review) throws PersistentException, UserException {
		try {
			review.setId(UUID.randomUUID().toString());

			Review existingReview = ReviewDAO.getReviewByORMID(review.getId());
			if (existingReview != null) {
				throw new IllegalArgumentException("Review com ID: '" + review.getId() + "' já existe!");
			}

			review.setData(new Date());

			UserService userService = new UserService();
			User user = userService.getUserById(userId);
			if (user == null) throw new UserException("User com ID '" + userId + "' não existe.");

			Restaurant restaurant = RestaurantDAO.getRestaurantByORMID(restaurantId);
			if (restaurant == null) throw new PersistentException("Restaurant com ID '" + restaurantId + "' não existe.");

			review.setAuthor(user);
			review.setRestaurant(restaurant);

			restaurant.getReviews().add(review);

			ReviewDAO.save(review);
			UserDAO.save(user);
			RestaurantDAO.save(restaurant);
			RestaurantService restaurantService = new RestaurantService();
			restaurantService.updateRating(restaurant);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}

	public boolean registerReview(RegisterReviewDTO dto) throws PersistentException, UserException {
		try {
			UserService userService = new UserService();
			User user = userService.getUserById(dto.getUserId());
			if (user == null) throw new UserException("User com ID '" + dto.getUserId() + "' não existe.");

			RestaurantService restaurantService = new RestaurantService();
			Restaurant restaurant = restaurantService.getRestaurantById(dto.getRestaurantId());
			if (restaurant == null) throw new PersistentException("Restaurant com ID '" + dto.getRestaurantId() + "' não existe.");

			String reviewId = UUID.randomUUID().toString();

			Review existingReview = ReviewDAO.getReviewByORMID(reviewId);
			if (existingReview != null) {
				throw new IllegalArgumentException("Review com ID: '" + reviewId + "' já existe!");
			}

			Review review = new Review();
			review.setId(reviewId);
			review.setData(new Date());
			review.setText(dto.getText());
			review.setRating(dto.getRating());
			review.setAuthor(user);
			review.setRestaurant(restaurant);

			restaurant.getReviews().add(review);

			ReviewDAO.save(review);
			UserDAO.save(user);
			RestaurantDAO.save(restaurant);
			restaurantService.updateRating(restaurant);

			return true;
		} catch (PersistentException | UserException e) {
			throw new RuntimeException(e);
		}

	}

	public boolean updateReview(String reviewId, UpdateReviewDTO updateDTO) throws  PersistentException {
		Review review = getReviewById(reviewId);
		if (review == null) {
			throw new IllegalArgumentException("Review com ID '" + reviewId + "' não existe.");
		}

		boolean hasChanges = false;

		if (updateDTO.getText() != null && !updateDTO.getText().isEmpty()) {
			review.setText(updateDTO.getText());
			hasChanges = true;
		}

		if (updateDTO.getRating() != null) {
			review.setRating(updateDTO.getRating());
			hasChanges = true;
		}

		if (!hasChanges) {
			throw new IllegalArgumentException("Nenhum campo fornecido para atualizar.");
		}

		review.setData(new Date());

		ReviewDAO.save(review);
		RestaurantService restaurantService = new RestaurantService();
		restaurantService.updateRating(review.getRestaurant());
		return true;
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

			Restaurant restaurant = review.getRestaurant();

			if (restaurant != null) {
				restaurant.getReviews().remove(review);
				RestaurantDAO.save(restaurant); // atualiza a lista de reviews
				RestaurantService restaurantService = new RestaurantService();
				restaurantService.updateRating(restaurant);
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

			Restaurant restaurant = review.getRestaurant();

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
