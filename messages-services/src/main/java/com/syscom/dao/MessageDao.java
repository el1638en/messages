package com.syscom.dao;

import org.springframework.data.repository.CrudRepository;

import com.syscom.beans.Message;

/**
 * 
 * Repository pour effectuer les CRUD des messages {@link Message}
 *
 */
public interface MessageDao extends CrudRepository<Message, Long> {

}
