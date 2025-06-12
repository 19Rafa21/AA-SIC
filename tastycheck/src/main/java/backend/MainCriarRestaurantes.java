package backend;
import backend.DTOs.RestaurantDTO;
import backend.Models.Restaurant;
import backend.Models.Owner;
import backend.Services.RestaurantService;
import backend.Services.UserService;
import org.orm.PersistentException;

public class MainCriarRestaurantes {

    public static void main(String[] args) {
        try {
            RestaurantService restaurantService = new RestaurantService();
            UserService userService = new UserService();

            // Supondo que já tens este owner na BD
            Owner owner = new Owner();
            owner.setId("o1"); // ID deve existir na BD
            owner.setUsername("chef_gino");

            userService.registerOwner(owner);

            // Restaurante 1
            RestaurantDTO r1 = new RestaurantDTO();
            r1.setId("rest_001");
            r1.setName("Bella Italia");
            r1.setCuisineType("Italiana");
            r1.setLocation("Rua da Liberdade, 101, Braga");
            r1.setRating(4.5);
            r1.setOwner(owner);


            restaurantService.createRestaurant(r1);

            // Restaurante 2
            RestaurantDTO r2 = new RestaurantDTO();
            r2.setId("rest_002");
            r2.setName("Sabores do Norte");
            r2.setCuisineType("Portuguesa");
            r2.setLocation("Avenida Central, 88, Porto");
            r2.setRating(4.2);
            r2.setOwner(owner);

            restaurantService.createRestaurant(r2);

            // Restaurante 3
            RestaurantDTO r3 = new RestaurantDTO();
            r3.setId("rest_003");
            r3.setName("Pasta & Pesto");
            r3.setCuisineType("Italiana");
            r3.setLocation("Rua das Flores, 33, Lisboa");
            r3.setRating(3.8);
            r3.setOwner(owner);

            restaurantService.createRestaurant(r3);

            System.out.println("Restaurantes criados com sucesso!");

            AASICPersistentManager.instance().disposePersistentManager();

        } catch (PersistentException e) {
            e.printStackTrace();
        }
    }
}

