package ie.cit.adf.muss.services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import ie.cit.adf.muss.domain.Gamification;
import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.domain.notifications.FollowNotification;
import ie.cit.adf.muss.repositories.UserRepository;

@org.springframework.stereotype.Service
public class UserService extends CrudService<User> {

	// ------------------- Managed repository --------------------
	
    @Autowired
    UserRepository repository;

    // ------------------- Supporting services -------------------

    @Autowired
	AuthService authService;
    @Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	MussNotificationService notificationService;

	@Autowired
	GamificationService gamificationService;

    // ----------------------- Constructor -----------------------
    
    // ------------------- Simple CRUD methods -------------------

    public boolean usernameExists(String username) {
        User user = repository.findByUsername(username);
        return user != null;
    }

    public boolean emailExists(String email) {
        User user = repository.findByEmail(email);
        return user != null;
    }
    
	public User registerUser(User user) {
		// Encode the password
		String rawPassword = user.getPassword();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		user.setPassword(encodedPassword);
		
		return repository.save(user);
	}

    // ----------------- Other business methods ------------------
		
	public String encodePassword(String rawPassword) {
		String encodedPassword = passwordEncoder.encode(rawPassword);
		return encodedPassword;
	}
	
	public boolean passwordMatches(User user, String rawPassword) {
		return passwordEncoder.matches(rawPassword, user.getPassword());
	}
	
    // REPOSITORY:

	public User findByUsername(String username) {
		return repository.findByUsername(username);
	}
	
    // USE CASES:
	
	public void followUser(int userID) {
		User user = find(userID);
		User principal = authService.getPrincipal();
		Assert.notNull(user);
		Assert.notNull(principal);
		Assert.isTrue(user.getId() != principal.getId());
		
		List<User> followed = new ArrayList<>(principal.getFollowed());
		
		if(!followed.contains(user)){
			followed.add(user);
			principal.setFollowed(followed);
			save(principal);

			FollowNotification notification = new FollowNotification(principal, user);
			notificationService.notificateFollowers(notification, user);

			gamificationService.assignPoints(Gamification.FOLLOWING, principal);
			gamificationService.assignPoints(Gamification.FOLLOWERS, user);
		}
	}

	public void unFollowUser(int userID) {
		User user = find(userID);
		User principal = authService.getPrincipal();
		Assert.notNull(user);
		Assert.notNull(principal);
		
		List<User> followed = new ArrayList<>(principal.getFollowed());
		
		if(followed.contains(user)){
			followed.remove(user);
			principal.setFollowed(followed);
			save(principal);

			gamificationService.removePoints(Gamification.FOLLOWING, principal);
			gamificationService.removePoints(Gamification.FOLLOWERS, user);
		}
	}
}
