package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.common.HTMLStringSanitizer;
import com.shoppingcenter.domain.shop.ShopContact;
import com.shoppingcenter.domain.shop.ShopCreateInput;
import com.shoppingcenter.domain.shop.ShopMember;
import com.shoppingcenter.domain.shop.ShopMember.Role;
import com.shoppingcenter.domain.shop.dao.ShopDao;
import com.shoppingcenter.domain.user.UserDao;

import lombok.Setter;

@Setter
public class CreateShopUseCase {

	private ShopDao shopDao;

	private UserDao userDao;

	private HTMLStringSanitizer htmlStringSanitizer;

	private UpdateShopContactUseCase saveShopContactUseCase;

	private CreateShopMemberUseCase createShopMemberUseCase;

	private UploadShopLogoUseCase uploadShopLogoUseCase;

	private UploadShopCoverUseCase uploadShopCoverUseCase;

	public void apply(ShopCreateInput data) {
		if (!Utils.hasText(data.getName())) {
			throw new ApplicationException("Required shop name");
		}

		if (!Utils.hasText(data.getSlug())) {
			throw new ApplicationException("Required shop slug");
		}

		if (!userDao.existsById(data.getUserId())) {
			throw new ApplicationException("User not fond");
		}
		
		var slug = Utils.convertToSlug(data.getName());
		
		if (!Utils.hasText(slug)) {
        	throw new ApplicationException("Invalid slug value");
        }

		if (Utils.hasText(data.getAbout())) {
			var about = data.getAbout();
			data.setAbout(htmlStringSanitizer.sanitize(about));
		}

		data.setSlug(slug);

		var shopId = shopDao.create(data);
		
		var userId = data.getUserId();

		var contact = new ShopContact();

		contact.setAddress(data.getAddress());
		contact.setShopId(shopId);
		saveShopContactUseCase.apply(contact);

		var member = new ShopMember();
		member.setRole(Role.OWNER);
		member.setShopId(shopId);
		member.setUserId(userId);

		createShopMemberUseCase.apply(member);

		if (data.getLogo() != null && !data.getLogo().isEmpty()) {
			uploadShopLogoUseCase.apply(shopId, data.getLogo());
		}

		if (data.getCover() != null && !data.getCover().isEmpty()) {
			uploadShopCoverUseCase.apply(shopId, data.getCover());
		}

	}

}
