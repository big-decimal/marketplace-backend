package com.shoppingcenter.service.product.model;

import org.springframework.util.StringUtils;

import com.shoppingcenter.data.product.ProductImageEntity;
import com.shoppingcenter.service.UploadFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImage {

    private long id;

    private long productId;

    private String name;

    private boolean thumbnail;

    private UploadFile file;

    private boolean deleted;

    public static ProductImage create(ProductImageEntity entity, String baseUrl) {
        ProductImage image = new ProductImage();
        image.setId(entity.getId());
        image.setThumbnail(entity.isThumbnail());
        if (StringUtils.hasText(entity.getName())) {
            image.setName(baseUrl + entity.getName());
        }
        return image;
    }
}