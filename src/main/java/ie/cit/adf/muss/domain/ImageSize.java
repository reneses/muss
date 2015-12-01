package ie.cit.adf.muss.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Image size
 */
@Entity
public class ImageSize {

	@Id
	@GeneratedValue
	@JsonIgnore
	private int id;
	
    private int width, height;
    private String url;

    @JsonIgnore
    private String label;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="image_id")
    private Image image;
    
    public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
		if (!image.getSizes().contains(this)) {
			image.getSizes().add(this);
        }
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        if (this.label != null)
            throw new IllegalArgumentException("Once set, the label cannot be changed");
        this.label = label;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
    
}
