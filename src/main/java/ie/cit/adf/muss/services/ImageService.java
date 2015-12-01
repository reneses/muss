package ie.cit.adf.muss.services;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Image;
import ie.cit.adf.muss.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ImageService extends CrudService<Image> {

    @Autowired
    ImageRepository imageRepository;

    /**
     * Retrieve all the images of an object
     *
     * @throws IllegalArgumentException if the id is not a positive integer
     * @param object Object from which obtain the relationships
     * @return Model with the given id, null if it does not exist
     */
     public List<Image> findAll(ChObject object) {
         return imageRepository.findAllByChObject(object);
     }

    /**
     * Retrieve a model given its original id
     *
     * @throws IllegalArgumentException if the id is not a positive integer
     * @param id ID of the role
     * @return Model with the given id, null if it does not exist
     */
    public Image findOneByOriginalId(int id) {
        return imageRepository.findOneByOriginalId(id);
    }

}
