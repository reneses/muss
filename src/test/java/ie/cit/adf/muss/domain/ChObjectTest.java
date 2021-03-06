package ie.cit.adf.muss.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import ie.cit.adf.muss.utility.JsonMapper;

public class ChObjectTest {

    File file;

    @Before
    public void setUp() throws Exception {
        file = new File(ParticipationTest.class.getResource("/chobject.json").toURI());
    }

    @Test
    public void testMapChObject() throws Exception {

        ChObject object = JsonMapper.mapToClass(file, ChObject.class);
        assertNotNull(object);

        assertEquals(12345, object.getOriginalId());
        assertEquals("TITLE", object.getTitle());
        assertEquals("DATE", object.getDate());
        assertEquals("MEDIUM", object.getMedium());
        assertEquals("DESCRIPTION", object.getDescription());
        assertEquals("CREDIT", object.getCreditLine());
        assertEquals("GALLERY", object.getGalleryText());

        assertEquals(3, object.getParticipations().size());
        object.getParticipations().forEach( p -> {
            assertEquals(object, p.getChObject());
        });

        assertEquals(2, object.getImages().size());
        //assertTrue(object.getImages().get(0).getSizes().containsKey("A"));
        //assertTrue(object.getImages().get(0).getSizes().containsKey("B"));
        //assertTrue(object.getImages().get(1).getSizes().containsKey("C"));
        //assertTrue(object.getImages().get(1).getSizes().containsKey("D"));

    }


}