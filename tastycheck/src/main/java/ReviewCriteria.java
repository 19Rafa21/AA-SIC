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

import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class ReviewCriteria extends AbstractORMCriteria {
	public final StringExpression id;
	public final DoubleExpression rating;
	public final StringExpression text;
	public final StringExpression author;
	public final StringExpression restaurant;
	
	public ReviewCriteria(Criteria criteria) {
		super(criteria);
		id = new StringExpression("id", this);
		rating = new DoubleExpression("rating", this);
		text = new StringExpression("text", this);
		author = new StringExpression("author", this);
		restaurant = new StringExpression("restaurant", this);
	}
	
	public ReviewCriteria(PersistentSession session) {
		this(session.createCriteria(Review.class));
	}
	
	public ReviewCriteria() throws PersistentException {
		this(AASICPersistentManager.instance().getSession());
	}
	
	public Review uniqueReview() {
		return (Review) super.uniqueResult();
	}
	
	public Review[] listReview() {
		java.util.List list = super.list();
		return (Review[]) list.toArray(new Review[list.size()]);
	}
}

