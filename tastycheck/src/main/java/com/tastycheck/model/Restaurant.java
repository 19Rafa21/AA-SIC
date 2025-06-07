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
package com.tastycheck.model;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "restaurants")
public class Restaurant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String name;

	@Column(name = "location_coordinates")
	private String locationCoordinates;

	@Column(name = "location")
	private String location;

	@Column(name = "cuisine_type")
	private String cuisineType;

	@Column(name = "rating")
	private Double rating;

	@Column(name = "image")
	private String image;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;

	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	private Set<Review> reviews;

	public Restaurant() {
	}

	public boolean equals(Object aObj) {
		if (aObj == this)
			return true;
		if (!(aObj instanceof Restaurant))
			return false;
		Restaurant restaurant = (Restaurant)aObj;
		if (this.id != restaurant.id)
			return false;
		return true;
	}

	public int hashCode() {
		return id;
	}

	public void setId(int value) {
		this.id = value;
	}

	public int getId() {
		return id;
	}

	public String getORMID() {
		return String.valueOf(getId());
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

	public void setRating(Double value) {
		this.rating = value;
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

	public void getAttribute() {
		// Implementation depends on what this method is supposed to do
	}

	public void setAttribute(int attribute) {
		// Implementation depends on what this method is supposed to do
	}

	public String toString() {
		return "Restaurant{id=" + id + ", name='" + name + "', location='" + location + "'}";
	}
}