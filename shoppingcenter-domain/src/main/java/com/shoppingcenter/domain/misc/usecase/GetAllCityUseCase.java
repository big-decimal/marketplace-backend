package com.shoppingcenter.domain.misc.usecase;

import java.util.List;

import com.shoppingcenter.domain.misc.City;
import com.shoppingcenter.domain.misc.CityDao;

public class GetAllCityUseCase {

	private CityDao dao;
	
	public List<City> apply() {
		return dao.findAll();
	}
}
