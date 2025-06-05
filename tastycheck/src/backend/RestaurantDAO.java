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

public class RestaurantDAO {
	public static Restaurant loadRestaurantByORMID(String id) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return loadRestaurantByORMID(session, id);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Restaurant getRestaurantByORMID(String id) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return getRestaurantByORMID(session, id);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Restaurant loadRestaurantByORMID(String id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return loadRestaurantByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Restaurant getRestaurantByORMID(String id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return getRestaurantByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Restaurant loadRestaurantByORMID(PersistentSession session, String id) throws PersistentException {
		try {
			return (Restaurant) session.load(backend.Restaurant.class, id);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Restaurant getRestaurantByORMID(PersistentSession session, String id) throws PersistentException {
		try {
			return (Restaurant) session.get(backend.Restaurant.class, id);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Restaurant loadRestaurantByORMID(PersistentSession session, String id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Restaurant) session.load(backend.Restaurant.class, id, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Restaurant getRestaurantByORMID(PersistentSession session, String id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Restaurant) session.get(backend.Restaurant.class, id, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryRestaurant(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return queryRestaurant(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryRestaurant(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return queryRestaurant(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Restaurant[] listRestaurantByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return listRestaurantByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Restaurant[] listRestaurantByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return listRestaurantByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryRestaurant(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From backend.Restaurant as Restaurant");
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
	
	public static List queryRestaurant(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From backend.Restaurant as Restaurant");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("Restaurant", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Restaurant[] listRestaurantByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryRestaurant(session, condition, orderBy);
			return (Restaurant[]) list.toArray(new Restaurant[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Restaurant[] listRestaurantByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryRestaurant(session, condition, orderBy, lockMode);
			return (Restaurant[]) list.toArray(new Restaurant[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Restaurant loadRestaurantByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return loadRestaurantByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Restaurant loadRestaurantByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return loadRestaurantByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Restaurant loadRestaurantByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		Restaurant[] restaurants = listRestaurantByQuery(session, condition, orderBy);
		if (restaurants != null && restaurants.length > 0)
			return restaurants[0];
		else
			return null;
	}
	
	public static Restaurant loadRestaurantByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		Restaurant[] restaurants = listRestaurantByQuery(session, condition, orderBy, lockMode);
		if (restaurants != null && restaurants.length > 0)
			return restaurants[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateRestaurantByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return iterateRestaurantByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateRestaurantByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AASICPersistentManager.instance().getSession();
			return iterateRestaurantByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateRestaurantByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From backend.Restaurant as Restaurant");
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
	
	public static java.util.Iterator iterateRestaurantByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From backend.Restaurant as Restaurant");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("Restaurant", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Restaurant createRestaurant() {
		return new backend.Restaurant();
	}
	
	public static boolean save(backend.Restaurant restaurant) throws PersistentException {
		try {
			AASICPersistentManager.instance().saveObject(restaurant);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean delete(backend.Restaurant restaurant) throws PersistentException {
		try {
			AASICPersistentManager.instance().deleteObject(restaurant);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean refresh(backend.Restaurant restaurant) throws PersistentException {
		try {
			AASICPersistentManager.instance().getSession().refresh(restaurant);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean evict(backend.Restaurant restaurant) throws PersistentException {
		try {
			AASICPersistentManager.instance().getSession().evict(restaurant);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Restaurant loadRestaurantByCriteria(RestaurantCriteria restaurantCriteria) {
		Restaurant[] restaurants = listRestaurantByCriteria(restaurantCriteria);
		if(restaurants == null || restaurants.length == 0) {
			return null;
		}
		return restaurants[0];
	}
	
	public static Restaurant[] listRestaurantByCriteria(RestaurantCriteria restaurantCriteria) {
		return restaurantCriteria.listRestaurant();
	}
}
