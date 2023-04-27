package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.user.User;
import com.shoppingcenter.domain.user.UserDao;

public class CreateUserUseCase {

    private UserDao dao;

    public CreateUserUseCase(UserDao dao) {
        this.dao = dao;
    }

    public User apply(User user) {
        if (!Utils.hasText(user.getName())) {
            throw new ApplicationException("Required user name");
        }

        var phoneRegex = "^(\\+959)\\d{7,12}$";

        if (!Utils.hasText(user.getPhone()) || !user.getPhone().matches(phoneRegex)) {
            throw new ApplicationException("Required valid phone number");
        }

        if (dao.existsByPhone(user.getPhone())) {
            throw new ApplicationException("username-exists");
        }

        user.setRole(User.Role.USER);

        return dao.create(user);
    }

}