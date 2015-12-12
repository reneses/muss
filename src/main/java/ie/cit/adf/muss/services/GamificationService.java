package ie.cit.adf.muss.services;

import javax.naming.OperationNotSupportedException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Gamification;
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
    
    // ----------------------- Constructor -----------------------
    
    // ------------------- Simple CRUD methods -------------------

    public Gamification findOne(){
		return gamificationRepository.findAll().iterator().next();
	}

	public Gamification create(ChObject chObject){
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
		gamification.setFollowedPoints(10);
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
 
}