/**
 * Licensee: Bruno Alves(Universidade do Minho)
 * License Type: Academic
 */
package ormsamples;

import org.orm.*;
public class CreateAASICDatabaseSchema {
	public static void main(String[] args) {
		try {
			ORMDatabaseInitiator.createSchema(backend.AASICPersistentManager.instance());
			backend.AASICPersistentManager.instance().disposePersistentManager();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
