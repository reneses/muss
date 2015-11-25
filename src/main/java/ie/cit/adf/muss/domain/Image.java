package ie.cit.adf.muss.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ie.cit.adf.muss.domain.ImageSize;
import ie.cit.adf.muss.utility.JsonImageDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Image
 */
@JsonDeserialize(using = JsonImageDeserializer.class)
public class Image {

    @JsonIgnore
    private int id;

    private int originalId;

    private boolean isPrimary;

    @JsonIgnore
    private ChObject object;

    private Map<String, ImageSize> sizes = new HashMap<>();


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

    public ChObject getObject() {
        return object;
    }

    public void setObject(ChObject object) {
        if (this.object != null)
            throw new IllegalArgumentException("Once set, the object cannot be changed");
        this.object = object;
    }

    public Map<String, ImageSize> getSizes() {
        return sizes;
    }

    public void setSizes(Map<String, ImageSize> sizes) {
        this.sizes = sizes;
    }

    public void addSize(ImageSize size) {
        sizes.put(size.getLabel(), size);
    }

    public void removeSize(ImageSize size) {
        sizes.remove(size.getLabel());
    }
}
