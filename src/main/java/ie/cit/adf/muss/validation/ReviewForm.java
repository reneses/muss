package ie.cit.adf.muss.validation;

import ie.cit.adf.muss.validation.annotation.EmailAvailable;
import ie.cit.adf.muss.validation.annotation.UsernameAvailable;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class ReviewForm {

    @NotBlank
    @Length(min = 4)
    private String title;
    
    @NotBlank
    private String content;

    @Min(1)
    @Max(5)
    private Integer rating;

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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
