package backend.DTOs.Restaurant;

import backend.Models.Restaurant;

public class FavRestaurantsDTO {

	private String id;
	private String name;
	private Double rating;
	private String image;
	private String cuisineType;

	public FavRestaurantsDTO(Restaurant restaurant){
		this.id = restaurant.getId();
		this.name = restaurant.getName();
		this.rating = restaurant.getRating();
		this.image = restaurant.getCoverImage() != null ? restaurant.getCoverImage() : null;
		this.cuisineType = restaurant.getCuisineType();
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCuisineType() {
		return cuisineType;
	}

	public void setCuisineType(String cuisine) {
		this.cuisineType = cuisine;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
