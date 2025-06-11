package backend.Services;
import backend.Criteria.RestaurantCriteria;
import backend.DAOs.RestaurantDAO;
import backend.Models.Restaurant;

import java.util.List;

public class RestaurantService {

    private RestaurantDAO restaurantDAO;

    public RestaurantService() {
        restaurantDAO = new RestaurantDAO();
    }

    public boolean createRestaurante(Restaurant r) {
        try {
            restaurantDAO.save(r);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editRestaurant(Restaurant r) {
        try {
            restaurantDAO.save(r);  // O mesmo método pode fazer update se o ID já existir
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeRestaurant(String id) {
        try {
            Restaurant r = restaurantDAO.getRestaurantByORMID(id);
            if (r != null) {
                restaurantDAO.delete(r);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Restaurant> searchForName(String name) {
        try {
            RestaurantCriteria criteria = new RestaurantCriteria();
            criteria.name.like("%" + name + "%");
            return List.of(criteria.listRestaurant());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Restaurant> searchForLocation(String city) {
        try {
            RestaurantCriteria criteria = new RestaurantCriteria();
            // Vai comparar se o valor termina com ", cidade"
            criteria.location.like("%, " + city);
            return List.of(criteria.listRestaurant());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Restaurant> searchForCuisineType(String type) {
        try {
            RestaurantCriteria criteria = new RestaurantCriteria();
            criteria.cuisineType.like("%" + type + "%");
            return List.of(criteria.listRestaurant());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<Restaurant> searchForRating(double rate) {
        try {
            RestaurantCriteria criteria = new RestaurantCriteria();
            criteria.rating.ge(rate);
            return List.of(criteria.listRestaurant());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Restaurant> searchWithAllFilters(String nome, String tipoCozinha, String cidade, Double avaliacaoMin) {
        try {
            RestaurantCriteria criteria = new RestaurantCriteria();

            boolean filtroAtivo = false;

            if (nome != null && !nome.isBlank()) {
                criteria.name.like("%" + nome + "%");
                filtroAtivo = true;
            }

            if (tipoCozinha != null && !tipoCozinha.isBlank()) {
                criteria.cuisineType.like("%" + tipoCozinha + "%");
                filtroAtivo = true;
            }

            if (cidade != null && !cidade.isBlank()) {
                criteria.location.like("%, " + cidade); // assume cidade como último campo
                filtroAtivo = true;
            }

            if (avaliacaoMin != null) {
                criteria.rating.ge(avaliacaoMin);
                filtroAtivo = true;
            }

            if (!filtroAtivo) {
                return List.of(); // nenhum filtro → devolve lista vazia
            }

            return List.of(criteria.listRestaurant());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
