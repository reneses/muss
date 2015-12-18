package ie.cit.adf.muss.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ie.cit.adf.muss.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByEmail(String email);
    User findByUsername(String username);
    
    @Query("select u from User u order by u.points DESC")
    List<User> findSortedByPoints();

}
