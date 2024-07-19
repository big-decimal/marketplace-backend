package com.marketplace.domain.shop.usecase;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.Constants;
import com.marketplace.domain.PageData;
import com.marketplace.domain.Utils;
import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.common.SearchCriteria;
import com.marketplace.domain.common.SearchCriteria.Operator;
import com.marketplace.domain.common.SearchQuery;
import com.marketplace.domain.shop.Shop;
import com.marketplace.domain.shop.ShopQuery;
import com.marketplace.domain.shop.dao.ShopDao;

@Component
public class GetAllShopUseCase {

	@Autowired
	private ShopDao dao;

	@Transactional(readOnly = true)
	public PageData<Shop> apply(ShopQuery query) {
		var sq = new SearchQuery();
		if (Utils.hasText(query.getQ())) {
			var q = "%" + query.getQ().toLowerCase() + "%";

			var c = SearchCriteria.simple("name", Operator.LIKE, q);
			c.setOrCriteria(SearchCriteria.simple("headline", Operator.LIKE, q));

			sq.addCriteria(c);
		}

		if (query.getCityId() != null && query.getCityId() > 0) {
			var c = SearchCriteria.simple("city.id", Operator.EQUAL, query.getCityId());
			sq.addCriteria(c);
		}

		if (query.getMarketId() != null && query.getMarketId() > 0) {
			var c = SearchCriteria.simple("market.id", Operator.EQUAL, query.getMarketId());
			sq.addCriteria(c);
		}

		if (query.getFeatured() == Boolean.TRUE) {
			var c = SearchCriteria.simple("featured", Operator.EQUAL, Boolean.TRUE);
			sq.addCriteria(c);
		}

		if (query.getExpired() != null) {
			var operator = query.getExpired() ? Operator.LESS_THAN : Operator.GREATER_THAN_EQ;

			var c = SearchCriteria.simple("expiredAt", operator, System.currentTimeMillis());
			sq.addCriteria(c);
		} else if (query.getExpireBefore() != null && query.getExpireBefore() > 0) {
			var now = System.currentTimeMillis();
			var diff = TimeUnit.DAYS.toMillis(query.getExpireBefore());
			long daysFromNow = now + diff;
			
			var cNotExpired = SearchCriteria.simple("expiredAt", Operator.GREATER_THAN_EQ, now);
			var cFromNow = SearchCriteria.simple("expiredAt", Operator.LESS_THAN_EQ, daysFromNow);
			
			sq.addCriteria(cNotExpired);
			sq.addCriteria(cFromNow);
		}

		if (query.getStatus() != null) {
			var c = SearchCriteria.simple("status", Operator.EQUAL, query.getStatus());
			sq.addCriteria(c);
		}

		var deletedCriteria = SearchCriteria.simple("deleted", Operator.EQUAL, Boolean.FALSE);

		sq.addCriteria(deletedCriteria);

		sq.setPageQuery(PageQuery.of(query.getPage(), Constants.PAGE_SIZE));

		return dao.findAll(sq);
	}

}
