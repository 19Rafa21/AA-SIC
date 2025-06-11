package backend.Services;

import backend.DAOs.ReplyDAO;
import backend.DAOs.ReviewDAO;
import backend.Exceptions.UserException;
import backend.Models.Reply;
import backend.Models.Review;
import backend.Models.User;
import org.orm.PersistentException;

import java.util.ArrayList;
import java.util.List;

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

	public boolean updateReply(Reply reply) throws  PersistentException {
		return  ReplyDAO.save(reply);
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
