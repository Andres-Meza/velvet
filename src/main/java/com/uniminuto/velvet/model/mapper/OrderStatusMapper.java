package com.uniminuto.velvet.model.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.uniminuto.velvet.model.dto.OrderStatusDTO;
import com.uniminuto.velvet.model.entity.Order;
import com.uniminuto.velvet.model.entity.OrderStatus;

@Mapper(componentModel = "spring")
public interface OrderStatusMapper {

  OrderStatusMapper INSTANCE = Mappers.getMapper(OrderStatusMapper.class);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "orders", ignore = true)
  OrderStatus toEntity(OrderStatusDTO.CreateOrderStatus createOrderStatusDTO);

  @Mapping(target = "orders", ignore = true)
  void updateEntityFromDto(OrderStatusDTO.UpdateOrderStatus updateOrderStatusDTO, @MappingTarget OrderStatus orderStatus);

  @Mapping(target = "orders", expression = "java(mapOrdersToStrings(orderStatus.getOrders()))")
  OrderStatusDTO.DetailsOrderStatus toDetailsDTO(OrderStatus orderStatus);

  List<OrderStatusDTO.DetailsOrderStatus> toDetailsDTOList(List<OrderStatus> orderStatuses);

  default Set<String> mapOrdersToStrings(Set<Order> orders) {
    if (orders == null) {
      return java.util.Collections.emptySet();
    }
    
    return orders.stream()
      .map(order -> {
        return "Orden # " + order.getOrderNumber();
      })
      .collect(Collectors.toSet());
  }
}