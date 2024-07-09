package com.marketplace.api.vendor.shop;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.marketplace.api.MultipartFileConverter;
import com.marketplace.api.vendor.VendorDataMapper;
import com.marketplace.domain.order.usecase.GetPendingOrderCountByShopUseCase;
import com.marketplace.domain.shop.ShopLegalInput;
import com.marketplace.domain.shop.dao.ShopMemberDao;
import com.marketplace.domain.shop.usecase.CreateShopMemberUseCase;
import com.marketplace.domain.shop.usecase.CreateShopUseCase;
import com.marketplace.domain.shop.usecase.DeleteShopAcceptedPaymentUseCase;
import com.marketplace.domain.shop.usecase.DeleteShopLicenseUseCase;
import com.marketplace.domain.shop.usecase.DeleteShopMemberUseCase;
import com.marketplace.domain.shop.usecase.GetMonthlySaleByShopUseCase;
import com.marketplace.domain.shop.usecase.GetShopByIdUseCase;
import com.marketplace.domain.shop.usecase.GetShopLicensesUseCase;
import com.marketplace.domain.shop.usecase.GetShopMembersByShopUseCase;
import com.marketplace.domain.shop.usecase.GetShopSettingUseCase;
import com.marketplace.domain.shop.usecase.GetShopStatisticUseCase;
import com.marketplace.domain.shop.usecase.SaveShopAcceptedPaymentUseCase;
import com.marketplace.domain.shop.usecase.SaveShopSettingUseCase;
import com.marketplace.domain.shop.usecase.UpdateShopContactUseCase;
import com.marketplace.domain.shop.usecase.UpdateShopLegalUseCase;
import com.marketplace.domain.shop.usecase.UpdateShopUseCase;
import com.marketplace.domain.shop.usecase.UploadShopCoverUseCase;
import com.marketplace.domain.shop.usecase.UploadShopLicenseUseCase;
import com.marketplace.domain.shop.usecase.UploadShopLogoUseCase;

@Component
public class ShopControllerFacade {

	@Autowired
	private CreateShopUseCase createShopUseCase;

	@Autowired
	private UpdateShopUseCase updateShopUseCase;

	@Autowired
	private UpdateShopContactUseCase updateShopContactUseCase;
	
	@Autowired
	private UpdateShopLegalUseCase updateShopLegalUseCase;

	@Autowired
	private UploadShopLogoUseCase uploadShopLogoUseCase;

	@Autowired
	private UploadShopCoverUseCase uploadShopCoverUseCase;

	@Autowired
	private UploadShopLicenseUseCase uploadShopLicenseUseCase;

	@Autowired
	private DeleteShopLicenseUseCase deleteShopLicenseUseCase;

	@Autowired
	private GetShopByIdUseCase getShopByIdUseCase;

	@Autowired
	private GetShopStatisticUseCase getShopStatisticUseCase;

	@Autowired
	private SaveShopSettingUseCase saveShopSettingUseCase;

	@Autowired
	private SaveShopAcceptedPaymentUseCase saveShopAcceptedPaymentUseCase;

	@Autowired
	private DeleteShopAcceptedPaymentUseCase deleteShopAcceptedPaymentUseCase;

	@Autowired
	private GetShopSettingUseCase getShopSettingUseCase;

	@Autowired
	private GetMonthlySaleByShopUseCase getMonthlySaleByShopUseCase;

	@Autowired
	private GetPendingOrderCountByShopUseCase getPendingOrderCountByShopUseCase;

	@Autowired
	private GetShopLicensesUseCase getShopLicensesUseCase;

	@Autowired
	private GetShopMembersByShopUseCase getShopMembersByShopUseCase;
	
	@Autowired
	private CreateShopMemberUseCase createShopMemberUseCase;
	
	@Autowired
	private DeleteShopMemberUseCase deleteShopMemberUseCase;

	@Autowired
	private ShopMemberDao shopMemberDao;

	@Autowired
	private VendorDataMapper mapper;

	public void create(ShopCreateDTO values) {
		createShopUseCase.apply(mapper.map(values));
	}

	public ShopDTO update(ShopUpdateDTO values) {
		var source = updateShopUseCase.apply(mapper.map(values));
		return mapper.map(source);
	}

	public void updateContact(ShopContactUpdateDTO values) {
		updateShopContactUseCase.apply(mapper.map(values));
	}
	
	public void updateLegal(ShopLegalInput values) {
		updateShopLegalUseCase.apply(values);
	}

	public void updateSetting(ShopSettingDTO values) {
		saveShopSettingUseCase.apply(mapper.map(values));
	}

	public void uploadLogo(long shopId, MultipartFile file) {
		uploadShopLogoUseCase.apply(shopId, MultipartFileConverter.toUploadFile(file));
	}

	public void uploadCover(long shopId, MultipartFile file) {
		uploadShopCoverUseCase.apply(shopId, MultipartFileConverter.toUploadFile(file));
	}

	public void uploadShopLicense(long shopId, MultipartFile file) {
		var f = MultipartFileConverter.toUploadFile(file);
		uploadShopLicenseUseCase.apply(shopId, List.of(f));
	}

	public void deleteShopLicense(long id, long shopId) {
		deleteShopLicenseUseCase.apply(id, shopId);
	}

	public void saveAcceptedPayment(long shopId, ShopAcceptedPaymentDTO values) {
		var input = mapper.map(values);
		saveShopAcceptedPaymentUseCase.apply(shopId, Arrays.asList(input));
	}

	public void deleteAcceptedPayment(long id) {
		deleteShopAcceptedPaymentUseCase.apply(id);
	}
	
	public void createShopMember(long shopId, String phone) {
		createShopMemberUseCase.apply(shopId, phone);
	}
	
	public void deleteShopMember(long shopId, long userId) {
		deleteShopMemberUseCase.apply(shopId, userId);
	}

	public long getPendingOrderCount(long shopId) {
		return getPendingOrderCountByShopUseCase.apply(shopId);
	}

	public ShopDTO findById(long id) {
		var source = getShopByIdUseCase.apply(id);
		return mapper.map(source);
	}

	public ShopStatisticDTO getShopStatistic(long shopId) {
		var source = getShopStatisticUseCase.apply(shopId);
		return mapper.map(source);
	}

	public ShopSettingDTO getShopSetting(long shopId) {
		var source = getShopSettingUseCase.apply(shopId);
		return mapper.map(source);
	}

	public List<ShopMonthlySaleDTO> getMonthlySale(long shopId, int year) {
		var source = getMonthlySaleByShopUseCase.apply(shopId, year);
		return mapper.mapShopMonthlySaleList(source);
	}

	public List<ShopLicenseDTO> getShopLicenses(long shopId) {
		var source = getShopLicensesUseCase.apply(shopId);
		return mapper.mapShopLicenseList(source);
	}

	public ShopMemberDTO getShopMember(long shopId, long userId) {
		var source = shopMemberDao.findByShopAndUser(shopId, userId);
		return mapper.map(source);
	}

	public List<ShopMemberDTO> getShopMembers(long shopId) {
		var source = getShopMembersByShopUseCase.apply(shopId);
		return mapper.map(source);
	}
}
