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
package backend.Criteria;

import java.util.List;

import backend.Criteria.ReviewCriteria;
import backend.Models.Review;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class ReviewDetachedCriteria extends AbstractORMDetachedCriteria {
	public final StringExpression id;
	public final DoubleExpression rating;
	public final StringExpression text;
	public final StringExpression author;
	public final StringExpression restaurant;
	
	public ReviewDetachedCriteria() {
		super(Review.class, ReviewCriteria.class);
		id = new StringExpression("id", this.getDetachedCriteria());
		rating = new DoubleExpression("rating", this.getDetachedCriteria());
		text = new StringExpression("text", this.getDetachedCriteria());
		author = new StringExpression("author", this.getDetachedCriteria());
		restaurant = new StringExpression("restaurant", this.getDetachedCriteria());
	}
	
	public ReviewDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, ReviewCriteria.class);
		id = new StringExpression("id", this.getDetachedCriteria());
		rating = new DoubleExpression("rating", this.getDetachedCriteria());
		text = new StringExpression("text", this.getDetachedCriteria());
		author = new StringExpression("author", this.getDetachedCriteria());
		restaurant = new StringExpression("restaurant", this.getDetachedCriteria());
	}
	
	public Review uniqueReview(PersistentSession session) {
		return (Review) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Review[] listReview(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Review[]) list.toArray(new Review[list.size()]);
	}
}

