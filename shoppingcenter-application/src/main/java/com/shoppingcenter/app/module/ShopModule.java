package com.shoppingcenter.app.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcenter.domain.common.AuthenticationContext;
import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.common.HTMLStringSanitizer;
import com.shoppingcenter.domain.product.dao.ProductDao;
import com.shoppingcenter.domain.shop.dao.ShopAcceptedPaymentDao;
import com.shoppingcenter.domain.shop.dao.ShopDao;
import com.shoppingcenter.domain.shop.dao.ShopMemberDao;
import com.shoppingcenter.domain.shop.dao.ShopReviewDao;
import com.shoppingcenter.domain.shop.dao.ShopSearchDao;
import com.shoppingcenter.domain.shop.usecase.CheckIsShopMemberUseCase;
import com.shoppingcenter.domain.shop.usecase.CreateShopMemberUseCase;
import com.shoppingcenter.domain.shop.usecase.CreateShopUseCase;
import com.shoppingcenter.domain.shop.usecase.DeleteShopAcceptedPaymentUseCase;
import com.shoppingcenter.domain.shop.usecase.GetAllShopAcceptedPaymentUseCase;
import com.shoppingcenter.domain.shop.usecase.GetAllShopReviewUseCase;
import com.shoppingcenter.domain.shop.usecase.GetAllShopUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopByIdUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopBySlugUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopByUserUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopHintsUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopStatisticUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopReviewByUserUseCase;
import com.shoppingcenter.domain.shop.usecase.SaveShopAcceptedPaymentUseCase;
import com.shoppingcenter.domain.shop.usecase.UpdateShopBasicInfoUseCase;
import com.shoppingcenter.domain.shop.usecase.UpdateShopContactUseCase;
import com.shoppingcenter.domain.shop.usecase.UploadShopCoverUseCase;
import com.shoppingcenter.domain.shop.usecase.UploadShopLogoUseCase;
import com.shoppingcenter.domain.shop.usecase.ValidateShopActiveUseCase;
import com.shoppingcenter.domain.shop.usecase.ValidateShopMemberUseCase;
import com.shoppingcenter.domain.shop.usecase.WriteShopReviewUseCase;
import com.shoppingcenter.domain.user.UserDao;

@Configuration
public class ShopModule {

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private ShopReviewDao shopReviewDao;

    @Autowired
    private ShopMemberDao shopMemberDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ShopSearchDao shopSearchDao;

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private ShopAcceptedPaymentDao shopAcceptedPaymentDao;

    @Autowired
    private AuthenticationContext authenticationContext;

    @Autowired
    private HTMLStringSanitizer htmlStringSanitizer;

    @Autowired
    private FileStorageAdapter fileStorageAdapter;

    @Bean
    CreateShopMemberUseCase createShopMemberUseCase() {
        var usecase = new CreateShopMemberUseCase();
        usecase.setDao(shopMemberDao);
        usecase.setShopDao(shopDao);
        usecase.setUserDao(userDao);
        return usecase;
    }

    @Bean
    ValidateShopActiveUseCase validateShopActiveUseCase() {
        return new ValidateShopActiveUseCase(shopDao);
    }

    @Bean
    ValidateShopMemberUseCase validateShopMemberUseCase() {
        return new ValidateShopMemberUseCase(shopMemberDao);
    }

    @Bean
    UploadShopLogoUseCase uploadShopLogoUseCase() {
        var usecase = new UploadShopLogoUseCase();
        usecase.setDao(shopDao);
        usecase.setFileStorageAdapter(fileStorageAdapter);
        return usecase;
    }

    @Bean
    UploadShopCoverUseCase uploadShopCoverUseCase() {
        var usecase = new UploadShopCoverUseCase();
        usecase.setDao(shopDao);
        usecase.setFileStorageAdapter(fileStorageAdapter);
        return usecase;
    }

    @Bean
    UpdateShopContactUseCase saveShopContactUseCase() {
        return new UpdateShopContactUseCase(shopDao);
    }

