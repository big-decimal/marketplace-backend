package com.marketplace.domain.product.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.product.dao.FavoriteProductDao;

@Component
public class CheckFavoriteProductUseCase {

	@Autowired
    private FavoriteProductDao dao;

    public boolean apply(long userId, long productId) {
        return dao.exists(userId, productId);
    }

}
