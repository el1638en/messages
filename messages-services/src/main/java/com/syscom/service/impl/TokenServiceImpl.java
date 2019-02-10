package com.syscom.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.syscom.beans.Token;
import com.syscom.beans.User;
import com.syscom.dao.TokenDao;
import com.syscom.dao.UserDao;
import com.syscom.exceptions.BusinessException;
import com.syscom.exceptions.TechnicalException;
import com.syscom.service.BaseService;
import com.syscom.service.TokenService;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

/**
 * Implémentation du contrat d'interface des services métiers des tokens
 * d'authentification. http://www.baeldung.com/java-json-web-tokens-jjwt
 *
 */
@Service
@Transactional
public class TokenServiceImpl extends BaseService implements TokenService {

	private static final String ROLE = "ROLE";
	private static final String ISSUER = "SYSCOM";
	private static final String NAME = "NAME";

	@Autowired
	private TokenDao tokenDao;

	@Autowired
	private UserDao userDao;

	@Value("${token.jwt.secret}")
	private String secret;

	@Value("${token.jwt.duration}")
	private Integer duration;

	@Override
	public Token findToken(String value) {
		Assert.notNull(value, getMessage("user.not.null"));
		tokenDao.deleteExpiredToken(LocalDateTime.now());
		Token existToken = tokenDao.findByValue(value);
		return (existToken != null && isValidToken(existToken.getValue())) ? existToken : null;
	}

	@Override
	public Token createToken(String login) throws BusinessException {
		Assert.notNull(login, getMessage("user.not.null"));
		LocalDateTime currentTime = LocalDateTime.now();
		tokenDao.deleteExpiredToken(currentTime);
		String upperLogin = StringUtils.upperCase(login);
		User user = userDao.findByLogin(upperLogin);
		if (user == null) {
			throw new BusinessException(getMessage("unkonwn.user.error"));
		}
		Token existToken = tokenDao.findByUser_login(upperLogin);
		return existToken != null ? existToken : createNewToken(user);
	}

	/**
	 * Création d'un token.
	 * 
	 * @param userDTO l'utilisateur pour lequel le token est créé.
	 * @return le token {@link Token}
	 */
	private Token createNewToken(User user) {
		Date now = new Date();
		LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(duration);
		Date dateConvert = java.util.Date.from(expirationDate.atZone(ZoneId.systemDefault()).toInstant());
//		String id = UUID.randomUUID().toString().replace("-", "");
//		String jws = Jwts.builder().setId(id).setIssuer(ISSUER).setSubject(user.getLogin()).claim(NAME, user.getName())
//				.claim(ROLE, user.getRole()).setIssuedAt(now).setNotBefore(now).setExpiration(dateConvert).compressWith(CompressionCodecs.DEFLATE)
//				.signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.decode(secret)).compact();

		String jws = Jwts.builder().setIssuer(ISSUER).setSubject(user.getLogin()).claim(NAME, user.getName())
				.claim(ROLE, user.getRole().getCode()).setIssuedAt(now).setNotBefore(now).setExpiration(dateConvert)
				.compressWith(CompressionCodecs.DEFLATE)
				.signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.decode(secret)).compact();

		Token token = new Token();
		token.setDateExpiration(expirationDate);
		token.setValue(jws);
		token.setUser(userDao.findByLogin(user.getLogin()));
		return tokenDao.save(token);
	}

	/**
	 * Vérifie si un token est valide.
	 * 
	 * @param token le token à vérifier.
	 * @return
	 */
	private boolean isValidToken(String token) {
		boolean isValid = false;
		try {
			Jwts.parser().setSigningKey(TextCodec.BASE64.decode(secret)).requireIssuer(ISSUER).parseClaimsJws(token);
			isValid = true;
		} catch (JwtException e) {
			throw new TechnicalException(getMessage("error.create.token"), e);
		}
		return isValid;
	}

}