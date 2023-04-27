package com.shoppingcenter.app.controller.shop.dto;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopSaleHistoryDTO {

	private int year;

	private int month;

	private double totalSale;
	
	public static Type listType() {
        return new TypeToken<List<ShopSaleHistoryDTO>>() {
        }.getType();
    }

}
