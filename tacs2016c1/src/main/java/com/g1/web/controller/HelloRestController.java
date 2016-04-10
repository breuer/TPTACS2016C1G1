package com.g1.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.g1.web.model.User;
import com.g1.web.service.UserService;

@RestController
public class HelloRestController {
	@Autowired
	private UserService userService;
	//UserService userService = new UserServiceImpl(); 

	// -------------------Retrieve All
	// Users--------------------------------------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.findAllUsers();
		if (users.isEmpty()) {
			// You many decide to return HttpStatus.NOT_FOUND			
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/usuarios", method = RequestMethod.POST)
	public ResponseEntity<User> newUser(@RequestBody User usuario) {
		return new ResponseEntity<User>(usuario, HttpStatus.CREATED);
	}
}