package repositories;

import domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static List<User> users = new ArrayList<User>(){{
        add(new User("user", "pass"));
    }};

    public void createUser(User user) {
        UserRepository.users.add(user);
    }

    public User getUser(Integer id) {
        return UserRepository.users.get(0);
    }

}
