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

    public List<Restaurant> procurarComFiltros(String name, String kitchen, String city, Double rate) {
        try {
            RestaurantCriteria criteria = new RestaurantCriteria();

            if (name != null && !name.isBlank()) {
                searchForName(name);
            }

            if (kitchen != null && !kitchen.isBlank()) {
                searchForCuisineType(kitchen);
            }

            if (city != null && !city.isBlank()) {
                searchForLocation(city);
            }

            if (rate != null) {
                searchForRating(rate);
            }

            return List.of(criteria.listRestaurant());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
