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
package backend;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "Restaurant")
public class Restaurant {

	@Id
	@Column(name = "Id")
	private String id;

	@ManyToOne
	@JoinColumn(name = "Owner")
	private Owner owner;

	@Column(name = "Name")
	private String name;

	@Column(name = "Location")
	private String location;

	@Column(name = "CuisineType")
	private String cuisineType;

	@Column(name = "Rating")
	private Double rating;

	@Column(name = "Image")
	private String image;
	public Restaurant() {
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
	
	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return id;
	}
	
	public String getORMID() {
		return getId();
	}
	
	public void setOwner(Owner value) {
		this.owner = value;
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
	
	public void setRating(double value) {
		setRating(Double.valueOf(value));
	}
	
	public Double getRating() {
		return rating;
	}
	
	public void setImage(String value) {
		this.image = value;
	}
	
	public String getImage() {
		return image;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Map<String, Object> getAttribute() {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("id", this.id);
		attributes.put("name", this.name);
		attributes.put("location", this.location);
		attributes.put("cuisineType", this.cuisineType);
		attributes.put("rating", this.rating);
		attributes.put("image", this.image);
		attributes.put("owner", this.owner);
		return attributes;
	}

	public void setAttribute(int attribute, Object value) {
		switch(attribute) {
			case 0:
				if (value instanceof String) this.id = (String) value;
				break;
			case 1:
				if (value instanceof Owner) this.owner = (Owner) value;
				break;
			case 2:
				if (value instanceof String) this.name = (String) value;
				break;
			case 3:
				if (value instanceof String) this.location = (String) value;
				break;
			case 4:
				if (value instanceof String) this.cuisineType = (String) value;
				break;
			case 5:
				if (value instanceof Double) this.rating = (Double) value;
				break;
			case 6:
				if (value instanceof String) this.image = (String) value;
				break;
			default:
				throw new IllegalArgumentException("Invalid attribute index: " + attribute);
		}
	}
	
	public String toString() {
		return String.valueOf(getId());
	}
	
}
