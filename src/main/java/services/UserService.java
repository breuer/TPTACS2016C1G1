package services;

import domain.User;
import repositories.UserRepository;

public class UserService {

    UserRepository repository;

    public void createUser(User user) {
        repository.createUser(user);
    }

    public User getUser(Integer id) {
        return repository.getUser(id);
    }

}
