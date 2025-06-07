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
@Table(name = "reviews")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private int rating;

	@Column(columnDefinition = "TEXT")
	private String comment;

	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
	private Set<Reply> replies;

	public Review() {
	}

	public boolean equals(Object aObj) {
		if (aObj == this)
			return true;
		if (!(aObj instanceof Review))
			return false;
		Review review = (Review)aObj;
		if (this.id != review.id)
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

	public void setRating(int value) {
		this.rating = value;
	}

	public int getRating() {
		return rating;
	}

	public void setComment(String value) {
		this.comment = value;
	}

	public String getComment() {
		return comment;
	}

	public void setClient(Client value) {
		this.client = value;
	}

	public Client getClient() {
		return client;
	}

	public void setRestaurant(Restaurant value) {
		this.restaurant = value;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public Set<Reply> getReplies() {
		return replies;
	}

	public void setReplies(Set<Reply> replies) {
		this.replies = replies;
	}

	public String toString() {
		return "Review{id=" + id + ", rating=" + rating + ", restaurant=" +
				(restaurant != null ? restaurant.getName() : "null") + "}";
	}
}