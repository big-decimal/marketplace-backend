package com.marketplace.domain.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpInput {

	private String name;
	
	private String phone;
	
	private String password;
	
}
