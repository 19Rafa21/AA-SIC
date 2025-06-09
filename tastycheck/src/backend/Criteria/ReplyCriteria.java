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

import backend.AASICPersistentManager;
import backend.Models.Reply;
import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class ReplyCriteria extends AbstractORMCriteria {
	public final StringExpression id;
	public final StringExpression text;
	public final StringExpression review;
	public final StringExpression author;
	
	public ReplyCriteria(Criteria criteria) {
		super(criteria);
		id = new StringExpression("id", this);
		text = new StringExpression("text", this);
		review = new StringExpression("review", this);
		author = new StringExpression("author", this);
	}
	
	public ReplyCriteria(PersistentSession session) {
		this(session.createCriteria(Reply.class));
	}
	
	public ReplyCriteria() throws PersistentException {
		this(AASICPersistentManager.instance().getSession());
	}
	
	public Reply uniqueReply() {
		return (Reply) super.uniqueResult();
	}
	
	public Reply[] listReply() {
		java.util.List list = super.list();
		return (Reply[]) list.toArray(new Reply[list.size()]);
	}
}

