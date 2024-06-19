package com.marketplace.data.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketplace.domain.shop.ShopLicense;
import com.marketplace.domain.shop.dao.ShopLicenseDao;

@Repository
public class ShopLicenseDaoImpl implements ShopLicenseDao {

	@Autowired
	private ShopRepo shopRepo;

	@Autowired
	private ShopLicenseRepo shopLicenseRepo;

	@Override
	public void create(ShopLicense values) {
		var entity = new ShopLicenseEntity();
		entity.setShop(shopRepo.getReferenceById(values.getShopId()));
		entity.setImage(values.getImage());

		shopLicenseRepo.save(entity);
	}

	@Override
	public void delete(long id) {
		shopLicenseRepo.deleteById(id);
	}

	@Override
	public ShopLicense findByIdAndShop(long id, long shopId) {
		return shopLicenseRepo.findByIdAndShopId(id, shopId).map(ShopLicenseMapper::toDomain).orElse(null);
	}

	@Override
	public List<ShopLicense> findByShop(long shopId) {
		return shopLicenseRepo.findByShopId(shopId).stream().map(ShopLicenseMapper::toDomain).toList();
	}

}
