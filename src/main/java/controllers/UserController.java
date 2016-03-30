package controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import domain.User;
import services.UserService;

@RestController
public class UserController {

    UserService service;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder) {
        // TODO validate user

        service.createUser(user);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@RequestParam Integer id) {
        return new ResponseEntity<User>(service.getUser(id), HttpStatus.OK);
    }
}
