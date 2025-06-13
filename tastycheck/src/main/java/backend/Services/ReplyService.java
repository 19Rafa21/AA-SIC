package backend.Services;

import backend.DAOs.ReplyDAO;
import backend.DAOs.ReviewDAO;
import backend.DTOs.Reply.RegisterReplyDTO;
import backend.DTOs.Reply.UpdateReplyDTO;
import backend.Exceptions.UserException;
import backend.Models.Reply;
import backend.Models.Review;
import backend.Models.User;
import org.orm.PersistentException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ReplyService {

	public boolean registerReply(User user, Review review, Reply reply) throws PersistentException {
		try {
			Reply existingReply = ReplyDAO.getReplyByORMID(reply.getId());
			if (existingReply != null) {
				throw new IllegalArgumentException("Reply com ID: '" + reply.getId() + "' já existe!");
			}

			reply.setAuthor(user);
			reply.setReview(review);

			review.getReplies().add(reply);

			ReplyDAO.save(reply);
			ReviewDAO.save(review);

			System.out.println("Reply registada com sucesso!");

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}

	public boolean registerReply(String userId, String reviewId, Reply reply) throws PersistentException {
		try {
			Reply existingReply = ReplyDAO.getReplyByORMID(reply.getId());
			if (existingReply != null) {
				throw new IllegalArgumentException("Reply com ID: '" + reply.getId() + "' já existe!");
			}

			UserService userService = new UserService();
			User user = userService.getUserById(userId);
			if (user == null) throw new UserException("User com ID '" + userId + "' não existe.");

			ReviewService reviewService = new ReviewService();
			Review review = reviewService.getReviewById(reviewId);
			if (review == null) throw  new PersistentException("Review com ID '" + reviewId + "' não existe.");

			reply.setAuthor(user);
			reply.setReview(review);

			review.getReplies().add(reply);

			ReplyDAO.save(reply);
			ReviewDAO.save(review);

			System.out.println("Reply registada com sucesso!");

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}

	public boolean registerReply(RegisterReplyDTO replyDTO) {

		try {
			UserService userService = new UserService();
			User user = userService.getUserById(replyDTO.getUserId());
			if (user == null) throw new UserException("User com ID '" + replyDTO.getUserId() + "' não existe.");

			ReviewService reviewService = new ReviewService();
			Review review = reviewService.getReviewById(replyDTO.getReviewId());
			if (review == null) throw new PersistentException("Review com ID '" + replyDTO.getReviewId() + "' não existe.");

			String replyId = UUID.randomUUID().toString();

			Reply existingReply = ReplyDAO.getReplyByORMID(replyId);
			if (existingReply != null) {
				throw new IllegalArgumentException("Reply com ID: '" + replyId + "' já existe!");
			}

			Reply reply = new Reply();
			reply.setId(replyId);
			reply.setData(new Date());
			reply.setText(replyDTO.getText());
			reply.setReview(review);
			reply.setAuthor(user);

			review.getReplies().add(reply);

			ReplyDAO.save(reply);
			ReviewDAO.save(review);

			return true;
		} catch (PersistentException | UserException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean updateReply(String replyId, UpdateReplyDTO updateDTO) throws  PersistentException {
		Reply reply = getReplyById(replyId);
		if (reply == null) {
			throw new IllegalArgumentException("Reply com ID '" + replyId + "' não existe.");
		}

		boolean hasChanges = false;

		if (updateDTO.getText() != null && !updateDTO.getText().isEmpty()) {
			reply.setText(updateDTO.getText());
			hasChanges = true;
		}

		if (!hasChanges) {
			throw new IllegalArgumentException("Nenhum campo fornecido para atualizar.");
		}

		reply.setData(new Date());

		ReplyDAO.save(reply);
		return true;
	}

	public Reply getReplyById(String id) throws PersistentException {
		Reply reply = ReplyDAO.getReplyByORMID(id);
		if (reply == null) {
			throw new IllegalArgumentException("Reply com ID '" + id + "' não existe.");
		}
		return reply;
	}

	public boolean deleteReply(String replyId) throws PersistentException {
		try {
			Reply reply = getReplyById(replyId);

			Review review = reply.getReview();

			if (review != null) {
				review.getReplies().remove(reply);
				ReviewDAO.save(review);
			}

			ReplyDAO.delete(reply);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}

	public boolean deleteReply(Reply reply) throws PersistentException {
		try {
			if (reply == null) {
				throw new IllegalArgumentException("Reply nao existe.");
			}

			Review review = reply.getReview();

			if (review != null) {
				review.getReplies().remove(reply);
				ReviewDAO.save(review);
			}

			ReplyDAO.delete(reply);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}

	public List<Reply> getRepliesByReview(Review review) throws PersistentException {
		return new ArrayList<>(review.getReplies());
	}

	public List<Reply> getRepliesByReviewId(String reviewId) throws PersistentException {
		ReviewService reviewService = new ReviewService();
		Review review = reviewService.getReviewById(reviewId);
		return new ArrayList<>(review.getReplies());
	}

	public List<Reply> getAllReplies() throws PersistentException {
		Reply[] replies = ReplyDAO.listReplyByQuery(null,null);
		return List.of(replies);
	}


}
