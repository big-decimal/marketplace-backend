package com.marketplace.domain.market.usecase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.PageData;
import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.market.Market;
import com.marketplace.domain.market.MarketDao;

@Component
public class GetAllMarketUseCase {

	@Autowired
	private MarketDao dao;

	public PageData<Market> apply(Integer page, Integer limit) {
		return dao.findAll(PageQuery.of(page, Optional.ofNullable(limit).orElse(0)));
	}

}
