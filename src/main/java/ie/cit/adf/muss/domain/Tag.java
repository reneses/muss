package ie.cit.adf.muss.domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Tag {

	private int id;
	private String tag;

	private User user;

	private Collection<ChObject> chObjects;

	public Tag(int id, String tag, User user, Collection<ChObject> chObjects) {
		super();
		this.id = id;
		this.tag = tag;
		this.user = user;
		this.chObjects = chObjects;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@ManyToMany
	public Collection<ChObject> getChObjects() {
		return chObjects;
	}

	public void setChObjects(Collection<ChObject> chObjects) {
		this.chObjects = chObjects;
	}

	@ManyToOne(optional = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", tag=" + tag + ", user=" + user + ", chObjects=" + chObjects + "]";
	}

}
