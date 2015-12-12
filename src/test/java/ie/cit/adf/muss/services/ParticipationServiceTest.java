package ie.cit.adf.muss.services;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

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
import ie.cit.adf.muss.domain.Participant;
import ie.cit.adf.muss.domain.Participation;
import ie.cit.adf.muss.domain.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MussApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@TransactionConfiguration(defaultRollback=true)
public class ParticipationServiceTest {

    @Autowired
    ParticipationService service;

    @Autowired
    RoleService roleService;

    @Autowired
    ParticipantService participantService;

    Participation participation;


    @Before
    public void setUp() throws Exception {

        ChObject object = new ChObject();
        object.setId(1);
        object.setTitle("OBJECT");

        Role role = new Role();
        role.setName("ROLE");

        Participant participant = new Participant();
        participant.setName("PARTICIPATION");

        participation = new Participation();
        participation.setChObject(object);
        participation.setRole(role);
        participation.setParticipant(participant);

    }

    @Test
    public void testFindAll() throws Exception {
        List<Participation> participations = service.findAll();
        assertEquals(2, participations.size());
        assertFalse(participations.get(0).equals(participations.get(1)));
    }

    @Test
    public void testFindAllEmpty() throws Exception {
        List<Participation> participations = service.findAll();
        participations.forEach(service::remove);
        participations = service.findAll();
        assertTrue(participations.isEmpty());
    }

    @Test
    public void testGet() throws Exception {
        Participation participation = service.find(1);
        assertNotNull(participation.getParticipant());
        assertNotNull(participation.getRole());
        assertEquals("participant1", participation.getParticipant().getName());
        assertEquals("role1", participation.getRole().getName());
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
        Participation participation = service.find(Integer.MAX_VALUE);
        assertNull(participation);
    }

    @Test(expected = IllegalArgumentException.class)
    //@Transactional
    public void testSaveNull() throws Exception {
        service.save((Participation) null);
    }

    @Test
    //@Transactional
    public void testSaveInserting() throws Exception {

        int numberOfItems = service.findAll().size();

        service.save(participation);

        // Check that the models was saved
        assertNotNull(roleService.find(participation.getRole().getId()));
        assertNotNull(participantService.find(participation.getParticipant().getId()));

        // Check that the repo was saved
        assertTrue(participation.getId() != 0);
        assertEquals(numberOfItems + 1, service.findAll().size());
        assertNotNull(service.find(participation.getId()));

    }


    @Test
    //@Transactional
    public void testSaveUpdating() throws Exception {

        int numberOfItems = service.findAll().size();
        Role role = new Role();
        role.setName("roleName");
        role.setDisplayName("Role Name");
        role.setUrl("URL");
        role.setOriginalId(123);

        service.save(participation);
        participation.setRole(role);
        service.save(participation);

        assertEquals(numberOfItems + 1, service.findAll().size());
        assertTrue(role.getId() > 0);
        assertEquals("roleName", roleService.find(role.getId()).getName());

    }

    @Test
    //@Transactional
    public void testRemove() throws Exception {

//        List<Participation> participations = service.findAll();

        service.save(participation);
        int numberOfItems = service.findAll().size();
        service.remove(participation);

        assertEquals(numberOfItems == 0? 0 : numberOfItems-1, service.findAll().size());   // There is participation less
        assertFalse(service.findAll().contains(participation));              // The participation is not in the repo any more

    }

    @Test(expected = IllegalArgumentException.class)
    //@Transactional
    public void testRemoveNull() throws Exception {
        service.remove((Participation) null);
    }

    @Test
    //@Transactional
    public void testRemoveTwice() throws Exception {

        service.save(participation);
        int numberOfItems = service.findAll().size();
        service.remove(participation);
        service.remove(participation);

        assertEquals(numberOfItems == 0 ? 0 : numberOfItems - 1, service.findAll().size());    // There just one less

    }

    @Test
    //@Transactional
    public void testRemoveNotExisting() throws Exception {

        service.save(participation);
        int numberOfItems = service.findAll().size();
        service.remove(participation);
        service.remove(participation);

        assertEquals(numberOfItems == 0? 0 : numberOfItems-1, service.findAll().size());    // There just one less

    }
}