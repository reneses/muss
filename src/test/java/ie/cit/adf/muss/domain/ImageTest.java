package ie.cit.adf.muss.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import ie.cit.adf.muss.utility.JsonMapper;


public class ImageTest {

    private File imageFile;

    @Before
    public void setUp() throws Exception {
        imageFile = new File(ParticipationTest.class.getResource("/images.json").toURI());
    }

    @Test
    public void testMapParticipant() throws Exception {

        Image image = JsonMapper.mapToClass(imageFile, Image.class);
        assertNotNull(image);
        assertEquals(12345, image.getOriginalId());
        assertEquals(true, image.isPrimary());
        assertEquals(2, image.getSizes().size());

        /*assertNotNull(image.getSizes().get("a"));
        assertEquals(1000, image.getSizes().get("a").getWidth());
        assertEquals(2000, image.getSizes().get("a").getHeight());
        assertEquals("imageURL_a", image.getSizes().get("a").getUrl());
        assertEquals("a", image.getSizes().get("a").getLabel());

        assertNotNull(image.getSizes().get("b"));
        assertEquals(100, image.getSizes().get("b").getWidth());
        assertEquals(200, image.getSizes().get("b").getHeight());
        assertEquals("imageURL_b", image.getSizes().get("b").getUrl());
        assertEquals("b", image.getSizes().get("b").getLabel());
        */
    }

}