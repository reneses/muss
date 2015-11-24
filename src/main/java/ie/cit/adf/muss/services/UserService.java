package ie.cit.adf.muss.services;


import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class UserService extends CrudService<User> {

    @Autowired
    UserRepository repository;

    public boolean usernameExists(String username) {
        User user = repository.findByUsername(username);
        return user != null;
    }

    public boolean emailExists(String email) {
        User user = repository.findByEmail(email);
        return user != null;
    }

}
