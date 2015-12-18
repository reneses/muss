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
import ie.cit.adf.muss.domain.Gamification;
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

    @Autowired
	AuthService authService;
    @Autowired
	ChObjectService chObjectService;
    @Autowired
	GamificationService gamificationService;
    
    // ----------------------- Constructor -----------------------
    
    // ------------------- Simple CRUD methods -------------------
    
    public Review find(int id) {
    	return reviewRepository.findOne(id);
    }

	public Collection<Review> findAll(){
		return (Collection<Review>) reviewRepository.findAll();
	}

	public Review create(ChObject chObject){
		Review review = new Review();
		User user = authService.getPrincipal();
		
		review.setUser(user);
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
    	User user = authService.getPrincipal();
    	Review result = reviewRepository.findOne(review.getId());
    	
    	List<User> users = new ArrayList<> (result.getLikes());
    	users.add(user);
    	
    	result.setLikes(users);
    	reviewRepository.save(result);
    }

	public void addReview(ChObject object, String title, Integer rating, String content) {
		User principal = authService.getPrincipal();
		
		Review review = new Review();
		review.setRating(rating);
		review.setTitle(title);
		review.setContent(content);
		review.setDate(new Date());
		review.setUser(principal);

		object.addReview(review);
		chObjectService.save(object);
		
		gamificationService.assignPoints(Gamification.REVIEW, principal);
	}

	public boolean hasReviewBy(ChObject object, User user) {
		if (user == null)
			return false;
		for (Review review :object.getReviews())
			if (review.getUser().equals(user))
				return true;
		return false;
	}

	public void addLike(Review review, User user) {
		boolean real = review.addLike(user);
		save(review);
		
		if(real){
			gamificationService.assignPoints(Gamification.LIKEGIVEN, user);
			User reviewPrincipal = review.getUser();
			if(reviewPrincipal!=null)
				gamificationService.assignPoints(Gamification.LIKERECEIVED, reviewPrincipal);
		}
	}

	public void removeLike(Review review, User user) {
		boolean real = review.removeLike(user);
		save(review);
		
		if(real){
			gamificationService.removePoints(Gamification.LIKEGIVEN, user);
			User reviewPrincipal = review.getUser();
			if(reviewPrincipal!=null)
				gamificationService.removePoints(Gamification.LIKERECEIVED, reviewPrincipal);
		}
	}
 
}
