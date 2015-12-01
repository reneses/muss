package ie.cit.adf.muss.services;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Participation;
import ie.cit.adf.muss.loaders.AbstractChObjectLoader;
import ie.cit.adf.muss.repositories.ChObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class ChObjectService extends CrudService<ChObject> {

    @Autowired
    ChObjectRepository objectRepository;

    @Autowired
    ParticipationService participationService;

    @Autowired
    AbstractChObjectLoader loader;

    /**
     * Default constructor
     */
    public ChObjectService() {
    }

    /**
     * Constructor specifying a custom repository
     *
     * @param objectRepository
     */
    public ChObjectService(ChObjectRepository objectRepository) {
        super(objectRepository);
        this.objectRepository = objectRepository;
    }

    /**
     * Constructor specifying a custom loader
     *
     * @param loader
     */
    public ChObjectService(AbstractChObjectLoader loader) {
        this.loader = loader;
    }


    /**
     * Constructor specifying a custom loader and repository
     *
     * @param objectRepository
     * @param loader
     */
    public ChObjectService(ChObjectRepository objectRepository, AbstractChObjectLoader loader) {
        super(objectRepository);
        this.objectRepository = objectRepository;
        this.loader = loader;
    }


    /**
     * Load the objects from the disk
     *
     * @return list of objects
     * @throws IOException
     * @throws URISyntaxException 
     */
    public List<ChObject> loadFromFolder() throws IOException, URISyntaxException {
        List<ChObject> objects = loader.loadChObjects();
        try {
            save(objects);
        }
        catch (DuplicateKeyException e) {
            System.err.println("The files were already imported!");
        }
        return objects;
    }


    /**
     * Retrieve a model given its original id
     *
     * @throws IllegalArgumentException if the id is not a positive integer
     * @param id ID of the role
     * @return Model with the given id, null if it does not exist
     */
    public ChObject findOneByOriginalId(int id) {
        return objectRepository.findOneByOriginalId(id);
    }


    @Override
    public ChObject save(ChObject model) {
        if (model == null)
            throw new IllegalArgumentException("The object cannot be null");
        participationService.save(model.getParticipations());
        return super.save(model);
    }

}