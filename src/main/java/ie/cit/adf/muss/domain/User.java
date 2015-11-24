package ie.cit.adf.muss.domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class User {

	@Id
	@GeneratedValue
	private int id;
	private String username;
	private String password;
	private String email;
	private String name;
	private String picture;
	private int points;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
	private Collection<Tag> tags;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="writer")
	private Collection<Review> written;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
      name="likes",
      joinColumns={
		 @JoinColumn(name="user_id", referencedColumnName="id")
      },
      inverseJoinColumns={
		  @JoinColumn(name="review_id", referencedColumnName="id")
	  }
	)
	private Collection<Review> likes;
	
	private Collection<User> following;
	
	private Collection<User> followed;

	public User() {
	}

	public User(int id, String username, String password, String email, String name, String picture, int points,
				Collection<Tag> tags) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
		this.picture = picture;
		this.points = points;
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", name="
				+ name + ", picture=" + picture + ", points=" + points + ", tags=" + tags + "]";
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@OneToMany(mappedBy = "user")
	public Collection<Tag> getTags() {
		return tags;
	}

	public void setTags(Collection<Tag> tags) {
		this.tags = tags;
	}

	@OneToMany(mappedBy = "writer")
	public Collection<Review> getWritten() {
		return written;
	}

	public void setWritten(Collection<Review> written) {
		this.written = written;
	}

	@ManyToMany
	public Collection<Review> getLikes() {
		return likes;
	}

	public void setLikes(Collection<Review> likes) {
		this.likes = likes;
	}
	
	@ManyToMany
	public Collection<User> getFollowing() {
		return following;
	}

	public void setFollowing(Collection<User> following) {
		this.following = following;
	}
	
	@ManyToMany
	public Collection<User> getFollowed() {
		return followed;
	}

	public void setFollowed(Collection<User> followed) {
		this.followed = followed;
	}
	
	

}
