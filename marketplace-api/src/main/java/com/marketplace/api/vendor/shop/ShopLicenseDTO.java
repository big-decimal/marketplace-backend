package com.marketplace.api.vendor.shop;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.marketplace.api.AbstractImageFieldSerializer.ShopImageSerializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopLicenseDTO {

	private long id;

	@JsonSerialize(using = ShopImageSerializer.class)
	private String image;

}
