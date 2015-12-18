package ie.cit.adf.muss.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Cultural Heritage Object
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChObject {

	@Id
	@GeneratedValue
	@JsonIgnore
	private int id;

	@JsonProperty("id")
	private int originalId;

	private String title, date, medium;

	@Column(columnDefinition = "TEXT")
	private String description;

	@JsonProperty("creditline")
	private String creditLine;

	@JsonProperty("gallery_text")
	@Column(columnDefinition = "TEXT")
	private String galleryText;

	@JsonProperty("participants")
	@OneToMany(fetch= FetchType.EAGER, mappedBy="chObject", cascade=CascadeType.ALL)
	private List<Participation> participations;

	@JsonIgnoreProperties("images")
	@OneToMany(fetch= FetchType.EAGER, mappedBy="chObject", cascade=CascadeType.ALL)
	private List<Image> images;

	@OneToMany(fetch= FetchType.EAGER, mappedBy="chObject", cascade=CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Collection<Review> reviews;
	
	@OneToMany(fetch= FetchType.EAGER, mappedBy="chObject", cascade=CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Collection<Tag> tags;
	
	@ManyToMany(fetch= FetchType.EAGER, mappedBy="chObjectLikes", cascade=CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Collection<User> likes;

	public ChObject() {
		participations = new ArrayList<>();
		images = new ArrayList<>();
		tags = new ArrayList<>();
		reviews = new ArrayList<>();
		likes = new ArrayList<>();
	}

	/* SETTERS AND GETTERS */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOriginalId() {
		return originalId;
	}

	public void setOriginalId(int originalId) {
		this.originalId = originalId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreditLine() {
		return creditLine;
	}

	public void setCreditLine(String creditLine) {
		this.creditLine = creditLine;
	}

	public String getGalleryText() {
		return galleryText;
	}

	public void setGalleryText(String galleryText) {
		this.galleryText = galleryText;
	}

	public List<Participation> getParticipations() {
		return participations;
	}

	public void setParticipations(List<Participation> participations) {
		this.participations = participations;
		participations.forEach(p -> p.setChObject(this));
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
		images.forEach(image -> image.setChObject(this));
	}

	public Collection<Tag> getTags() {
		return tags;
	}

	public void setTags(Collection<Tag> tags) {
		this.tags = tags;
		tags.forEach(tag -> tag.setChObject(this));
	}
	
	public void addTag(Tag tag) {
		tags.add(tag);
		tag.setChObject(this);
	}

	public Tag getTagByName(String name) {
		for (Tag tag : tags) {
			if (tag.getName().equals(name))
				return tag;
		}
		return null;
	}

	public Collection<User> getLikes() {
		return likes;
	}

	public void setLikes(Collection<User> likes) {
		this.likes = likes;
		likes.forEach(user -> {
			if (!user.getChObjectLikes().contains(this))
				user.getChObjectLikes().add(this);
		});
	}

	public boolean addLike(User user) {
		if (!this.likes.contains(user)) {
			this.likes.add(user);
			if (!user.getChObjectLikes().contains(this))
				return user.getChObjectLikes().add(this);
		}
		return false;
	}

	public boolean removeLike(User user) {
		user.getChObjectLikes().remove(this);
		return this.likes.remove(user);
	}

	public Review getReviewByUser(User user) {
		for (Review review : reviews) {
			if (review.getUser().equals(user))
				return review;
		}
		return null;
	}

	public Collection<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Collection<Review> reviews) {
		this.reviews = reviews;
		reviews.forEach(review -> review.setChObject(this));
	}
	
	public void addReview(Review review) {
		reviews.add(review);
		review.setChObject(this);
	}

    /* TO STRING */

	@Override
	public String toString() {
		return "ChObject{" +
				"title='" + title + '\'' +
				", id=" + id +
				'}';
	}
	
	@Override
    public boolean equals(Object obj) {
        return obj instanceof ChObject && id > 0 && id == ((ChObject) obj).getId();
    }

    @Override
    public int hashCode() {
        return id * title.hashCode();
    }
}