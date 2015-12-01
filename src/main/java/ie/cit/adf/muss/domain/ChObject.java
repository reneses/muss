package ie.cit.adf.muss.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ie.cit.adf.muss.domain.Image;
import ie.cit.adf.muss.domain.Participation;
import ie.cit.adf.muss.domain.Review;

import javax.persistence.*;

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
	@OneToMany(fetch= FetchType.EAGER, mappedBy="chObject")
	private List<Participation> participations;

	@JsonIgnoreProperties("images")
	@OneToMany(fetch= FetchType.EAGER, mappedBy="chObject", cascade=CascadeType.ALL)
	private List<Image> images;

	@OneToMany(fetch= FetchType.EAGER, mappedBy="chObject")
	private Collection<Review> reviews;
	
	@ManyToMany(fetch= FetchType.EAGER)
	@JoinTable(
			name="chobjects_tags",
			joinColumns={@JoinColumn(name="chobject_id", referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="tag_id", referencedColumnName="id")}
	)
	private Collection<Tag> tags;
	
	@ManyToMany(fetch= FetchType.EAGER, mappedBy="chObjectLikes")
	private Collection<User> likes;

	public ChObject() {
		participations = new ArrayList<>();
		images = new ArrayList<>();
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
	}

	public Collection<User> getLikes() {
		return likes;
	}

	public void setLikes(Collection<User> likes) {
		this.likes = likes;
	}

	public Collection<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Collection<Review> reviews) {
		this.reviews = reviews;
	}

    /* TO STRING */

	@Override
	public String toString() {
		return "ChObject{" +
				"title='" + title + '\'' +
				", id=" + id +
				'}';
	}
}