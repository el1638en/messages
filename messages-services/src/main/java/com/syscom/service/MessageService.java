package com.syscom.service;

import java.util.List;

import com.syscom.beans.Message;
import com.syscom.exceptions.BusinessException;

public interface MessageService {

	/**
	 * Ajouter un nouveau message
	 * 
	 * @param messageDTO message à ajouter
	 * @throws BusinessException
	 */
	Message create(Message message) throws BusinessException;

	/**
	 * Rechercher un message � partir de son id.
	 *
	 * @param id du message
	 */
	Message findById(Long id);

	/**
	 * Rechercher tous les messages
	 *
	 */
	List<Message> findAll();

	/**
	 * Modifier un message
	 *
	 * @param id      identifiant du message
	 * @param message message � modifier
	 * @throws BusinessException
	 */
	Message update(Long id, Message message) throws BusinessException;

	/**
	 * Supprimer un message
	 *
	 * @param id identifiant du message
	 * @throws BusinessException
	 */
	void delete(Long id) throws BusinessException;

}
