/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: lucas(Universidade do Minho)
 * License Type: Academic
 */
package backend;

import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class RestaurantCriteria extends AbstractORMCriteria {
	public final StringExpression id;
	public final StringExpression owner;
	public final StringExpression name;
	public final StringExpression location;
	public final StringExpression cuisineType;
	public final DoubleExpression rating;
	public final StringExpression image;
	
	public RestaurantCriteria(Criteria criteria) {
		super(criteria);
		id = new StringExpression("id", this);
		owner = new StringExpression("owner", this);
		name = new StringExpression("name", this);
		location = new StringExpression("location", this);
		cuisineType = new StringExpression("cuisineType", this);
		rating = new DoubleExpression("rating", this);
		image = new StringExpression("image", this);
	}
	
	public RestaurantCriteria(PersistentSession session) {
		this(session.createCriteria(Restaurant.class));
	}
	
	public RestaurantCriteria() throws PersistentException {
		this(AASICPersistentManager.instance().getSession());
	}
	
	public Restaurant uniqueRestaurant() {
		return (Restaurant) super.uniqueResult();
	}
	
	public Restaurant[] listRestaurant() {
		java.util.List list = super.list();
		return (Restaurant[]) list.toArray(new Restaurant[list.size()]);
	}
}

