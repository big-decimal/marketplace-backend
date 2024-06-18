package com.marketplace.domain.market;

import com.marketplace.domain.PageData;
import com.marketplace.domain.common.PageQuery;

public interface MarketDao {

	Market save(MarketInput values);

	void delete(long id);
	
	boolean existsById(long id);

	Market findById(long id);

	Market findBySlug(String slug);

	PageData<Market> findAll(PageQuery pageQuery);

}
