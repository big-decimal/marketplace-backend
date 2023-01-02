package com.shoppingcenter.core.subscription.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.shoppingcenter.data.subscription.SubscriptionPlanEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionPlan {

	private long id;

	private String title;

	private int duration; // by days

	private boolean promoUsable;

	private double price;

	@JsonProperty(access = Access.READ_ONLY)
	private long createdAt;

	public static SubscriptionPlan create(SubscriptionPlanEntity entity) {
		SubscriptionPlan sp = new SubscriptionPlan();
		sp.setId(entity.getId());
		sp.setTitle(entity.getTitle());
		sp.setDuration(entity.getDuration());
		sp.setPromoUsable(entity.isPromoUsable());
		sp.setPrice(entity.getPrice());
		sp.setCreatedAt(entity.getCreatedAt());
		return sp;
	}
}
