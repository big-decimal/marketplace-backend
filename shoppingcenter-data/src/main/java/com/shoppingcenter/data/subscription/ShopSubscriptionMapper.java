package com.shoppingcenter.data.subscription;

import com.shoppingcenter.data.shop.ShopMapper;
import com.shoppingcenter.domain.subscription.ShopSubscription;

public class ShopSubscriptionMapper {
	
	public static ShopSubscription toDomain(ShopSubscriptionEntity entity) {
		var subscription = toDomainCompat(entity);
		if (entity.getShop() != null) {
			subscription.setShop(ShopMapper.toDomainCompat(entity.getShop()));
			subscription.setShopId(entity.getShop().getId());
		}
		
		if (entity.getTransaction() != null) {
			subscription.setTransaction(ShopSubscriptionTransactionMapper.toDomain(entity.getTransaction()));
		}
		return subscription;
	}

	public static ShopSubscription toDomainCompat(ShopSubscriptionEntity entity) {
		var subscription = new ShopSubscription();
		subscription.setId(entity.getId());
		subscription.setTitle(entity.getTitle());
		subscription.setTotalPrice(entity.getTotalPrice());
		subscription.setSubTotalPrice(entity.getSubTotalPrice());
		subscription.setDiscount(entity.getDiscount());
		subscription.setDuration(entity.getDuration());
		subscription.setStartAt(entity.getStartAt());
		subscription.setEndAt(entity.getEndAt());
		subscription.setCreatedAt(entity.getCreatedAt());
		subscription.setStatus(entity.getStatus());
		subscription.setPreSubscription(entity.isPreSubscription());
		subscription.setPromoCode(entity.getPromoCode());
		return subscription;
	}
	
}
