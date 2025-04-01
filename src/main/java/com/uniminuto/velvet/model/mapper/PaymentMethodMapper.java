package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.PaymentMethodDTO;
import com.uniminuto.velvet.model.entity.Order;
import com.uniminuto.velvet.model.entity.PaymentMethod;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PaymentMethodMapper {

  PaymentMethodMapper INSTANCE = Mappers.getMapper(PaymentMethodMapper.class);
  
  // Convierte un DTO de creación a una entidad PaymentMethod
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "orders", ignore = true)
  PaymentMethod toEntity(PaymentMethodDTO.CreatePaymentMethod createPaymentMethod);
  
  // Actualiza una entidad PaymentMethod existente con los datos de un DTO de actualización
  @Mapping(target = "orders", ignore = true)
  void updateEntityFromDto(PaymentMethodDTO.UpdatePaymentMethod updatePaymentMethod, @MappingTarget PaymentMethod paymentMethod);
  
  // Convierte una entidad PaymentMethod a un DTO SimplePaymentMethod
  PaymentMethodDTO.SimplePaymentMethod toSimpleDto(PaymentMethod paymentMethod);
  
  // Convierte una entidad PaymentMethod a un DTO DetailsPaymentMethod
  @Mapping(target = "orders", source = "orders", qualifiedByName = "orderSetToStringSet")
  PaymentMethodDTO.DetailsPaymentMethod toDetailsDto(PaymentMethod paymentMethod);

  List<PaymentMethodDTO.SimplePaymentMethod> toSimpleDtoList(List<PaymentMethod> entities);

  // Convertir lista de PaymentMethod a lista de DetailsPaymentMethod
  List<PaymentMethodDTO.DetailsPaymentMethod> toDetailsDtoList(List<PaymentMethod> entities);

  // Método auxiliar para convertir un conjunto de órdenes a un conjunto de cadenas
  @Named("orderSetToStringSet")
  default Set<String> orderSetToStringSet(Set<Order> orders) {
    if (orders == null) {
      return Set.of();
    }
    return orders.stream()
      .map(order -> String.valueOf(order.getId()))
      .collect(Collectors.toSet());
  }
}