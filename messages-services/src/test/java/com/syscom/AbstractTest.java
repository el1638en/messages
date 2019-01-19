package com.syscom;

import java.time.LocalDateTime;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.syscom.beans.Role;
import com.syscom.beans.User;
import com.syscom.config.TestConfiguration;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@DataJpaTest
public abstract class AbstractTest {

	@Autowired
	protected TestEntityManager testEntityManager;

	protected static final String ROLE_USER_CODE = "USER_ROLE_CODE";
	protected static final String ROLE_USER_LIBELLE = "USER_ROLE_LIBELLE";
	protected static final String ROLE_ADMIN_CODE = "ADMIN";
	protected static final String ROLE_ADMIN_LIBELLE = "ADMIN_ROLE_LIBELLE";
	
	// Donnees de test pour les utilisateurs
	protected static final String USER_LOGIN = "LOGIN";
	protected static final String USER_NAME = "NAME";
	protected static final String USER_FIRST_NAME = "FIRST_NAME";
	protected static final String USER_PASSWORD = "PASSWORD";

	// Donnees de test pour les tokens
	protected static final String TOKEN = "TOKEN";
	protected static final LocalDateTime EXPIRATION_DATE = LocalDateTime.now().plusMinutes(10);

	protected Role getAdminRole() {
		return Role.builder().code(ROLE_ADMIN_CODE).libelle(ROLE_ADMIN_LIBELLE).build();
	}

	protected Role getUserRole() {
		return Role.builder().code(ROLE_USER_CODE).libelle(ROLE_USER_LIBELLE).build();
	}

	protected User getUser(Role role) {
		return User.builder().role(role).name(USER_NAME).firstName(USER_FIRST_NAME).login(USER_LOGIN)
				.password(USER_PASSWORD).build();

	}

}
