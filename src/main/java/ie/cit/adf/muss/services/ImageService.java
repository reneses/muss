package ie.cit.adf.muss.services;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Image;
import ie.cit.adf.muss.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ImageService extends Service<Image> {

    @Autowired
    ImageRepository imageRepository;

    /**
     * Retrieve a model given its original id
     *
     * @param originalId Original ID of the model
     * @return Model with the given original id, null if it does not exist
     */
    public Image getByOriginalId(int originalId) {
        return imageRepository.getByOriginalId(originalId);
    }

    /**
     * Retrieve all the images of an object
     *
     * @throws IllegalArgumentException if the id is not a positive integer
     * @param object Object from which obtain the relationships
     * @return Model with the given id, null if it does not exist
     */
     public List<Image> findAll(ChObject object) {
         return imageRepository.findAll(object);
     }

}
