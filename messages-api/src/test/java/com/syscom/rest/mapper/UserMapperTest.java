package com.syscom.rest.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.syscom.beans.Fonction;
import com.syscom.beans.Role;
import com.syscom.beans.User;
import com.syscom.rest.dto.FonctionDTO;
import com.syscom.rest.dto.UserDTO;
	
@RunWith(SpringRunner.class)
@Import({UserMapperImpl.class, FonctionMapperImpl.class})
public class UserMapperTest {
	
	private static final String FONC_CODE = "F_CODE";
	private static final String FONC_LIBELLE = "F_LIBELLE";
	
	private static final String ROLE_CODE = "R_CODE";
	private static final String ROLE_LIBELLE = "R_LIBELLE";
	
	private static final Long USER_ID = 1L;
	private static final String USER_NAME = "NAME";
	private static final String USER_FIRST_NAME = "FIRST_NAME";
	private static final String USER_LOGIN = "LOGIN";
	private static final String USER_PASSWORD = "PASSWORD";
	
	@Autowired
	private UserMapper userMapper;

	@Test
    public void testBeanToDTO() {
		// GIVEN
		Fonction fonction = Fonction.builder().code(FONC_CODE).libelle(FONC_LIBELLE).build(); 
		Role role = Role.builder().code(ROLE_CODE).libelle(ROLE_LIBELLE).fonctions(Arrays.asList(fonction)).build();
		User user = User.builder().id(USER_ID).name(USER_NAME).firstName(USER_FIRST_NAME).login(USER_LOGIN).password(USER_PASSWORD).role(role).build();
		
		// WHEN
		UserDTO userDTO = userMapper.beanToDto(user);
		
		// THEN
		assertThat(userDTO).isNotNull();
		assertThat(userDTO.getLogin()).isEqualTo(USER_LOGIN);
		assertThat(userDTO.getPassword()).isEqualTo(USER_PASSWORD);
		assertThat(userDTO.getName()).isEqualTo(USER_NAME);
		assertThat(userDTO.getFirstName()).isEqualTo(USER_FIRST_NAME);
		assertThat(userDTO.getRole()).isEqualTo(ROLE_CODE);
		assertThat(userDTO.getFonctions()).isNotNull();
		assertThat(userDTO.getFonctions().size()).isEqualTo(1);
		FonctionDTO fonctionDTO = userDTO.getFonctions().get(0);
		assertThat(fonctionDTO.getCode()).isEqualTo(FONC_CODE);
		assertThat(fonctionDTO.getLibelle()).isEqualTo(FONC_LIBELLE);
	}
}
