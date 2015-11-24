package ie.cit.adf.muss.services;


import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.domain.validation.UserForm;
import ie.cit.adf.muss.exceptions.EmailExistsException;
import ie.cit.adf.muss.exceptions.UsernameExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO: add profile picture
 * TODO: work with hashed passwords
 */
@Service
public class AuthService {

    @Autowired
    UserService userService;

    @Transactional
    public User register(UserForm userForm) throws UsernameExistsException, EmailExistsException {

        // Check that there is no user registered with the same username / email
        if (userService.usernameExists(userForm.getUsername()))
            throw new UsernameExistsException("There already exists an account with the username: "+ userForm.getUsername());
        if (userService.emailExists(userForm.getEmail()))
            throw new EmailExistsException("There already exists an account with the email: "+ userForm.getEmail());

        // Create and store the user
        User user = new User();
        user.setName(userForm.getName());
        user.setUsername(userForm.getUsername());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        return userService.save(user);

    }

}
