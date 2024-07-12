package com.marketplace.domain.user.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.user.User;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class UpdateDisabledUseCase {

	@Autowired
	private UserDao dao;

	@Transactional
	public void apply(long userId, boolean disabled) {
		var user = dao.findById(userId);

		if (user == null) {
			throw new ApplicationException("User not found");
		}

		if (user.getRole() == User.Role.OWNER) {
			throw new ApplicationException("You cannot update owner");
		}

		dao.updateDisabled(userId, disabled);
	}

}
