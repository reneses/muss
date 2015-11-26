package ie.cit.adf.muss.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Tag;
import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.repositories.TagRepository;

@Service
@Transactional
public class TagService{

	// ------------------- Managed repository --------------------
	
    @Autowired
    TagRepository tagRepository;
    
    // ------------------- Supporting services -------------------
    
    // ----------------------- Constructor -----------------------
    
    // ------------------- Simple CRUD methods -------------------

	public Collection<Tag> findAll(){
		return (Collection<Tag>) tagRepository.findAll();
	}

	public Tag create(){
		Tag tag = new Tag();
		
		User user = LoginService.getPrincipal();
		tag.setUser(user);
		
		return tag;
    }

    public void save(Tag tag, ChObject chObject){
    	Assert.notNull(tag);

    	// If exists in DB, work over that one
    	Tag tagDB = tagRepository.findByTag(tag.getTag());
    	if(tagDB != null)			
    		tag = tagDB;
    	
    	// If the Object does not have the Tag, add it
    	List<ChObject> chObjects = new ArrayList<>(tag.getChObjects());
    	if(!chObjects.contains(chObject)){
    		chObjects.add(chObject);
    		tag.setChObjects(chObjects);
    	}
    	
    	tagRepository.save(tag);
    }

    public void delete (Tag tag){
    	Assert.notNull(tag);
//    	checkPrincipal(tag);
    	
    	tagRepository.delete(tag);
    }
    
    public void deleteObjectFromTag (Tag tag, ChObject chObject){
    	Assert.notNull(tag);
//    	checkPrincipal(tag);
    	
    	Tag tagDB = tagRepository.findByTag(tag.getTag());
    	if(tagDB != null){		
    		List<ChObject> chObjects = new ArrayList<>(tag.getChObjects());
    		chObjects.remove(chObject);
    		tagDB.setChObjects(chObjects);
    	}
    	
    	tagRepository.delete(tag);
    }
    
    // ----------------- Other business methods ------------------

    // REPOSITORY:

    // USE CASES:
    
}