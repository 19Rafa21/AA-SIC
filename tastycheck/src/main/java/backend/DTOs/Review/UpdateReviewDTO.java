package backend.DTOs.Review;

import java.util.List;

public class UpdateReviewDTO {
	private String text;
	private Double rating;

	private List<String> reviewImages;

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

	public List<String> getReviewImages() {
		return reviewImages;
	}

	public void setReviewImages(List<String> reviewImages) {
		this.reviewImages = reviewImages;
	}
}
