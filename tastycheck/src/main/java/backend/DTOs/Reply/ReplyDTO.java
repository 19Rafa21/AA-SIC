package backend.DTOs.Reply;

import backend.Models.Reply;

import java.util.Date;

public class ReplyDTO {
	private String id;
	private String text;
	private String authorId;
	private String authorUsername;
	private String reviewId;
	private Date date;

	public ReplyDTO() {
	}

	public ReplyDTO(String id, String text, String authorId, String authorUsername, String reviewId, Date date) {
		this.id = id;
		this.text = text;
		this.authorId = authorId;
		this.authorUsername = authorUsername;
		this.reviewId = reviewId;
		this.date = date;
	}

	public ReplyDTO(Reply reply) {
		this.id = reply.getId();
		this.text = reply.getText();
		this.authorId = reply.getAuthor().getId();
		this.authorUsername = reply.getAuthor().getUsername();
		this.reviewId = reply.getReview().getId();
		this.date = reply.getData();
	}

	// Getters & Setters

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

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getReviewId() {
		return reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
