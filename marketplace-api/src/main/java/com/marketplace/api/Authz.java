package com.marketplace.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.shop.ShopMember;
import com.marketplace.domain.shop.dao.ShopMemberDao;

@Component
public class Authz {

	@Autowired
	private ShopMemberDao shopMemberDao;

	public boolean isShopMember(Long shopId) {
		if (shopId == null || shopId == 0) {
			return false;
		}
		var user = AuthenticationUtil.getAuthenticatedUser();
		if (user == null) {
			return false;
		}

//		if (user.isAdmin()) {
//			return true;
//		}

		var result = shopMemberDao.existsByShopAndUser(shopId, user.getId());
		return result;
	}

	public boolean isShopOwner(Long shopId) {
		if (shopId == null || shopId == 0) {
			return false;
		}
		var user = AuthenticationUtil.getAuthenticatedUser();
		if (user == null) {
			return false;
		}

//		if (user.isAdmin()) {
//			return true;
//		}

		var result = shopMemberDao.findByShopAndUser(shopId, user.getId());

		if (result == null) {
			return false;
		}
		return result.getRole() == ShopMember.Role.OWNER;
	}
}
