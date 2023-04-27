package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.dao.ShopDao;

public class ValidateShopActiveUseCase {

    private ShopDao dao;

    public ValidateShopActiveUseCase(ShopDao dao) {
        this.dao = dao;
    }

    public void apply(long shopId) {

        var status = dao.getStatus(shopId);

        if (status == null || status == Shop.Status.PENDING) {
            throw new ApplicationException("Shop not found");
        }

        if (status == Shop.Status.EXPIRED) {
            throw new ApplicationException("shop-subscription-expired");
        }

        if (status == Shop.Status.DISABLED) {
            throw new ApplicationException("shop-disabled");
        }
    }

}