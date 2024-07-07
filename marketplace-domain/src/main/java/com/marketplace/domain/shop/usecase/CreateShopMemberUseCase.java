package com.marketplace.domain.shop.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.shop.ShopMember;
import com.marketplace.domain.shop.ShopMemberInput;
import com.marketplace.domain.shop.dao.ShopDao;
import com.marketplace.domain.shop.dao.ShopMemberDao;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class CreateShopMemberUseCase {

	@Autowired
    private ShopMemberDao dao;

	@Autowired
    private ShopDao shopDao;

	@Autowired
    private UserDao userDao;

	@Transactional
    public void apply(ShopMemberInput values) {
        if (!shopDao.existsById(values.getShopId())) {
            throw new ApplicationException("Shop not found");
        }

        if (!userDao.existsById(values.getUserId())) {
            throw new ApplicationException("User not found");
        }
        
        if (dao.existsByShopAndUser(values.getShopId(), values.getUserId())) {
        	throw new ApplicationException("Shop member already exists");
        }
        
        dao.save(values);
    }
	
	@Transactional
    public void apply(long shopId, String phone) {
        if (!shopDao.existsById(shopId)) {
            throw new ApplicationException("Shop not found");
        }
        
        var user = userDao.findByPhone(phone);

        if (user == null) {
            throw new ApplicationException("User not found");
        }
        
        if (!user.isPhoneNumberVerified()) {
        	throw new ApplicationException("User is not verified");
        }
        
        if (dao.existsByShopAndUser(shopId, user.getId())) {
        	throw new ApplicationException("Shop member already exists");
        }
        
        var values = new ShopMemberInput();
        values.setShopId(shopId);
        values.setUserId(user.getId());
        values.setRole(ShopMember.Role.ADMIN);
        
        dao.save(values);
    }

}
