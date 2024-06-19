package com.marketplace.data.market;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.marketplace.data.market.view.MarketWithShopCountView;

public interface MarketRepo extends JpaRepository<MarketEntity, Long> {

	Optional<MarketEntity> findBySlug(String slug);

	@Query("SELECT m AS market, COUNT(s.id) AS shopCount FROM Market m LEFT JOIN Shop s ON m.id = s.market.id GROUP BY m.id")
	Page<MarketWithShopCountView> findMarkets(Pageable pageable);

}
