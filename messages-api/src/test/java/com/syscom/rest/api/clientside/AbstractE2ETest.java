package com.syscom.rest.api.clientside;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.syscom.rest.api.AbstractTest;
import com.syscom.rest.api.LoginController;
import com.syscom.rest.dto.TokenDTO;

public class AbstractE2ETest extends AbstractTest {

	@Autowired
	protected TestRestTemplate testRestTemplate;

	
	protected String getAccessToken(String login, String password) {
		ResponseEntity<TokenDTO> response = testRestTemplate.withBasicAuth(
				LOGIN, PASSWORD).getForEntity(LoginController.PATH,TokenDTO.class);
		return response.getBody().getValue();
	}
}
