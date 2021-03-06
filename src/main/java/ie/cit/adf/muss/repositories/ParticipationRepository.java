package ie.cit.adf.muss.repositories;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Participation;

public interface ParticipationRepository extends CrudRepository<Participation, Integer> {


    /**
     * Retrieve all the participations of a given object
     *
     * @throws IllegalArgumentException if the id is not a positive integer
     * @param object Object from which obtain the relationships
     * @return Model with the given id, null if it does not exist
     */
    List<Participation> findAllByChObject(ChObject object);


}
