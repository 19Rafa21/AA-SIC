package backend.DTOs.Review;

import backend.Models.Review;

public class ReviewDTO {
	private String id;
	private String text;
	private Double rating;
	private String userId; // ou username
	private String restaurantId; // ou nome

	public ReviewDTO(Review review) {
		this.id = review.getId();
		this.text = review.getText();
		this.rating = review.getRating();
		this.userId = review.getAuthor() != null ? review.getAuthor().getId() : null;
		this.restaurantId = review.getRestaurant() != null ? review.getRestaurant().getId() : null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}
}