package backend;

import backend.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.orm.PersistentException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		try{
			//Client client = ClientDAO.getClientByORMID("u3");

			Restaurant restaurant = RestaurantDAO.getRestaurantByORMID("r1");

			//Review review = ReviewDAO.getReviewByORMID("rev1");

			if (restaurant != null) {
				boolean deleted = RestaurantDAO.delete(restaurant);

				if (deleted) {
					System.out.println("Restaurante + Reviews + Replies eliminados com sucesso!");
				} else {
					System.out.println("Erro ao eliminar Restaurante.");
				}
			}

			// 2️⃣ Criar nova Review
			//Review review = new Review();
			//review.setId("rev2"); // ID único
			//review.setRating(3.5);
			//review.setText("Comida boa e atendimento bom!");
			//review.setData(new Date());
			//review.setAuthor(client.getId()); // por exemplo, ID do utilizador que fez a review
			//review.setRestaurant(restaurant);

			//restaurant.getReviews().add(review);

			//Client client = new Client();
			//client.setId("u3");
			//client.setEmail("teste3@teste.com");
			//client.setPassword("12");
			//client.setUsername("teste3");

			//boolean saved = RestaurantDAO.save(restaurant);

			//if (saved) {
			//	System.out.println("Restaurante atualizado com sucesso!");
			//} else {
			//	System.out.println("Falha ao atualizar Restaurante.");
			//}

			// Listar as reviews
			/*System.out.println("Reviews do restaurante " + restaurant.getName() + ":");

			for (Review review : restaurant.getReviews()) {
				System.out.println(" - Autor: " + review.getAuthor());
				System.out.println("   Rating: " + review.getRating());
				System.out.println("   Texto: " + review.getText());
				System.out.println("   Data: " + review.getData());

				// Listar replies da review
				if (review.getReplies() != null && !review.getReplies().isEmpty()) {
					System.out.println("   Replies:");
					for (Reply reply : review.getReplies()) {
						System.out.println("     - Autor: " + reply.getAuthor());
						System.out.println("       Texto: " + reply.getText());
						System.out.println("       Data: " + reply.getData());
					}
				} else {
					System.out.println("   Sem replies.");
				}

				System.out.println();
			}
			*/

			//if (review == null) {
			//	System.out.println("Review não encontrada!");
			//	return;
			//}

			//Reply reply = new Reply();
			//reply.setId("rp1"); // ID único
			//reply.setText("Concordo!");
			//reply.setData(new Date()); // agora usas java.util.Date
			//reply.setAuthor(client.getId()); // por exemplo ID do Owner
			//reply.setReview(review);

			//review.getReplies().add(reply);

			//boolean saved = ReviewDAO.save(review);

			//if (saved) {
			//	System.out.println("Review alterada com sucesso!");
			//} else {
			//	System.out.println("Falha ao alterar o review.");
			//}

			AASICPersistentManager.instance().disposePersistentManager();
		} catch (PersistentException e) {
			e.printStackTrace();
		}
	}
}

