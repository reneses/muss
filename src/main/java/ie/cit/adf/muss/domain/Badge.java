package ie.cit.adf.muss.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
	private Integer quantity;
	@Pattern(regexp = "^" + FOLLOWING + "|" + FOLLOWERS + "|" + POINTS + "|" + COMPLETED + "$")
	private String type;

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