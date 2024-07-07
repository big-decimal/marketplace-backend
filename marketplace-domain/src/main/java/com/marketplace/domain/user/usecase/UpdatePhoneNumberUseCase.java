package com.marketplace.domain.user.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Utils;
import com.marketplace.domain.user.User;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class UpdatePhoneNumberUseCase {

	@Autowired
	private UserDao dao;

	@Transactional
	public void apply(long userId, String phone) {
		if (!Utils.isPhoneNumber(phone)) {
			throw new ApplicationException("Phone number not valid");
		}

		var user = dao.findById(userId);

		if (user == null) {
			throw new ApplicationException("User not found");
		}
		
		if (user.getRole() == User.Role.OWNER) {
			throw new ApplicationException("You cannot update owner's phone number");
		}

		var altUser = dao.findByPhone(phone);

		if (altUser != null && altUser.getId() != userId) {
			throw new ApplicationException("Phone number already in used");
		}

		dao.updatePhoneNumber(userId, phone);
	}

}
