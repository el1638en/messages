package com.syscom.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import com.syscom.AbstractTest;
import com.syscom.beans.Token;
import com.syscom.beans.User;
import com.syscom.dao.RoleDao;
import com.syscom.dao.TokenDao;
import com.syscom.exceptions.BusinessException;

public class TokenServiceTest extends AbstractTest {

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Autowired
	private TokenDao tokenDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserService userService;

	@Test
	public void whenCreateTokenWithNullLoginThenThrowException() throws Exception {
		// GIVEN
		exceptionRule.expect(IllegalArgumentException.class);

		// WHEN
		tokenService.createToken(null);

		// THEN
	}

	@Test
	public void whenCreateTokenForUnknownUserThenThrowException() throws Exception {
		// GIVEN
		exceptionRule.expect(BusinessException.class);

		// WHEN
		tokenService.createToken(USER_LOGIN);

		// THEN
	}

	@Test
	public void createToken() throws Exception {
		// GIVEN
		User user = getUser(roleDao.save(getUserRole()));
		userService.create(user);

		// WHEN
		Token token = tokenService.createToken(user.getLogin());

		// THEN
		Token result = tokenDao.findByUser_login(user.getLogin());
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(token.getId());
		assertThat(result.getValue()).isEqualTo(token.getValue());
		assertThat(result.getUser().getLogin()).isEqualTo(user.getLogin());
		assertThat(result.getCreateDate()).isEqualTo(token.getCreateDate());
		assertThat(result.getDateExpiration()).isEqualTo(token.getDateExpiration());
		assertThat(result.getUpdateDate()).isEqualTo(token.getUpdateDate());
	}

	@Test
	public void findToken() throws Exception {
		// GIVEN
		User user = getUser(roleDao.save(getUserRole()));
		userService.create(user);
		Token token = tokenService.createToken(user.getLogin());

		// WHEN
		Token result = tokenService.findToken(token.getValue());

		// THEN
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(token.getId());

	}

}
