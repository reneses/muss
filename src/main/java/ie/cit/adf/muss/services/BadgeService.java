package ie.cit.adf.muss.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.OperationNotSupportedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import ie.cit.adf.muss.domain.Badge;
import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.repositories.BadgeRepository;

@Service
//@Transactional
public class BadgeService{

	// ------------------- Managed repository --------------------
	
    @Autowired
    BadgeRepository badgeRepository;
    
    // ------------------- Supporting services -------------------

    @Autowired
	AuthService authService;
    @Autowired
	UserService userService;
    
    // ----------------------- Constructor -----------------------
    
    // ------------------- Simple CRUD methods -------------------

    public Badge findOne(Integer id){
		return badgeRepository.findOne(id);
	}

	public Badge create(ChObject chObject){
//		Assert.isTrue(isAdmin());
		
		return new Badge();
    }

    public void save(Badge badge){
    	Assert.notNull(badge);
//		Assert.isTrue(isAdmin());
    	
    	badgeRepository.save(badge);
    }

    public void delete (Badge badge) throws OperationNotSupportedException{
    	throw new OperationNotSupportedException("Operation not supported");
    }
    
    // ----------------- Other business methods ------------------

    // REPOSITORY:

    // USE CASES:
    
    public void assignBadgePoints(User user){
//    	User user = authService.getPrincipal();
    	Assert.notNull(user);
    	
    	List<Badge> assignable = badgeRepository.findAssignableBadges(Badge.POINTS, user.getPoints());
    	
    	assignBadge(assignable, user);
	}
    
    public void assignBadgeFriends(User user){
//    	User user = authService.getPrincipal();
    	Assert.notNull(user);
    	
    	List<Badge> assignableWED = badgeRepository.findAssignableBadges(Badge.FOLLOWING,
    																		user.getFollowed().size());
    	List<Badge> assignableWER = badgeRepository.findAssignableBadges(Badge.FOLLOWERS,
																			user.getFollowers().size());
    	
    	assignBadge(assignableWED, user);
    	assignBadge(assignableWER, user);
    }
    
    public void assignBadgeCompletedByName(String name){
    	User user = authService.getPrincipal();
    	Assert.notNull(user);
    
    	List<Badge> assignable = badgeRepository.findByName(name);
    	
    	assignBadge(assignable, user);
    }
    
    // Ignores duplicated badges, ignores if there is no change
    public void assignBadge(List<Badge> assignable, User user){
    	
    	Set<Badge> badges = new HashSet<Badge>(user.getBadges());
    	badges.addAll(assignable);
    	
    	badges.forEach(b -> {
    		if (!user.getBadges().contains(b))
    			user.getBadges().add(b);
    	});

    	userService.save(user);
    }
    
}