package ie.cit.adf.muss.repositories;


import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Image;

import java.util.List;

public interface ImageRepository extends Repository<Image> {


    /**
     * Retrieve a model given its original id
     *
     * @param originalId Original ID of the model
     * @return Model with the given original id, null if it does not exist
     */
    Image getByOriginalId(int originalId);


    /**
     * Retrieve all the images of an object
     *
     * @throws IllegalArgumentException if the id is not a positive integer
     * @param object Object from which obtain the relationships
     * @return Model with the given id, null if it does not exist
     */
    List<Image> findAll(ChObject object);


}
