package ie.cit.adf.muss.services;

import ie.cit.adf.muss.domain.Participant;
import ie.cit.adf.muss.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class ParticipantService extends Service<Participant> {

    @Autowired
    ParticipantRepository participantRepository;

}