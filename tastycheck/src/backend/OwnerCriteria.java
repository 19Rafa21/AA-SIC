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

import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class OwnerCriteria extends AbstractORMCriteria {
	public final StringExpression id;
	public final StringExpression username;
	public final StringExpression password;
	public final StringExpression email;
	
	public OwnerCriteria(Criteria criteria) {
		super(criteria);
		id = new StringExpression("id", this);
		username = new StringExpression("username", this);
		password = new StringExpression("password", this);
		email = new StringExpression("email", this);
	}
	
	public OwnerCriteria(PersistentSession session) {
		this(session.createCriteria(Owner.class));
	}
	
	public OwnerCriteria() throws PersistentException {
		this(AASICPersistentManager.instance().getSession());
	}
	
	public Owner uniqueOwner() {
		return (Owner) super.uniqueResult();
	}
	
	public Owner[] listOwner() {
		java.util.List list = super.list();
		return (Owner[]) list.toArray(new Owner[list.size()]);
	}
}

