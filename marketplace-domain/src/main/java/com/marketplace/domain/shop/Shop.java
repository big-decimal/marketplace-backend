package com.marketplace.domain.shop;

import java.math.BigDecimal;
import java.util.List;

import com.marketplace.domain.Audit;
import com.marketplace.domain.general.City;
import com.marketplace.domain.market.Market;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Shop {

    public enum Status {
        PENDING, APPROVED, DISABLED
    }

    private long id;

    private String name;

    private String slug;

    private String headline;

    private String about;
    
    private BigDecimal rating;

    private boolean featured;
    
    private boolean deleted;
    
    private long expiredAt;
    
    private Shop.Status status;

    private String logo;

    private String cover;

    private ShopContact contact;
    
    private ShopLegal legal;
    
    private City city;
    
    private Market market;
    
    private List<ShopLicense> licenses;

    private Audit audit = new Audit();

    public Shop() {
    	this.status = Status.PENDING;
    }

}
