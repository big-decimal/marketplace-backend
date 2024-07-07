package com.marketplace.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.marketplace.api.JwtUtil;
import com.marketplace.domain.auth.AuthAdapter;

@Component
public class AuthAdapterImpl implements AuthAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public boolean matchPassword(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

	@Override
	public String encodePassword(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

	@Override
	public String generateAccessToken(String username) {
		return jwtUtil.generateAccessToken(username);
	}

	@Override
	public String generateRefreshToken(String username) {
		return jwtUtil.generateRefreshToken(username);
	}

}
