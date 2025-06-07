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

@Entity
@Table(name = "Review")
public class Review {

	@Id
	@Column(name = "Id")
	private String id;

	@Column(name = "Rating")
	private Double rating;

	@Column(name = "Text")
	private String text;

	@ManyToOne
	@JoinColumn(name = "Author")
	private Client author;

	@ManyToOne
	@JoinColumn(name = "Restaurant")
	private Restaurant restaurant;
	
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
	
	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return id;
	}
	
	public String getORMID() {
		return getId();
	}
	
	public void setRating(double value) {
		setRating(Double.valueOf(value));
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
	
	public void setAuthor(Client value) {
		this.author = value;
	}
	
	public Client getAuthor() {
		return author;
	}
	
	public void setRestaurant(Restaurant value) {
		this.restaurant = value;
	}
	
	public Restaurant getRestaurant() {
		return restaurant;
	}
	
	public void setRating(Double rating) {
		//TODO: Implement Method
		throw new UnsupportedOperationException();
	}

	public String toString() {
		return String.valueOf(getId());
	}

}
