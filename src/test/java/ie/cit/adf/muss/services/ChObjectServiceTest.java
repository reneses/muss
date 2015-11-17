package ie.cit.adf.muss.services;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.loaders.AbstractChObjectLoader;
import ie.cit.adf.muss.loaders.ChObjectJsonLoader;
import ie.cit.adf.muss.repositories.ChObjectRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;


/**
 * ChObjectServiceTest
 *
 * We will perform a behavioural test by means of Mockito to ensure that every method in the repository
 * is called just the needed times, with the proper parameters
 */
public class ChObjectServiceTest {

    ChObjectRepository repository;
    ChObjectService service;
    AbstractChObjectLoader loader;

    ChObject returned;

    @Before
    public void setUp() throws Exception {

        // Create the mock
        repository = Mockito.mock(ChObjectRepository.class);
        returned = new ChObject();
        returned.setTitle("RETURNED");

        when(repository.getByOriginalId(anyInt())).thenReturn(returned);
        when(repository.get(anyInt())).thenReturn(returned);
        when(repository.remove(any())).thenReturn(true);
        when(repository.findAll()).thenReturn(new ArrayList<>());

        // Set up the loader and service
        loader = new ChObjectJsonLoader();
        service = new ChObjectService(repository, loader);

    }

    @Test
    public void testGetByOriginalId() throws Exception {
        assertEquals(returned, service.getByOriginalId(40));
        verify(repository, times(1)).getByOriginalId(40);
    }

    @Test
    public void testLoadFromFolder() throws Exception {
        int numberOfObjectsToBeLoaded = loader.loadChObjects().size();
        service.loadFromFolder();
        verify(repository, times(numberOfObjectsToBeLoaded)).save(any());
    }

    @Test
    public void testGet() throws Exception {
        assertEquals(returned, service.get(10));
        verify(repository, times(1)).get(10);
    }

    @Test
    public void testSave() throws Exception {
        ChObject o = new ChObject();
        service.save(o);
        verify(repository, times(1)).save(o);
    }

    @Test
    public void testRemove() throws Exception {
        ChObject o = new ChObject();
        assertTrue(service.remove(o));
        verify(repository, times(1)).remove(o);
    }

    @Test
    public void testFindAll() throws Exception {
        service.findAll();
        verify(repository, times(1)).findAll();
    }
}