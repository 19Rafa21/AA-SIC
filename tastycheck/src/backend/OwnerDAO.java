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

public class OwnerDAO {
	public static Owner loadOwnerByORMID(String id) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return loadOwnerByORMID(session, id);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Owner getOwnerByORMID(String id) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return getOwnerByORMID(session, id);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Owner loadOwnerByORMID(String id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return loadOwnerByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Owner getOwnerByORMID(String id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return getOwnerByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Owner loadOwnerByORMID(PersistentSession session, String id) throws PersistentException {
		try {
			return (Owner) session.load(backend.Owner.class, id);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Owner getOwnerByORMID(PersistentSession session, String id) throws PersistentException {
		try {
			return (Owner) session.get(backend.Owner.class, id);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Owner loadOwnerByORMID(PersistentSession session, String id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Owner) session.load(backend.Owner.class, id, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Owner getOwnerByORMID(PersistentSession session, String id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Owner) session.get(backend.Owner.class, id, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryOwner(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return queryOwner(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryOwner(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return queryOwner(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Owner[] listOwnerByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return listOwnerByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Owner[] listOwnerByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return listOwnerByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryOwner(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From backend.Owner as Owner");
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
	
	public static List queryOwner(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From backend.Owner as Owner");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("Owner", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Owner[] listOwnerByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryOwner(session, condition, orderBy);
			return (Owner[]) list.toArray(new Owner[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Owner[] listOwnerByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryOwner(session, condition, orderBy, lockMode);
			return (Owner[]) list.toArray(new Owner[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Owner loadOwnerByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return loadOwnerByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Owner loadOwnerByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return loadOwnerByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Owner loadOwnerByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		Owner[] owners = listOwnerByQuery(session, condition, orderBy);
		if (owners != null && owners.length > 0)
			return owners[0];
		else
			return null;
	}
	
	public static Owner loadOwnerByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		Owner[] owners = listOwnerByQuery(session, condition, orderBy, lockMode);
		if (owners != null && owners.length > 0)
			return owners[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateOwnerByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return iterateOwnerByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateOwnerByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return iterateOwnerByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateOwnerByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From backend.Owner as Owner");
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
	
	public static java.util.Iterator iterateOwnerByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From backend.Owner as Owner");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("Owner", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Owner createOwner() {
		return new backend.Owner();
	}
	
	public static boolean save(backend.Owner owner) throws PersistentException {
		try {
			AASICPersistentManager.instance().saveObject(owner);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean delete(backend.Owner owner) throws PersistentException {
		try {
			AASICPersistentManager.instance().deleteObject(owner);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean refresh(backend.Owner owner) throws PersistentException {
		try {
			AASICPersistentManager.instance().getSession().refresh(owner);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean evict(backend.Owner owner) throws PersistentException {
		try {
			AASICPersistentManager.instance().getSession().evict(owner);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Owner loadOwnerByCriteria(OwnerCriteria ownerCriteria) {
		Owner[] owners = listOwnerByCriteria(ownerCriteria);
		if(owners == null || owners.length == 0) {
			return null;
		}
		return owners[0];
	}
	
	public static Owner[] listOwnerByCriteria(OwnerCriteria ownerCriteria) {
		return ownerCriteria.listOwner();
	}
}
