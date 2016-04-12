package com.g1.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.g1.web.model.User;
import com.g1.web.model.Character;
import com.g1.web.service.CharacterService;
import com.g1.web.service.UserService;

@RestController
public class Rest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CharacterService characterService;
	

	@RequestMapping(value = "/usuarios", method = RequestMethod.POST)
	public ResponseEntity<String> createUser(@RequestParam("username") String username, @RequestParam("password") String password) {
		userService.createUser(username, password);
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/personajes", method = RequestMethod.GET)
	public ResponseEntity<List<Character>> getCharaters() {
		List<Character> characters = characterService.findAllCharacters();
		return new ResponseEntity<List<Character>>(characters, HttpStatus.OK);
	}
}
