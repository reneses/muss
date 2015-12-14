package ie.cit.adf.muss.services;

import javax.naming.OperationNotSupportedException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ie.cit.adf.muss.domain.Gamification;
import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.repositories.GamificationRepository;

@Service
@Transactional
public class GamificationService{

	// ------------------- Managed repository --------------------
	
    @Autowired
    GamificationRepository gamificationRepository;
    
    // ------------------- Supporting services -------------------

    @Autowired
	AuthService authService;
    @Autowired
	UserService userService;
    @Autowired
  	BadgeService badgeService;
    
    // ----------------------- Constructor -----------------------
    
    // ------------------- Simple CRUD methods -------------------

    public Gamification findOne(){
		return gamificationRepository.findAll().iterator().next();
	}

	public Gamification create(){
//		Assert.isTrue(isAdmin());

		//	Singleton behavior, if exist, return it
		Gamification gamification = findOne();
		if(gamification != null)
			return gamification;
		
		//	If don't, create one (default values)
		gamification = new Gamification();
		gamification.setTagPoints(10);
		gamification.setDescriptionPoints(15);
		gamification.setReviewPoints(15);
		gamification.setLikesPoints(5);
		gamification.setFollowingPoints(10);
		gamification.setFollowersPoints(20);
		
		return gamification;
    }

    public void save(Gamification gamification){
    	Assert.notNull(gamification);
//		Assert.isTrue(isAdmin());

    	gamificationRepository.save(gamification);
    }

    public void delete (Gamification gamification) throws OperationNotSupportedException{
    	throw new OperationNotSupportedException("Operation not supported");
    }
    
    // ----------------- Other business methods ------------------

    // REPOSITORY:

    // USE CASES:
    
    public void assignPoints(String type){
    	Gamification gamification = findOne();
    	User user = authService.getPrincipal();
    	Assert.notNull(gamification);
    	Assert.notNull(user);
    	
		Integer points;
		switch(type){
	    	case Gamification.TAG:
	    		points = gamification.getTagPoints();
	    		break;
	    	case Gamification.DESCRIPTION:
	    		points = gamification.getDescriptionPoints();
	    		break;
	    	case Gamification.REVIEW:
	    		points = gamification.getReviewPoints();
	    		break;
	    	case Gamification.LIKES:
	    		points = gamification.getLikesPoints();
	    		break;
	    	case Gamification.FOLLOWING:
	    		points = gamification.getFollowingPoints();
	    		break;
	    	case Gamification.FOLLOWERS:
	    		points = gamification.getFollowersPoints();
	    		break;
	    	default:
	    		throw new IllegalArgumentException("Incorrect type for point assignation ");
    	}

    	user.setPoints(user.getPoints() + points);
    	userService.save(user);
    	
    	if(type == Gamification.FOLLOWING || type == Gamification.FOLLOWERS)
    		badgeService.assignBadgeFriends();
    	badgeService.assignBadgePoints();
    }
}