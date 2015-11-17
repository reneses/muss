package ie.cit.adf.muss.repositories;

import org.springframework.data.repository.CrudRepository;

import ie.cit.adf.muss.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
