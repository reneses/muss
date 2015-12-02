package ie.cit.adf.muss.validation;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import ie.cit.adf.muss.validation.annotation.EmailAvailable;
import ie.cit.adf.muss.validation.annotation.UsernameAvailable;

public class UserForm {

    @NotBlank
    @Length(min = 4)
    private String name;
    
    @NotBlank
    @Email
    @EmailAvailable
    private String email;

    @NotBlank
    @Length(min = 8)
    private String password;

    @NotBlank
    @Length(min = 4)
    @UsernameAvailable
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
