package com.syscom.rest.api.serversides;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Base64Utils;

import com.syscom.beans.Role;
import com.syscom.beans.User;
import com.syscom.dao.RoleDao;
import com.syscom.rest.api.LoginController;
import com.syscom.service.UserService;

public class LoginControllerIntTest extends AbstractIntTest {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleDao roleDao;

    private Role role;
    private User user;
	
    @Before
    public void setup() {
		role = Role.builder().code(R_CODE).libelle(R_LIBELLE).build();
		user = User.builder().login(LOGIN).password(PASSWORD).name(NAME).firstName(FIRST_NAME).role(roleDao.save(role)).build();
    }
    
	@Test
	public void testAuthenticationWithWrongCredentials() throws Exception {
		// GIVEN
		userService.create(user);
		String badCredentials = StringUtils.join("BAD_LOGIN", ":", "BAD_PASSWORD");
		
		// WHEN
		
		// THEN
		 mockMvc
         .perform(MockMvcRequestBuilders.get(LoginController.PATH).header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString(badCredentials.getBytes())))
         .andExpect(status().isUnauthorized());
	}
	
    
	@Test
	public void testAuthentication() throws Exception {
		// GIVEN
		userService.create(user);
		
		// WHEN
		
		// THEN
		 mockMvc
         .perform(MockMvcRequestBuilders.get(LoginController.PATH).header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString(StringUtils.join(LOGIN, ":",PASSWORD).getBytes())))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
         .andExpect(jsonPath("$").isNotEmpty())
         .andExpect(jsonPath("$.value").isNotEmpty())
         .andExpect(jsonPath("$.userDTO.name").value(NAME))
         .andExpect(jsonPath("$.userDTO.firstName").value(FIRST_NAME))
         .andExpect(jsonPath("$.userDTO.login").value(LOGIN))
         .andExpect(jsonPath("$.userDTO.firstName").value(FIRST_NAME));
	}
	
	
	
	@Test
	public void testAuth() throws Exception {
		// GIVEN
		userService.create(user);
		
		// WHEN
		String token = getAccessToken(LOGIN, PASSWORD);
		
		// THEN
		
		assertThat(token).isNotNull();
	}

}
