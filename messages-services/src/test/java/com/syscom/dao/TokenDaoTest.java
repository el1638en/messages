package com.syscom.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.syscom.AbstractTest;
import com.syscom.beans.Role;
import com.syscom.beans.Token;
import com.syscom.beans.User;

public class TokenDaoTest extends AbstractTest {

	@Autowired
	private TokenDao tokenDao;

	@Test
	public void testFindByUser_login_UknownUser() throws Exception {
		// GIVEN
		persistToken();

		// WHEN
		Token findToken = tokenDao.findByUser_login("UNKNOWN_LOGIN");

		// THEN
		assertThat(findToken).isNull();
	}

	@Test
	public void testFindByUser_login() throws Exception {
		// GIVEN
		persistToken();

		// WHEN
		Token findToken = tokenDao.findByUser_login(USER_LOGIN);

		// THEN
		assertThat(findToken).isNotNull();
		assertThat(findToken.getCreateDate()).isNotNull();
		assertThat(findToken.getUpdateDate()).isNotNull();
		assertThat(findToken.getId()).isNotNull();
		assertThat(findToken.getDateExpiration()).isEqualTo(EXPIRATION_DATE);
		assertThat(findToken.getValue()).isEqualTo(TOKEN);
	}

	@Test
	public void testFindByValue() throws Exception {
		// GIVEN
		persistToken();

		// WHEN
		Token findToken = tokenDao.findByValue(TOKEN);

		// THEN
		assertThat(findToken).isNotNull();
		assertThat(findToken.getCreateDate()).isNotNull();
		assertThat(findToken.getUpdateDate()).isNotNull();
		assertThat(findToken.getId()).isNotNull();
		assertThat(findToken.getValue()).isEqualTo(TOKEN);
	}

	@Test
	public void testDeleteExpiredToken() throws Exception {
		// GIVEN
		persistToken();
		LocalDateTime expireMoment = LocalDateTime.now().plusMinutes(15);

		// WHEN
		tokenDao.deleteExpiredToken(expireMoment);
		long count = tokenDao.count();
		Token findToken = tokenDao.findByValue(TOKEN);

		// THEN
		assertThat(count).isEqualTo(0L);
		assertThat(findToken).isNull();
	}

	@Test
	public void testDeleteByUserLogin() throws Exception {
		// GIVEN
		persistToken();

		// WHEN
		tokenDao.deleteByUserLogin(USER_LOGIN);
		long count = tokenDao.count();
		Token findToken = tokenDao.findByValue(TOKEN);

		// THEN
		assertThat(count).isEqualTo(0L);
		assertThat(findToken).isNull();
	}

	private Token persistToken() {
		Role roleAdmin = testEntityManager.persistAndFlush(getAdminRole());
		User user = testEntityManager.persistAndFlush(getUser(roleAdmin));
		Token token = Token.builder().value(TOKEN).dateExpiration(EXPIRATION_DATE).user(user).build();
		return testEntityManager.persistAndFlush(token);
	}

}
