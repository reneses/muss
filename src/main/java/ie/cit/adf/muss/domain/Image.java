package ie.cit.adf.muss.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ie.cit.adf.muss.utility.JsonImageDeserializer;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Image
 */
@Entity
@JsonDeserialize(using = JsonImageDeserializer.class)
public class Image {

	@Id
	@GeneratedValue
    @JsonIgnore
    private int id;

    private int originalId;

    private boolean isPrimary;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="chobject_id")
    private ChObject chObject;

    @OneToMany(fetch= FetchType.EAGER, mappedBy="image", cascade=CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private List<ImageSize> sizes;

    public Image() {
    	sizes = new ArrayList<>();
    }
    
    /* GETTERS AND SETTERS */

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

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public ChObject getChObject() {
		return chObject;
	}

	public void setChObject(ChObject chObject) {
		this.chObject = chObject;
		if (!chObject.getImages().contains(this)) {
			chObject.getImages().add(this);
        }
	}

	public List<ImageSize> getSizes() {
        return sizes;
    }

    public void setSizes(List<ImageSize> sizes) {
        this.sizes = sizes;
        sizes.forEach(size -> size.setImage(this));
    }

    public void addSize(ImageSize size) {
        sizes.add(size);
    }

    public void removeSize(ImageSize size) {
        sizes.remove(size);
    }
}
