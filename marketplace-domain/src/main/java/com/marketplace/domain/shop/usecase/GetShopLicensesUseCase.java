package com.marketplace.domain.shop.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.shop.ShopLicense;
import com.marketplace.domain.shop.dao.ShopLicenseDao;

@Component
public class GetShopLicensesUseCase {

	@Autowired
    private ShopLicenseDao dao;

    public List<ShopLicense> apply(long shopId) {
		return dao.findByShop(shopId);
    }

}
