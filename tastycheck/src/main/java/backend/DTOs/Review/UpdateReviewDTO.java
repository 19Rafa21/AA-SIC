package backend.DTOs.Review;

public class UpdateReviewDTO {
	private String text;
	private Double rating;

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
}
