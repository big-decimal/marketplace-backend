package com.marketplace.domain.auth;

public interface AuthAdapter {

	boolean matchPassword(String rawPassword, String encodedPassword);
	
	String encodePassword(String rawPassword);
	
	String generateAccessToken(String username);
	
	String generateRefreshToken(String username);
	
}
