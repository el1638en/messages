package com.syscom.rest.api.clientside;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.syscom.beans.Role;
import com.syscom.beans.User;
import com.syscom.dao.RoleDao;
import com.syscom.rest.api.LoginController;
import com.syscom.rest.dto.TokenDTO;
import com.syscom.service.UserService;

public class LoginControllerE2ETest extends AbstractE2ETest {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleDao roleDao;

	private Role role;
	private User user;

	@Before
	public void setup() {
		role = Role.builder().code(R_CODE).libelle(R_LIBELLE).build();
		user = User.builder().login(LOGIN).password(PASSWORD).name(NAME).firstName(FIRST_NAME).role(roleDao.save(role))
				.build();
	}

	@Test
	public void testAuthenticationWithWithWrongCredentials() throws Exception {
		// GIVEN
		userService.create(user);
				
		// WHEN
		ResponseEntity<String> response = testRestTemplate.withBasicAuth(
				"wrongLogin", "wrongPassword").getForEntity(LoginController.PATH,String.class);
			
		// THEN
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}
	
	@Test
	public void testAuthentication() throws Exception {
		// GIVEN
		userService.create(user);
				
		// WHEN		
		ResponseEntity<TokenDTO> response = testRestTemplate.withBasicAuth(
				LOGIN, PASSWORD).getForEntity(LoginController.PATH,TokenDTO.class);
			
		// THEN
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		TokenDTO tokenDTO = response.getBody();
		assertThat(tokenDTO).isNotNull();
		assertThat(tokenDTO.getValue()).isNotNull();
		assertThat(tokenDTO.getUserDTO()).isNotNull();
		assertThat(tokenDTO.getUserDTO().getLogin()).isEqualTo(user.getLogin());

	}
}
