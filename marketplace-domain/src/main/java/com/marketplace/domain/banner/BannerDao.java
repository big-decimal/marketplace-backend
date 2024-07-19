package com.marketplace.domain.banner;

import java.util.List;

import com.marketplace.domain.common.SortQuery;

public interface BannerDao {

    Banner save(BannerInput values);

    void updateImage(long id, String image);

    void delete(long id);

    boolean existsById(long id);

    String getBannerImage(long id);

    Banner findById(long id);

    List<Banner> findAll(SortQuery sort);
}
