package ie.cit.adf.muss.services;

import org.springframework.beans.factory.annotation.Autowired;

import ie.cit.adf.muss.domain.Participant;
import ie.cit.adf.muss.repositories.ParticipantRepository;

@org.springframework.stereotype.Service
public class ParticipantService extends CrudService<Participant> {

    @Autowired
    ParticipantRepository participantRepository;

    /**
     * Retrieve a model given its original id
     *
     * @throws IllegalArgumentException if the id is not a positive integer
     * @param id ID of the role
     * @return Model with the given id, null if it does not exist
     */
    public Participant findOneByOriginalId(int id) {
        return participantRepository.findOneByOriginalId(id);
    }

}