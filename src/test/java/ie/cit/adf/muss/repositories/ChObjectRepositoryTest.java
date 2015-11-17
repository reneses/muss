package ie.cit.adf.muss.repositories;

import ie.cit.adf.muss.MussApplication;
import ie.cit.adf.muss.domain.ChObject;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MussApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@TransactionConfiguration(defaultRollback=true)
public class ChObjectRepositoryTest {

    @Autowired
    ChObjectRepository repository;

    @Autowired
    ParticipationRepository participationRepository;

    @Autowired
    ImageRepository imageRepository;

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
        object.setId(1001);
    }

    @Test
    public void testFindAll() throws Exception {
        List<ChObject> objects = repository.findAll();
        assertEquals(2, objects.size());
        assertFalse(objects.get(0).equals(objects.get(1)));
    }

    @Test
    public void testFindAllEmpty() throws Exception {
        List<ChObject> objects = repository.findAll();
        objects.forEach(repository::remove);
        objects = repository.findAll();
        assertTrue(objects.isEmpty());
    }

    @Test
    public void testGet() throws Exception {

        ChObject object = repository.get(1);
        assertEquals(101, object.getId());
        assertEquals("object1", object.getTitle());
        assertEquals("credit1", object.getCreditLine());
        assertEquals("2001", object.getDate());
        assertEquals("description1", object.getDescription());
        assertEquals("gallery1", object.getGalleryText());
        assertEquals("medium1", object.getMedium());

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
        ChObject object = repository.get(Integer.MAX_VALUE);
        assertNull(object);
    }

    @Test
    public void testGetByOriginalId() throws Exception {
        repository.save(object);
        ChObject object = repository.getByOriginalId(1001);
        assertEquals("TITLE", object.getTitle());
        assertEquals("LINE", object.getCreditLine());
        assertEquals("DATE", object.getDate());
        assertEquals("DESCRIPTION", object.getDescription());
        assertEquals("GALLERY", object.getGalleryText());
        assertEquals("MEDIUM", object.getMedium());
    }

    @Test
    public void testGetByOriginalIdNotExisting() throws Exception {
        ChObject object = repository.getByOriginalId(Integer.MAX_VALUE);
        assertNull(object);
    }

    @Test
    public void testGetByOriginalIdNegative() throws Exception {
        ChObject object = repository.getByOriginalId(-1);
        assertNull(object);
    }

    @Test(expected = IllegalArgumentException.class)
    //@Transactional
    public void testSaveNull() throws Exception {
        repository.save(null);
    }

    @Test
    //@Transactional
    public void testSaveInserting() throws Exception {

        int numberOfItems = repository.findAll().size();

        repository.save(object);

        // Check that the object was saved
        assertTrue(object.getId() != 0);
        assertEquals(numberOfItems + 1, repository.findAll().size());
        ChObject insertedChObject = repository.get(object.getId());
        assertNotNull(insertedChObject);
        assertEqualsChObject(object, insertedChObject);

        // Check that the participations and images were saved
        imageRepository
                .findAll(object)
                .forEach(image -> assertEqualsChObject(object, image.getChObject()));
        participationRepository
                .findAll(object)
                .forEach( participation -> assertEqualsChObject(object, participation.getChObject()));

    }


    @Test
    //@Transactional
    public void testSaveUpdating() throws Exception {

        int numberOfItems = repository.findAll().size();

        repository.save(object);
        object.setTitle("1");
        object.setCreditLine("2");
        object.setDate("3");
        object.setDescription("4");
        object.setGalleryText("5");
        object.setMedium("6");
        object.setId(1001);
        repository.save(object);

        assertEquals(numberOfItems + 1, repository.findAll().size());
        assertEqualsChObject(object, repository.get(object.getId()));

    }

    @Test
    //@Transactional
    public void testRemove() throws Exception {

        repository.save(object);
        int numberOfItems = repository.findAll().size();
        repository.remove(object);

        assertEquals(0, object.getId());                                 // The object id has ben reset
        assertEquals(numberOfItems == 0? 0 : numberOfItems-1, repository.findAll().size());   // There is object less
        assertFalse(repository.findAll().contains(object));              // The object is not in the repo any more

    }

    @Test(expected = IllegalArgumentException.class)
    //@Transactional
    public void testRemoveNull() throws Exception {
        repository.remove(null);
    }

    @Test
    //@Transactional
    public void testRemoveTwice() throws Exception {

        repository.save(object);
        int numberOfItems = repository.findAll().size();
        repository.remove(object);
        repository.remove(object);

        assertEquals(numberOfItems == 0 ? 0 : numberOfItems - 1, repository.findAll().size());    // There just one less

    }

    @Test
    //@Transactional
    public void testRemoveNotExisting() throws Exception {

        repository.save(object);
        int numberOfItems = repository.findAll().size();
        repository.remove(object);
        repository.remove(object);

        assertEquals(numberOfItems == 0? 0 : numberOfItems-1, repository.findAll().size());    // There just one less

    }
}