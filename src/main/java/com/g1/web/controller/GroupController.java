package com.g1.web.controller;

import com.g1.HttpException;
import com.g1.web.model.Character;
import com.g1.web.model.Group;
import com.g1.web.model.User;
import com.g1.web.service.CharacterService;
import com.g1.web.service.GroupService;
import com.g1.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("group")
public class GroupController {

    @Autowired
    UserService userService;
    @Autowired
    CharacterService characterService;
    @Autowired
    GroupService groupService;

    /**
     * Devuelve la cantidad de grupos de un usuario
     *
     * @param nombreUsuario     nombre de usuario
     * @return cantidad de grupos
     */
    @RequestMapping(value = "/usuarios/{nombreUsuario}/gruposPersonajes", method = RequestMethod.GET)
    public ResponseEntity countGroup(
            @PathVariable String nombreUsuario
    ) {
        User user = userService.getByName(nombreUsuario);

        if (user == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);

        Integer count = groupService.countByUser(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(count);
    }

    /**
     * Devuelve detalle de un grupo
     * @param nombreUsuario     nombre deusuario
     * @param nombreGrupo       nombre del grupo
     * @return grupo
     */
    @RequestMapping(value = "/usuarios/{nombreUsuario}/gruposPersonajes/{nombreGrupo}", method = RequestMethod.GET)
    public ResponseEntity getInfoGroup(
            @PathVariable String nombreUsuario,
            @PathVariable final String nombreGrupo
    ) {
        User user = userService.getByName(nombreUsuario);

        if (user == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);

        Group grupo = groupService.getByUserAndName(user, nombreGrupo);

        if (grupo == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(grupo);
    }

	/**
	 * Crea un grupo para personajes
     * @param nombreUsuario     nombre deusuario
	 * @param nombreGrupo       nombre del grupo
	 */
	@RequestMapping(value = "/usuarios/{nombreUsuario}/gruposPersonajes/{nombreGrupo}", method = RequestMethod.PUT)
	public ResponseEntity addGroup(
            @PathVariable String nombreUsuario,
            @PathVariable String nombreGrupo
    ) throws HttpException {
        if (nombreGrupo.trim().isEmpty())
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);

        User user = userService.getByName(nombreUsuario);

        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        Group newGroup = new Group(nombreGrupo);

        groupService.saveGroup(user, newGroup);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

	/**
	 * Agrega un personaje a un grupo
     * @param nombreUsuario     nombre de usuario
	 * @param nombreGrupo	    nombre de un grupo
	 * @param idPersonaje	    id del personaje
	 */
	@RequestMapping(value = "/usuarios/{nombreUsuario}/gruposPersonajes/{nombreGrupo}/personajes/{idPersonaje}", method = RequestMethod.PUT)
	public ResponseEntity addCharacterToGroup(
            @PathVariable String nombreUsuario,
            @PathVariable String nombreGrupo,
            @PathVariable long idPersonaje
    ) {
        User user = userService.getByName(nombreUsuario);

        if (user == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null); // TODO falta agregar mensaje de error en TODOS los resultados con error

        Group grupo = groupService.getByUserAndName(user, nombreGrupo);

        if (grupo == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);

        Character character = characterService.getById(idPersonaje);

        if (character == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);

        groupService.addCharacter(grupo, character);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

	/**
	 * Quita un personaje de un grupo
     * @param nombreUsuario     nombre de usuario
     * @param nombreGrupo	    nombre de un grupo
     * @param idPersonaje	    id del personaje
	 */
	@RequestMapping(value = "/usuarios/{nombreUsuario}/gruposPersonajes/{nombreGrupo}/personajes/{idPersonaje}", method = RequestMethod.DELETE)
	public ResponseEntity deleteCharacterFromGroup(
            @PathVariable String nombreUsuario,
            @PathVariable String nombreGrupo,
            @PathVariable long idPersonaje
    ) {
        User user = userService.getByName(nombreUsuario);

        if (user == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null); // TODO falta agregar mensaje de error en TODOS los resultados con error

        Group grupo = groupService.getByUserAndName(user, nombreGrupo);

        if (grupo == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);

        Character character = characterService.getById(idPersonaje);

        if (character == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);

        groupService.removeCharacter(grupo, character);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
	}

	/**
	 * Devuelve una lista de personajes que es intersección de 2 listas grupo
	 * @param idGrupo1	id del primer grupo
	 * @param idGrupo2	ide del segundo grupo
	 * @return	status code   NOT_FOUND/FOUND más la lista
	 */
    @RequestMapping(value = "/gruposPersonajes/{idGrupo1}", method = RequestMethod.GET)
	public ResponseEntity getIntersection(
            @PathVariable long idGrupo1,
            @RequestParam("idGrupo2") long idGrupo2
    ) {
        List<Character> intersection = groupService.intersection(idGrupo1, idGrupo2);

		return ResponseEntity
                .status(HttpStatus.OK)
                .body(intersection);
	}
}