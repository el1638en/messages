package com.syscom.rest.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.syscom.exceptions.BusinessException;
import com.syscom.rest.dto.UserDTO;
import com.syscom.rest.mapper.UserMapper;
import com.syscom.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * API pour la création de utilisateurs
 *
 */
@Api(value = UserController.PATH, description = "Creation d'un utilisateur")
@RestController
@RequestMapping(UserController.PATH)
public class UserController {

	public static final String PATH = "/api/user";
	
	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMapper userMapper;

	/**
	 * API pour creer un nouvel utilisateur
	 *
	 * @param userDTO {@link UserDTO}
	 * @throws BusinessException Exception fonctionnelle {@link BusinessException}
	 */
	@PostMapping
	@ApiOperation(value = "Creer un nouvel utilisateur", notes = "Creer un nouvel utilisateur")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request param error") })
	public void createUser(@ApiParam(value = "Utilisateur à creer", required = true) @RequestBody UserDTO userDTO)
			throws BusinessException {
		logger.info("Create new user {}", userDTO);
        userService.create(userMapper.dtoToBean(userDTO));
	}

	
	/**
	 * API pour supprimer un utilisateur.
	 *
	 * @param login identifiant de l'utilisateur à supprimer.
	 * @throws BusinessException Exception fonctionnelle {@link BusinessException}
	 */
	@DeleteMapping(value = "/{login}")
	@ApiOperation(value = "Supprimer un utilisateur", notes = "Supprimer un utilisateur")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request param error") })
	public void delete(@PathVariable("login") String login) throws BusinessException {
		logger.info("Delete user identified by login {}.", login);
		userService.delete(login);
	}

}