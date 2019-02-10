package com.syscom.dao;

import org.springframework.data.repository.CrudRepository;

import com.syscom.beans.Role;

public interface RoleDao extends CrudRepository<Role, Long> {

	Role findByCode(String code);

}
