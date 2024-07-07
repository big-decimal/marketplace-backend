package com.marketplace.domain.auth;

public interface OtpRequestDao {

	void save(OtpRequest values);
	
	OtpRequest findByPhoneAndDate(String phone, String date);
	
}
