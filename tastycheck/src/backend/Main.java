package backend;

import backend.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		// Criar o SessionFactory a partir do configuration
		Configuration configuration = new Configuration();
		configuration.configure("ormmapping/AASIC.cfg.xml"); // Caminho para o teu ficheiro cfg

		SessionFactory sessionFactory = configuration.buildSessionFactory();

		// Abrir uma nova sessão
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			// Supondo que já tens um Owner (User com discriminator = "Owner") com ID = "1"
			//User owner = session.get(User.class, "1"); // Põe aqui o ID correto de um Owner existente na BD

			// Criar restaurante
			//backend.Restaurant restaurante = new backend.Restaurant();
			//restaurante.setId("rest2");
			//restaurante.setName("Marisqueira Estrela do Mar");
			//restaurante.setLocation("Aveiro");


			// Criar reviews
			backend.Review r1 = new Review();
			r1.setId("1");
			r1.setRating(4.5);
			r1.setComment("Marisco fresquíssimo!");
			r1.setRestaurant("Marisqueira Estrela do Mar");

			session.save(r1);

			backend.Review r2 = new Review();
			r2.setId("2");
			r2.setRating(4.4);
			r2.setComment("Ambiente agradável.");
			r2.setRestaurant("Marisqueira Estrela do Mar");

			session.save(r2);

			// Adicionar à lista do restaurante
			List<Review> reviews = new ArrayList<>();
			reviews.add(r1);
			reviews.add(r2);
			//restaurante.setReviews(reviews);

			// Guardar restaurante (cascade salva as reviews)
			//session.save(restaurante);

			//AASICPersistentManager.instance().getSession().save(restaurante);

			tx.commit();
			System.out.println("Dados guardados com sucesso!");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				AASICPersistentManager.instance().disposePersistentManager();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}

