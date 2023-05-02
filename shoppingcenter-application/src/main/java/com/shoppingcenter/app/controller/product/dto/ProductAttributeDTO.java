package com.shoppingcenter.app.controller.product.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAttributeDTO {
	
	private long id;
	
	private String name;
	
	private int sort;
	
	private List<ProductAttributeValueDTO> values;
	
}
