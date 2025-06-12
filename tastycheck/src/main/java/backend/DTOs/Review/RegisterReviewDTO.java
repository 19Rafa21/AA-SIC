package backend.DTOs.Review;

import backend.Models.Review;
import backend.Services.UserService;

public class RegisterReviewDTO {
	private String text;
	private Double rating;
	private String userId;
	private String restaurantId;

	public RegisterReviewDTO(String text, Double rating, String userId, String restaurantId){
		this.text = text;
		this.rating = rating;
		this.userId = userId;
		this.restaurantId = restaurantId;
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
