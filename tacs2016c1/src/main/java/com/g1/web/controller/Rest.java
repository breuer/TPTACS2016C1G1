package com.g1.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	/**
	 *	Crea un nuevo usuario
	 *	@param	username	es el nombre de usuario
	 * 	@param	password	es el password de usuario
	 * 	@return	httpStatus	status code
	 * */
	@RequestMapping(value = "/usuarios", method = RequestMethod.POST)
	public ResponseEntity<String> createUser(@RequestParam("username") String username, @RequestParam("password") String password) {
		userService.createUser(username, password);
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
	
	/**
	 * Lista todos los personajes
	 * @return	status code más listado de los personajes
	 */
	@RequestMapping(value = "/personajes/list", method = RequestMethod.GET)
	public ResponseEntity<List<Character>> getCharaters() {
		List<Character> characters = characterService.findAllCharacters();
		return new ResponseEntity<List<Character>>(characters, HttpStatus.FOUND);
	}

	/**
	 * Marca como favorito a un personaje
	 * @param idPersonaje	es el id del personaje	
	 * @return	status code NOT_FOUND/OK
	 */
	@RequestMapping(value = "/personajesFavoritos/{idPersonaje}", method = RequestMethod.PUT)
	public ResponseEntity<String> markFavorite(@PathVariable  long idPersonaje) {
		int result = characterService.markFavorite(idPersonaje);
		if(result < 0)
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * Desmarca un personaje como favorito
	 * @param idPersonaje	es el id del personaje
	 * @return	status code	NOT_FOUND/OK
	 */
	@RequestMapping(value = "/personajesFavoritos/{idPersonaje}", method = RequestMethod.DELETE)
	public ResponseEntity<String> unmarkFavorite(@PathVariable long idPersonaje) {
		int result = characterService.unmarkFavorite(idPersonaje);
		if(result < 0)
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/**
	 * Obtiene un listado con los personajes favoritos
	 * @return	status code más listado con los personajes favoritos
	 */
	@RequestMapping(value = "/personajesFavoritos", method = RequestMethod.GET)
	public ResponseEntity<List<Character>> getFavorites() {
		List<Character> characters = characterService.findAllFavorites();
		return new ResponseEntity<List<Character>>(characters, HttpStatus.FOUND);
	}
	
	
	/**
	 * Crea un grupo para personajes
	 * @param nombreGrupo	es el nombre del grupo
	 * @return status code
	 */
	@RequestMapping(value = "/gruposPersonajes/{nombreGrupo}", method = RequestMethod.PUT)
	public ResponseEntity<String> addGroup(@PathVariable String nombreGrupo) {
		characterService.createGroup(nombreGrupo);
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}

	/**
	 * Devuelve información de un grupo
	 * @param nombreGrupo	es el nombre del grupo
	 * @return	status code NOT_FOUND/FOUND más un grupo o no
	 */
	@RequestMapping(value = "/gruposPersonajes/{nombreGrupo}", method = RequestMethod.GET)
	public ResponseEntity<Group> getGroup(@PathVariable String nombreGrupo) {
		Group group = characterService.getGroupByName(nombreGrupo);
		if(group == null)
			return new ResponseEntity<Group>(HttpStatus.NOT_FOUND);		
		return new ResponseEntity<Group>(group, HttpStatus.FOUND);
	}

	/**
	 * Agrega un personaje a un grupo
	 * @param nombreGrupo	es el nombre de un grupo
	 * @param idPersonaje	es el id de un personaje
	 * @return	status code
	 */
	@RequestMapping(value = "/gruposPersonajes/{nombreGrupo}/personajes/{idPersonaje}", method = RequestMethod.PUT)
	public ResponseEntity<String> addCharacterToGroup(@PathVariable String nombreGrupo, @PathVariable long idPersonaje) {
		characterService.addCharacterToGroup(nombreGrupo, idPersonaje);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * Quita un personaje de un grupo
	 * @param nombreGrupo	es el nombre de un grupo
	 * @param idPersonaje	es el id de un personaje
	 * @return	status code
	 */
	@RequestMapping(value = "/gruposPersonajes/{nombreGrupo}/personajes/{idPersonaje}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCharacterFromGroup(@PathVariable String nombreGrupo, @PathVariable long idPersonaje) {
		characterService.deleteCharacterFromGroup(nombreGrupo, idPersonaje);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/**
	 * Devuelve información de un usuario
	 * @param idUsuario	es el id del usuario
	 * @return status code  NOT_FOUND/FOUND más un usuario
	 */
	@RequestMapping(value = "/usuarios/{idUsuario}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable long idUsuario ) {
		User user = userService.findById(idUsuario);
		if(user == null)
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);		
		return new ResponseEntity<User>(user, HttpStatus.FOUND);
	}
	
	/**
	 * Devuelve todos los usuarios
	 * @return	status code más lista de usuarios
	 */
	@RequestMapping(value = "/usuarios", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.findAllUsers();
		return new ResponseEntity<List<User>>(users, HttpStatus.FOUND);
	}
	
	/**
	 * Devuelve la cantidad de grupos
	 * @return	status code más la cantidad
	 */
	@RequestMapping(value = "/usuarios/gruposPersonajes/count", method = RequestMethod.GET)
	public ResponseEntity<Integer> countGroup() {
		Integer count = characterService.countGroup();
		return new ResponseEntity<Integer>(count, HttpStatus.OK);
	}

	/**
	 * Devuelve la cantidad de los personajes marcados como favoritos
	 * @return	status code más la cantidad
	 */
	@RequestMapping(value = "/usuarios/personajesFavoritos/count", method = RequestMethod.GET)
	public ResponseEntity<Integer> countFavoriteCharacter() {
		Integer count = characterService.countFavoriteCharacter();
		return new ResponseEntity<Integer>(count, HttpStatus.OK);
	}
	
	/**
	 * Devuelve detalle de un grupo
	 * @param idGrupoPersonaje	es el id de un grupo 	
	 * @return	status code   NOT_FOUND/FOUND más un grupo
	 */
	@RequestMapping(value = "/usuarios/gruposPersonajes/{idGrupoPersonaje}", method = RequestMethod.GET)
	public ResponseEntity<Group> getInfoGroup(@PathVariable long idGrupoPersonaje) {
		Group group = characterService.getGroupById(idGrupoPersonaje);
		if(group == null)
			return new ResponseEntity<Group>(HttpStatus.NOT_FOUND);	
		return new ResponseEntity<Group>(group, HttpStatus.FOUND);
	}

	/**
	 * Devuelve la fecha y la hora del último acceso de un usuario
	 * @param idUsuario	 es el id del usuario
	 * @return 	status code   NOT_FOUND/FOUND más la fecha y la hora
	 */
	@RequestMapping(value = "/usuarios/{idUsuario}/ultimoAcceso", method = RequestMethod.GET)
	public ResponseEntity<String> getLastAccess(@PathVariable long idUsuario) {
		User user = userService.findById(idUsuario);
		if(user == null)
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);	
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String ultimoAcceso = df.format(user.getUltimoAcceso());
		return new ResponseEntity<String>(ultimoAcceso, HttpStatus.FOUND);
	}
	
	/**
	 * Devuelve una lista ranking de personajes según un filtro
	 * @param order	filtro de la lista
	 * @return	status code   NOT_FOUND/FOUND más la lista
	 */
	@RequestMapping(value = "/personajes", method = RequestMethod.GET)
	public ResponseEntity<List<Character>> getRanking(@RequestParam("order") String order) {
		List<Character> characters = characterService.getRankingByOrder(order);
		return new ResponseEntity<List<Character>>(characters, HttpStatus.OK);
	}
	
	/**
	 * Devuelve una lista de personajes que es intersección de 2 listas grupo
	 * @param idGrupo1	id del primer grupo
	 * @param idGrupo2	ide del segundo grupo
	 * @return	status code   NOT_FOUND/FOUND más la lista
	 */	
	@RequestMapping(value = "/gruposPersonajes/{idGrupo1}", method = RequestMethod.GET)
	public ResponseEntity<List<Character>> getIntersection(@PathVariable long idGrupo1, @RequestParam("idGrupo2") long idGrupo2) {
		List<Character> characters = characterService.getIntersection(idGrupo1, idGrupo2);
		if(characters == null)
			return new ResponseEntity<List<Character>>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<List<Character>>(characters, HttpStatus.FOUND);
	}

}
