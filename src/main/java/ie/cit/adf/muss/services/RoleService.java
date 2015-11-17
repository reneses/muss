package ie.cit.adf.muss.services;


import ie.cit.adf.muss.domain.Role;
import ie.cit.adf.muss.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class RoleService extends Service<Role> {

    @Autowired
    RoleRepository roleRepository;

    /**
     * Retrieve a model given its original id
     *
     * @param originalId Original ID of the model
     * @return Model with the given original id, null if it does not exist
     */
    public Role getByOriginalId(int originalId) {
        return roleRepository.getByOriginalId(originalId);
    }

}
