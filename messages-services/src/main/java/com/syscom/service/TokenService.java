package com.syscom.service;

import com.syscom.beans.Token;
import com.syscom.exceptions.BusinessException;

/**
 * Contrat d'interface pour la gestion métier des tokens de connexion.
 * 
 */
public interface TokenService {

	/**
	 * Creation d'un token d'authorisation pour un utilisateur
	 * @param login Login de l'utilisateur
	 * @return token {@link Token}
	 * @throws une exception métier {@link BusinessException}
	 */
	Token createToken(String login) throws BusinessException;

	/**
	 * Rechercher un token à partir de sa valeur.
	 * 
	 * @param value valeur du token à rechercher
	 * @return token {@link Token}
	 */
	Token findToken(String value);

}
