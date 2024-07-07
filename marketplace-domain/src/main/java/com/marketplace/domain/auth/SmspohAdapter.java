package com.marketplace.domain.auth;

public interface SmspohAdapter {

	SmspohResult requestOTP(String phone);
	
	SmspohResult verifyOTP(long requestId, String code);
	
}
