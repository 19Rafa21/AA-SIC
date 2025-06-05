/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: Bruno Alves(Universidade do Minho)
 * License Type: Academic
 */
package backend;

import org.orm.*;
import org.hibernate.Query;
import org.hibernate.LockMode;
import java.util.List;

public class ReviewDAO {
	public static Review loadReviewByORMID(String id) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return loadReviewByORMID(session, id);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Review getReviewByORMID(String id) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return getReviewByORMID(session, id);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Review loadReviewByORMID(String id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return loadReviewByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Review getReviewByORMID(String id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return getReviewByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Review loadReviewByORMID(PersistentSession session, String id) throws PersistentException {
		try {
			return (Review) session.load(backend.Review.class, id);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Review getReviewByORMID(PersistentSession session, String id) throws PersistentException {
		try {
			return (Review) session.get(backend.Review.class, id);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Review loadReviewByORMID(PersistentSession session, String id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Review) session.load(backend.Review.class, id, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Review getReviewByORMID(PersistentSession session, String id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Review) session.get(backend.Review.class, id, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryReview(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return queryReview(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryReview(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return queryReview(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Review[] listReviewByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return listReviewByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Review[] listReviewByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return listReviewByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryReview(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From backend.Review as Review");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryReview(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From backend.Review as Review");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("Review", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Review[] listReviewByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryReview(session, condition, orderBy);
			return (Review[]) list.toArray(new Review[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Review[] listReviewByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryReview(session, condition, orderBy, lockMode);
			return (Review[]) list.toArray(new Review[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Review loadReviewByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return loadReviewByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Review loadReviewByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return loadReviewByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Review loadReviewByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		Review[] reviews = listReviewByQuery(session, condition, orderBy);
		if (reviews != null && reviews.length > 0)
			return reviews[0];
		else
			return null;
	}
	
	public static Review loadReviewByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		Review[] reviews = listReviewByQuery(session, condition, orderBy, lockMode);
		if (reviews != null && reviews.length > 0)
			return reviews[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateReviewByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return iterateReviewByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateReviewByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return iterateReviewByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateReviewByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From backend.Review as Review");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateReviewByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From backend.Review as Review");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("Review", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Review createReview() {
		return new backend.Review();
	}
	
	public static boolean save(backend.Review review) throws PersistentException {
		try {
			AASICPersistentManager.instance().saveObject(review);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean delete(backend.Review review) throws PersistentException {
		try {
			AASICPersistentManager.instance().deleteObject(review);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean refresh(backend.Review review) throws PersistentException {
		try {
			AASICPersistentManager.instance().getSession().refresh(review);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean evict(backend.Review review) throws PersistentException {
		try {
			AASICPersistentManager.instance().getSession().evict(review);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Review loadReviewByCriteria(ReviewCriteria reviewCriteria) {
		Review[] reviews = listReviewByCriteria(reviewCriteria);
		if(reviews == null || reviews.length == 0) {
			return null;
		}
		return reviews[0];
	}
	
	public static Review[] listReviewByCriteria(ReviewCriteria reviewCriteria) {
		return reviewCriteria.listReview();
	}
}
