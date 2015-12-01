package ie.cit.adf.muss.services;

import ie.cit.adf.muss.MussApplication;
import ie.cit.adf.muss.domain.Image;
import ie.cit.adf.muss.domain.ImageSize;
import ie.cit.adf.muss.repositories.ChObjectRepository;
import ie.cit.adf.muss.repositories.ImageRepository;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MussApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@Transactional
public class ImageServiceTest {

    @Autowired
    ImageService imageService;

    @Autowired
    ChObjectService objectService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    Image image;
    ImageSize size;

    private void assertEqualsImage(Image expected, Image actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getOriginalId(), actual.getOriginalId());
        assertEquals(expected.isPrimary(), actual.isPrimary());
        //expected.getSizes().forEach( (label, size) -> {
        //    ImageSize actualSize = actual.getSizes().getOrDefault(label, null);
        //    assertNotNull(actualSize);
        //    assertEquals(size.getHeight(), actualSize.getHeight());
        //    assertEquals(size.getWidth(), actualSize.getWidth());
        //    assertEquals(size.getUrl(), actualSize.getUrl());
        //});
    }

    @Before
    public void setUp() throws Exception {

        image = new Image();
        image.setChObject(objectService.find(1));
        image.setOriginalId(123);
        image.setPrimary(true);
        size = new ImageSize();
        size.setHeight(200);
        size.setWidth(200);
        size.setUrl("URL_z");
        size.setLabel("z");
        image.addSize(size);
        assertFalse("Test data is corrupted. Image is not supposed to be saved.", imageService.findAll().contains(image));
    }

    @Test
    public void testFindAll() throws Exception {
        List<Image> images = imageService.findAll();
        assertEquals(2, images.size());
    }

    @Test
    public void testGet() throws Exception {
        Image image = imageService.find(1);
        assertEquals(1001, image.getOriginalId());
        assertTrue(image.isPrimary());
       // assertEquals(200, image.getSizes().get("a").getWidth());
       // assertEquals(100, image.getSizes().get("a").getHeight());
       // assertEquals("image1.ie_a", image.getSizes().get("a").getUrl());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetNegative() throws Exception {
        imageService.find(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetZero() throws Exception {
        imageService.find(0);
    }

    @Test
    public void testGetNotExisting() throws Exception {
        Image image = imageService.find(Integer.MAX_VALUE);
        assertNull(image);
    }

    @Test
    public void testGetByOriginalId() throws Exception {
        Image image = imageService.findOneByOriginalId(1001);
        assertEquals(1001, image.getOriginalId());
        assertTrue(image.isPrimary());
        //assertEquals(200, image.getSizes().get("a").getWidth());
        //assertEquals(100, image.getSizes().get("a").getHeight());
        //assertEquals("image1.ie_a", image.getSizes().get("a").getUrl());
    }

    @Test
    public void testGetByOriginalIdNotExisting() throws Exception {
        Image image = imageService.findOneByOriginalId(Integer.MAX_VALUE);
        assertNull(image);
    }

    @Test
    public void testGetByOriginalIdNegative() throws Exception {
        Image image = imageService.findOneByOriginalId(-1);
        assertNull(image);
    }

    @Test(expected = IllegalArgumentException.class)
    //@Transactional
    public void testSaveNull() throws Exception {
        imageService.save((Image) null);
    }

    @Test
    //@Transactional
    public void testSaveInserting() throws Exception {

        int numberOfImages = imageService.findAll().size();

        imageService.save(image);

        assertTrue(image.getId() != 0);
        assertEquals(numberOfImages + 1, imageService.findAll().size());
        Image insertedImage = imageService.find(image.getId());
        assertNotNull(insertedImage);
        assertEqualsImage(image, insertedImage);

    }


    @Test
    //@Transactional
    public void testSaveUpdating() throws Exception {

        int numberOfImages = imageService.findAll().size();

        imageService.save(image);
        image.setPrimary(false);
        //ImageSize size = image.getSizes().get("z");
        //size.setHeight(10);
        //size.setWidth(20);
        //size.setUrl("X");
        imageService.save(image);

        assertEquals(numberOfImages + 1, imageService.findAll().size());
        Image insertedImage = imageService.find(image.getId());
        assertEqualsImage(image, insertedImage);

    }

    @Test
    //@Transactional
    public void testRemove() throws Exception {

        imageService.save(image);
        int numberOfImages = imageService.findAll().size();
        imageService.remove(image);

        assertEquals(numberOfImages == 0? 0 : numberOfImages-1, imageService.findAll().size());    // There is an image less
        assertFalse(imageService.findAll().contains(image));              // The image is not in the repo any more

    }

    @Test(expected = IllegalArgumentException.class)
    //@Transactional
    public void testRemoveNull() throws Exception {
        imageService.remove((Image) null);
    }

    @Test
    //@Transactional
    public void testRemoveTwice() throws Exception {
        imageService.save(image);
        int numberOfImages = imageService.findAll().size();
        imageService.remove(image);
        imageService.remove(image);
        assertEquals(numberOfImages == 0 ? 0 : numberOfImages - 1, imageService.findAll().size());    // There just one image less
    }

    @Test
    //@Transactional
    public void testRemoveNotExisting() throws Exception {
        imageService.save(image);
        int numberOfImages = imageService.findAll().size();
        imageService.remove(image);
        imageService.remove(image);
        assertEquals(numberOfImages == 0? 0 : numberOfImages-1, imageService.findAll().size());    // There just one image less
        assertEquals(numberOfImages == 0? 0 : numberOfImages-1, imageService.findAll().size());    // There just one image less
    }

    @Test
    public void testFindAllByObject() throws Exception {

        List<Image> images = imageService.findAll(objectService.find(1));
        assertEquals(2, images.size());
        assertEquals(2, images.get(0).getSizes().size());
        assertEquals(2, images.get(1).getSizes().size());
        //assertTrue(images.get(0).getSizes().containsKey("a"));
        //assertTrue(images.get(0).getSizes().containsKey("b"));
        //assertTrue(images.get(1).getSizes().containsKey("a"));
        //assertTrue(images.get(1).getSizes().containsKey("b"));

    }
}