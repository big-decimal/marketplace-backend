package com.marketplace.domain.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpRequest {

	private String phone;
	
	private String date;
	
	private int count;
	
}
