package com.marketplace.data.shop;

import com.marketplace.data.AuditingEntity;
import com.marketplace.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ShopLegal")
@Table(name = Constants.TABLE_PREFIX + "shop_legal")
public class ShopLegalEntity extends AuditingEntity {

    @Id
    private long id;

    @Column(name = "owner_name", length = 1000)
    private String ownerName;
	
    @Column(name = "seller_name", length = 1000)
    private String sellerName;
    
    @Column(name = "shop_number", length = 1000)
    private String shopNumber;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private ShopEntity shop;

    public ShopLegalEntity() {
    }
}
