package ie.cit.adf.muss.repositories;

import ie.cit.adf.muss.MussApplication;
import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Participant;
import ie.cit.adf.muss.domain.Participation;
import ie.cit.adf.muss.domain.Role;
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
public class ParticipationRepositoryTest {

    @Autowired
    ParticipationRepository repository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ParticipantRepository participantRepository;

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
        List<Participation> participations = repository.findAll();
        assertEquals(2, participations.size());
        assertFalse(participations.get(0).equals(participations.get(1)));
    }

    @Test
    public void testFindAllEmpty() throws Exception {
        List<Participation> participations = repository.findAll();
        participations.forEach(repository::remove);
        participations = repository.findAll();
        assertTrue(participations.isEmpty());
    }

    @Test
    public void testGet() throws Exception {
        Participation participation = repository.get(1);
        assertNotNull(participation.getParticipant());
        assertNotNull(participation.getRole());
        assertEquals("participant1", participation.getParticipant().getName());
        assertEquals("role1", participation.getRole().getName());
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
        Participation participation = repository.get(Integer.MAX_VALUE);
        assertNull(participation);
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

        repository.save(participation);

        // Check that the models was saved
        assertNotNull(roleRepository.get(participation.getRole().getId()));
        assertNotNull(participantRepository.get(participation.getParticipant().getId()));

        // Check that the repo was saved TODO update
        //assertTrue(participation.getId() != 0);
        //assertEquals(numberOfItems + 1, repository.findAll().size());
        //assertNotNull(repository.get(participation.getId()));

    }


    @Test
    //@Transactional
    public void testSaveUpdating() throws Exception {

        int numberOfItems = repository.findAll().size();
        Role role = new Role();
        role.setName("roleName");
        role.setDisplayName("Role Name");
        role.setUrl("URL");
        role.setId(123);

        repository.save(participation);
        participation.setRole(role);
        repository.save(participation);

        assertEquals(numberOfItems + 1, repository.findAll().size());
        assertTrue(role.getId() > 0);
        assertEquals("roleName", roleRepository.get(role.getId()).getName());

    }

    @Test
    //@Transactional
    public void testRemove() throws Exception {

        List<Participation> participations = repository.findAll();

        repository.save(participation);
        int numberOfItems = repository.findAll().size();
        repository.remove(participation);

        // TODO
        //assertEquals(0, participation.getId());                                 // The participation id has ben reset
        //assertEquals(numberOfItems == 0? 0 : numberOfItems-1, repository.findAll().size());   // There is participation less
        //assertFalse(repository.findAll().contains(participation));              // The participation is not in the repo any more

    }

    @Test(expected = IllegalArgumentException.class)
    //@Transactional
    public void testRemoveNull() throws Exception {
        repository.remove(null);
    }

    @Test
    //@Transactional
    public void testRemoveTwice() throws Exception {

        repository.save(participation);
        int numberOfItems = repository.findAll().size();
        repository.remove(participation);
        repository.remove(participation);

        assertEquals(numberOfItems == 0 ? 0 : numberOfItems - 1, repository.findAll().size());    // There just one less

    }

    @Test
    //@Transactional
    public void testRemoveNotExisting() throws Exception {

        repository.save(participation);
        int numberOfItems = repository.findAll().size();
        repository.remove(participation);
        repository.remove(participation);

        assertEquals(numberOfItems == 0? 0 : numberOfItems-1, repository.findAll().size());    // There just one less

    }
}