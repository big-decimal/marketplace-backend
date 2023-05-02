package com.shoppingcenter.app.controller.shop.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopCreateDTO {
	
	@JsonIgnore
    private long userId;

    private String name;

    private String slug;

    private String headline;

    private String about;

    private String address;
    
    private String deliveryNote;
    
    private boolean cashOnDelivery;
    
    private boolean bankTransfer;
    
    private List<ShopAcceptedPaymentDTO> acceptedPayments;
    
    private long subscriptionPlanId;

    private MultipartFile logo;

    private MultipartFile cover;

}
