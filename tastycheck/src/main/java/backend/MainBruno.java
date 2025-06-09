package backend;

import backend.Models.User;
import backend.Services.UserService;
import org.orm.PersistentException;

public class MainBruno {
    public static void main(String[] args) {
        try{

            UserService userService = new UserService();

            User user = new User();
            user.setId("u123");
            user.setUsername("john");
            user.setPassword("1234");
            user.setEmail("john@example.com");

            boolean saved = userService.registerUser(user);

            if (saved) {
                System.out.println("User criado com sucesso!");
            }


			/*User user = userService.getUserById("u123");

			boolean valid = userService.checkPassword("12345", user.getPassword());

			if (valid) {
				System.out.println("Password corresponde ao User!");
			} else {
				System.out.println("Password não corresponde ao User!");
			}
			 */



            //Owner owner = OwnerDAO.getOwnerByORMID("u2");

            //Restaurant restaurant = RestaurantDAO.getRestaurantByORMID("rest_001");

            //Review review = ReviewDAO.getReviewByORMID("rev1");

            //Restaurant restaurant = new Restaurant();
            //restaurant.setId("r1");
            //restaurant.setOwner(owner);
            //restaurant.setName("restaurante teste");
            //restaurant.setLocation("Braga");
            //restaurant.setCuisineType("portuguesa");
            //restaurant.setRating(5.0);
            //restaurant.setImage("imagem.png");

            //boolean saved = RestaurantDAO.save(restaurant);

            //if (saved) {
            //	System.out.println("Restaurante guardado com sucesso!");
            //} else {
            //	System.out.println("Erro ao guardar Restaurante.");
            //}

			/*if (owner != null) {
				System.out.println(" - ID: " + owner.getId());
				System.out.println("   Name: " + owner.getUsername());
				System.out.println("   pass: " + owner.getPassword());
				System.out.println(" - email: " + owner.getEmail());
				System.out.println("Restaurantes do " + owner.getUsername() + ":");
				for (Restaurant restaurant : owner.getRestaurants()) {
					System.out.println(" - ID: " + restaurant.getId());
					System.out.println("   Owner: " + restaurant.getOwner());
					System.out.println("   Name: " + restaurant.getName());
					System.out.println("   Location: " + restaurant.getLocation());
					System.out.println(" - Cuisine: " + restaurant.getCuisineType());
					System.out.println("   Rating: " + restaurant.getRating());
					System.out.println("   Image: " + restaurant.getImage());
				}
			}
			 */


			/*if (restaurant != null) {
				System.out.println(" - ID: " + restaurant.getId());
				System.out.println("   Owner: " + restaurant.getOwner());
				System.out.println("   Name: " + restaurant.getName());
				System.out.println("   Location: " + restaurant.getLocation());
				System.out.println(" - Cuisine: " + restaurant.getCuisineType());
				System.out.println("   Rating: " + restaurant.getRating());
				System.out.println("   Image: " + restaurant.getImage());
			} else {
				System.out.println("Restaurante nao encontrado");
			}
			 */

            //if (restaurant != null) {
            //	boolean deleted = RestaurantDAO.delete(restaurant);

            //	if (deleted) {
            //		System.out.println("Restaurante + Reviews + Replies eliminados com sucesso!");
            //	} else {
            //		System.out.println("Erro ao eliminar Restaurante.");
            //	}
            //}

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
