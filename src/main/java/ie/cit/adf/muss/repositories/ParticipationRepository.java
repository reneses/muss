package ie.cit.adf.muss.repositories;


import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Participation;

import java.util.List;

public interface ParticipationRepository extends Repository<Participation> {


    /**
     * Retrieve all the participations of a given object
     *
     * @throws IllegalArgumentException if the id is not a positive integer
     * @param object Object from which obtain the relationships
     * @return Model with the given id, null if it does not exist
     */
    List<Participation> findAll(ChObject object);


}
