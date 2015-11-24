package ie.cit.adf.muss.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Image {
	
	@JsonProperty("image_id")
	private int id;
	private String label;
	private int width;
	private int height;
	@JsonProperty("is_primary")
	private int isPrimary;
	private String url;
	@JsonBackReference
	private ChObject chObject;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int isPrimary() {
		return isPrimary;
	}
	
	public void setPrimary(int isPrimary) {
		this.isPrimary = isPrimary;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@ManyToOne(optional=false)
	public ChObject getChObject() {
		return chObject;
	}
	
	public void setChObject(ChObject chObject) {
		this.chObject = chObject;
	}
	
}
