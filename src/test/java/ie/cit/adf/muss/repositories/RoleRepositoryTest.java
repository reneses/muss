package ie.cit.adf.muss.repositories;

import ie.cit.adf.muss.MussApplication;
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
public class RoleRepositoryTest {

    @Autowired
    RoleRepository repository;

    Role role;

    private void assertEqualsRole(Role expected, Role actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDisplayName(), actual.getDisplayName());
        assertEquals(expected.getUrl(), actual.getUrl());
        assertEquals(expected.getName(), actual.getName());
    }

    @Before
    public void setUp() throws Exception {
        role = new Role();
        role.setName("roleName");
        role.setDisplayName("Role Name");
        role.setUrl("URL");
        role.setId(123);
        assertFalse("Test data is corrupted. Image is not supposed to be saved.", repository.findAll().contains(role));
    }

    @Test
    public void testFindAll() throws Exception {
        List<Role> roles = repository.findAll();
        assertEquals(2, roles.size());
        assertFalse(roles.get(0).equals(roles.get(1)));
    }

    @Test
    public void testFindAllEmpty() throws Exception {
        List<Role> roles = repository.findAll();
        roles.forEach(repository::remove);
        roles = repository.findAll();
        assertTrue(roles.isEmpty());
    }

    @Test
    public void testGet() throws Exception {
        Role role = repository.get(1);
        assertEquals(1001, role.getId());
        assertEquals("role1", role.getName());
        assertEquals("Role 1", role.getDisplayName());
        assertEquals("role1.ie", role.getUrl());
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
        Role role = repository.get(Integer.MAX_VALUE);
        assertNull(role);
    }

    @Test
    public void testGetByOriginalId() throws Exception {
        Role role = repository.getByOriginalId(1001);
        assertEquals(1001, role.getId());
        assertEquals("role1", role.getName());
        assertEquals("Role 1", role.getDisplayName());
        assertEquals("role1.ie", role.getUrl());
    }

    @Test
    public void testGetByOriginalIdNotExisting() throws Exception {
        Role role = repository.getByOriginalId(Integer.MAX_VALUE);
        assertNull(role);
    }

    @Test
    public void testGetByOriginalIdNegative() throws Exception {
        Role role = repository.getByOriginalId(-1);
        assertNull(role);
    }

    @Test(expected = IllegalArgumentException.class)
    //@Transactional
    public void testSaveNull() throws Exception {
        repository.save(null);
    }

    @Test
    //@Transactional
    public void testSaveInserting() throws Exception {

        Role role = new Role();
        role.setId(31);
        role.setName("name1");
        role.setDisplayName("Name 1");
        role.setUrl("test1.ie");

        int numberOfItems = repository.findAll().size();

        repository.save(role);

        assertTrue(role.getId() != 0);
        assertEquals(numberOfItems + 1, repository.findAll().size());
        Role insertedRole = repository.get(role.getId());
        assertNotNull(insertedRole);
        assertEqualsRole(role, insertedRole);

    }


    @Test
    //@Transactional
    public void testSaveUpdating() throws Exception {

        int numberOfItems = repository.findAll().size();

        repository.save(role);
        role.setName("aaa");
        role.setDisplayName("A A A");
        role.setId(10);
        role.setUrl("new");
        repository.save(role);

        assertEquals(numberOfItems + 1, repository.findAll().size());
        assertEqualsRole(role, repository.get(role.getId()));

    }

    @Test
    //@Transactional
    public void testRemove() throws Exception {

        repository.save(role);
        int numberOfItems = repository.findAll().size();
        repository.remove(role);

        assertEquals(0, role.getId());                                 // The role id has ben reset
        assertEquals(numberOfItems == 0? 0 : numberOfItems-1, repository.findAll().size());   // There is role less
        assertFalse(repository.findAll().contains(role));              // The role is not in the repo any more

    }

    @Test(expected = IllegalArgumentException.class)
    //@Transactional
    public void testRemoveNull() throws Exception {
        repository.remove(null);
    }

    @Test
    //@Transactional
    public void testRemoveTwice() throws Exception {

        repository.save(role);
        int numberOfItems = repository.findAll().size();
        repository.remove(role);
        repository.remove(role);

        assertEquals(numberOfItems == 0 ? 0 : numberOfItems - 1, repository.findAll().size());    // There just one less

    }

    @Test
    //@Transactional
    public void testRemoveNotExisting() throws Exception {

        repository.save(role);
        int numberOfItems = repository.findAll().size();
        repository.remove(role);
        repository.remove(role);

        assertEquals(numberOfItems == 0? 0 : numberOfItems-1, repository.findAll().size());    // There just one less

    }
}