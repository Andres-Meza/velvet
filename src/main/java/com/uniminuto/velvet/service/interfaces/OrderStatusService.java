
package com.uniminuto.velvet.service.interfaces;

import com.uniminuto.velvet.model.dto.OrderStatusDTO;

import java.util.List;

public interface OrderStatusService {
	OrderStatusDTO.DetailsOrderStatus createOrderStatus(OrderStatusDTO.CreateOrderStatus createOrderStatus);
	OrderStatusDTO.DetailsOrderStatus updateOrderStatus(OrderStatusDTO.UpdateOrderStatus updateOrderStatus);
	OrderStatusDTO.DetailsOrderStatus getOrderStatusById(Long id);
	List<OrderStatusDTO.DetailsOrderStatus> getAllOrderStatuses();
	boolean deleteOrderStatus(Long id);
	boolean existsByName(String name);
}