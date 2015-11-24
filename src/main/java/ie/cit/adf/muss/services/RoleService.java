package ie.cit.adf.muss.services;


import ie.cit.adf.muss.domain.Role;
import ie.cit.adf.muss.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class RoleService extends CrudService<Role> {

    @Autowired
    RoleRepository roleRepository;

}
