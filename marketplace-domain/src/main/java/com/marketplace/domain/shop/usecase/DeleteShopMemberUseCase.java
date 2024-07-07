package com.marketplace.domain.shop.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.shop.ShopMember;
import com.marketplace.domain.shop.dao.ShopMemberDao;

@Component
public class DeleteShopMemberUseCase {

	@Autowired
	private ShopMemberDao dao;

	@Transactional
	public void apply(long shopId, long userId) {
		var member = dao.findByShopAndUser(shopId, userId);

		if (member == null) {
			throw new ApplicationException("Shop member not found");
		}

		if (member.getRole() == ShopMember.Role.OWNER) {
			throw new ApplicationException("Owner member not found");
		}

		dao.delete(shopId, userId);
	}

}
