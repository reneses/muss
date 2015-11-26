package ie.cit.adf.muss.domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Tag {

	@Id
	@GeneratedValue
	private int id;
	private String name;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
      name="tags_chobject",
      joinColumns={
		 @JoinColumn(name="tag_id", referencedColumnName="id")
      },
      inverseJoinColumns={
		  @JoinColumn(name="chobject_id", referencedColumnName="id")
	  }
	)
	private Collection<ChObject> chObjects;

//	public Tag(int id, String name, User user, Collection<ChObject> chObjects) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.user = user;
//		this.chObjects = chObjects;
//	}

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Collection<ChObject> getChObjects() {
		return chObjects;
	}

	public void setChObjects(Collection<ChObject> chObjects) {
		this.chObjects = chObjects;
	}

}
