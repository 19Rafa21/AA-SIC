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

public class ReplyDAO {
	public static Reply loadReplyByORMID(String id) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return loadReplyByORMID(session, id);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Reply getReplyByORMID(String id) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return getReplyByORMID(session, id);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Reply loadReplyByORMID(String id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return loadReplyByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Reply getReplyByORMID(String id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return getReplyByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Reply loadReplyByORMID(PersistentSession session, String id) throws PersistentException {
		try {
			return (Reply) session.load(backend.Reply.class, id);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Reply getReplyByORMID(PersistentSession session, String id) throws PersistentException {
		try {
			return (Reply) session.get(backend.Reply.class, id);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Reply loadReplyByORMID(PersistentSession session, String id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Reply) session.load(backend.Reply.class, id, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Reply getReplyByORMID(PersistentSession session, String id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Reply) session.get(backend.Reply.class, id, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryReply(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return queryReply(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryReply(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return queryReply(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Reply[] listReplyByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return listReplyByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Reply[] listReplyByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return listReplyByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryReply(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From backend.Reply as Reply");
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
	
	public static List queryReply(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From backend.Reply as Reply");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("Reply", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Reply[] listReplyByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryReply(session, condition, orderBy);
			return (Reply[]) list.toArray(new Reply[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Reply[] listReplyByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryReply(session, condition, orderBy, lockMode);
			return (Reply[]) list.toArray(new Reply[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Reply loadReplyByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return loadReplyByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Reply loadReplyByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return loadReplyByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Reply loadReplyByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		Reply[] replys = listReplyByQuery(session, condition, orderBy);
		if (replys != null && replys.length > 0)
			return replys[0];
		else
			return null;
	}
	
	public static Reply loadReplyByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		Reply[] replys = listReplyByQuery(session, condition, orderBy, lockMode);
		if (replys != null && replys.length > 0)
			return replys[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateReplyByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return iterateReplyByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateReplyByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return iterateReplyByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateReplyByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From backend.Reply as Reply");
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
	
	public static java.util.Iterator iterateReplyByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From backend.Reply as Reply");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("Reply", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Reply createReply() {
		return new backend.Reply();
	}
	
	public static boolean save(backend.Reply reply) throws PersistentException {
		try {
			AASICPersistentManager.instance().saveObject(reply);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean delete(backend.Reply reply) throws PersistentException {
		try {
			AASICPersistentManager.instance().deleteObject(reply);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean refresh(backend.Reply reply) throws PersistentException {
		try {
			AASICPersistentManager.instance().getSession().refresh(reply);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean evict(backend.Reply reply) throws PersistentException {
		try {
			AASICPersistentManager.instance().getSession().evict(reply);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Reply loadReplyByCriteria(ReplyCriteria replyCriteria) {
		Reply[] replys = listReplyByCriteria(replyCriteria);
		if(replys == null || replys.length == 0) {
			return null;
		}
		return replys[0];
	}
	
	public static Reply[] listReplyByCriteria(ReplyCriteria replyCriteria) {
		return replyCriteria.listReply();
	}
}
