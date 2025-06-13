package backend.DTOs.Reply;

import backend.Models.Reply;

import java.util.Date;

public class RegisterReplyDTO {

	private String text;
	private String userId;
	private String reviewId;

	public RegisterReplyDTO() {}

	public RegisterReplyDTO(String text, String userId, String reviewId) {
		this.text = text;
		this.userId = userId;
		this.reviewId = reviewId;
	}

	public RegisterReplyDTO(Reply reply) {
		this.text = reply.getText();
		this.userId = reply.getAuthor().getId();
		this.reviewId = reply.getReview().getId();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getReviewId() {
		return reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}
}
