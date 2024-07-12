package com.marketplace.api.auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.api.AuthenticationUtil;
import com.marketplace.api.JwtUtil;
import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.ErrorCodes;
import com.marketplace.domain.auth.AuthResult;
import com.marketplace.domain.auth.ChangePasswordInput;
import com.marketplace.domain.auth.ChangePasswordUseCase;
import com.marketplace.domain.auth.ChangePhoneNumberInput;
import com.marketplace.domain.auth.ChangePhoneNumberUseCase;
import com.marketplace.domain.auth.RequestOtpUseCase;
import com.marketplace.domain.auth.ResetPasswordInput;
import com.marketplace.domain.auth.ResetPasswordUseCase;
import com.marketplace.domain.auth.SignInInput;
import com.marketplace.domain.auth.SignInUseCase;
import com.marketplace.domain.auth.SignUpInput;
import com.marketplace.domain.auth.SignUpUseCase;
import com.marketplace.domain.auth.SmspohAdapter;
import com.marketplace.domain.auth.SmspohResult;
import com.marketplace.domain.auth.VerifyPhoneNumberUseCase;
import com.marketplace.domain.user.dao.UserDao;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/auth")
@Tag(name = "Authentication")
public class AuthenticationController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private SignInUseCase signInUseCase;

	@Autowired
	private SignUpUseCase signUpUseCase;
	
	@Autowired
	private ChangePasswordUseCase changePasswordUseCase;
	
	@Autowired
	private ResetPasswordUseCase resetPasswordUseCase;
	
	@Autowired
	private VerifyPhoneNumberUseCase verifyPhoneNumberUseCase;
	
	@Autowired
	private ChangePhoneNumberUseCase changePhoneNumberUseCase;
	
	@Autowired
	private RequestOtpUseCase requestOtpUseCase;
	
	@Autowired
	private SmspohAdapter smspohAdapter;

	@Autowired
	private AuthDataMappter mapper;

	@PostMapping("sign-in")
	public AuthResultDTO signIn(@RequestBody SignInInput body) {
		var result = signInUseCase.apply(body);
		return mapper.map(result);
	}

	@PostMapping("sign-up")
	public AuthResultDTO signUp(@RequestBody SignUpInput body) {
		var result = signUpUseCase.apply(body);
		return mapper.map(result);
	}

	@PostMapping("refresh")
	public AuthResultDTO refresh(@RequestBody Map<String, String> body) {
		try {
			var token = body.get("token");
			var jwt = jwtUtil.verifyRefreshToken(token);
			var user = userDao.findByPhone(jwt.getSubject());

			if (user == null) {
				throw new RuntimeException("User not found");
			}
			
			if (user.isDisabled()) {
				throw new RuntimeException("Account disabled");
			}

			var result = new AuthResult();
			result.setUser(user);
			result.setAccessToken(jwtUtil.generateAccessToken(user.getPhone()));
			result.setRefreshToken(jwtUtil.generateRefreshToken(user.getPhone()));
			return mapper.map(result);
		} catch (Exception e) {
			throw new ApplicationException(ErrorCodes.UNAUTHORIZED, e.getMessage());
		}

	}
	
	@PutMapping("change-phone")
	public void changePhone(@RequestBody ChangePhoneNumberInput body) {
		var userId = AuthenticationUtil.getAuthenticatedUserId();
		changePhoneNumberUseCase.apply(userId, body);
	}
	
	@PutMapping("change-password")
	public void changePassword(@RequestBody ChangePasswordInput body) {
		var userId = AuthenticationUtil.getAuthenticatedUserId();
		changePasswordUseCase.apply(userId, body);
	}
	
	@PutMapping("reset-password")
	public void resetPassword(@RequestBody ResetPasswordInput body) {
		resetPasswordUseCase.apply(body);
	}
	
	@GetMapping("request-otp")
	public SmspohResult requestOTP(@RequestParam String phone) {
		return requestOtpUseCase.apply(phone);
	}
	
	@GetMapping("verify-otp")
	public SmspohResult verifyOTP(@RequestParam long requestId, @RequestParam String code) {
		return smspohAdapter.verifyOTP(requestId, code);
	}
	
	@GetMapping("verify-phone")
	public void verifyPhone(@RequestParam long requestId, @RequestParam String code) {
		var userId = AuthenticationUtil.getAuthenticatedUserId();
		verifyPhoneNumberUseCase.apply(userId, requestId, code);
	}
	
	@GetMapping("exists-user")
	public boolean existsUser(@RequestParam String phone) {
		return userDao.existsByPhone(phone);
	}
}
