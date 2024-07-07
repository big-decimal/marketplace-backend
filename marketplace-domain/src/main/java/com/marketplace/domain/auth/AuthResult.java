package com.marketplace.domain.auth;

import com.marketplace.domain.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResult {
	
	private User user;

	private String accessToken;
	
	private String refreshToken;
	
}
