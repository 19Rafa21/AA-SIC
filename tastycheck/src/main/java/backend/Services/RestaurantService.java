package backend.Services;
import backend.Criteria.RestaurantCriteria;
import backend.DAOs.*;
import backend.DTOs.RestaurantDTO;
import backend.DTOs.RestaurantDetailsDTO;
import backend.Exceptions.UnauthorizedException;
import backend.Models.Image;
import backend.Models.Restaurant;
import backend.Models.Review;
import org.orm.PersistentException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RestaurantService {

    private RestaurantDAO restaurantDAO;

    private ImageService imageService;

    public RestaurantService() throws IOException {
        imageService = new ImageService();
        restaurantDAO = new RestaurantDAO();
    }

    public boolean createRestaurant(RestaurantDetailsDTO dto) {
        try {
            Restaurant r = toRestaurant(dto);  // usa DTO com imagens
            restaurantDAO.save(r);

            // Criar e guardar imagens (menu)
            for (String url : dto.getMenuImages()) {
                Image img = new Image();
                img.setId(UUID.randomUUID().toString());
                img.setRestaurantId(r.getId());
                img.setUrl(url);
                img.setType("menu");
                ImageDAO.save(img);
            }

            // Criar e guardar imagens (food)
            for (String url : dto.getFoodImages()) {
                Image img = new Image();
                img.setId(UUID.randomUUID().toString());
                img.setRestaurantId(r.getId());
                img.setUrl(url);
                img.setType("food");
                ImageDAO.save(img);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRestaurant(String id, RestaurantDetailsDTO dto) {
        try {
            // 1. Obter restaurante existente
            Restaurant r2 = getRestaurantByOrmID(id);

            // 2. Atualizar campos editáveis (só se não forem null)
            r2 = toRestaurantEdit(r2, dto);

            // 3. Gravar alterações do restaurante (nome, morada, capa, etc.)
            restaurantDAO.save(r2);

            // 4. Guardar novas imagens de menu na BD
            if (dto.getMenuImages() != null) {
                for (String url : dto.getMenuImages()) {
                    Image img = new Image(UUID.randomUUID().toString(), id, url, "menu");
                    ImageDAO.save(img);
                    System.out.println("Imagem de menu guardada: " + url);
                }
            }

            // 5. Guardar novas imagens de comida na BD
            if (dto.getFoodImages() != null) {
                for (String url : dto.getFoodImages()) {
                    Image img = new Image(UUID.randomUUID().toString(), id, url, "food");
                    ImageDAO.save(img);
                    System.out.println("Imagem de comida guardada: " + url);
                }
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteAllImagesByRestaurant(String restaurantId) throws IOException, PersistentException {
        List<Image> images = ImageDAO.getImagesByRestaurantId(restaurantId);
        for (Image img : images) {
            imageService.deleteImage(img.getUrl()); // Apaga do bucket
        }
        ImageDAO.deleteByRestaurantId(restaurantId); // Apaga da BD (1x)
    }



    public boolean removeRestaurant(String id) {
        try {
            Restaurant r = restaurantDAO.getRestaurantByORMID(id);
            if (r != null) {
                ImageDAO.deleteByRestaurantId(id);
                restaurantDAO.delete(r);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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

    public static Restaurant toRestaurant(RestaurantDetailsDTO dto) throws PersistentException {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(UUID.randomUUID().toString());
        restaurant.setName(dto.getName());
        restaurant.setLocation(dto.getLocation());
        restaurant.setCuisineType(dto.getCuisineType());
        restaurant.setOwner(OwnerDAO.getOwnerByORMID(dto.getOwner()));
        restaurant.setCoverImage(dto.getImage());
        restaurant.setRating(0.0);

        return restaurant;
    }


    public void updateRating(Restaurant restaurant) throws PersistentException {
        try {
            double avgRating = restaurant.getReviews().stream()
                    .mapToDouble(Review::getRating)
                    .average()
                    .orElse(0.0);

            restaurant.setRating(avgRating);
            RestaurantDAO.save(restaurant);
        } catch (PersistentException e) {
            throw new RuntimeException(e);
        }
    }


    public Restaurant getRestaurantByOrmID(String id) throws PersistentException {
        Restaurant restaurant = RestaurantDAO.getRestaurantByORMID(id);
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant com ID '" + id + "' não existe.");
        }
        return restaurant;
    }


    public RestaurantDetailsDTO getRestaurantById(String id) throws PersistentException {
        Restaurant restaurant = getRestaurantByOrmID(id);
        return toDetailsDTO(restaurant);
    }

    public Restaurant toRestaurantEdit(Restaurant restaurant, RestaurantDetailsDTO dto) {
        if (dto.getName() != null)
            restaurant.setName(dto.getName());

        if (dto.getLocation() != null)
            restaurant.setLocation(dto.getLocation());

        if (dto.getCuisineType() != null)
            restaurant.setCuisineType(dto.getCuisineType());

        if (dto.getRating() != null)
            restaurant.setRating(dto.getRating());

        if (dto.getImage() != null)
            restaurant.setCoverImage(dto.getImage());

        if (dto.getMenuImages() != null)
            restaurant.setMenuImages(dto.getMenuImages());

        if (dto.getFoodImages() != null)
            restaurant.setFoodImages(dto.getFoodImages());

        return restaurant;
    }


    private RestaurantDetailsDTO toDetailsDTO(Restaurant r) throws PersistentException {
        RestaurantDetailsDTO dto = new RestaurantDetailsDTO();
        dto.setId(r.getId());
        dto.setName(r.getName());
        dto.setOwner(r.getOwner() != null ? r.getOwner().getId() : null);
        dto.setLocation(r.getLocation());
        dto.setCuisineType(r.getCuisineType());
        dto.setRating(r.getRating());
        dto.setImage(r.getCoverImage());
        dto.setMenuImages(ImageDAO.getMenuImages(r.getId()) != null ? ImageDAO.getMenuImages(r.getId()) : new ArrayList<>());
        dto.setFoodImages(ImageDAO.getFoodImages(r.getId()) != null ? ImageDAO.getFoodImages(r.getId()) : new ArrayList<>());
        //dto.setMenuImages(r.getMenuImages() != null ? r.getMenuImages() : new ArrayList<>());
        //dto.setFoodImages(r.getFoodImages() != null ? r.getFoodImages() : new ArrayList<>());

        return dto;
    }

    public List<Review> getAllRestaurantReviews(Restaurant restaurant) throws PersistentException {
        return new ArrayList<>(restaurant.getReviews());
    }

    public boolean isOwnerOfRestaurant(String userId, String restaurantId) {
        try {
            Restaurant restaurant = getRestaurantByOrmID(restaurantId);
            if (restaurant == null || restaurant.getOwner() == null) {
                throw new PersistentException("Restaurante não encontrado ou sem Owner!");
            }

            return Objects.equals(restaurant.getOwner().getId(), userId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }






}
