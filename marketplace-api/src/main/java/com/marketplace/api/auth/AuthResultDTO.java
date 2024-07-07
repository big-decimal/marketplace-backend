package com.marketplace.api.auth;

import com.marketplace.api.consumer.user.UserDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResultDTO {

	private UserDTO user;

	private String accessToken;

	private String refreshToken;
}
