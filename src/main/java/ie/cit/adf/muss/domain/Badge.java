package ie.cit.adf.muss.domain;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Badge {
	
	// --------------------------- Values ----------------------------
	
	public static final String FOLLOWING = "FOLLOWING";
	public static final String FOLLOWERS = "FOLLOWERS";
	public static final String POINTS = "POINTS";
	public static final String COMPLETED = "COMPLETED";
//	private static enum types { USERS, POINTS, COMPLETED };
	
	// ------------------------- Attributes --------------------------
	
	@Id
	@GeneratedValue
	private int id;
	
	@NotBlank
	private String name;
	@NotNull
	@Min(value=1)
	private Integer quantity;
	@Pattern(regexp = "^" + FOLLOWING + "|" + FOLLOWERS + "|" + POINTS + "|" + COMPLETED + "$")
	private String type;
	@NotNull
	private String image;

	@ManyToMany(fetch= FetchType.EAGER, mappedBy="badges", cascade=CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Collection<User> users;
	
	// ---------------------- Getters & Setters ----------------------
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Collection<User> getUsers() {
		return users;
	}
	public void setUsers(Collection<User> users) {
		this.users = users;
	}
	
	// -------------------------- Relations --------------------------
	
//	private User user;
//	
//	@Valid
//	@ManyToOne
//	public User getUser() {
//		return user;
//	}
//	public void setUser(User user) {
//		this.user = user;
//	}
	
	// ------------------------ Quick methods ------------------------

}