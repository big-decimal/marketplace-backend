package com.marketplace.data.shop;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopLicenseRepo extends JpaRepository<ShopLicenseEntity, Long> {

	Optional<ShopLicenseEntity> findByIdAndShopId(long id, long shopId);
	
	List<ShopLicenseEntity> findByShopId(long shopId);
	
}
