package com.marketplace.domain.shop.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.shop.ShopLegalInput;
import com.marketplace.domain.shop.dao.ShopDao;

@Component
public class UpdateShopLegalUseCase {

	@Autowired
	private ShopDao shopDao;

	@Transactional
	public void apply(ShopLegalInput values) {
		if (!shopDao.existsById(values.getShopId())) {
			throw new ApplicationException("Shop not found");
		}

		shopDao.saveLegal(values);
	}

}
