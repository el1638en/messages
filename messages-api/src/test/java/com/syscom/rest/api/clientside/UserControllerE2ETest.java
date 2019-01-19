package com.syscom.rest.api.clientside;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.syscom.beans.Role;
import com.syscom.beans.User;
import com.syscom.dao.RoleDao;
import com.syscom.rest.api.UserController;
import com.syscom.rest.dto.UserDTO;
import com.syscom.service.UserService;

public class UserControllerE2ETest extends AbstractE2ETest {

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private UserService userService;
	
	private Role role;
	
	@Before
	public void setUp() {
		role = Role.builder().code(R_CODE).libelle(R_LIBELLE).build();
		role = roleDao.save(role);
	}
	
	@Test
	public void testCreateUserWithoutMandatoryData() throws Exception {
		// GIVEN
		UserDTO userDTO = new UserDTO();
		HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserDTO> request = new HttpEntity<>(userDTO, headers);
        
		// WHEN
		ResponseEntity<String> response = testRestTemplate.postForEntity(UserController.PATH, request, String.class);
		
		
		// THEN		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void testCreateExistUser() throws Exception {
		// GIVEN
		User user = User.builder().login(LOGIN).password(PASSWORD).name(NAME).firstName(FIRST_NAME).role(role).build();
		userService.create(user);
		UserDTO userDTO = UserDTO.builder().name(NAME).firstName(FIRST_NAME).login(LOGIN).password(PASSWORD).role(R_CODE).build();
		HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserDTO> request = new HttpEntity<>(userDTO, headers);
        
		// WHEN
		ResponseEntity<String> response = testRestTemplate.postForEntity(UserController.PATH, request, String.class);
		
		
		// THEN		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void testDeleteUnknownUser() throws Exception {
		// GIVEN
		User user = User.builder().login(LOGIN).password(PASSWORD).name(NAME).firstName(FIRST_NAME).role(role).build();
		userService.create(user);
		
		// WHEN
		testRestTemplate.delete(UserController.PATH+"/UNKNOWN_USER_LOGIN");
		
		// THEN
		assertThat(userService.findByLogin(LOGIN)).isNotNull();
		
	}
	
	@Test
	public void testDeleteUser() throws Exception {
		// GIVEN
		Role role = Role.builder().code(R_CODE).libelle(R_LIBELLE).build();
		User user = User.builder().login(LOGIN).password(PASSWORD).name(NAME).firstName(FIRST_NAME).role(role).build();
		userService.create(user);
		
		
		// WHEN
		testRestTemplate.delete(UserController.PATH+"/"+LOGIN);
		
		// THEN
		assertThat(userService.findByLogin(LOGIN)).isNull();
	}
	
	@Test
	public void testCreateUser() throws Exception {
		// GIVEN
		UserDTO userDTO = UserDTO.builder().name(NAME).firstName(FIRST_NAME).login(LOGIN).password(PASSWORD).role(R_CODE).build();
		HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserDTO> request = new HttpEntity<>(userDTO, headers);
        
		// WHEN
		ResponseEntity<String> response = testRestTemplate.postForEntity(UserController.PATH, request, String.class);
		
		
		// THEN		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
}
