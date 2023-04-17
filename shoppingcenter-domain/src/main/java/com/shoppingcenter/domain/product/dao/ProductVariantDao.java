package com.shoppingcenter.domain.product.dao;

import java.util.List;

import com.shoppingcenter.domain.product.ProductVariant;

public interface ProductVariantDao {

    void saveAll(List<ProductVariant> list);

    void deleteAll(List<Long> list);

    boolean exists(long id);

}
