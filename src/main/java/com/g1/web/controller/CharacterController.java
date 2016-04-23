package com.g1.web.controller;

import com.g1.web.model.Character;
import com.g1.web.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("character")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    /**
     * Lista todos los personajes
     *
     * @return listado de los personajes
     */
    @RequestMapping(value = "/personajes", method = RequestMethod.GET)
    public ResponseEntity getCharaters(
            @RequestParam(value = "order", required = false, defaultValue = "") String order
    ) {
        List<Character> characters = characterService.getAll();

        characters = characterService.OrderCharacters(characters, order);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(characters);
    }

    /**
     * Retorna un personaje
     *
     * @param idPersonaje id del personaje
     * @return personaje
     */
    @RequestMapping(value = "/personajes/{idPersonaje}", method = RequestMethod.GET)
    public ResponseEntity getCharater(
            @PathVariable("idPersonaje") Long idPersonaje
    ) {
        Character character = characterService.getById(idPersonaje);

        if (character == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(character);
    }

}