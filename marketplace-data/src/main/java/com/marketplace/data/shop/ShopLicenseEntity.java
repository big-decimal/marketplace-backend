package com.marketplace.data.shop;

import com.marketplace.data.AuditingEntity;
import com.marketplace.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ShopLicense")
@Table(name = Constants.TABLE_PREFIX + "shop_license")
public class ShopLicenseEntity extends AuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 500, nullable = false)
	private String image;

	@ManyToOne(fetch = FetchType.LAZY)
	private ShopEntity shop;
	
	public ShopLicenseEntity() {
	}
}
