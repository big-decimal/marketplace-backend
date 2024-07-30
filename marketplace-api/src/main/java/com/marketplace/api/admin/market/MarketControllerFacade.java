package com.marketplace.api.admin.market;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.api.PageDataDTO;
import com.marketplace.api.admin.AdminDataMapper;
import com.marketplace.api.admin.shop.ShopDTO;
import com.marketplace.api.consumer.ConsumerDataMapper;
import com.marketplace.api.consumer.market.MarketDTO;
import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.market.MarketDao;
import com.marketplace.domain.market.usecase.DeleteMarketUseCase;
import com.marketplace.domain.market.usecase.SaveMarketUseCase;
import com.marketplace.domain.shop.dao.ShopDao;

@Component
public class MarketControllerFacade {

	@Autowired
	private MarketDao marketDao;

	@Autowired
	private ShopDao shopDao;

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

	public MarketDTO findById(long id) {
		return consumerDataMapper.map(marketDao.findById(id));
	}

	@Transactional(readOnly = true)
	public PageDataDTO<ShopDTO> findByMarket(long marketId, String q, Integer limit, Integer page) {
		var source = shopDao.findByMarket(marketId, q, PageQuery.of(page, Optional.ofNullable(limit).orElse(0)));
		return adminDataMapper.mapShopPage(source);
	}

}
