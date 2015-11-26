package ie.cit.adf.muss.domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Review {

	@Id
	@GeneratedValue
	private int id;
	private String title;
	private String content;
	private Date date;
	private Integer rating;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="chobject_id")
	private ChObject chObject;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="writer_id")
	private User writer;
	
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="likes")
	private Collection<User> likes;

//	public Review(int id, String title, String content, Date date, Integer rating, ChObject chObject, User writer,
//			Collection<User> likes) {
//		super();
//		this.id = id;
//		this.title = title;
//		this.content = content;
//		this.date = date;
//		this.rating = rating;
//		this.chObject = chObject;
//		this.writer = writer;
//		this.likes = likes;
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public ChObject getChObject() {
		return chObject;
	}

	public void setChObject(ChObject chObject) {
		this.chObject = chObject;
	}

	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public Collection<User> getLikes() {
		return likes;
	}

	public void setLikes(Collection<User> likes) {
		this.likes = likes;
	}

}
