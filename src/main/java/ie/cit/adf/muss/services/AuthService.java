package ie.cit.adf.muss.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.exceptions.EmailExistsException;
import ie.cit.adf.muss.exceptions.UsernameExistsException;
import ie.cit.adf.muss.validation.UserForm;

@Service
public class AuthService {

    @Autowired
    UserService userService;

    @Transactional
    public User register(UserForm userForm) throws UsernameExistsException, EmailExistsException {

        // Check that there is no user registered with the same username / email
        if (userService.usernameExists(userForm.getUsername()))
            throw new UsernameExistsException(
                    "There already exists an account with the username: " + userForm.getUsername());
        if (userService.emailExists(userForm.getEmail()))
            throw new EmailExistsException("There already exists an account with the email: " + userForm.getEmail());

        // Create and store the user
        User user = new User();
        user.setName(userForm.getName());
        user.setUsername(userForm.getUsername());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        return userService.save(user);

    }

    // Managed repository -----------------------------------------------------

    // Business methods -------------------------------------------------------

    public User getPrincipal() {

        // Obtain the principal
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        // If the user is not logged, return null
        if (principal.equals("anonymousUser"))
            return null;

        // If it is, return find it and return it
        org.springframework.security.core.userdetails.User userDB =
                (org.springframework.security.core.userdetails.User) principal;

        return userService.findByUsername(userDB.getUsername());
    }
    
    public void updatePrincipal(User user) {
    	
    	UserDetails currentPrincipal;
    	currentPrincipal = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    	    	
    	UserDetails newPrincipal;
    	newPrincipal = new org.springframework.security.core.userdetails.User(
    			user.getUsername(),
    			user.getPassword(),
    			currentPrincipal.getAuthorities());
    	
    	Authentication authentication = new UsernamePasswordAuthenticationToken(newPrincipal, newPrincipal.getPassword(), newPrincipal.getAuthorities());
    	SecurityContextHolder.getContext().setAuthentication(authentication);

    }

}
