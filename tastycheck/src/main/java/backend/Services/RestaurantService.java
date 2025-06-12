package backend.Services;
import backend.Criteria.RestaurantCriteria;
import backend.DAOs.RestaurantDAO;
import backend.DAOs.ReviewDAO;
import backend.DTOs.RestaurantDTO;
import backend.Models.Restaurant;
import backend.Models.Review;
import org.orm.PersistentException;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RestaurantService {

    private RestaurantDAO restaurantDAO;

    public RestaurantService() {
        restaurantDAO = new RestaurantDAO();
    }

    public boolean createRestaurant(RestaurantDTO dto) {
        try {
            Restaurant r = toRestaurant(dto);
            restaurantDAO.save(r);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRestaurant(RestaurantDTO dto) {
        try {
            Restaurant r2 = getRestaurantById(dto.getId());

            r2 = toRestaurantEdit(dto,r2);

            restaurantDAO.save(r2);

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

    public Restaurant getRestaurantById(String id) throws PersistentException {
        Restaurant restaurant = RestaurantDAO.getRestaurantByORMID(id);
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant com ID '" + id + "' não existe.");
        }
        return restaurant;
    }

    public static Restaurant toRestaurant(RestaurantDTO dto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(UUID.randomUUID().toString());
        restaurant.setName(dto.getName());
        restaurant.setLocation(dto.getLocation());
        restaurant.setCuisineType(dto.getCuisineType());
        restaurant.setOwner(dto.getOwner());
        restaurant.setImage(dto.getImage());
        restaurant.setRating(0.0);

        return restaurant;
    }

    public static Restaurant toRestaurantEdit(RestaurantDTO dto, Restaurant restaurant) {
        restaurant.setName(dto.getName());
        restaurant.setLocation(dto.getLocation());
        restaurant.setCuisineType(dto.getCuisineType());
        restaurant.setOwner(dto.getOwner());
        restaurant.setImage(dto.getImage());
        restaurant.setRating(0.0);

        return restaurant;
    }
}
