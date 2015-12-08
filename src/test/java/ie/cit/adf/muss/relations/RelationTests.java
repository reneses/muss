package ie.cit.adf.muss.relations;

import static org.junit.Assert.*;

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
import ie.cit.adf.muss.services.ChObjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MussApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RelationTests {
	
	@Autowired
	ChObjectService chObjectService;

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
		chObject.addReview(review);
		chObjectService.save(chObject);
		
		chObject = null;
		chObject = chObjectService.find(1);
		assertNotNull(chObject);
		assertEquals(1, chObject.getReviews().size());
	}
	
}
