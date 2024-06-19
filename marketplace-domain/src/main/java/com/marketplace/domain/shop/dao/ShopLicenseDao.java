package com.marketplace.domain.shop.dao;

import java.util.List;

import com.marketplace.domain.shop.ShopLicense;

public interface ShopLicenseDao {

	void create(ShopLicense values);

	void delete(long id);
	
	ShopLicense findByIdAndShop(long id, long shopId);

	List<ShopLicense> findByShop(long shopId);

}
