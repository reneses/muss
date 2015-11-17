package ie.cit.adf.muss.domain;


import ie.cit.adf.muss.utility.JsonMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ChObjectTest {

    File file;

    @Before
    public void setUp() throws Exception {
        file = new File(ParticipationTest.class.getResource("/chobject.json").getFile());
    }

    @Test
    public void testMapChObject() throws Exception {

        ChObject object = JsonMapper.mapToClass(file, ChObject.class);
        assertNotNull(object);

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