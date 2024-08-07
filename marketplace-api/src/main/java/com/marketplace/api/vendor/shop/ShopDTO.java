package com.marketplace.api.vendor.shop;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.marketplace.api.AbstractImageFieldSerializer.ShopImageSerializer;
import com.marketplace.api.AuditDTO;
import com.marketplace.api.consumer.general.CityDTO;
import com.marketplace.api.consumer.market.MarketDTO;
import com.marketplace.domain.shop.Shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopDTO {
    private long id;

    private String name;

    private String slug;

    private String headline;

    private String about;

    private BigDecimal rating;

    private boolean featured;
    
    private long expiredAt;
    
    private Shop.Status status;

    @JsonSerialize(using = ShopImageSerializer.class)
    private String logo;

    @JsonSerialize(using = ShopImageSerializer.class)
    private String cover;
    
    private ShopContactDTO contact;
    
    private ShopLegalDTO legal;
    
    private CityDTO city;
    
    private MarketDTO market;
    
    private AuditDTO audit;
}
