package com.marketplace.domain.auth;

import java.time.LocalDate;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Utils;

@Component
public class RequestOtpUseCase {

	@Autowired
	private OtpRequestDao otpRequestDao;

	@Autowired
	private SmspohAdapter smsPohAdapter;

	@Retryable(noRetryFor = { ApplicationException.class })
	@Transactional
	public SmspohResult apply(String phone) {
		if (!Utils.isPhoneNumber(phone)) {
			throw new ApplicationException("Phone number not valid");
		}

//		if (!userDao.existsByPhone(phone)) {
//			throw new ApplicationException("User not found");
//		}

		var date = LocalDate.now(ZoneOffset.UTC).toString();
		var request = otpRequestDao.findByPhoneAndDate(phone, date);

		if (request != null && request.getCount() >= 5) {
			throw new ApplicationException("OTP request limit exceeds. Please try again tomorrow.");
		}

		if (request == null) {
			request = new OtpRequest();
			request.setPhone(phone);
			request.setDate(date);
		}

		request.setCount(request.getCount() + 1);
		otpRequestDao.save(request);
		
		var result = smsPohAdapter.requestOTP(phone);

		return result;
	}
}
