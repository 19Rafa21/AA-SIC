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

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class OwnerDetachedCriteria extends AbstractORMDetachedCriteria {
	public final StringExpression id;
	public final StringExpression username;
	public final StringExpression password;
	public final StringExpression email;
	
	public OwnerDetachedCriteria() {
		super(backend.Owner.class, backend.OwnerCriteria.class);
		id = new StringExpression("id", this.getDetachedCriteria());
		username = new StringExpression("username", this.getDetachedCriteria());
		password = new StringExpression("password", this.getDetachedCriteria());
		email = new StringExpression("email", this.getDetachedCriteria());
	}
	
	public OwnerDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, backend.OwnerCriteria.class);
		id = new StringExpression("id", this.getDetachedCriteria());
		username = new StringExpression("username", this.getDetachedCriteria());
		password = new StringExpression("password", this.getDetachedCriteria());
		email = new StringExpression("email", this.getDetachedCriteria());
	}
	
	public Owner uniqueOwner(PersistentSession session) {
		return (Owner) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Owner[] listOwner(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Owner[]) list.toArray(new Owner[list.size()]);
	}
}

