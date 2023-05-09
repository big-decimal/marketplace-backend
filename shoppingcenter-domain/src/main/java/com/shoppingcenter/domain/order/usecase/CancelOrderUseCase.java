package com.shoppingcenter.domain.order.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.order.Order.Status;
import com.shoppingcenter.domain.order.dao.OrderDao;
import com.shoppingcenter.domain.shop.dao.ShopMemberDao;

import lombok.Setter;

@Setter
public class CancelOrderUseCase {

	private OrderDao orderDao;
	
	private ShopMemberDao shopMemberDao;

	public void apply(long userId, long orderId) {
		var order = orderDao.findById(orderId);

		if (order == null) {
			throw new ApplicationException("Order not found");
		}
		
		var seller = shopMemberDao.existsByShopAndUser(order.getShop().getId(), userId);
		
		var buyer = order.getUser().getId() == userId;

		if (!seller && !buyer) {
			throw new ApplicationException("Order not found");
		}
		
		if (order.getStatus() == Status.COMPLETED) {
			throw new ApplicationException("You cannot cancel completed order");
		}
		
		if (order.getStatus() == Status.CONFIRMED && buyer) {
			throw new ApplicationException("You cannot cancel confirmed order");
		}

		orderDao.updateStatus(orderId, Status.CANCELLED);
	}

}
