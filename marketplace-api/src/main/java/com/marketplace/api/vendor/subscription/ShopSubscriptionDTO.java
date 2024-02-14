package com.marketplace.api.vendor.subscription;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.TypeToken;

import com.marketplace.api.PageDataDTO;
import com.marketplace.domain.subscription.ShopSubscription;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopSubscriptionDTO {

	private long invoiceNo;

	private String title;

	private BigDecimal subTotalPrice;

	private BigDecimal discount;

	private BigDecimal totalPrice;

	private ShopSubscription.Status status;

	private int duration;

	private long startAt;

	private long endAt;
	
	private String promoCode;
	
	public static Type listType() {
        return new TypeToken<List<ShopSubscriptionDTO>>() {
        }.getType();
    }
	
	public static Type pageType() {
        return new TypeToken<PageDataDTO<ShopSubscriptionDTO>>() {
        }.getType();
    }

}