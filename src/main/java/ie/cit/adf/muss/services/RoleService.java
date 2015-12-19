package ie.cit.adf.muss.services;

import org.springframework.beans.factory.annotation.Autowired;

import ie.cit.adf.muss.domain.Role;
import ie.cit.adf.muss.repositories.RoleRepository;

@org.springframework.stereotype.Service
public class RoleService extends CrudService<Role> {

    @Autowired
    RoleRepository roleRepository;

    /**
     * Retrieve a model given its original id
     *
     * @throws IllegalArgumentException if the id is not a positive integer
     * @param id ID of the role
     * @return Model with the given id, null if it does not exist
     */
    public Role findOneByOriginalId(int id) {
        return roleRepository.findOneByOriginalId(id);
    }

}
