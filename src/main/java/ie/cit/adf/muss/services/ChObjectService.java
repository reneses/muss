package ie.cit.adf.muss.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Gamification;
import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.repositories.ChObjectRepository;

@Service
public class ChObjectService extends CrudService<ChObject> {

	// ------------------- Managed repository --------------------
	
    @Autowired
    ChObjectRepository objectRepository;
    
    // ------------------- Supporting services -------------------
    
    @Autowired
    ParticipationService participationService;
    @Autowired
    TagService tagService;
    @Autowired
    GamificationService gamificationService;

    // ----------------------- Constructor -----------------------

    // ------------------- Simple CRUD methods -------------------
    
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
    	Assert.notNull(model, "The object cannot be null");

        participationService.save(model.getParticipations()); // TODO: remove this line?
        return super.save(model);
    }

    // ----------------- Other business methods ------------------

    // REPOSITORY:

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
    
    // USE CASES:
    
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
        
        gamificationService.assignPoints(Gamification.TAG, user);
    }

    public void addLike(ChObject object, User user) {
        boolean real = object.addLike(user);
        save(object);
        
        if(real)
			gamificationService.assignPoints(Gamification.LIKEGIVEN, user);
    }

    public void removeLike(ChObject object, User user) {
    	boolean real = object.removeLike(user);
        save(object);
        
        if(real)
			gamificationService.removePoints(Gamification.LIKEGIVEN, user);
    }
}