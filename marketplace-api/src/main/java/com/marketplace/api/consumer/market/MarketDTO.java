package com.marketplace.api.consumer.market;

import com.marketplace.api.AuditDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketDTO {

	private long id;

	private String name;

	private String slug;

	private String url;
	
	private Long shopCount;

	private AuditDTO audit;

}
