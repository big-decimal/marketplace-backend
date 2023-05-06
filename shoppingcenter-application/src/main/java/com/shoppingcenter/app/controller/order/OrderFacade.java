package com.shoppingcenter.app.controller.order;

import org.hibernate.StaleObjectStateException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.order.dto.OrderCreateDTO;
import com.shoppingcenter.app.controller.order.dto.OrderDTO;
import com.shoppingcenter.domain.order.CreateOrderInput;
import com.shoppingcenter.domain.order.Order;
import com.shoppingcenter.domain.order.OrderQuery;
import com.shoppingcenter.domain.order.usecase.CancelOrderUseCase;
import com.shoppingcenter.domain.order.usecase.CreateOrderUseCase;
import com.shoppingcenter.domain.order.usecase.GetAllOrderByQueryUseCase;
import com.shoppingcenter.domain.order.usecase.GetOrderByCodeUseCase;
import com.shoppingcenter.domain.order.usecase.MarkOrderItemAsRemovedUseCase;
import com.shoppingcenter.domain.order.usecase.UpdateOrderStatusUseCase;

@Facade
public class OrderFacade {
	
	@Autowired
	private CreateOrderUseCase createOrderUseCase;
	
	@Autowired
	private UpdateOrderStatusUseCase updateOrderStatusUseCase;
	
	@Autowired
	private MarkOrderItemAsRemovedUseCase markOrderItemAsRemovedUseCase;
	
	@Autowired
	private CancelOrderUseCase cancelOrderUseCase;
	
	@Autowired
	private GetAllOrderByQueryUseCase getAllOrderByQueryUseCase;
	
	@Autowired
	private GetOrderByCodeUseCase getOrderByCodeUseCase;
	
	@Autowired
	private ModelMapper modelMapper;

	@Retryable(retryFor = { StaleObjectStateException.class })
	@Transactional
	public String createOrder(OrderCreateDTO data) {
		return createOrderUseCase.apply(modelMapper.map(data, CreateOrderInput.class));
	}
	
	@Retryable(retryFor = { StaleObjectStateException.class })
	@Transactional
	public void updateOrderStatus(long userId, long orderId, Order.Status status) {
		updateOrderStatusUseCase.apply(userId, orderId, status);
	}
	
	@Transactional
	public void removeOrderItem(long itemId) {
    	markOrderItemAsRemovedUseCase.apply(itemId);
    }
	
	public void cancelOrder(long userId, long orderId) {
		cancelOrderUseCase.apply(userId, orderId);
	}
	
	@Transactional(readOnly = true)
	public OrderDTO getOrderByCode(String code) {
		var result = getOrderByCodeUseCase.apply(code);
		
		return result != null ? modelMapper.map(result, OrderDTO.class) : null;
	}
	
	@Transactional(readOnly = true)
	public PageDataDTO<OrderDTO> getOrders(OrderQuery query) {
		return modelMapper.map(getAllOrderByQueryUseCase.apply(query), OrderDTO.pageType());
	}
}