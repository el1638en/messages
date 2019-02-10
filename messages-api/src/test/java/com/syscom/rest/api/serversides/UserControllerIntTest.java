package com.syscom.rest.api.serversides;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.syscom.beans.Role;
import com.syscom.beans.User;
import com.syscom.dao.RoleDao;
import com.syscom.rest.TestUtil;
import com.syscom.rest.api.UserController;
import com.syscom.rest.dto.UserDTO;
import com.syscom.service.UserService;

public class UserControllerIntTest extends AbstractIntTest {

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private UserService userService;
	
	
	@Test
	public void testCreateUserWithoutMandatoryData() throws Exception {
		// GIVEN
		Role role = Role.builder().code(R_CODE).libelle(R_LIBELLE).build();
		roleDao.save(role);
		UserDTO  userDTO = new UserDTO();
		
		// WHEN
		
		// THEN		
		 mockMvc
         .perform(MockMvcRequestBuilders.post(UserController.PATH).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(userDTO)))
         .andExpect(status().isBadRequest());
	}
	
	@Test
	public void testCreateExistUser() throws Exception {
		// GIVEN
		Role role = Role.builder().code(R_CODE).libelle(R_LIBELLE).build();
		User user = User.builder().login(LOGIN).password(PASSWORD).name(NAME).firstName(FIRST_NAME).role(roleDao.save(role)).build();
		userService.create(user);
		
		UserDTO userDTO = UserDTO.builder().name(NAME).firstName(FIRST_NAME).login(LOGIN).password(PASSWORD).role(R_CODE).build();
		
		// WHEN
		
		// THEN		
		 mockMvc
         .perform(MockMvcRequestBuilders.post(UserController.PATH).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(userDTO)))
         .andExpect(status().isBadRequest());
	}
	
	
	@Test
	public void testCreateUser() throws Exception {
		// GIVEN
		Role role = Role.builder().code(R_CODE).libelle(R_LIBELLE).build();
		roleDao.save(role);
		UserDTO userDTO = UserDTO.builder().name(NAME).firstName(FIRST_NAME).login(LOGIN).password(PASSWORD).role(R_CODE).build();
		
		// WHEN
		
		// THEN		
		 mockMvc
         .perform(MockMvcRequestBuilders.post(UserController.PATH).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(userDTO)))
         .andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteUnknownUser() throws Exception {
		// GIVEN
		
		// WHEN
		
		// THEN		
		 mockMvc
         .perform(MockMvcRequestBuilders.delete(UserController.PATH+"/"+LOGIN).contentType(TestUtil.APPLICATION_JSON_UTF8))
         .andExpect(status().isBadRequest());
	}
	
	@Test
	public void testDeleteUser() throws Exception {
		// GIVEN
		Role role = Role.builder().code(R_CODE).libelle(R_LIBELLE).build();
		User user = User.builder().login(LOGIN).password(PASSWORD).name(NAME).firstName(FIRST_NAME).role(roleDao.save(role)).build();
		userService.create(user);
		
		// WHEN
		
		// THEN		
		 mockMvc
         .perform(MockMvcRequestBuilders.delete(UserController.PATH+"/"+LOGIN).contentType(TestUtil.APPLICATION_JSON_UTF8))
         .andExpect(status().isOk());
	}
}
