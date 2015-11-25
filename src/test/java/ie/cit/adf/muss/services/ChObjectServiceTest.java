package ie.cit.adf.muss.services;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.repositories.ChObjectRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

/**
 * ChObjectServiceTest
 *
 * We will perform a behavioural test by means of Mockito to ensure that every method in the repository
 * is called just the needed times, with the proper parameters
 */
public class ChObjectServiceTest {


    /*
    ChObjectRepository repository;
    ChObjectService service;
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

        // Set up the loader and service
        loader = new ChObjectJsonLoader();
        service = new ChObjectService(repository, loader);

    }

    @Test
    public void testGetByOriginalId() throws Exception {
        assertEquals(returned, service.getByOriginalId(40));
        verify(repository, times(1)).getByOriginalId(40);
    }

    @Test
    public void testLoadFromFolder() throws Exception {
        int numberOfObjectsToBeLoaded = loader.loadChObjects().size();
        service.loadFromFolder();
        verify(repository, times(numberOfObjectsToBeLoaded)).save(any());
    }

    @Test
    public void testGet() throws Exception {
        assertEquals(returned, service.find(10));
        verify(repository, times(1)).get(10);
    }

    @Test
    public void testSave() throws Exception {
        ChObject o = new ChObject();
        service.save(o);
        verify(repository, times(1)).save(o);
    }

    @Test
    public void testRemove() throws Exception {
        ChObject o = new ChObject();
        assertTrue(service.remove(o));
        verify(repository, times(1)).remove(o);
    }

    @Test
    public void testFindAll() throws Exception {
        service.findAll();
        verify(repository, times(1)).findAll();
    }*/

    @Autowired
    ChObjectService service;

    @Autowired
    ParticipationService participationService;

    @Autowired
    ImageService imageService;

    ChObject object;

    private void assertEqualsChObject(ChObject object, ChObject insertedChObject) {
        assertEquals(object.getTitle(), insertedChObject.getTitle());
        assertEquals(object.getCreditLine(), insertedChObject.getCreditLine());
        assertEquals(object.getDate(), insertedChObject.getDate());
        assertEquals(object.getDescription(),insertedChObject.getDescription());
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
    }

    @Test
    public void testFindAll() throws Exception {
        List<ChObject> objects = service.findAll();
        assertEquals(2, objects.size());
        assertFalse(objects.get(0).equals(objects.get(1)));
    }

    @Test
    public void testFindAllEmpty() throws Exception {
        List<ChObject> objects = service.findAll();
        objects.forEach(service::remove);
        objects = service.findAll();
        assertTrue(objects.isEmpty());
    }

    @Test
    public void testGet() throws Exception {

        ChObject object = service.find(1);
        assertEquals(101, object.getOriginalId());
        assertEquals("object1", object.getTitle());
        assertEquals("credit1", object.getCreditLine());
        assertEquals("2001", object.getDate());
        assertEquals("description1", object.getDescription());
        assertEquals("gallery1", object.getGalleryText());
        assertEquals("medium1", object.getMedium());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNegative() throws Exception {
        service.find(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetZero() throws Exception {
        service.find(0);
    }

    @Test
    public void testGetNotExisting() throws Exception {
        ChObject object = service.find(Integer.MAX_VALUE);
        assertNull(object);
    }

    @Test
    public void testGetByOriginalId() throws Exception {
        service.save(object);
        ChObject object = service.findByOriginalId(1001);
        assertEquals("TITLE", object.getTitle());
        assertEquals("LINE", object.getCreditLine());
        assertEquals("DATE", object.getDate());
        assertEquals("DESCRIPTION", object.getDescription());
        assertEquals("GALLERY", object.getGalleryText());
        assertEquals("MEDIUM", object.getMedium());
    }

    @Test
    public void testGetByOriginalIdNotExisting() throws Exception {
        ChObject object = service.findByOriginalId(Integer.MAX_VALUE);
        assertNull(object);
    }

    @Test
    public void testGetByOriginalIdNegative() throws Exception {
        ChObject object = service.findByOriginalId(-1);
        assertNull(object);
    }

    @Test(expected = IllegalArgumentException.class)
    //@Transactional
    public void testSaveNull() throws Exception {
        service.save((ChObject) null);
    }

    @Test
    //@Transactional
    public void testSaveInserting() throws Exception {

        int numberOfItems = service.findAll().size();

        service.save(object);

        // Check that the object was saved
        assertTrue(object.getId() != 0);
        assertEquals(numberOfItems + 1, service.findAll().size());
        ChObject insertedChObject = service.find(object.getId());
        assertNotNull(insertedChObject);
        assertEqualsChObject(object, insertedChObject);

        // Check that the participations and images were saved
        imageService
                .findAll(object)
                .forEach(image -> assertEqualsChObject(object, image.getObject()));
        participationService
                .findAll(object)
                .forEach( participation -> assertEqualsChObject(object, participation.getObject()));

    }


    @Test
    //@Transactional
    public void testSaveUpdating() throws Exception {

        int numberOfItems = service.findAll().size();

        service.save(object);
        object.setTitle("1");
        object.setCreditLine("2");
        object.setDate("3");
        object.setDescription("4");
        object.setGalleryText("5");
        object.setMedium("6");
        object.setOriginalId(1001);
        service.save(object);

        assertEquals(numberOfItems + 1, service.findAll().size());
        assertEqualsChObject(object, service.find(object.getId()));

    }

    @Test
    //@Transactional
    public void testRemove() throws Exception {

        service.save(object);
        int numberOfItems = service.findAll().size();
        service.remove(object);

        assertEquals(0, object.getId());                                 // The object id has ben reset
        assertEquals(numberOfItems == 0? 0 : numberOfItems-1, service.findAll().size());   // There is object less
        assertFalse(service.findAll().contains(object));              // The object is not in the repo any more

    }

    @Test(expected = IllegalArgumentException.class)
    //@Transactional
    public void testRemoveNull() throws Exception {
        service.remove((ChObject) null);
    }

    @Test
    //@Transactional
    public void testRemoveTwice() throws Exception {

        service.save(object);
        int numberOfItems = service.findAll().size();
        service.remove(object);
        service.remove(object);

        assertEquals(numberOfItems == 0 ? 0 : numberOfItems - 1, service.findAll().size());    // There just one less

    }

    @Test
    //@Transactional
    public void testRemoveNotExisting() throws Exception {

        service.save(object);
        int numberOfItems = service.findAll().size();
        service.remove(object);
        service.remove(object);

        assertEquals(numberOfItems == 0? 0 : numberOfItems-1, service.findAll().size());    // There just one less

    }
}