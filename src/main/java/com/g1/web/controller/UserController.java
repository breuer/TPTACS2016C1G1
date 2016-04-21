package com.g1.web.controller;

import com.g1.HttpException;
import com.g1.web.model.User;
import com.g1.web.service.CharacterService;
import com.g1.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController("user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private CharacterService characterService;

	/**
	 * Crea un nuevo usuario
	 *
	 * @param username nombre de usuario
	 * @param password password de usuario
	 * @return identificador del nuevo usuario
	 */
	@RequestMapping(value = "/usuarios", method = RequestMethod.POST)
	public ResponseEntity createUser(
			@RequestParam("username") String username,
			@RequestParam("password") String password
	) throws HttpException { // TODO error filters!
		if (username.trim().isEmpty())
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("El nombre de usuario es obligatorio.");

		if (password.trim().isEmpty())
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("La contraseña es obligatoria.");

		String newUsername = userService.createUser(username, password);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(newUsername);
	}

	/**
	 * Retorna la lista de usuarios
	 *
	 * @return listado de usuarios
	 */
	@RequestMapping(value = "/usuarios", method = RequestMethod.GET)
	public ResponseEntity getUsers() {
		List<User> users = userService.getAll();

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(users);
	}

	/**
	 * Retorna un usuario
	 *
	 * @param nombreUsuario nombre de usuario
	 * @return usuario
	 */
	@RequestMapping(value = "/usuarios/{nombreUsuario}", method = RequestMethod.GET)
	public ResponseEntity getUser(
			@PathVariable String nombreUsuario,
			@RequestParam(required = false) String[] fields
	) {
        User user = userService.getByName(nombreUsuario);

        if (user == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);

        if (fields != null && fields.length > 0) { // TODO esto quedo horribleeeeeeeeeeeeeeeeeeeee
            User dtoUser = new User();
            if (Stream.of(fields).anyMatch(str -> str.equals("name")))
                dtoUser.setName(user.getName());
            if (Stream.of(fields).anyMatch(str -> str.equals("password")))
                dtoUser.setPassword(user.getPassword());
            if (Stream.of(fields).anyMatch(str -> str.equals("ultimoAcceso")))
                dtoUser.setUltimoAcceso(user.getUltimoAcceso());    // TODO falta formatear el datetime
            if (Stream.of(fields).anyMatch(str -> str.equals("favoritos")))
                dtoUser.setFavoritos(user.getFavoritos());
            user = dtoUser;
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }
}