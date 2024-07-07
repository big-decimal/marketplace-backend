package com.marketplace.domain.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordInput {

	private String oldPassword;
	
	private String newPassword;
	
}
