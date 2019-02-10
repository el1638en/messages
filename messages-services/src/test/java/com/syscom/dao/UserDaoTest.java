package com.syscom.dao;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.syscom.AbstractTest;
import com.syscom.beans.Role;
import com.syscom.beans.User;

public class UserDaoTest extends AbstractTest {

	@Autowired
	private UserDao userRepository;

	@Test
	public void testFindByLoginWithUnknownUser() throws Exception {
		// GIVEN

		// WHEN
		User findUser = userRepository.findByLogin(USER_LOGIN);

		// THEN
		assertThat(findUser).isNull();
	}

	@Test
	public void testFindByLogin() throws Exception {
		// GIVEN
		Role roleAdmin = testEntityManager.persistAndFlush(getAdminRole());
		User user = getUser(roleAdmin);
		testEntityManager.persistAndFlush(user);

		// WHEN
		User findUser = userRepository.findByLogin(user.getLogin());

		// THEN
		assertThat(findUser).isNotNull();
		assertThat(findUser.getLogin()).isEqualTo(USER_LOGIN);
		assertThat(findUser.getFirstName()).isEqualTo(USER_FIRST_NAME);
		assertThat(findUser.getName()).isEqualTo(USER_NAME);
		assertThat(findUser.getCreateDate()).isNotNull();
		assertThat(findUser.getUpdateDate()).isNotNull();
		assertThat(findUser.getId()).isNotNull();
	}

	@Test
	public void testDeleteByLogin() throws Exception {
		// GIVEN
		Role roleAdmin = testEntityManager.persistAndFlush(getAdminRole());
		User user = getUser(roleAdmin);
		testEntityManager.persistAndFlush(user);

		// WHEN
		userRepository.deleteByLogin(USER_LOGIN);
		User findUser = userRepository.findByLogin(user.getLogin());

		// THEN
		assertThat(findUser).isNull();
	}

}
