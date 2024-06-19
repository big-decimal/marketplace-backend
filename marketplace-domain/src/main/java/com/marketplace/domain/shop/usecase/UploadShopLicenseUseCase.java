package com.marketplace.domain.shop.usecase;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Constants;
import com.marketplace.domain.UploadFile;
import com.marketplace.domain.common.FileStorageAdapter;
import com.marketplace.domain.shop.ShopLicense;
import com.marketplace.domain.shop.dao.ShopDao;
import com.marketplace.domain.shop.dao.ShopLicenseDao;

@Component
public class UploadShopLicenseUseCase {

	@Autowired
	private ShopDao shopDao;

	@Autowired
	private ShopLicenseDao shopLicenseDao;

	@Autowired
	private FileStorageAdapter fileStorageAdapter;

	@Transactional
	public void apply(long shopId, List<UploadFile> files) {

		if (files == null || files.isEmpty()) {
			return;
		}

		var licenseFiles = files.stream().filter(f -> f != null && !f.isEmpty()).toList();
		
		if (licenseFiles.isEmpty()) {
			return;
		}

		if (!shopDao.existsById(shopId)) {
			throw new ApplicationException("Shop not found");
		}

		var dateTime = LocalDateTime.now(ZoneOffset.UTC);
		var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		var uploadedLicenses = new HashMap<String, UploadFile>();

		for (var f : licenseFiles) {
			var suffix = f.getExtension();
			var dateTimeStr = dateTime.format(dateTimeFormatter);
			var imageName = String.format("shop-license-%d-%s.%s", shopId, dateTimeStr, suffix);

			var license = new ShopLicense();
			license.setShopId(shopId);
			license.setImage(imageName);

			shopLicenseDao.create(license);

			uploadedLicenses.put(imageName, f);
			dateTime = dateTime.plus(1, ChronoUnit.SECONDS);
		}

		var dir = Constants.IMG_SHOP_ROOT;

		fileStorageAdapter.write(uploadedLicenses, dir);

	}

}
