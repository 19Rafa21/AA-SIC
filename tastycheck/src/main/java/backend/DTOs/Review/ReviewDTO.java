package backend.DTOs.Review;

import backend.Models.Review;

import java.util.Date;

public class ReviewDTO {
	private String id;
	private String text;
	private Double rating;
	private String userId;
	private String username;
	private String restaurantId;
	private Date date;

	public ReviewDTO(Review review) {
		this.id = review.getId();
		this.text = review.getText();
		this.rating = review.getRating();
		this.userId = review.getAuthor() != null ? review.getAuthor().getId() : null;
		this.username = review.getAuthor() != null ? review.getAuthor().getUsername() : null;
		this.restaurantId = review.getRestaurant() != null ? review.getRestaurant().getId() : null;
		this.date = review.getData();
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}