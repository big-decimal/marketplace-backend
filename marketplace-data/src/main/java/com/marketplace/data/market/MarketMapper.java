package com.marketplace.data.market;

import com.marketplace.data.AuditMapper;
import com.marketplace.data.market.view.MarketWithShopCountView;
import com.marketplace.domain.market.Market;

public interface MarketMapper {

	static Market toDomain(MarketEntity entity) {
		var market = new Market();
		market.setId(entity.getId());
		market.setName(entity.getName());
		market.setSlug(entity.getSlug());
		market.setUrl(entity.getUrl());
		market.setAudit(AuditMapper.from(entity));
		return market;
	}

	static Market toDomain(MarketWithShopCountView view) {
		var market = toDomain(view.getMarket());
		market.setShopCount(view.getShopCount());
		return market;
	}

}
