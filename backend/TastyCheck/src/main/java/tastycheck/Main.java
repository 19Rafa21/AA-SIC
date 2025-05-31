package tastycheck;

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import tastycheck.models.User;

public class Main {
	public static void main(String[] args) {
		try {
			Configuration configuration = new Configuration().configure().addAnnotatedClass(User.class);
			StandardServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

			SessionFactory sf = configuration.buildSessionFactory(sr);

			Session s = sf.openSession();
			s.setFlushMode(FlushModeType.COMMIT);

			Transaction t = s.beginTransaction();

			User u1 = new User();
			u1.setEmail("teste@gmail.com");
			u1.setPassword("12345");
			u1.setUsername("Teste");
			u1.setRole(0);

			s.save(u1);

			try {
				t.commit();
				System.out.println("User criado com sucesso!");
			} catch (Exception e) {
				t.rollback();
				e.printStackTrace();
				System.out.println("Unable to commit changes");
			}

			s.close();
			StandardServiceRegistryBuilder.destroy(sr);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to connect to hibernate");
		}


	}
}
