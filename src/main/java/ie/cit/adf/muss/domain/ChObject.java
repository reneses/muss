package ie.cit.adf.muss.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ie.cit.adf.muss.domain.Image;
import ie.cit.adf.muss.domain.Participation;
import ie.cit.adf.muss.domain.Review;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Cultural Heritage Object
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChObject {

	@JsonIgnore
	private int id;

	@JsonProperty("id")
	private int originalId;

	private String title, date, medium, description;

	@JsonProperty("creditline")
	private String creditLine;

	@JsonProperty("gallery_text")
	private String galleryText;

	@JsonProperty("participants")
	private List<Participation> participations;

	@JsonIgnoreProperties("images")
	List<Image> images;

	@OneToMany(fetch= FetchType.LAZY, mappedBy="chObject")
	private Collection<Review> reviews;

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
		participations.forEach( p -> p.setObject(this));
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
		images.forEach( image -> image.setObject(this));
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