package com.shoppingcenter.data.order;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppingcenter.domain.order.Order;

public interface OrderRepo extends JpaRepository<OrderEntity, Long>, JpaSpecificationExecutor<OrderEntity> {
	
	Optional<OrderEntity> findByOrderCode(String orderCode);
	
	boolean existsByOrderCode(String orderCode);

	long countByShopIdAndStatus(long shopId, Order.Status status);
	
	long countByShopId(long shopId);
	
	@Modifying
	@Query("UPDATE Order o SET o.status = :status WHERE o.id = :id")
	void updateStatus(@Param("id") long id, @Param("status") Order.Status status);
	
}