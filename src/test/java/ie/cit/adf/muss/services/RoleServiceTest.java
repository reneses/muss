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
import ie.cit.adf.muss.domain.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MussApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@TransactionConfiguration(defaultRollback=true)
public class RoleServiceTest {

    @Autowired
    RoleService service;

    Role role;

    private void assertEqualsRole(Role expected, Role actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getOriginalId(), actual.getOriginalId());
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
        role.setOriginalId(123);
        assertFalse("Test data is corrupted. Image is not supposed to be saved.", service.findAll().contains(role));
    }

    @Test
    public void testFindAll() throws Exception {
        List<Role> roles = service.findAll();
        assertEquals(2, roles.size());
        assertFalse(roles.get(0).equals(roles.get(1)));
    }

    @Test
    public void testGet() throws Exception {
        Role role = service.find(1);
        assertEquals(1001, role.getOriginalId());
        assertEquals("role1", role.getName());
        assertEquals("Role 1", role.getDisplayName());
        assertEquals("role1.ie", role.getUrl());
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
        Role role = service.find(Integer.MAX_VALUE);
        assertNull(role);
    }

    @Test
    public void testGetByOriginalId() throws Exception {
        Role role = service.findOneByOriginalId(1001);
        assertEquals(1001, role.getOriginalId());
        assertEquals("role1", role.getName());
        assertEquals("Role 1", role.getDisplayName());
        assertEquals("role1.ie", role.getUrl());
    }

    @Test
    public void testGetByOriginalIdNotExisting() throws Exception {
        Role role = service.findOneByOriginalId(Integer.MAX_VALUE);
        assertNull(role);
    }

    @Test
    public void testGetByOriginalIdNegative() throws Exception {
        Role role = service.findOneByOriginalId(-1);
        assertNull(role);
    }

    @Test(expected = IllegalArgumentException.class)
    //@Transactional
    public void testSaveNull() throws Exception {
        service.save((Role) null);
    }

    @Test
    //@Transactional
    public void testSaveInserting() throws Exception {

        Role role = new Role();
        role.setOriginalId(31);
        role.setName("name1");
        role.setDisplayName("Name 1");
        role.setUrl("test1.ie");

        int numberOfItems = service.findAll().size();

        service.save(role);

        assertTrue(role.getId() != 0);
        assertEquals(numberOfItems + 1, service.findAll().size());
        Role insertedRole = service.find(role.getId());
        assertNotNull(insertedRole);
        assertEqualsRole(role, insertedRole);

    }


    @Test
    //@Transactional
    public void testSaveUpdating() throws Exception {

        int numberOfItems = service.findAll().size();

        service.save(role);
        role.setName("aaa");
        role.setDisplayName("A A A");
        role.setOriginalId(10);
        role.setUrl("new");
        service.save(role);

        assertEquals(numberOfItems + 1, service.findAll().size());
        assertEqualsRole(role, service.find(role.getId()));

    }

    @Test
    //@Transactional
    public void testRemove() throws Exception {

        service.save(role);
        int numberOfItems = service.findAll().size();
        service.remove(role);

        assertEquals(numberOfItems == 0? 0 : numberOfItems-1, service.findAll().size());   // There is role less
        assertFalse(service.findAll().contains(role));              // The role is not in the repo any more

    }

    @Test(expected = IllegalArgumentException.class)
    //@Transactional
    public void testRemoveNull() throws Exception {
        service.remove((Role) null);
    }

    @Test
    //@Transactional
    public void testRemoveTwice() throws Exception {

        service.save(role);
        int numberOfItems = service.findAll().size();
        service.remove(role);
        service.remove(role);

        assertEquals(numberOfItems == 0 ? 0 : numberOfItems - 1, service.findAll().size());    // There just one less

    }

    @Test
    //@Transactional
    public void testRemoveNotExisting() throws Exception {

        service.save(role);
        int numberOfItems = service.findAll().size();
        service.remove(role);
        service.remove(role);

        assertEquals(numberOfItems == 0? 0 : numberOfItems-1, service.findAll().size());    // There just one less

    }
}