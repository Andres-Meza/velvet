package com.uniminuto.velvet.model.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.uniminuto.velvet.model.dto.OrderDetailDTO.CreateOrderDetail;
import com.uniminuto.velvet.model.dto.OrderDetailDTO.OrderDetailResponse;
import com.uniminuto.velvet.model.dto.OrderDetailDTO.OrderDetailsByProduct;
import com.uniminuto.velvet.model.dto.OrderDetailDTO.SimpleOrderDetail;
import com.uniminuto.velvet.model.dto.OrderDetailDTO.UpdateOrderDetail;
import com.uniminuto.velvet.model.entity.OrderDetail;

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface OrderDetailMapper {

  OrderDetailMapper INSTANCE = Mappers.getMapper(OrderDetailMapper.class);

  // Convertir CreateOrderDetail DTO a entidad OrderDetail
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "order.id", source = "orderId")
  @Mapping(target = "product.id", source = "productId")
  OrderDetail toEntity(CreateOrderDetail dto);

  // Método para calcular el subtotal después del mapeo
  @AfterMapping
  default void calculateSubtotal(@MappingTarget OrderDetail orderDetail) {
    if (orderDetail.getQuantity() != null && orderDetail.getUnitPrice() != null) {
      orderDetail.setSubtotal(
        orderDetail.getUnitPrice().multiply(BigDecimal.valueOf(orderDetail.getQuantity()))
      );
    }
  }

  // Actualizar entidad OrderDetail desde UpdateOrderDetail DTO
  @Mapping(target = "order.id", source = "orderId")
  @Mapping(target = "product.id", source = "productId")
  void updateFromDto(UpdateOrderDetail dto, @MappingTarget OrderDetail entity);

  // Convertir OrderDetail a OrderDetailResponse
  @Mapping(target = "orderId", source = "order.id")
  @Mapping(target = "orderNumber", source = "order.orderNumber")
  @Mapping(target = "productId", source = "product.id")
  @Mapping(target = "productName", source = "product.name")
  OrderDetailResponse toDetailResponse(OrderDetail entity);

  // Convertir OrderDetail a SimpleOrderDetail
  @Mapping(target = "orderId", source = "order.id")
  @Mapping(target = "orderNumber", source = "order.orderNumber")
  @Mapping(target = "productId", source = "product.id")
  @Mapping(target = "productName", source = "product.name")
  SimpleOrderDetail toSimpleDetail(OrderDetail entity);

  // Convertir lista de OrderDetail a lista de OrderDetailResponse
  List<OrderDetailResponse> toDetailResponseList(List<OrderDetail> entities);

  // Convertir lista de OrderDetail a lista de SimpleOrderDetail
  List<SimpleOrderDetail> toSimpleDetailList(List<OrderDetail> entities);

  // Método para mapear a OrderDetailsByProduct (para reportes/estadísticas)
  @Mapping(target = "productId", source = "product.id")
  @Mapping(target = "productName", source = "product.name")
  @Mapping(target = "totalOrders", ignore = true)
  @Mapping(target = "totalQuantity", source = "quantity")
  @Mapping(target = "totalRevenue", source = "subtotal")
  OrderDetailsByProduct toOrderDetailsByProduct(OrderDetail entity);
  
  // Para conversiones personalizadas que requieran lógica compleja, 
  // se pueden definir métodos default con la implementación específica
  default OrderDetailsByProduct toOrderDetailsByProductWithAggregation(
      List<OrderDetail> orderDetails, 
      Long productId, 
      String productName) {
    
    Long totalOrders = (long) orderDetails.size();
    Integer totalQuantity = orderDetails.stream()
        .mapToInt(OrderDetail::getQuantity)
        .sum();
    BigDecimal totalRevenue = orderDetails.stream()
        .map(OrderDetail::getSubtotal)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    
    return OrderDetailsByProduct.builder()
        .productId(productId)
        .productName(productName)
        .totalOrders(totalOrders)
        .totalQuantity(totalQuantity)
        .totalRevenue(totalRevenue)
        .build();
  }
}