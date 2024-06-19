package com.marketplace.domain.market.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.market.MarketDao;
import com.marketplace.domain.shop.dao.ShopDao;

@Component
public class DeleteMarketUseCase {

	@Autowired
	private MarketDao marketDao;

	@Autowired
	private ShopDao shopDao;

	@Transactional
	public void apply(long id) {
		if (!marketDao.existsById(id)) {
			throw new ApplicationException("Market not found");
		}

		if (shopDao.existsByMarket(id)) {
			throw new ApplicationException("Referenced by shops");
		}

		marketDao.delete(id);
	}

}
