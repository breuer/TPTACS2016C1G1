package com.g1.web.controller;

import com.g1.web.model.Character;
import com.g1.web.model.User;
import com.g1.web.service.CharacterService;
import com.g1.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("favorite")
public class FavoriteCharacterController {

    @Autowired
    UserService userService;
    @Autowired
    CharacterService characterService;

    /**
     * Devuelve los personajes favoritos de un usuario
     *
     * @param nombreUsuario nombre de usuario
     * @return personajes favoritos
     */
    @RequestMapping(value = "/usuarios/{nombreUsuario}/personajesFavoritos", method = RequestMethod.GET)
    public ResponseEntity getFavoritos(
            @PathVariable String nombreUsuario,
            @RequestParam(defaultValue = "false") Boolean count
    ) {
        User user = userService.getByName(nombreUsuario);

        if (user == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);

        if (count)
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(user.getFavoritos().size());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user.getFavoritos());
    }

    /**
     * Marca como favorito a un personaje
     *
     * @param nombreUsuario nombre de usuario
     * @param idPersonaje   id del personaje
     * @return status code NOT_FOUND/OK
     */
    @RequestMapping(value = "/usuarios/{nombreUsuario}/personajesFavoritos/{idPersonaje}", method = RequestMethod.PUT)
    public ResponseEntity markFavorite(
            @PathVariable String nombreUsuario,
            @PathVariable long idPersonaje
    ) {
        User user = userService.getByName(nombreUsuario);

        if (user == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);

        Character character = characterService.getById(idPersonaje);

        if (character == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);

        userService.addFavorite(user, character);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    /**
     * Desmarca un personaje como favorito
     *
     * @param nombreUsuario nombre de usuario
     * @param idPersonaje   es el id del personaje
     * @return status code	NOT_FOUND/OK
     */
    @RequestMapping(value = "/usuarios/{nombreUsuario}/personajesFavoritos/{idPersonaje}", method = RequestMethod.DELETE)
    public ResponseEntity unmarkFavorite(
            @PathVariable String nombreUsuario,
            @PathVariable long idPersonaje
    ) {
        User user = userService.getByName(nombreUsuario);

        if (user == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);

        Character character = characterService.getById(idPersonaje);

        if (character == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);

        userService.removeFavorite(user, character);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }
}
