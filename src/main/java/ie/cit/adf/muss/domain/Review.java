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

	public Review(int id, String title, String content, Date date, Integer rating, ChObject chObject, User writer,
			Collection<User> likes) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.date = date;
		this.rating = rating;
		this.chObject = chObject;
		this.writer = writer;
		this.likes = likes;
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

	@ManyToOne(optional = false)
	public ChObject getChObject() {
		return chObject;
	}

	public void setChObject(ChObject chObject) {
		this.chObject = chObject;
	}

	@ManyToOne(optional = false)
	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	@ManyToMany
	public Collection<User> getLikes() {
		return likes;
	}

	public void setLikes(Collection<User> likes) {
		this.likes = likes;
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
		return "Review [id=" + id + ", title=" + title + ", content=" + content + ", date=" + date + ", rating="
				+ rating + ", chObject=" + chObject + ", writer=" + writer + ", likes=" + likes + "]";
	}

}
