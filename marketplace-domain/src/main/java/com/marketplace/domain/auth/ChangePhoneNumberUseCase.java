package com.marketplace.domain.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Utils;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class ChangePhoneNumberUseCase {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SmspohAdapter smspohAdapter;

	@Transactional
	public void apply(long userId, ChangePhoneNumberInput values) {
		var user = userDao.findById(userId);

		if (user == null) {
			throw new ApplicationException("User not found");
		}
		
		if (!Utils.isPhoneNumber(values.getPhone())) {
			throw new ApplicationException("Phone number not valid");
		}
		
		var result = smspohAdapter.verifyOTP(values.getRequestId(), values.getCode());

		if (!result.isStatus()) {
			throw new ApplicationException("Invalid OTP code");
		}

		userDao.updatePhoneNumber(userId, values.getPhone());
	}
}
