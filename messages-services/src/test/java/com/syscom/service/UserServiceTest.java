package com.syscom.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import com.syscom.AbstractTest;
import com.syscom.beans.Role;
import com.syscom.beans.User;
import com.syscom.dao.RoleDao;
import com.syscom.dao.UserDao;
import com.syscom.exceptions.BusinessException;

public class UserServiceTest extends AbstractTest {

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Autowired
	private UserService userService;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private UserDao userDao;

	@Test
	public void testWhenCreateNullUserThenThrowException() throws Exception {
		// GIVEN
		exceptionRule.expect(IllegalArgumentException.class);

		// WHEN
		userService.create(null);

		// THEN
	}

	@Test
	public void testWhenCreateUserWithoutAllDataThenThrowException() throws Exception {
		// GIVEN
		exceptionRule.expect(BusinessException.class);

		// WHEN
		userService.create(User.builder().name(USER_NAME).firstName(USER_FIRST_NAME).build());

		// THEN
	}

	@Test
	public void testWhenCreateExistsUserThenThrowException() throws Exception {
		// GIVEN
		User user = userDao.save(getUser(roleDao.save(getUserRole())));
		exceptionRule.expect(BusinessException.class);

		// WHEN
		userService.create(user);

		// THEN
	}

	@Test
	public void testWhenCreateUserWithoutRoleThenThrowException() throws Exception {
		// GIVEN
		User user = getUser(new Role());
		exceptionRule.expect(BusinessException.class);

		// WHEN
		userService.create(user);

		// THEN
	}

	@Test
	public void tesCreateUser() throws Exception {
		// GIVEN
		User user = getUser(roleDao.save(getUserRole()));
		userService.create(user);

		// WHEN
		User findUser = userDao.findByLogin(USER_LOGIN);

		// THEN
		assertThat(findUser).isNotNull();
		assertThat(findUser.getFirstName()).isEqualTo(user.getFirstName());
		assertThat(findUser.getName()).isEqualTo(user.getName());
		assertThat(findUser.getLogin()).isEqualTo(user.getLogin());
		assertThat(findUser.getRole()).isNotNull();
		assertThat(findUser.getRole().getCode()).isEqualTo(user.getRole().getCode());
	}

}
