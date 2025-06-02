package com.uniminuto.velvet.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.uniminuto.velvet.model.dto.OrderDTO.CreateOrder;
import com.uniminuto.velvet.model.dto.OrderDTO.DetailsOrder;
import com.uniminuto.velvet.model.dto.OrderDTO.SimpleOrder;
import com.uniminuto.velvet.model.dto.OrderDTO.UpdateOrder;
import com.uniminuto.velvet.model.dto.OrderDTO.UpdatePaymentStatus;
import com.uniminuto.velvet.model.dto.OrderDTO.UpdateOrderStatus;  // Corregido: usar la clase correcta
import com.uniminuto.velvet.model.entity.Order;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {OrderDetailMapper.class}
)
public interface OrderMapper {

  OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

  // Convertir CreateOrder DTO a entidad Order
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "orderNumber", ignore = true)
  @Mapping(target = "user.id", source = "userId")
  @Mapping(target = "location.id", source = "locationId")
  @Mapping(target = "table.id", source = "tableId")
  @Mapping(target = "orderStatus.id", source = "orderStatusId")
//  @Mapping(target = "paymentMethod.id", source = "paymentMethodId")
  @Mapping(target = "orderDate", expression = "java(java.time.LocalDateTime.now())")
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "orderDetails", ignore = true)
  @Mapping(target = "paid", source = "paid")  // Mapeo correcto de paid
  Order toEntity(CreateOrder dto);

  // Actualizar entidad Order desde UpdateOrder DTO
  @Mapping(target = "orderNumber", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "location", ignore = true)
  @Mapping(target = "table", ignore = true)
  @Mapping(target = "orderStatus.id", source = "orderStatusId")
  @Mapping(target = "paymentMethod.id", source = "paymentMethodId")
  @Mapping(target = "orderDate", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "paid", source = "paid")  // Mapeo correcto de paid
  void updateOrderFromDto(UpdateOrder dto, @MappingTarget Order order);

  // Convertir entidad Order a DetailsOrder DTO
  @Mapping(target = "locationId", source = "location.id")
  @Mapping(target = "locationName", source = "location.name")
  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "userName", source = "user.name")
  @Mapping(target = "userLastName", source = "user.lastName")
  @Mapping(target = "tableId", source = "table.id")
  @Mapping(target = "tableName", source = "table.number")
  @Mapping(target = "orderStatusName", source = "orderStatus.name")
  @Mapping(target = "paymentMethodName", source = "paymentMethod.name")
  @Mapping(target = "orderDetails", source = "orderDetails")
  @Mapping(target = "paid", source = "paid")  // Mapeo correcto de paid
  DetailsOrder toDetailsDTO(Order entity);

  // Convertir entidad Order a SimpleOrder DTO
  @Mapping(target = "locationId", source = "location.id")
  @Mapping(target = "locationName", source = "location.name")
  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "userName", source = "user.name")
  @Mapping(target = "userLastName", source = "user.lastName")
  @Mapping(target = "orderStatusName", source = "orderStatus.name")
  @Mapping(target = "paid", source = "paid")  // Mapeo correcto de paid
  SimpleOrder toSimpleDTO(Order entity);

  // Convertir lista de Order a lista de SimpleOrder DTO
  List<SimpleOrder> toSimpleDTOList(List<Order> entities);

  // Actualizar estado de la orden
  @Mapping(target = "orderNumber", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "location", ignore = true)
  @Mapping(target = "table", ignore = true)
  @Mapping(target = "orderStatus.id", source = "orderStatusId")  // Corregido: usar orderStatusId
  @Mapping(target = "paymentMethod", ignore = true)
  @Mapping(target = "totalAmount", ignore = true)
  @Mapping(target = "paid", ignore = true)  // Corregido: usar paid
  @Mapping(target = "orderDate", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "orderDetails", ignore = true)
  void updateOrderStatus(UpdateOrderStatus dto, @MappingTarget Order order);

  // Actualizar estado de pago
  @Mapping(target = "orderNumber", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "location", ignore = true)
  @Mapping(target = "table", ignore = true)
  @Mapping(target = "orderStatus", ignore = true)
  @Mapping(target = "paymentMethod.id", source = "paymentMethodId")
  @Mapping(target = "totalAmount", ignore = true)
  @Mapping(target = "orderDate", ignore = true)
  @Mapping(target = "completedDate", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "orderDetails", ignore = true)
  @Mapping(target = "paid", source = "paid")  // Corregido: usar paid
  void updatePaymentStatus(UpdatePaymentStatus dto, @MappingTarget Order order);
}