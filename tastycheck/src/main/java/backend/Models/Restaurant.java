/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: lucas(Universidade do Minho)
 * License Type: Academic
 */
package backend.Models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Restaurant {
	public Restaurant() {
		this.rating = 0.0;
		this.reviews = new HashSet<>();
	}
	
	public boolean equals(Object aObj) {
		if (aObj == this)
			return true;
		if (!(aObj instanceof Restaurant))
			return false;
		Restaurant restaurant = (Restaurant)aObj;
		if ((getId() != null && !getId().equals(restaurant.getId())) || (getId() == null && restaurant.getId() != null))
			return false;
		return true;
	}
	
	public int hashCode() {
		int hashcode = 0;
		hashcode = hashcode + (getId() == null ? 0 : getId().hashCode());
		return hashcode;
	}
	
	private String id;
	
	private Owner owner;
	
	private String name;
	
	private String location;
	
	private String cuisineType;
	
	private Double rating;
	
	private String coverImage;

	private List<String> menuImages;

	private List<String> foodImages;

	private Set<Review> reviews;
	
	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return id;
	}
	
	public String getORMID() {
		return getId();
	}
	
	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	
	public Owner getOwner() {
		return owner;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setLocation(String value) {
		this.location = value;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setCuisineType(String value) {
		this.cuisineType = value;
	}
	
	public String getCuisineType() {
		return cuisineType;
	}
	public void setRating(Double value) {
		this.rating = value;
	}
	
	public Double getRating() {
		return rating;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
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

	public String toString() {
		return String.valueOf(getId());
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}
}
