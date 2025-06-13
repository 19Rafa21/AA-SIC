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

import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Set;

public class User {
	public User() {
		this.reviews = new HashSet<>();
	}

	public User(String id, String username, String email, String password) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.reviews = new HashSet<>();
	}

	public boolean equals(Object aObj) {
		if (aObj == this)
			return true;
		if (!(aObj instanceof User))
			return false;
		User user = (User)aObj;
		if ((getId() != null && !getId().equals(user.getId())) || (getId() == null && user.getId() != null))
			return false;
		return true;
	}
	
	public int hashCode() {
		int hashcode = 0;
		hashcode = hashcode + (getId() == null ? 0 : getId().hashCode());
		return hashcode;
	}
	
	private String id;
	
	private String username;
	
	private String password;
	
	private String email;

	private String avatar;

	private Set<Review> reviews;

	private Set<Restaurant> restaurantsFav;
	
	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return id;
	}

	public void setUsername(String value) {
		this.username = value;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setPassword(String value) {
		this.password = value;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setEmail(String value) {
		this.email = value;
	}
	
	public String getEmail() {
		return email;
	}

	@Transient
	public String getDiscriminator() {
		if (this instanceof Owner) return "Owner";
		else return "User";
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	public Set<Restaurant> getRestaurantsFav() {
		return restaurantsFav;
	}

	public void setRestaurantsFav(Set<Restaurant> restaurantsFav) {
		this.restaurantsFav = restaurantsFav;
	}

	public String toString() {
		return String.valueOf(getId());
	}
	
}
