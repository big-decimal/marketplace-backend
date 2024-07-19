package com.marketplace.data.shop;

import java.util.Arrays;

import com.marketplace.data.AuditMapper;
import com.marketplace.data.general.CityMapper;
import com.marketplace.data.market.MarketMapper;
import com.marketplace.domain.Utils;
import com.marketplace.domain.shop.Shop;
import com.marketplace.domain.shop.ShopContact;
import com.marketplace.domain.shop.ShopLegal;

public interface ShopMapper {

	static Shop toDomainCompat(ShopEntity entity) {
		var s = new Shop();
		s.setId(entity.getId());
		s.setName(entity.getName());
		s.setSlug(entity.getSlug());
		s.setHeadline(entity.getHeadline());
		s.setFeatured(entity.isFeatured());
		s.setStatus(entity.getStatus());
		s.setLogo(entity.getLogo());
		s.setCover(entity.getCover());
		s.setExpiredAt(entity.getExpiredAt());
		s.setAudit(AuditMapper.from(entity));
		s.setRating(entity.getRating().getRating());
		return s;
	}

	static Shop toDomain(ShopEntity entity) {
		var s = toDomainCompat(entity);
		s.setAbout(entity.getAbout());
		if (entity.getContact() != null) {
			s.setContact(toContact(entity.getContact()));
		}

		if (entity.getCity() != null) {
			s.setCity(CityMapper.toDomain(entity.getCity()));
		}

		if (entity.getMarket() != null) {
			s.setMarket(MarketMapper.toDomain(entity.getMarket()));
		}
		
		if (entity.getLegal() != null) {
			s.setLegal(toLegal(entity.getLegal()));
		}
		return s;
	}
	
	static Shop toLegalDomain(ShopEntity entity) {
		var s = toDomainCompat(entity);
		if (entity.getContact() != null) {
			s.setContact(toContact(entity.getContact()));
		}

		if (entity.getMarket() != null) {
			s.setMarket(MarketMapper.toDomain(entity.getMarket()));
		}
		
		if (entity.getLegal() != null) {
			s.setLegal(toLegal(entity.getLegal()));
		}
		return s;
	}

	static ShopContact toContact(ShopContactEntity entity) {
		var contact = new ShopContact();
		if (entity == null) {
			return contact;
		}
		contact.setAddress(entity.getAddress());
		contact.setLatitude(entity.getLatitude());
		contact.setLongitude(entity.getLongitude());

		if (Utils.hasText(entity.getPhones())) {
			contact.setPhones(Arrays.asList(entity.getPhones().split(",")));
		}
		return contact;
	}
	
	static ShopLegal toLegal(ShopLegalEntity entity) {
		var legal = new ShopLegal();
		if (entity == null) {
			return legal;
		}
		
		legal.setOwnerName(entity.getOwnerName());
		legal.setSellerName(entity.getSellerName());
		legal.setShopNumber(entity.getShopNumber());
		return legal;
	}

}
