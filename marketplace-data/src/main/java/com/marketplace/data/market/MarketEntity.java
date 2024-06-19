package com.marketplace.data.market;

import com.marketplace.data.AuditingEntity;
import com.marketplace.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Market")
@Table(name = Constants.TABLE_PREFIX + "market")
public class MarketEntity extends AuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 2000)
	private String name;

	@Column(unique = true, length = 2000)
	private String slug;

	@Column(length = 1000)
	private String url;

	public MarketEntity() {
	}

}
