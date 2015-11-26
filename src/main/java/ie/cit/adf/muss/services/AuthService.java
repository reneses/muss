package ie.cit.adf.muss.services;

import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.domain.validation.UserForm;
import ie.cit.adf.muss.exceptions.EmailExistsException;
import ie.cit.adf.muss.exceptions.UsernameExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * TODO: add profile picture TODO: work with hashed passwords
 */
@Service
public class AuthService implements UserDetailsService {

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

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Assert.notNull(username);
		//
		// UserDetails result;
		//
		// result = userRepository.findByUsername(username);
		// Assert.notNull(result);
		// // WARNING: The following sentences prevent lazy initialisation
		// problems!
		// Assert.notNull(result.getAuthorities());
		// result.getAuthorities().size();

		return null;
	}

	public static User getPrincipal() {
		User result;
		SecurityContext context;
		Authentication authentication;
		Object principal;

		// If the asserts in this method fail, then you're
		// likely to have your Tomcat's working directory
		// corrupt. Please, clear your browser's cache, stop
		// Tomcat, update your Maven's project configuration,
		// clean your project, clean Tomcat's working directory,
		// republish your project, and start it over.

		context = SecurityContextHolder.getContext();
		Assert.notNull(context);
		authentication = context.getAuthentication();
		Assert.notNull(authentication);
		principal = authentication.getPrincipal();
		Assert.isTrue(principal instanceof User);
		result = (User) principal;
		Assert.notNull(result);
		Assert.isTrue(result.getId() != 0);

		return result;
	}

}
