package com.marketplace.domain.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordInput {
	
	private String phone;
	
	private String password;
	
	private long requestId;
	
	private String code;
	
}
