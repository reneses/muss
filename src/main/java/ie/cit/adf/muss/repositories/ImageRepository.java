package ie.cit.adf.muss.repositories;


import ie.cit.adf.muss.domain.ChObject;

import java.util.List;

import ie.cit.adf.muss.domain.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Integer> {

    /**
     * Retrieve all the images of an object
     *
     * @throws IllegalArgumentException if the id is not a positive integer
     * @param object Object from which obtain the relationships
     * @return Model with the given id, null if it does not exist
     */
    List<Image> findAllByChObject(ChObject object);

    Image findOneByOriginalId(int id);

}
