package ie.cit.adf.muss.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Review;
import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.repositories.ReviewRepository;

@Service
@Transactional
public class ReviewService{

	// ------------------- Managed repository --------------------
	
    @Autowired
    ReviewRepository reviewRepository;
    
    // ------------------- Supporting services -------------------

    // ----------------------- Constructor -----------------------
    
    // ------------------- Simple CRUD methods -------------------

	public Collection<Review> findAll(){
		return (Collection<Review>) reviewRepository.findAll();
	}

	public Review create(ChObject chObject){
		Review review = new Review();
		User user = LoginService.getPrincipal();
		
		review.setWriter(user);
		review.setDate(new Date());
		review.setChObject(chObject);
		review.setLikes(new ArrayList<>());
		
		return review;
    }

    public void save(Review review){
    	Assert.notNull(review);
    	Assert.notNull(review.getChObject());
//    	checkPrincipal(review);

    	reviewRepository.save(review);
    }

    public void delete (Review review){
    	Assert.notNull(review);
//    	checkPrincipal(review);
    	
    	reviewRepository.delete(review);
    }
    
    // ----------------- Other business methods ------------------

    // REPOSITORY:

    // USE CASES:
    
    public void likeReview(Review review){
    	
    	User user = LoginService.getPrincipal();
    	Review result = reviewRepository.findOne(review.getId());
    	
    	List<User> users = new ArrayList<> (result.getLikes());
    	users.add(user);
    	
    	result.setLikes(users);
    	reviewRepository.save(result);
    }
 
}
