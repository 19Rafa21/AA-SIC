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

public class ReviewDetachedCriteria extends AbstractORMDetachedCriteria {
	public final StringExpression id;
	public final DoubleExpression rating;
	public final StringExpression text;
	public final StringExpression author;
	public final StringExpression restaurant;
	
	public ReviewDetachedCriteria() {
		super(backend.Review.class, backend.ReviewCriteria.class);
		id = new StringExpression("id", this.getDetachedCriteria());
		rating = new DoubleExpression("rating", this.getDetachedCriteria());
		text = new StringExpression("text", this.getDetachedCriteria());
		author = new StringExpression("author", this.getDetachedCriteria());
		restaurant = new StringExpression("restaurant", this.getDetachedCriteria());
	}
	
	public ReviewDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, backend.ReviewCriteria.class);
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

