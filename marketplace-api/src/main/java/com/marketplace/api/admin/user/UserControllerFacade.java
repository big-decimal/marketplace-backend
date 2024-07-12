package com.marketplace.api.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.api.PageDataDTO;
import com.marketplace.api.admin.AdminDataMapper;
import com.marketplace.domain.user.User;
import com.marketplace.domain.user.UserQuery;
import com.marketplace.domain.user.dao.UserDao;
import com.marketplace.domain.user.usecase.GetAllUserUseCase;
import com.marketplace.domain.user.usecase.GetUserByIdUseCase;
import com.marketplace.domain.user.usecase.UpdateDisabledUseCase;
import com.marketplace.domain.user.usecase.UpdatePasswordUseCase;
import com.marketplace.domain.user.usecase.UpdatePhoneNumberUseCase;
import com.marketplace.domain.user.usecase.UpdateUserPermissionsUseCase;
import com.marketplace.domain.user.usecase.UpdateUserRoleUseCase;

@Component
public class UserControllerFacade {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UpdateUserRoleUseCase updateUserRoleUseCase;

	@Autowired
	private UpdateUserPermissionsUseCase updateUserPermissionsUseCase;

	@Autowired
	private GetUserByIdUseCase getUserByIdUseCase;

	@Autowired
	private GetAllUserUseCase getAllUserUseCase;

	@Autowired
	private UpdatePhoneNumberUseCase updatePhoneNumberUseCase;

	@Autowired
	private UpdatePasswordUseCase updatePasswordUseCase;
	
	@Autowired
	private UpdateDisabledUseCase updateDisabledUseCase;

	@Autowired
	private AdminDataMapper mapper;

	public void updateRole(long userId, User.Role role) {
		updateUserRoleUseCase.apply(userId, role);
	}

	public void updatePhoneNumber(long userId, String phone) {
		updatePhoneNumberUseCase.apply(userId, phone);
	}

	public void updatePassword(long userId, String password) {
		updatePasswordUseCase.apply(userId, password);
	}

	public void updatePermissions(long userId, List<User.Permission> values) {
		updateUserPermissionsUseCase.apply(userId, values);
	}
	
	public void updateDisabled(long userId, boolean disabled) {
		updateDisabledUseCase.apply(userId, disabled);
	}

	@Transactional
	public void verifyPhoneNumber(long userId) {
		userDao.verifyPhoneNumber(userId);
	}

	public UserDTO findById(long userId) {
		var source = getUserByIdUseCase.apply(userId);
		return mapper.map(source);
	}

	public PageDataDTO<UserDTO> findAll(UserQuery query) {
		return mapper.mapUserPage(getAllUserUseCase.apply(query));
	}

}
