package com.marketplace.domain.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class VerifyPhoneNumberUseCase {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SmspohAdapter smspohAdapter;
	
	@Transactional
	public void apply(long userId, long requestId, String code) {
		var result = smspohAdapter.verifyOTP(requestId, code);
		
		if (result.isStatus()) {
			userDao.verifyPhoneNumber(userId);
		} else {
			throw new ApplicationException("Invalid OTP code");
		}
	}
}
