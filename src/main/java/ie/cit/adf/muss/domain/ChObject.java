package ie.cit.adf.muss.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChObject {

	@Id
	private int id;
	private String title;
	private String date;
	private String medium;
	@JsonProperty("creditline")
	private String creditLine;
	private String description;
	@JsonProperty("gallery_text")
	private String galleryText;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="chObject")
	private Collection<Review> reviews;
	
	@JsonManagedReference
	@JsonProperty("participants")
	private List<Participation> participations;
	
	@JsonProperty("images")
	private List<Map<String, Image>> images;

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

	public String getCreditLine() {
		return creditLine;
	}

	public void setCreditLine(String creditLine) {
		this.creditLine = creditLine;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGalleryText() {
		return galleryText;
	}

	public void setGalleryText(String galleryText) {
		this.galleryText = galleryText;
	}

	@OneToMany(mappedBy = "chObject")
	public List<Participation> getParticipations() {
		return participations;
	}

	public void setParticipations(List<Participation> participations) {
		this.participations = participations;
	}

	public List<Map<String, Image>> getImages() {
		return images;
	}

	public void setImages(List<Map<String, Image>> images) {
		this.images = images;
	}

	@OneToMany(mappedBy = "chObject")
	public Collection<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Collection<Review> reviews) {
		this.reviews = reviews;
	}

}
