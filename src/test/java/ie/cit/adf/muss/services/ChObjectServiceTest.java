package ie.cit.adf.muss.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
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
import ie.cit.adf.muss.domain.ImageSize;

/**
 * ChObjectServiceTest
 * <p>
 * We will perform a behavioural test by means of Mockito to ensure that every method in the repository
 * is called just the needed times, with the proper parameters
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MussApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ChObjectServiceTest {


    /*
    ChObjectRepository repository;
    ChObjectService chObjectService;
    AbstractChObjectLoader loader;

    ChObject returned;

    @Before
    public void setUp() throws Exception {

        // Create the mock
        repository = Mockito.mock(ChObjectRepository.class);
        returned = new ChObject();
        returned.setTitle("RETURNED");

        when(repository.getByOriginalId(anyInt())).thenReturn(returned);
        when(repository.get(anyInt())).thenReturn(returned);
        when(repository.remove(any())).thenReturn(true);
        when(repository.findAll()).thenReturn(new ArrayList<>());

        // Set up the loader and chObjectService
        loader = new ChObjectJsonLoader();
        chObjectService = new ChObjectService(repository, loader);

    }

    @Test
    public void testGetByOriginalId() throws Exception {
        assertEquals(returned, chObjectService.getByOriginalId(40));
        verify(repository, times(1)).getByOriginalId(40);
    }

    @Test
    public void testLoadFromFolder() throws Exception {
        int numberOfObjectsToBeLoaded = loader.loadChObjects().size();
        chObjectService.loadFromFolder();
        verify(repository, times(numberOfObjectsToBeLoaded)).save(any());
    }

    @Test
    public void testGet() throws Exception {
        assertEquals(returned, chObjectService.find(10));
        verify(repository, times(1)).get(10);
    }

    @Test
    public void testSave() throws Exception {
        ChObject o = new ChObject();
        chObjectService.save(o);
        verify(repository, times(1)).save(o);
    }

    @Test
    public void testRemove() throws Exception {
        ChObject o = new ChObject();
        assertTrue(chObjectService.remove(o));
        verify(repository, times(1)).remove(o);
    }

    @Test
    public void testFindAll() throws Exception {
        chObjectService.findAll();
        verify(repository, times(1)).findAll();
    }*/

    @Autowired
    ChObjectService chObjectService;

    @Autowired
    ParticipationService participationService;

    @Autowired
    ImageService imageService;

    ChObject object;

    private void assertEqualsChObject(ChObject object, ChObject insertedChObject) {
        assertEquals(object.getTitle(), insertedChObject.getTitle());
        assertEquals(object.getCreditLine(), insertedChObject.getCreditLine());
        assertEquals(object.getDate(), insertedChObject.getDate());
        assertEquals(object.getDescription(), insertedChObject.getDescription());
        assertEquals(object.getGalleryText(), insertedChObject.getGalleryText());
        assertEquals(object.getMedium(), insertedChObject.getMedium());
    }

    @Before
    public void setUp() throws Exception {
        object = new ChObject();
        object.setTitle("TITLE");
        object.setCreditLine("LINE");
        object.setDate("DATE");
        object.setDescription("DESCRIPTION");
        object.setGalleryText("GALLERY");
        object.setMedium("MEDIUM");
        object.setOriginalId(1001);
        ImageSize size = new ImageSize();
        size.setHeight(100);
        size.setWidth(200);
        size.setUrl("url");
        size.setLabel("s");
        List<ImageSize> sizes = new ArrayList<>();
        sizes.add(size);
        Image image = new Image();
        image.setOriginalId(3001);
        image.setPrimary(true);
        image.setSizes(sizes);
        List<Image> images = new ArrayList<>();
        images.add(image);
        object.setImages(images);
    }

    @Test
    public void testFindAll() throws Exception {
        List<ChObject> objects = chObjectService.findAll();
        assertEquals(2, objects.size());
        assertFalse(objects.get(0).equals(objects.get(1)));
    }

    @Test
    public void testGet() throws Exception {

        ChObject object = chObjectService.find(1);
        assertEquals(101, object.getOriginalId());
        assertEquals("object1", object.getTitle());
        assertEquals("credit1", object.getCreditLine());
        assertEquals("2001", object.getDate());
        assertEquals("description1", object.getDescription());
        assertEquals("gallery1", object.getGalleryText());
        assertEquals("medium1", object.getMedium());

        assertNotNull(object.getImages());
        assertEquals(1, object.getImages().size());

        Image image = object.getImages().get(0);
        assertTrue(image.isPrimary());
        assertEquals(3001, image.getOriginalId());
        assertNotNull(image.getSizes());
        assertEquals(1, image.getSizes().size());

        ImageSize size = image.getSizes().get(0);
        assertEquals(100, size.getHeight());
        assertEquals(200, size.getWidth());
        assertEquals("url", size.getUrl());
        assertEquals("s", size.getLabel());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNegative() throws Exception {
        chObjectService.find(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetZero() throws Exception {
        chObjectService.find(0);
    }

    @Test
    public void testGetNotExisting() throws Exception {
        ChObject object = chObjectService.find(Integer.MAX_VALUE);
        assertNull(object);
    }

    @Test
    public void testGetByOriginalId() throws Exception {
        chObjectService.save(object);
        ChObject object = chObjectService.findOneByOriginalId(1001);
        assertEquals("TITLE", object.getTitle());
        assertEquals("LINE", object.getCreditLine());
        assertEquals("DATE", object.getDate());
        assertEquals("DESCRIPTION", object.getDescription());
        assertEquals("GALLERY", object.getGalleryText());
        assertEquals("MEDIUM", object.getMedium());
    }

    @Test
    public void testGetByOriginalIdNotExisting() throws Exception {
        ChObject object = chObjectService.findOneByOriginalId(Integer.MAX_VALUE);
        assertNull(object);
    }

    @Test
    public void testGetByOriginalIdNegative() throws Exception {
        ChObject object = chObjectService.findOneByOriginalId(-1);
        assertNull(object);
    }

    @Test(expected = IllegalArgumentException.class)
    //@Transactional
    public void testSaveNull() throws Exception {
        chObjectService.save((ChObject) null);
    }

    @Test
    //@Transactional
    public void testSaveInserting() throws Exception {

        int numberOfItems = chObjectService.findAll().size();

        chObjectService.save(object);

        // Check that the object was saved
        assertTrue(object.getId() != 0);
        assertEquals(numberOfItems + 1, chObjectService.findAll().size());
        ChObject insertedChObject = chObjectService.find(object.getId());
        assertNotNull(insertedChObject);
        assertEqualsChObject(object, insertedChObject);

        // Check that the participations and images were saved
        imageService
                .findAll(object)
                .forEach(image -> assertEqualsChObject(object, image.getChObject()));
        participationService
                .findAll(object)
                .forEach(participation -> assertEqualsChObject(object, participation.getChObject()));

    }


    @Test
    //@Transactional
    public void testSaveUpdating() throws Exception {

        int numberOfItems = chObjectService.findAll().size();

        chObjectService.save(object);
        object.setTitle("1");
        object.setCreditLine("2");
        object.setDate("3");
        object.setDescription("4");
        object.setGalleryText("5");
        object.setMedium("6");
        object.setOriginalId(1001);
        chObjectService.save(object);

        assertEquals(numberOfItems + 1, chObjectService.findAll().size());
        assertEqualsChObject(object, chObjectService.find(object.getId()));

    }

    @Test
    //@Transactional
    public void testRemove() throws Exception {

        chObjectService.save(object);
        int numberOfItems = chObjectService.findAll().size();
        chObjectService.remove(object);

        assertEquals(numberOfItems == 0 ? 0 : numberOfItems - 1, chObjectService.findAll().size());   // There is object less
        assertFalse(chObjectService.findAll().contains(object));              // The object is not in the repo any more

    }

    @Test(expected = IllegalArgumentException.class)
    //@Transactional
    public void testRemoveNull() throws Exception {
        chObjectService.remove((ChObject) null);
    }

    @Test
    //@Transactional
    public void testRemoveTwice() throws Exception {

        chObjectService.save(object);
        int numberOfItems = chObjectService.findAll().size();
        chObjectService.remove(object);
        chObjectService.remove(object);

        assertEquals(numberOfItems == 0 ? 0 : numberOfItems - 1, chObjectService.findAll().size());    // There just one less

    }

    @Test
    //@Transactional
    public void testRemoveNotExisting() throws Exception {

        chObjectService.save(object);
        int numberOfItems = chObjectService.findAll().size();
        chObjectService.remove(object);
        chObjectService.remove(object);

        assertEquals(numberOfItems == 0 ? 0 : numberOfItems - 1, chObjectService.findAll().size());    // There just one less

    }

}