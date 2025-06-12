package backend.DTOs;

import backend.Models.Owner;

public class RestaurantDTO {

    private String id;
    private String name;
    private Owner owner;
    private String location;
    private String cuisineType;
    private String image;
    private Double rating;


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


    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
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

}
