package com.marketplace.api.vendor.subscription;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import org.modelmapper.TypeToken;

import com.marketplace.api.PageDataDTO;
import com.marketplace.domain.subscription.SubscriptionPromo.ValueType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionPromoDTO {

	private long id;

	private String code;

	private BigDecimal value;

	private BigDecimal minConstraint;

	private ValueType valueType;

	private long expiredAt;

	private boolean used;
	
	public static Type pageType() {
        return new TypeToken<PageDataDTO<SubscriptionPromoDTO>>() {
        }.getType();
    }
	
}