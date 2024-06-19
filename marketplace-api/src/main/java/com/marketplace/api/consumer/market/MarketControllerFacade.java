package com.marketplace.api.consumer.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.PageDataDTO;
import com.marketplace.api.consumer.ConsumerDataMapper;
import com.marketplace.domain.market.usecase.GetAllMarketUseCase;

@Component
public class MarketControllerFacade {

	@Autowired
	private GetAllMarketUseCase getAllMarketUseCase;

	@Autowired
	private ConsumerDataMapper mapper;

	public PageDataDTO<MarketDTO> findAll(Integer page, Integer limit) {
		var source = getAllMarketUseCase.apply(page, limit);
		return mapper.mapMarketPage(source);
	}

}
