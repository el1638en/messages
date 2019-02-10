package com.syscom.rest.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syscom.exceptions.BusinessException;
import com.syscom.rest.dto.TokenDTO;
import com.syscom.rest.mapper.TokenMapper;
import com.syscom.service.TokenService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = LoginController.PATH)
@RestController
@RequestMapping(LoginController.PATH)
public class LoginController implements BaseController {
	
	public static final String PATH = "/api/login";
	private final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private TokenMapper tokenMapper;

	/**
	 * API d'authentification d'un utilisateur.
	 *
	 * @param authotization
	 * @return token
	 * @throws BusinessException
	 * @throws AccessDeniedException
	 */
	@GetMapping
	@ApiOperation(value = "API pour l'authentification des utilisateurs", notes = "Authentification d'un utilisateur")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request param error") })
	public TokenDTO login() throws BusinessException, AccessDeniedException {
		logger.info("Login to API with credentials");

		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			throw new AccessDeniedException("Acces refuse");
		}
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return tokenMapper.beanToDto(tokenService.createToken(user.getUsername()));
	}

}
