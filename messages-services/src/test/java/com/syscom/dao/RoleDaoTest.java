package com.syscom.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.syscom.AbstractTest;
import com.syscom.beans.Role;

public class RoleDaoTest extends AbstractTest {

	@Autowired
	private RoleDao roleDao;

	@Test
	public void testFindByCodeWithWrongCode() {
		// GIVEN
		testEntityManager.persistAndFlush(getUserRole());

		// WHEN
		Role findRole = roleDao.findByCode("WRONG_ROLE_CODE");

		// THEN
		assertThat(findRole).isNull();
	}

	@Test
	public void testFindByCode() {
		// GIVEN
		testEntityManager.persistAndFlush(getUserRole());

		// WHEN
		Role findRole = roleDao.findByCode(ROLE_USER_CODE);

		// THEN
		assertThat(findRole).isNotNull();
		assertThat(findRole.getId()).isNotNull();
		assertThat(findRole.getCode()).isEqualTo(ROLE_USER_CODE);
		assertThat(findRole.getLibelle()).isEqualTo(ROLE_USER_LIBELLE);
		assertThat(findRole.getFonctions()).isNull();
		assertThat(findRole.getCreateDate()).isNotNull();
		assertThat(findRole.getUpdateDate()).isNotNull();
	}

}
