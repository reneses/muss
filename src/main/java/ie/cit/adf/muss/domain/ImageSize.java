package ie.cit.adf.muss.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Image size
 */
public class ImageSize {

    private int width, height;
    private String url;

    @JsonIgnore
    private String label;

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
}
