package ie.cit.adf.muss.repositories;

import ie.cit.adf.muss.MussApplication;
import ie.cit.adf.muss.domain.Participant;
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
public class ParticipantRepositoryTest {

    @Autowired
    ParticipantRepository repository;

    Participant participant;

    private void assertEqualsParticipant(Participant expected, Participant actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDate(), actual.getDate());
        assertEquals(expected.getUrl(), actual.getUrl());
        assertEquals(expected.getName(), actual.getName());
    }

    @Before
    public void setUp() throws Exception {
        participant = new Participant();
        participant.setName("participantName");
        participant.setUrl("URL");
        participant.setDate("2015");
        participant.setId(123);
        assertFalse("Test data is corrupted. Image is not supposed to be saved.", repository.findAll().contains(participant));
    }

    @Test
    public void testFindAll() throws Exception {
        List<Participant> participants = repository.findAll();
        assertEquals(2, participants.size());
        assertFalse(participants.get(0).equals(participants.get(1)));
    }

    @Test
    public void testFindAllEmpty() throws Exception {
        List<Participant> participants = repository.findAll();
        participants.forEach(repository::remove);
        participants = repository.findAll();
        assertTrue(participants.isEmpty());
    }

    @Test
    public void testGet() throws Exception {
        Participant participant = repository.get(1);
        assertEquals(1001, participant.getId());
        assertEquals("3001", participant.getDate());
        assertEquals("participant1", participant.getName());
        assertEquals("participant1.ie", participant.getUrl());
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
        Participant participant = repository.get(Integer.MAX_VALUE);
        assertNull(participant);
    }

    @Test
    public void testGetByOriginalId() throws Exception {
        Participant participant = repository.getByOriginalId(1001);
        assertEquals(1001, participant.getId());
        assertEquals("3001", participant.getDate());
        assertEquals("participant1", participant.getName());
        assertEquals("participant1.ie", participant.getUrl());
    }

    @Test
    public void testGetByOriginalIdNotExisting() throws Exception {
        Participant participant = repository.getByOriginalId(Integer.MAX_VALUE);
        assertNull(participant);
    }

    @Test
    public void testGetByOriginalIdNegative() throws Exception {
        Participant participant = repository.getByOriginalId(-1);
        assertNull(participant);
    }

    @Test(expected = IllegalArgumentException.class)
    //@Transactional
    public void testSaveNull() throws Exception {
        repository.save(null);
    }

    @Test
    //@Transactional
    public void testSaveInserting() throws Exception {

        Participant participant = new Participant();
        participant.setId(31);
        participant.setDate("1901");
        participant.setName("name1");
        participant.setUrl("test1.ie");

        int numberOfItems = repository.findAll().size();

        repository.save(participant);

        assertTrue(participant.getId() != 0);
        assertEquals(numberOfItems + 1, repository.findAll().size());
        Participant insertedParticipant = repository.get(participant.getId());
        assertNotNull(insertedParticipant);
        assertEqualsParticipant(participant, insertedParticipant);

    }


    @Test
    //@Transactional
    public void testSaveUpdating() throws Exception {

        int numberOfItems = repository.findAll().size();

        repository.save(participant);
        participant.setDate("301");
        participant.setName("aaa");
        participant.setUrl("new");
        repository.save(participant);

        assertEquals(numberOfItems + 1, repository.findAll().size());
        assertEqualsParticipant(participant, repository.get(participant.getId()));

    }

    @Test
    //@Transactional
    public void testRemove() throws Exception {

        repository.save(participant);
        int numberOfItems = repository.findAll().size();
        repository.remove(participant);

        assertEquals(0, participant.getId());                                 // The participant id has ben reset
        assertEquals(numberOfItems == 0? 0 : numberOfItems-1, repository.findAll().size());   // There is participant less
        assertFalse(repository.findAll().contains(participant));              // The participant is not in the repo any more

    }

    @Test(expected = IllegalArgumentException.class)
    //@Transactional
    public void testRemoveNull() throws Exception {
        repository.remove(null);
    }

    @Test
    //@Transactional
    public void testRemoveTwice() throws Exception {

        repository.save(participant);
        int numberOfItems = repository.findAll().size();
        repository.remove(participant);
        repository.remove(participant);

        assertEquals(numberOfItems == 0 ? 0 : numberOfItems - 1, repository.findAll().size());    // There just one less

    }

    @Test
    //@Transactional
    public void testRemoveNotExisting() throws Exception {

        repository.save(participant);
        int numberOfItems = repository.findAll().size();
        repository.remove(participant);
        repository.remove(participant);

        assertEquals(numberOfItems == 0? 0 : numberOfItems-1, repository.findAll().size());    // There just one less

    }
}