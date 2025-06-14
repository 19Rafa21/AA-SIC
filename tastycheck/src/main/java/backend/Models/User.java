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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class User {
	public User() {
		this.restaurantsFav = new HashSet<>();
	}

	public User(String id, String username, String email, String password) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.restaurantsFav = new HashSet<>();
	}

	public Map<String,Object> claimsForJwt(){
		Map<String,Object> claims = new HashMap<>();
		claims.put("id", this.id);
		claims.put("username", this.username);
		claims.put("email", this.email);
		claims.put("profilePicture", this.profilePicture != null ? this.profilePicture : "");
		claims.put("role", this.getDiscriminator());
		return claims;
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

	private String profilePicture;

	//private Set<Review> reviews;

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

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	/*public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}
	 */

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
