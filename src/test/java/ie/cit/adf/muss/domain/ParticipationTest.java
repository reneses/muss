package ie.cit.adf.muss.domain;

import ie.cit.adf.muss.utility.JsonMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/*
{
  "person_id": "1",
  "role_id": "2",
  "person_name": "PersonName",
  "person_date": "2000",
  "role_name": "RoleName",
  "role_display_name": "Role Name",
  "person_url": "personURL",
  "role_url": "roleURL"
}
 */

public class ParticipationTest {

    private File participationFile;

    @Before
    public void setUp() throws Exception {
        participationFile = new File(ParticipationTest.class.getResource("/participation.json").toURI());
    }

    @Test
    public void testMapParticipant() throws Exception {

        Participation participation = JsonMapper.mapToClass(participationFile, Participation.class);
        assertNotNull(participation);

        assertNotNull(participation.getParticipant());
        assertEquals(1, participation.getParticipant().getOriginalId());
        assertEquals("PersonName", participation.getParticipant().getName());
        assertEquals("2000", participation.getParticipant().getDate());
        assertEquals("personURL", participation.getParticipant().getUrl());

        assertNotNull(participation.getRole());
        assertEquals(2, participation.getRole().getOriginalId());
        assertEquals("RoleName", participation.getRole().getName());
        assertEquals("Role Name", participation.getRole().getDisplayName());
        assertEquals("roleURL", participation.getRole().getUrl());

    }
}