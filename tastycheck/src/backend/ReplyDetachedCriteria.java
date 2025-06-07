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

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class ReplyDetachedCriteria extends AbstractORMDetachedCriteria {
	public final StringExpression id;
	public final StringExpression text;
	public final StringExpression review;
	public final StringExpression author;
	
	public ReplyDetachedCriteria() {
		super(backend.Reply.class, backend.ReplyCriteria.class);
		id = new StringExpression("id", this.getDetachedCriteria());
		text = new StringExpression("text", this.getDetachedCriteria());
		review = new StringExpression("review", this.getDetachedCriteria());
		author = new StringExpression("author", this.getDetachedCriteria());
	}
	
	public ReplyDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, backend.ReplyCriteria.class);
		id = new StringExpression("id", this.getDetachedCriteria());
		text = new StringExpression("text", this.getDetachedCriteria());
		review = new StringExpression("review", this.getDetachedCriteria());
		author = new StringExpression("author", this.getDetachedCriteria());
	}
	
	public Reply uniqueReply(PersistentSession session) {
		return (Reply) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Reply[] listReply(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Reply[]) list.toArray(new Reply[list.size()]);
	}
}

