package com.syscom.service.impl;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.upperCase;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.syscom.beans.Role;
import com.syscom.beans.User;
import com.syscom.dao.RoleDao;
import com.syscom.dao.TokenDao;
import com.syscom.dao.UserDao;
import com.syscom.exceptions.BusinessException;
import com.syscom.service.BaseService;
import com.syscom.service.UserService;

/**
 * Implémentation du contrat d'interface des services métiers des utilisateurs
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends BaseService implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private TokenDao tokenDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void create(User user) throws BusinessException {
		Assert.notNull(user, getMessage("user.not.null"));
		List<String> errors = checkUserData(user);
		if (!errors.isEmpty()) {
			throw new BusinessException(StringUtils.join(errors, " "));
		}
		String login = upperCase(user.getLogin());
		if (userDao.findByLogin(login) != null) {
			throw new BusinessException(getMessage("user.login.already.used"));
		}
		Role role = roleDao.findByCode(user.getRole().getCode());
		if (role == null) {
			throw new BusinessException(getMessage("user.role.unknown"));
		}
		user = User.builder().login(login).firstName(user.getFirstName()).name(user.getName())
				.password(passwordEncoder.encode(user.getPassword())).role(role).build();
		userDao.save(user);
	}

	@Override
	public User findByLogin(String login) {
		Assert.notNull(login, getMessage("user.login.not.null"));
		return userDao.findByLogin(upperCase(login));
	}

	/**
	 * Vérifier les données obligatoires de l'utilisateur
	 *
	 * @param user Données de l'utilisateur {@link User}
	 * @return Liste de message d'erreurs
	 */
	private List<String> checkUserData(User user) {
		List<String> errors = new ArrayList<>();
		if (isEmpty(user.getName())) {
			errors.add(getMessage("user.name.empty"));
		}
		if (isEmpty(user.getFirstName())) {
			errors.add(getMessage("user.firstname.empty"));
		}
		if (isEmpty(user.getLogin())) {
			errors.add(getMessage("user.login.empty"));
		}
		if (isEmpty(user.getPassword())) {
			errors.add(getMessage("user.password.empty"));
		}
		if (user.getRole() == null) {
			errors.add(getMessage("user.role.empty"));
		}
		return errors;
	}

	@Override
	public void delete(String login) throws BusinessException {
		Assert.notNull(login, getMessage("delete.user.login.mandatory"));
		String upperLogin = upperCase(login);
		if (userDao.findByLogin(upperLogin) == null) {
			throw new BusinessException(getMessage("user.unknown"));
		}
		tokenDao.deleteByUserLogin(upperLogin);
		userDao.deleteByLogin(upperLogin);
	}

}
