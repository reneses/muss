package ie.cit.adf.muss.repositories;

import ie.cit.adf.muss.MussApplication;
import ie.cit.adf.muss.domain.Image;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * TODO Test sizes
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MussApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@TransactionConfiguration(defaultRollback=true)
public class ImageRepositoryTest {

    @Autowired
    ImageRepository repository;

    @Autowired
    ChObjectRepository objectRepository;

    Image image;

    private void assertEqualsImage(Image expected, Image actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.isPrimary(), actual.isPrimary());
        /*expected.getSizes().forEach( (label, size) -> {
            ImageSize actualSize = actual.getSizes().getOrDefault(label, null);
            assertNotNull(actualSize);
            assertEquals(size.getHeight(), actualSize.getHeight());
            assertEquals(size.getWidth(), actualSize.getWidth());
            assertEquals(size.getUrl(), actualSize.getUrl());
        });*/
    }

    @Before
    public void setUp() throws Exception {
        image = new Image();
        image.setChObject(objectRepository.get(1));
        image.setId(123);
        image.setPrimary(1);
        /*size = new ImageSize();
        size.setHeight(200);
        size.setWidth(200);
        size.setUrl("URL_z");
        size.setLabel("z");
        image.addSize(size);*/
        assertFalse("Test data is corrupted. Image is not supposed to be saved.", repository.findAll().contains(image));
    }

    @Test
    public void testFindAll() throws Exception {
        List<Image> images = repository.findAll();
        assertEquals(2, images.size());
    }

    @Test
    public void testFindAllEmpty() throws Exception {
        List<Image> images = repository.findAll();
        images.forEach(repository::remove);
        images = repository.findAll();
        assertTrue(images.isEmpty());
    }

    @Test
    public void testGet() throws Exception {
        Image image = repository.get(1);
        assertEquals(1001, image.getId());
        assertEquals(1, image.isPrimary());
        /*assertEquals(200, image.getSizes().get("a").getWidth());
        assertEquals(100, image.getSizes().get("a").getHeight());
        assertEquals("image1.ie_a", image.getSizes().get("a").getUrl());*/
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetNegative() throws Exception {
        repository.get(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetZero() throws Exception {
        repository.get(0);
    }

    @Test
    public void testGetNotExisting() throws Exception {
        Image image = repository.get(Integer.MAX_VALUE);
        assertNull(image);
    }

    @Test
    public void testGetByOriginalId() throws Exception {
        Image image = repository.getByOriginalId(1001);
        assertEquals(1001, image.getId());
        assertEquals(1, image.isPrimary());
        /*assertEquals(200, image.getSizes().get("a").getWidth());
        assertEquals(100, image.getSizes().get("a").getHeight());
        assertEquals("image1.ie_a", image.getSizes().get("a").getUrl());*/
    }

    @Test
    public void testGetByOriginalIdNotExisting() throws Exception {
        Image image = repository.getByOriginalId(Integer.MAX_VALUE);
        assertNull(image);
    }

    @Test
    public void testGetByOriginalIdNegative() throws Exception {
        Image image = repository.getByOriginalId(-1);
        assertNull(image);
    }

    @Test(expected = IllegalArgumentException.class)
    //@Transactional
    public void testSaveNull() throws Exception {
        repository.save((Image) null);
    }

    @Test
    //@Transactional
    public void testSaveInserting() throws Exception {

        int numberOfImages = repository.findAll().size();

        repository.save(image);

        assertTrue(image.getId() != 0);
        assertEquals(numberOfImages + 1, repository.findAll().size());
        Image insertedImage = repository.get(image.getId());
        assertNotNull(insertedImage);
        assertEqualsImage(image, insertedImage);

    }


    @Test
    //@Transactional
    public void testSaveUpdating() throws Exception {

        int numberOfImages = repository.findAll().size();

        repository.save(image);
        image.setPrimary(1);
        /*size.setHeight(10);
        size.setWidth(20);
        size.setUrl("X");
        repository.save(image);

        assertEquals(numberOfImages + 1, repository.findAll().size());
        Image insertedImage = repository.get(image.getId());
        assertEqualsImage(image, insertedImage);*/

    }

    @Test
    //@Transactional
    public void testRemove() throws Exception {

        repository.save(image);
        int numberOfImages = repository.findAll().size();
        repository.remove(image);

        assertEquals(0, image.getId());                                 // The image id has ben reset
        assertEquals(numberOfImages == 0? 0 : numberOfImages-1, repository.findAll().size());    // There is an image less
        assertFalse(repository.findAll().contains(image));              // The image is not in the repo any more

    }

    @Test(expected = IllegalArgumentException.class)
    //@Transactional
    public void testRemoveNull() throws Exception {
        repository.remove(null);
    }

    @Test
    //@Transactional
    public void testRemoveTwice() throws Exception {

        repository.save(image);
        int numberOfImages = repository.findAll().size();
        repository.remove(image);
        repository.remove(image);

        assertEquals(numberOfImages == 0 ? 0 : numberOfImages - 1, repository.findAll().size());    // There just one image less

    }

    @Test
    //@Transactional
    public void testRemoveNotExisting() throws Exception {

        repository.save(image);
        int numberOfImages = repository.findAll().size();
        repository.remove(image);
        repository.remove(image);

        assertEquals(numberOfImages == 0? 0 : numberOfImages-1, repository.findAll().size());    // There just one image less

    }

    @Test
    public void testFindAllByObject() throws Exception {

        List<Image> images = repository.findAll(objectRepository.get(1));
        assertEquals(2, images.size());
        /*assertEquals(2, images.get(0).getSizes().size());
        assertEquals(2, images.get(1).getSizes().size());
        assertTrue(images.get(0).getSizes().containsKey("a"));
        assertTrue(images.get(0).getSizes().containsKey("b"));
        assertTrue(images.get(1).getSizes().containsKey("a"));
        assertTrue(images.get(1).getSizes().containsKey("b"));*/

    }
}