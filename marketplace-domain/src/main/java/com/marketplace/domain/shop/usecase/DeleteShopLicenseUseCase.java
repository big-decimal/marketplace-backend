package com.marketplace.domain.shop.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Constants;
import com.marketplace.domain.Utils;
import com.marketplace.domain.common.FileStorageAdapter;
import com.marketplace.domain.shop.dao.ShopLicenseDao;

@Component
public class DeleteShopLicenseUseCase {

	@Autowired
	private ShopLicenseDao dao;

	@Autowired
	private FileStorageAdapter fileStorageAdapter;

	@Transactional
	public void apply(long id, long shopId) {
		var license = dao.findByIdAndShop(id, shopId);

		if (license == null) {
			throw new ApplicationException("Shop license not found");
		}

		dao.delete(id);

		if (Utils.hasText(license.getImage())) {
			fileStorageAdapter.delete(Constants.IMG_SHOP_ROOT, license.getImage());
		}

	}

}
