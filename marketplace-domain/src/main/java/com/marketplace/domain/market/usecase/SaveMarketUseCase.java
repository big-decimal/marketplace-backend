package com.marketplace.domain.market.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Utils;
import com.marketplace.domain.market.Market;
import com.marketplace.domain.market.MarketDao;
import com.marketplace.domain.market.MarketInput;

@Component
public class SaveMarketUseCase {

	@Autowired
	private MarketDao marketDao;

	@Transactional
	public Market apply(MarketInput values) {
		
		if (!Utils.hasText(values.getName())) {
			throw new ApplicationException("Required market name");
		}

		var rawSlug = Utils.convertToSlug(values.getSlug());

		if (!Utils.hasText(rawSlug)) {
			throw new ApplicationException("Required market slug");
		}

		return marketDao.save(values);

	}

}
