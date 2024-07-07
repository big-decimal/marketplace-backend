package com.marketplace.domain.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Utils;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class SignInUseCase {

	@Autowired
	private UserDao userDao;

	@Autowired
	private AuthAdapter authAdapter;

	@Transactional(readOnly = true)
	public AuthResult apply(SignInInput values) {
		if (!Utils.isPhoneNumber(values.getUsername())) {
			throw new ApplicationException("Phone number not valid");
		}
		
		if (!Utils.hasText(values.getPassword())) {
			throw new ApplicationException("Password must not empty");
		}
		
		var user = userDao.findByPhone(values.getUsername());
		
		if (user == null) {
			throw new ApplicationException("Phone number or password incorrect");
		}
		
		if (!authAdapter.matchPassword(values.getPassword(), user.getPassword())) {
			throw new ApplicationException("Phone number or password incorrect");
		}
		
		var result = new AuthResult();
		result.setUser(user);
		result.setAccessToken(authAdapter.generateAccessToken(user.getPhone()));
		result.setRefreshToken(authAdapter.generateRefreshToken(user.getPhone()));
		
		return result;
	}
}
