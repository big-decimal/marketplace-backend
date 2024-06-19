package com.marketplace.domain.user;

import java.util.List;

import com.marketplace.domain.Audit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

	public enum Role {
		USER, ADMIN, OWNER
	}

	// @formatter:off
    public enum Permission {
		DASHBOARD_READ,
		BANNER_READ, 
		BANNER_WRITE,
		CATEGORY_READ, 
		CATEGORY_WRITE, 
		CITY_READ, 
		CITY_WRITE, 
		MARKET_READ,
		MARKET_WRITE,
		SHOP_READ, 
		SHOP_WRITE, 
		PRODUCT_READ, 
		PRODUCT_WRITE, 
		USER_READ, 
		USER_WRITE, 
		SUBSCRIPTION_PLAN_READ, 
		SUBSCRIPTION_PLAN_WRITE, 
		PROMO_CODE_READ,
		PROMO_CODE_WRITE,
		SUBSCRIPTION_HISTORY_READ,
		SITE_SETTING_WRITE,
	}
    // @formatter:on

	private long id;

	private String uid;

	private String name;

	private String phone;

	private String email;

	private String image;

	private Role role;

	private boolean disabled;

	private List<User.Permission> permissions;

	private Audit audit = new Audit();

	public boolean isAdmin() {
		return role == User.Role.ADMIN || role == User.Role.OWNER;
	}

}
