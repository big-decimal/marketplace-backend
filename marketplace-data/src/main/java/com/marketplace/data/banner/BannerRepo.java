package com.marketplace.data.banner;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BannerRepo extends JpaRepository<BannerEntity, Long> {

    <T> Optional<T> getBannerById(long id, Class<T> type);
    
    @Modifying
	@Query("UPDATE Banner b SET b.image = :image WHERE b.id = :id")
	void updateImage(@Param("id") long id, @Param("image") String image);

}
