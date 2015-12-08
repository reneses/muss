package ie.cit.adf.muss.validation;

import org.hibernate.validator.constraints.NotBlank;

public class TagForm {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
