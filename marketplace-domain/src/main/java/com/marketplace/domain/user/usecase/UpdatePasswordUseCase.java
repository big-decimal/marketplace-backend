package com.marketplace.domain.user.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Utils;
import com.marketplace.domain.auth.AuthAdapter;
import com.marketplace.domain.user.User;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class UpdatePasswordUseCase {

	@Autowired
	private UserDao dao;
	
	@Autowired
	private AuthAdapter authAdapter;

	@Transactional
	public void apply(long userId, String password) {
		if (!Utils.hasText(password)) {
			throw new ApplicationException("Required password");
		}

		var user = dao.findById(userId);

		if (user == null) {
			throw new ApplicationException("User not found");
		}
		
		if (user.getRole() == User.Role.OWNER) {
			throw new ApplicationException("You cannot update owner's password");
		}

		dao.updatePassword(userId, authAdapter.encodePassword(password));
	}

}
