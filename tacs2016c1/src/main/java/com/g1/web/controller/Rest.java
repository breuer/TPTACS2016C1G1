package com.g1.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.g1.web.model.User;
import com.g1.web.model.Character;
import com.g1.web.model.Group;
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
	
	@RequestMapping(value = "/usuarios/personajes", method = RequestMethod.GET)
	public ResponseEntity<List<Character>> getCharaters() {
		List<Character> characters = characterService.findAllCharacters();
		return new ResponseEntity<List<Character>>(characters, HttpStatus.FOUND);
	}

	@RequestMapping(value = "/usuarios/personajesFavoritos/{idPersonaje}", method = RequestMethod.PUT)
	public ResponseEntity<String> markFavorite(@PathVariable  long idPersonaje) {
		characterService.markFavorite(idPersonaje);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/usuarios/personajesFavoritos/{idPersonaje}", method = RequestMethod.DELETE)
	public ResponseEntity<String> unmarkFavorite(@PathVariable long idPersonaje) {
		characterService.unmarkFavorite(idPersonaje);;
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/usuarios/personajesFavoritos", method = RequestMethod.GET)
	public ResponseEntity<List<Character>> getFavorites() {
		List<Character> characters = characterService.findAllFavorites();
		return new ResponseEntity<List<Character>>(characters, HttpStatus.FOUND);
	}

	@RequestMapping(value = "/usuarios/gruposPersonajes/{nombreGrupo}", method = RequestMethod.PUT)
	public ResponseEntity<String> addGroup(@PathVariable String nombreGrupo) {
		characterService.createGroup(nombreGrupo);
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/usuarios/gruposPersonajes/{nombreGrupo}", method = RequestMethod.GET)
	public ResponseEntity<Group> getGroup(@PathVariable String nombreGrupo) {
		Group group = characterService.getGroupByName(nombreGrupo);
		return new ResponseEntity<Group>(group, HttpStatus.FOUND);
	}

	@RequestMapping(value = "/usuarios/gruposPersonajes/{nombreGrupo}/personaje/{idPersonaje}", method = RequestMethod.PUT)
	public ResponseEntity<String> addCharacterToGroup(@PathVariable String nombreGrupo, @PathVariable long idPersonaje) {
		characterService.addCharacterToGroup(nombreGrupo, idPersonaje);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/usuarios/gruposPersonajes/{nombreGrupo}/personaje/{idPersonaje}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCharacterFromGroup(@PathVariable String nombreGrupo, @PathVariable long idPersonaje) {
		characterService.deleteCharacterFromGroup(nombreGrupo, idPersonaje);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/usuarios/{idUsuario}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable long idUsuario ) {
		User user = userService.findById(idUsuario);
		return new ResponseEntity<User>(user, HttpStatus.FOUND);
	}
	
	@RequestMapping(value = "/usuarios", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.findAllUsers();
		return new ResponseEntity<List<User>>(users, HttpStatus.FOUND);
	}
	
	@RequestMapping(value = "/usuarios/gruposPersonajes/count", method = RequestMethod.GET)
	public ResponseEntity<Integer> countGroup() {
		Integer count = characterService.countGroup();
		return new ResponseEntity<Integer>(count, HttpStatus.OK);
	}

	@RequestMapping(value = "/usuarios/personajesFavoritos/count", method = RequestMethod.GET)
	public ResponseEntity<Integer> countFavoriteCharacter() {
		Integer count = characterService.countFavoriteCharacter();
		return new ResponseEntity<Integer>(count, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/usuarios/gruposPersonajes/detalle/{idGrupoPersonaje}", method = RequestMethod.GET)
	public ResponseEntity<Group> getInfoGroup(@PathVariable long idGrupoPersonaje) {
		Group group = characterService.getGroupById(idGrupoPersonaje);
		return new ResponseEntity<Group>(group, HttpStatus.FOUND);
	}


}
