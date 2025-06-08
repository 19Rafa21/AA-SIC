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

import java.time.LocalDateTime;
import java.util.Date;

public class Review {
	public Review() {
	}
	
	public boolean equals(Object aObj) {
		if (aObj == this)
			return true;
		if (!(aObj instanceof Review))
			return false;
		Review review = (Review)aObj;
		if ((getId() != null && !getId().equals(review.getId())) || (getId() == null && review.getId() != null))
			return false;
		return true;
	}
	
	public int hashCode() {
		int hashcode = 0;
		hashcode = hashcode + (getId() == null ? 0 : getId().hashCode());
		return hashcode;
	}
	
	private String id;
	
	private Double rating;
	
	private String text;
	
	private String author;
	
	private Restaurant restaurant;

	private Date data;

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return id;
	}
	
	public String getORMID() {
		return getId();
	}

	public void setRating(Double value) {
		this.rating = value;
	}
	
	public Double getRating() {
		return rating;
	}
	
	public void setText(String value) {
		this.text = value;
	}
	
	public String getText() {
		return text;
	}
	
	public void setAuthor(String value) {
		this.author = value;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	public Restaurant getRestaurant() {
		return restaurant;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String toString() {
		return String.format("Review[id=%s, rating=%.2f, text=%s, date=%s]", id, rating, text, data);
	}

	public void setComment(String s) {
		this.text = s;
	}
}
