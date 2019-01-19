package com.syscom.dao;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.syscom.beans.Token;

/**
 * Repository pour effectuer les CRUD des jetons d'authentification
 * {@link Token}
 *
 */
public interface TokenDao extends CrudRepository<Token, Long> {

	/**
	 * Rechercher un token par le login de l'utilisateur.
	 * 
	 * @param login : login de l'utilisateur
	 * @return un token d'authentification {@link Token}
	 */
	Token findByUser_login(String login);

	/**
	 * Rechercher un token à partir de sa valeur.
	 * 
	 * @param value valeur du token
	 * @return un token d'authentification {@link Token}
	 */
	@Query("select token from Token token " 
			+ "left join fetch token.user user "
			+ "left join fetch user.role role "
			+ "left join fetch role.fonctions "
			+ "where token.value =:value")
	Token findByValue(@Param("value") String value);

	/**
	 * Supprimer les tokens expirés
	 * 
	 * @param localDateTime date d'expiration.
	 */
	@Modifying
	@Query(name = "deleteExpiredToken", value = "DELETE FROM Token t WHERE t.dateExpiration < :localDateTime")
	void deleteExpiredToken(@Param("localDateTime") LocalDateTime localDateTime);

	/**
	 * Supprimer les tokens d'un utilisateur
	 * 
	 * @param login login de l'utilisateur.
	 */
	@Modifying
	@Query(name = "deleteByUserLogin", value = "DELETE FROM Token t WHERE t.id in (SELECT t2.id FROM Token t2 WHERE t2.user.login = :login)")
	void deleteByUserLogin(@Param("login") String login);

}
