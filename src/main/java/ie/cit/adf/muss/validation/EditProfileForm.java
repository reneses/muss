package ie.cit.adf.muss.validation;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class EditProfileForm {
	
	@NotBlank
    @Length(min = 4)
    private String name;
    
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 4)
    private String username;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
