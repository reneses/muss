package ie.cit.adf.muss.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
import ie.cit.adf.muss.domain.Participant;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MussApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@TransactionConfiguration(defaultRollback=true)
public class ParticipantServiceTest {

    @Autowired
    ParticipantService service;

    Participant participant;

    private void assertEqualsParticipant(Participant expected, Participant actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getOriginalId(), actual.getOriginalId());
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
        participant.setOriginalId(123);
        assertFalse("Test data is corrupted. Image is not supposed to be saved.", service.findAll().contains(participant));
    }

    @Test
    public void testFindAll() throws Exception {
        List<Participant> participants = service.findAll();
        assertEquals(2, participants.size());
        assertFalse(participants.get(0).equals(participants.get(1)));
    }

    @Test
    public void testGet() throws Exception {
        Participant participant = service.find(1);
        assertEquals(1001, participant.getOriginalId());
        assertEquals("3001", participant.getDate());
        assertEquals("participant1", participant.getName());
        assertEquals("participant1.ie", participant.getUrl());
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
        Participant participant = service.find(Integer.MAX_VALUE);
        assertNull(participant);
    }

    @Test
    public void testGetByOriginalId() throws Exception {
        Participant participant = service.findOneByOriginalId(1001);
        assertEquals(1001, participant.getOriginalId());
        assertEquals("3001", participant.getDate());
        assertEquals("participant1", participant.getName());
        assertEquals("participant1.ie", participant.getUrl());
    }

    @Test
    public void testGetByOriginalIdNotExisting() throws Exception {
        Participant participant = service.findOneByOriginalId(Integer.MAX_VALUE);
        assertNull(participant);
    }

    @Test
    public void testGetByOriginalIdNegative() throws Exception {
        Participant participant = service.findOneByOriginalId(-1);
        assertNull(participant);
    }

    @Test(expected = IllegalArgumentException.class)
    //@Transactional
    public void testSaveNull() throws Exception {
        service.save((Participant) null);
    }

    @Test
    //@Transactional
    public void testSaveInserting() throws Exception {

        Participant participant = new Participant();
        participant.setOriginalId(31);
        participant.setDate("1901");
        participant.setName("name1");
        participant.setUrl("test1.ie");

        int numberOfItems = service.findAll().size();

        service.save(participant);

        assertTrue(participant.getId() != 0);
        assertEquals(numberOfItems + 1, service.findAll().size());
        Participant insertedParticipant = service.find(participant.getId());
        assertNotNull(insertedParticipant);
        assertEqualsParticipant(participant, insertedParticipant);

    }


    @Test
    //@Transactional
    public void testSaveUpdating() throws Exception {

        int numberOfItems = service.findAll().size();

        service.save(participant);
        participant.setDate("301");
        participant.setName("aaa");
        participant.setUrl("new");
        service.save(participant);

        assertEquals(numberOfItems + 1, service.findAll().size());
        assertEqualsParticipant(participant, service.find(participant.getId()));

    }

    @Test
    //@Transactional
    public void testRemove() throws Exception {

        service.save(participant);
        int numberOfItems = service.findAll().size();
        service.remove(participant);

        assertEquals(numberOfItems == 0? 0 : numberOfItems-1, service.findAll().size());   // There is participant less
        assertFalse(service.findAll().contains(participant));              // The participant is not in the repo any more

    }

    @Test(expected = IllegalArgumentException.class)
    //@Transactional
    public void testRemoveNull() throws Exception {
        service.remove((Participant) null);
    }

    @Test
    //@Transactional
    public void testRemoveTwice() throws Exception {

        service.save(participant);
        int numberOfItems = service.findAll().size();
        service.remove(participant);
        service.remove(participant);

        assertEquals(numberOfItems == 0 ? 0 : numberOfItems - 1, service.findAll().size());    // There just one less

    }

    @Test
    //@Transactional
    public void testRemoveNotExisting() throws Exception {

        service.save(participant);
        int numberOfItems = service.findAll().size();
        service.remove(participant);
        service.remove(participant);

        assertEquals(numberOfItems == 0? 0 : numberOfItems-1, service.findAll().size());    // There just one less

    }
}