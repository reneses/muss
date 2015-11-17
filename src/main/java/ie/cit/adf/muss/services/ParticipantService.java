package ie.cit.adf.muss.services;

import ie.cit.adf.muss.domain.Participant;
import ie.cit.adf.muss.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;


@org.springframework.stereotype.Service
public class ParticipantService extends Service<Participant> {

    @Autowired
    ParticipantRepository participantRepository;


    /**
     * Retrieve a model given its original id
     *
     * @param originalId Original ID of the model
     * @return Model with the given original id, null if it does not exist
     */
    public Participant getByOriginalId(int originalId) {
        return participantRepository.getByOriginalId(originalId);
    }

}