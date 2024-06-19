package com.marketplace.api.admin.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.admin.AdminDataMapper;
import com.marketplace.api.consumer.ConsumerDataMapper;
import com.marketplace.api.consumer.market.MarketDTO;
import com.marketplace.domain.market.usecase.DeleteMarketUseCase;
import com.marketplace.domain.market.usecase.SaveMarketUseCase;

@Component
public class MarketControllerFacade {

	@Autowired
	private SaveMarketUseCase saveMarketUseCase;

	@Autowired
	private DeleteMarketUseCase deleteMarketUseCase;

	@Autowired
	private AdminDataMapper adminDataMapper;

	@Autowired
	private ConsumerDataMapper consumerDataMapper;

	public MarketDTO save(MarketEditDTO values) {
		var source = saveMarketUseCase.apply(adminDataMapper.map(values));
		return consumerDataMapper.map(source);
	}

	public void delete(long id) {
		deleteMarketUseCase.apply(id);
	}

}
