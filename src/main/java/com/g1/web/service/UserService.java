package com.g1.web.service;

import com.g1.HttpException;
import com.g1.web.model.Character;
import com.g1.web.model.User;
import com.g1.web.repository.UserRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("userService")
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserService() {
    }

    public List<User> getAll() {
        return userRepository.getItems();
    }

    public User getById(long id) {
        return userRepository.getById(id);
    }

    public User getByName(String name) {
        return userRepository.getByName(name);
    }

    public String createUser(String username, String password) throws HttpException {
        User newUser = new User(username, password);

        // Validar existencia del nombre de usuario
        if (this.userExist(newUser))
            throw new HttpException(ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("El nombre de usuario ya existe."));

        newUser.setId(userRepository.getCounter().incrementAndGet());
        newUser.setUltimoAcceso(new DateTime());

        userRepository.addItem(newUser);

        return newUser.getName();
    }

    public Boolean userExist(User user) {
        return userRepository.getByName(user.getName()) != null;
    }

    public void addFavorite(User user, Character character) {
        userRepository.addFavorite(user, character);
    }

    public void removeFavorite(User user, Character character) {
        userRepository.removeFavorite(user, character);
    }
}