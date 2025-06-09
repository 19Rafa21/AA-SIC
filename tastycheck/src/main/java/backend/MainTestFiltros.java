package backend;


import backend.Services.RestaurantService;
import backend.Models.Restaurant;
import org.orm.PersistentException;

import java.util.List;

public class MainTestFiltros {

    public static void main(String[] args) {
        try {
            RestaurantService restaurantService = new RestaurantService();

            // Filtros aplicados
            String nome = null;
            String tipoCozinha = "Italiana";
            String cidade = "Braga";
            Double avaliacaoMin = 4.0;

            List<Restaurant> resultados = restaurantService.procurarComFiltros(nome, tipoCozinha, cidade, avaliacaoMin);

            System.out.println("Restaurantes encontrados:");
            for (Restaurant r : resultados) {
                // Apenas mostrar se todos os campos essenciais estiverem preenchidos
                if (r != null && r.getName() != null && r.getLocation() != null && r.getCuisineType() != null && r.getRating() != null) {
                    System.out.println(r.getName());
                }
            }

            AASICPersistentManager.instance().disposePersistentManager();

        } catch (PersistentException e) {
            e.printStackTrace();
        }
    }
}
