package ie.cit.adf.muss.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.loaders.AbstractChObjectLoader;
import ie.cit.adf.muss.repositories.ChObjectRepository;

@Service
public class ChObjectService extends CrudService<ChObject> {

    @Autowired
    ChObjectRepository objectRepository;

    @Autowired
    ParticipationService participationService;

    @Autowired
    TagService tagService;

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
     * Retrieve a model given its original id
     *
     * @param id ID of the role
     * @return Model with the given id, null if it does not exist
     * @throws IllegalArgumentException if the id is not a positive integer
     */
    public ChObject findOneByOriginalId(int id) {
        return objectRepository.findOneByOriginalId(id);
    }


    @Override
    public ChObject save(ChObject model) {
        if (model == null)
            throw new IllegalArgumentException("The object cannot be null");
        participationService.save(model.getParticipations()); // TODO: remove this line?
        return super.save(model);
    }

    public long count() {
        return objectRepository.count();
    }
    
    public List<ChObject> findByTagName(String name) {
    	return objectRepository.findByTagName(name);
    }

    public List<ChObject> findByTitleOrDescription(String search) {
    	return objectRepository.findByTitleOrDescription(search);
    }

    public List<ChObject> findByTitleOrDescriptionAndTagName(String search, String tagName) {
    	return objectRepository.findByTitleOrDescriptionAndTagName(search, tagName);
    }

    public boolean isLikedBy(ChObject object, User user) {
        if (object == null  ||  user == null)
            return false;
        for (ChObject liked : user.getChObjectLikes())
            if (liked.equals(object))
                return true;
        return false;
    }

    public void addTag(ChObject object, String tag, User user) {
        object.addTag(tagService.create(tag, user));
        save(object);
    }

    public void addLike(ChObject object, User user) {
        object.addLike(user);
        save(object);
    }

    public void removeLike(ChObject object, User user) {
        object.removeLike(user);
        save(object);
    }
}