package com.marketplace.data.shop;

import com.marketplace.domain.shop.ShopLicense;

public interface ShopLicenseMapper {

	static ShopLicense toDomain(ShopLicenseEntity entity) {
		var data = new ShopLicense();
		data.setId(entity.getId());
		data.setImage(entity.getImage());
		return data;
	}

}
