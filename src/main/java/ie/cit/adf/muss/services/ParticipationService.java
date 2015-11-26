package ie.cit.adf.muss.services;


import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Participation;
import ie.cit.adf.muss.repositories.ParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@org.springframework.stereotype.Service
public class ParticipationService extends CrudService<Participation> {

    @Autowired
    ParticipationRepository participationRepository;

    /**
     * Retrieve all the participations of a given object
     *
     * @throws IllegalArgumentException if the id is not a positive integer
     * @param object Object from which obtain the relationships
     * @return Model with the given id, null if it does not exist
     */
    public List<Participation> findAll(ChObject object) {
        return participationRepository.findAllByObject(object);
    }

}
