package ie.cit.adf.muss.relations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ie.cit.adf.muss.MussApplication;
import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Image;
import ie.cit.adf.muss.domain.Review;
import ie.cit.adf.muss.domain.Tag;
import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.services.ChObjectService;
import ie.cit.adf.muss.services.ReviewService;
import ie.cit.adf.muss.services.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MussApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RelationTests {
	
	@Autowired
	ChObjectService chObjectService;
	@Autowired
	ReviewService reviewService;
	@Autowired
	UserService userService;

	@Test
	public void testChObjectImages() {
		
		long numChObjects = chObjectService.count();
		int newChObjectId = (int) (numChObjects+1);
		
		ChObject chObject = new ChObject();
		chObject.setTitle("MyObject");
		
		Image image1 = new Image();
		image1.setOriginalId(101);
		
		Image image2 = new Image();
		image1.setOriginalId(102);
		
		List<Image> images = new ArrayList<>();
		images.add(image1);
		images.add(image2);
		
		chObject.setImages(images);
		
		assertNotNull(image1.getChObject());
		assertNotNull(image2.getChObject());
		
		chObject = chObjectService.save(chObject);
		assertEquals(newChObjectId, chObject.getId());
		assertEquals(newChObjectId, image1.getChObject().getId());
		assertEquals(newChObjectId, image2.getChObject().getId());
		
		chObject = null;
		chObject = chObjectService.find(newChObjectId);
		assertNotNull(chObject);
		assertEquals(newChObjectId, chObject.getId());
		assertEquals("MyObject", chObject.getTitle());
		
		images = null;
		images = chObject.getImages();
		assertNotNull(images);
		assertEquals(2, images.size());
		
		image1 = images.get(0);
		image2 = images.get(1);
		assertEquals(newChObjectId, image1.getChObject().getId());
		assertEquals(newChObjectId, image2.getChObject().getId());
		
	}
	
	@Test
	public void testChObjectTags() {
		
		ChObject chObject = chObjectService.find(1);
		assertNotNull(chObject);
		
		Tag tag = new Tag();
		tag.setName("MyTag");
		
		chObject.addTag(tag);
		
		chObjectService.save(chObject);
		
		chObject = null;
		chObject = chObjectService.find(1);
		assertNotNull(chObject);
		assertEquals(1, chObject.getTags().size());
		
		List<Tag> tags = new ArrayList<>(chObject.getTags());
		tag = tags.get(0);
		assertEquals("MyTag", tag.getName());
		
	}
	
	@Test
	public void testChObjectReviews() {		
		Review review = new Review();
		review.setTitle("My Review");
		review.setContent("This is my review...");
		review.setDate(Calendar.getInstance().getTime());
		review.setRating(5);
	
		ChObject chObject = chObjectService.find(1);
		assertNotNull(chObject);
		
		int numReviews = chObject.getReviews().size();
		
		chObject.addReview(review);
		chObjectService.save(chObject);
		
		chObject = null;
		chObject = chObjectService.find(1);
		assertNotNull(chObject);
		assertEquals(numReviews+1, chObject.getReviews().size());
		
		List<Review> reviews = new ArrayList<>(chObject.getReviews());
		review = reviews.get(reviews.size()-1);
		assertEquals("My Review", review.getTitle());
	}
	
	@Test
	public void testChObjectLikes() {
		User user = userService.find(1);
		assertNotNull(user);	
		ChObject chObject = chObjectService.find(1);
		assertNotNull(chObject);
		
		chObject.addLike(user);
		
		chObjectService.save(chObject);
		
		chObject = null;
		chObject = chObjectService.find(1);
		assertNotNull(chObject);
		assertEquals(1, chObject.getLikes().size());
		
		List<User> likes = new ArrayList<>(chObject.getLikes());
		user = likes.get(0);
		assertEquals("MyName", user.getName());
	}
	
	@Test
	public void testReviewLikes() {
		User user = userService.find(1);
		assertNotNull(user);	
		Review review = reviewService.find(1);
		assertNotNull(review);
		
		review.addLike(user);
		
		reviewService.save(review);
		
		review = null;
		review = reviewService.find(1);
		assertNotNull(review);
		assertEquals(1, review.getLikes().size());
		
		List<User> likes = new ArrayList<>(review.getLikes());
		user = likes.get(0);
		assertEquals("MyName", user.getName());
	}
	
}
