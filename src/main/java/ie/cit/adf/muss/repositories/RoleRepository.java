package ie.cit.adf.muss.repositories;

import org.springframework.data.repository.CrudRepository;

import ie.cit.adf.muss.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    Role findOneByOriginalId(int id);

}
