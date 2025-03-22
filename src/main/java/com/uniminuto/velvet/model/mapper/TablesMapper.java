package com.uniminuto.velvet.model.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.uniminuto.velvet.model.dto.OrderDTO.SimpleOrder;
import com.uniminuto.velvet.model.dto.TablesDTO;
import com.uniminuto.velvet.model.entity.Location;
import com.uniminuto.velvet.model.entity.Order;
import com.uniminuto.velvet.model.entity.Tables;

@Mapper(componentModel = "spring")
public interface TablesMapper {
  
  // Entity -> SimpleTable DTO
  @Mapping(target = "locationName", source = "location.name")
  TablesDTO.SimpleTable toSimpleDTO(Tables entity);
  
  // Entity -> DetailsTable DTO
  @Mapping(target = "locationName", source = "location.name")
  @Mapping(target = "ordersNumbers", source = "orders", qualifiedByName = "mapOrdersToSimple")
  TablesDTO.DetailsTable toDetailsDTO(Tables entity);
  
  // CreateTable DTO -> Entity
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "location", source = "locationId", qualifiedByName = "mapToLocation")
  @Mapping(target = "orders", ignore = true)
  @Mapping(target = "active", constant = "true")
  Tables toEntity(TablesDTO.CreateTable dto);
  
  // UpdateTable DTO -> Entity update
  @Mapping(target = "location", source = "locationId", qualifiedByName = "mapToLocation")
  @Mapping(target = "orders", ignore = true)
  void updateEntityFromDTO(TablesDTO.UpdateTable dto, @MappingTarget Tables entity);
  
  // Método para mapear locationId a Location
  @Named("mapToLocation")
  default Location mapToLocation(Long locationId) {
    if (locationId == null) {
      return null;
    }
    Location location = new Location();
    location.setId(locationId);
    return location;
  }
  
  // Método para mapear orders a SimpleOrder
  @Named("mapOrdersToSimple")
  default List<SimpleOrder> mapOrdersToSimple(List<Order> orders) {
    if (orders == null) {
      return new ArrayList<>();
    }
    return orders.stream()
      .map(order -> {
        SimpleOrder simpleOrder = new SimpleOrder();
        simpleOrder.setId(order.getId());
        if (order.getOrderNumber() != null) {
          simpleOrder.setOrderNumber(order.getOrderNumber());
        }
        return simpleOrder;
      })
      .collect(Collectors.toList());
  }
}