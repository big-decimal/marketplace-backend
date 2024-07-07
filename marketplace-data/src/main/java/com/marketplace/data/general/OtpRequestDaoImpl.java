package com.marketplace.data.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketplace.domain.auth.OtpRequest;
import com.marketplace.domain.auth.OtpRequestDao;

@Repository
public class OtpRequestDaoImpl implements OtpRequestDao {

	@Autowired
	private OtpRequestRepo otpRequestRepo;

	@Override
	public void save(OtpRequest values) {
		var id = new OtpRequestEntity.ID(values.getPhone(), values.getDate());
		var entity = otpRequestRepo.findById(id).orElseGet(OtpRequestEntity::new);
		entity.setId(id);
		entity.setCount(values.getCount());
		otpRequestRepo.save(entity);
	}
	
	@Override
	public OtpRequest findByPhoneAndDate(String phone, String date) {
		var id = new OtpRequestEntity.ID(phone, date);
		return otpRequestRepo.findById(id).map(e -> {
			var request = new OtpRequest();
			request.setPhone(e.getId().getPhone());
			request.setDate(e.getId().getDate());
			request.setCount(e.getCount());
			return request;
		}).orElse(null);
	}

}
