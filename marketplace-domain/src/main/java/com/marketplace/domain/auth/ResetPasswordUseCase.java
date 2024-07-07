package com.marketplace.domain.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Utils;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class ResetPasswordUseCase {

	@Autowired
	private UserDao userDao;

	@Autowired
	private AuthAdapter authAdapter;
	
	@Autowired
	private SmspohAdapter smspohAdapter;

	@Transactional
	public void apply(ResetPasswordInput values) {
		var user = userDao.findByPhone(values.getPhone());

		if (user == null) {
			throw new ApplicationException("User not found");
		}
		
		if (!Utils.hasText(values.getPassword())) {
			throw new ApplicationException("Password must not empty");
		}

		var result = smspohAdapter.verifyOTP(values.getRequestId(), values.getCode());

		if (!result.isStatus()) {
			throw new ApplicationException("Invalid OTP code");
		}

		userDao.updatePassword(user.getId(), authAdapter.encodePassword(values.getPassword()));
	}
}
