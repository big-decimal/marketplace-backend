package com.marketplace.domain.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Utils;
import com.marketplace.domain.user.User;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class SignUpUseCase {

	@Autowired
	private UserDao userDao;

	@Autowired
	private AuthAdapter authAdapter;

	@Transactional
	public AuthResult apply(SignUpInput values) {
		if (!Utils.hasText(values.getName())) {
			throw new ApplicationException("Name must not empty");
		}

		if (!Utils.isPhoneNumber(values.getPhone())) {
			throw new ApplicationException("Phone number not valid");
		}

		if (!Utils.hasText(values.getPassword())) {
			throw new ApplicationException("Password must not empty");
		}

		if (userDao.existsByPhone(values.getPhone())) {
			throw new ApplicationException("Phone number already in used");
		}

		var u = new User();
		u.setName(values.getName());
		u.setPhone(values.getPhone());
		u.setPassword(authAdapter.encodePassword(values.getPassword()));
		u.setRole(User.Role.USER);

		var user = userDao.create(u);

		var result = new AuthResult();
		result.setUser(user);
		result.setAccessToken(authAdapter.generateAccessToken(user.getPhone()));
		result.setRefreshToken(authAdapter.generateRefreshToken(user.getPhone()));
		return result;
	}
}
