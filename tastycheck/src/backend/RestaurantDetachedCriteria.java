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

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class RestaurantDetachedCriteria extends AbstractORMDetachedCriteria {
	public final StringExpression id;
	public final StringExpression owner;
	public final StringExpression name;
	public final StringExpression location;
	public final StringExpression cuisineType;
	public final DoubleExpression rating;
	public final StringExpression image;
	
	public RestaurantDetachedCriteria() {
		super(backend.Restaurant.class, backend.RestaurantCriteria.class);
		id = new StringExpression("id", this.getDetachedCriteria());
		owner = new StringExpression("owner", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		location = new StringExpression("location", this.getDetachedCriteria());
		cuisineType = new StringExpression("cuisineType", this.getDetachedCriteria());
		rating = new DoubleExpression("rating", this.getDetachedCriteria());
		image = new StringExpression("image", this.getDetachedCriteria());
	}
	
	public RestaurantDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, backend.RestaurantCriteria.class);
		id = new StringExpression("id", this.getDetachedCriteria());
		owner = new StringExpression("owner", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		location = new StringExpression("location", this.getDetachedCriteria());
		cuisineType = new StringExpression("cuisineType", this.getDetachedCriteria());
		rating = new DoubleExpression("rating", this.getDetachedCriteria());
		image = new StringExpression("image", this.getDetachedCriteria());
	}
	
	public Restaurant uniqueRestaurant(PersistentSession session) {
		return (Restaurant) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Restaurant[] listRestaurant(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Restaurant[]) list.toArray(new Restaurant[list.size()]);
	}
}

