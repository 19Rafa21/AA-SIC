/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: Bruno Alves(Universidade do Minho)
 * License Type: Academic
 */
package backend;

public class Reply {
	public Reply() {
	}
	
	public boolean equals(Object aObj) {
		if (aObj == this)
			return true;
		if (!(aObj instanceof Reply))
			return false;
		Reply reply = (Reply)aObj;
		if ((getId() != null && !getId().equals(reply.getId())) || (getId() == null && reply.getId() != null))
			return false;
		return true;
	}
	
	public int hashCode() {
		int hashcode = 0;
		hashcode = hashcode + (getId() == null ? 0 : getId().hashCode());
		return hashcode;
	}
	
	private String id;
	
	private String text;
	
	private String review;
	
	private String author;
	
	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return id;
	}
	
	public String getORMID() {
		return getId();
	}
	
	public void setText(String value) {
		this.text = value;
	}
	
	public String getText() {
		return text;
	}
	
	public void setReview(String value) {
		this.review = value;
	}
	
	public String getReview() {
		return review;
	}
	
	public void setAuthor(String value) {
		this.author = value;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String toString() {
		return String.valueOf(getId());
	}
	
}
