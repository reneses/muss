package ie.cit.adf.muss.services;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.loaders.AbstractChObjectLoader;
import ie.cit.adf.muss.repositories.ChObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.io.IOException;
import java.util.List;

@org.springframework.stereotype.Service
public class ChObjectService extends Service<ChObject> {

    @Autowired
    ChObjectRepository objectRepository;

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
     */
    public List<ChObject> loadFromFolder() throws IOException {
        List<ChObject> objects = loader.loadChObjects();
        try {
            save(objects);
        }
        catch (DuplicateKeyException e) {
            System.err.println("The files were already imported!");
        }
        return objects;
    }

}