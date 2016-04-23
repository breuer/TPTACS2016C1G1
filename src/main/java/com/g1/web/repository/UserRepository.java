package com.g1.web.repository;

import com.g1.web.model.Character;
import com.g1.web.model.User;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserRepository extends IRepository<User> {

    public UserRepository() {
        this.addItem(new User("user1", "pass1") {{
            setUltimoAcceso(DateTime.now());
        }});
    }

    public User getById(Long id) {
        return this.getItem(user -> user.getId().equals(id));
    }

    public User getByName(String name) {
        return this.getItem(user -> user.getName().equals(name));
    }

    @Override
    public AtomicLong getCounter() {
        return counter;
    }

    public void addFavorite(User user, Character character) {
        user.addFavorito(character);
    }

    public void removeFavorite(User user, Character character) {
        user.removeFavorito(character);
    }
}
