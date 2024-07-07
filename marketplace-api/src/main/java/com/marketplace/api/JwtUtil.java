package com.marketplace.api;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

	private static final long ACCESS_TOKEN_EXPIRY = 15 * 60; // 15 minutes

	private static final long REFRESH_TOKEN_EXPIRY = 30 * 24 * 60 * 60; // 30 days

	@Value("${app.auth.access-token-secret}")
	private String accessTokenSecret;
	
	@Value("${app.auth.refresh-token-secret}")
	private String refreshTokenSecret;

	private Algorithm accessTokenAlgorithm;
	
	private Algorithm refreshTokenAlgorithm;
	
	@PostConstruct
	public void init() {
		this.accessTokenAlgorithm = Algorithm.HMAC256(accessTokenSecret);
		this.refreshTokenAlgorithm = Algorithm.HMAC256(refreshTokenSecret);
	}
	
	public String generateAccessToken(String username) {
		return JWT.create()
                .withSubject(username)
                .withExpiresAt(Instant.now().plusSeconds(ACCESS_TOKEN_EXPIRY))
                .sign(accessTokenAlgorithm);
	}

	public String generateRefreshToken(String username) {
		return JWT.create()
                .withSubject(username)
                .withExpiresAt(Instant.now().plusSeconds(REFRESH_TOKEN_EXPIRY))
                .sign(refreshTokenAlgorithm);
	}
	
	public DecodedJWT verifyAccessToken(String token) {
		return JWT.require(accessTokenAlgorithm).build().verify(token);
	}
	
	public DecodedJWT verifyRefreshToken(String token) {
		return JWT.require(refreshTokenAlgorithm).build().verify(token);
	}
	
}
