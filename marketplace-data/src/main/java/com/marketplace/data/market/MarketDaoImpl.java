package com.marketplace.data.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import com.marketplace.data.PageDataMapper;
import com.marketplace.data.PageQueryMapper;
import com.marketplace.data.market.view.MarketWithShopCountView;
import com.marketplace.domain.PageData;
import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.market.Market;
import com.marketplace.domain.market.MarketDao;
import com.marketplace.domain.market.MarketInput;

@Repository
public class MarketDaoImpl implements MarketDao {

	@Autowired
	private MarketRepo marketRepo;

	@Override
	public Market save(MarketInput values) {
		var entity = marketRepo.findById(values.getId()).orElseGet(MarketEntity::new);
		entity.setName(values.getName());
		entity.setSlug(values.getSlug());
		entity.setUrl(values.getUrl());

		var result = marketRepo.save(entity);

		return MarketMapper.toDomain(result);
	}

	@Override
	public void delete(long id) {
		marketRepo.deleteById(id);
	}

	@Override
	public boolean existsById(long id) {
		return marketRepo.existsById(id);
	}

	@Override
	public Market findById(long id) {
		return marketRepo.findById(id).map(MarketMapper::toDomain).orElse(null);
	}

	@Override
	public Market findBySlug(String slug) {
		return marketRepo.findBySlug(slug).map(MarketMapper::toDomain).orElse(null);
	}

	@Override
	public PageData<Market> findAll(PageQuery pageQuery) {
		var request = PageQueryMapper.fromPageQuery(pageQuery);

		Page<MarketWithShopCountView> pageResult = marketRepo.findMarkets(request);

		return PageDataMapper.map(pageResult, MarketMapper::toDomain);
	}

}
