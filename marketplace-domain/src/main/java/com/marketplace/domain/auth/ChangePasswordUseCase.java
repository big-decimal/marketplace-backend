package com.marketplace.domain.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class ChangePasswordUseCase {

	@Autowired
	private UserDao userDao;

	@Autowired
	private AuthAdapter authAdapter;

	@Transactional
	public void apply(long userId, ChangePasswordInput values) {
		var user = userDao.findById(userId);

		if (user == null) {
			throw new ApplicationException("User not found");
		}

		if (!authAdapter.matchPassword(values.getOldPassword(), user.getPassword())) {
			throw new ApplicationException("Old password incorrect");
		}

		userDao.updatePassword(userId, authAdapter.encodePassword(values.getNewPassword()));
	}
}
