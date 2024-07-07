package com.marketplace.domain.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePhoneNumberInput {

	private String phone;
	
	private String code;
	
	private long requestId;
	
}
