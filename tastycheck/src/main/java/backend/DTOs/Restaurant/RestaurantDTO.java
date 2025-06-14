package backend.DTOs.Restaurant;


import backend.Models.Restaurant;

public class RestaurantDTO {

	private String id;
	private String name;
	private String ownerId;
	private String location;
	private String cuisineType;
	private String image;

	public RestaurantDTO(Restaurant restaurant){
		this.id = restaurant.getId();
		this.name = restaurant.getName();
		this.ownerId = restaurant.getOwner().getId();
		this.location = restaurant.getLocation();
		this.cuisineType = restaurant.getCuisineType();
		this.image = restaurant.getCoverImage();
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


	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}

