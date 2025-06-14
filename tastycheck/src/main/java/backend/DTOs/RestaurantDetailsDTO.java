package backend.DTOs;

import backend.Models.Owner;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailsDTO {

    private String id;
    private String name;
    private String owner;
    private String location;
    private String cuisineType;
    private Double rating;
    private String image;
    private List<String> menuImages = new ArrayList<String>();
    private List<String> foodImages = new ArrayList<String>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisine) {
        this.cuisineType = cuisine;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner_id) {
        this.owner = owner_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<String> getMenuImages() {
        return menuImages;
    }

    public void setMenuImages(List<String> menuImages) {
        this.menuImages = menuImages;
    }

    public List<String> getFoodImages() {
        return foodImages;
    }

    public void setFoodImages(List<String> foodImages) {
        this.foodImages = foodImages;
    }
}
