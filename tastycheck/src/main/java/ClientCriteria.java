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

public class ClientCriteria extends AbstractORMCriteria {
	public final StringExpression id;
	public final StringExpression username;
	public final StringExpression password;
	public final StringExpression email;
	
	public ClientCriteria(Criteria criteria) {
		super(criteria);
		id = new StringExpression("id", this);
		username = new StringExpression("username", this);
		password = new StringExpression("password", this);
		email = new StringExpression("email", this);
	}
	
	public ClientCriteria(PersistentSession session) {
		this(session.createCriteria(Client.class));
	}
	
	public ClientCriteria() throws PersistentException {
		this(AASICPersistentManager.instance().getSession());
	}
	
	public Client uniqueClient() {
		return (Client) super.uniqueResult();
	}
	
	public Client[] listClient() {
		java.util.List list = super.list();
		return (Client[]) list.toArray(new Client[list.size()]);
	}
}