    @Bean
    CreateShopUseCase createShopUseCase(
            CreateShopMemberUseCase createShopMemberUseCase,
            UploadShopLogoUseCase uploadShopLogoUseCase,
            UploadShopCoverUseCase uploadShopCoverUseCase,
            UpdateShopContactUseCase saveShopContactUseCase) {
        var usecase = new CreateShopUseCase();
        usecase.setShopDao(shopDao);
        usecase.setUserDao(userDao);
        usecase.setHtmlStringSanitizer(htmlStringSanitizer);
        usecase.setUploadShopLogoUseCase(uploadShopLogoUseCase);
        usecase.setUploadShopCoverUseCase(uploadShopCoverUseCase);
        usecase.setSaveShopContactUseCase(saveShopContactUseCase);
        usecase.setCreateShopMemberUseCase(createShopMemberUseCase);
        return usecase;
    }

    @Bean
    UpdateShopBasicInfoUseCase updateShopBasicInfoUseCase() {
        var usecase = new UpdateShopBasicInfoUseCase();
        usecase.setDao(shopDao);
        usecase.setShopSearchDao(shopSearchDao);
        usecase.setHtmlStringSanitizer(htmlStringSanitizer);
        return usecase;
    }
    
    @Bean
    SaveShopAcceptedPaymentUseCase saveShopAcceptedPaymentUseCase() {
    	var usecase = new SaveShopAcceptedPaymentUseCase();
    	usecase.setShopDao(shopDao);
    	usecase.setShopAcceptedPaymentDao(shopAcceptedPaymentDao);
    	return usecase;
    }
    
    @Bean
    DeleteShopAcceptedPaymentUseCase deleteShopAcceptedPaymentUseCase() {
    	var usecase = new DeleteShopAcceptedPaymentUseCase();
    	usecase.setShopAcceptedPaymentDao(shopAcceptedPaymentDao);
    	return usecase;
    }

    @Bean
    GetShopBySlugUseCase getShopBySlugUseCase() {
        return new GetShopBySlugUseCase(shopDao);
    }

    @Bean
    GetShopByIdUseCase getShopByIdUseCase() {
        return new GetShopByIdUseCase(shopDao);
    }

    @Bean
    GetShopHintsUseCase getShopHintsUseCase() {
        return new GetShopHintsUseCase(shopSearchDao);
    }

    @Bean
    GetShopByUserUseCase getShopByUserUseCase() {
        return new GetShopByUserUseCase(shopDao);
    }

    @Bean
    GetAllShopUseCase getAllShopUseCase() {
        return new GetAllShopUseCase(shopDao);
    }

    @Bean
    GetShopStatisticUseCase getShopInsightsUseCase() {
        var usecase = new GetShopStatisticUseCase();
        usecase.setShopDao(shopDao);
        return usecase;
    }

    @Bean
    CheckIsShopMemberUseCase checkIsShopMemberUseCase() {
        return new CheckIsShopMemberUseCase(shopMemberDao);
    }

    @Bean
    WriteShopReviewUseCase writeShopReviewUseCase() {
        var usecase = new WriteShopReviewUseCase();
        usecase.setShopDao(shopDao);
        usecase.setShopReviewDao(shopReviewDao);
        usecase.setUserDao(userDao);
        return usecase;
    }

    @Bean
    GetShopReviewByUserUseCase getShopReviewByUserUseCase() {
        return new GetShopReviewByUserUseCase(shopReviewDao);
    }

    @Bean
    GetAllShopReviewUseCase getAllShopReviewUseCase() {
        return new GetAllShopReviewUseCase(shopReviewDao);
    }
    
    @Bean
    GetAllShopAcceptedPaymentUseCase getAllShopAcceptedPaymentUseCase() {
    	return new GetAllShopAcceptedPaymentUseCase(shopAcceptedPaymentDao);
    }
}