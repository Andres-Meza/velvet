package com.uniminuto.velvet.model.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.uniminuto.velvet.model.dto.InventoryMovementDTO;
import com.uniminuto.velvet.model.entity.InventoryMovement;
import com.uniminuto.velvet.model.entity.InventoryStock;
import com.uniminuto.velvet.model.entity.Order;
import com.uniminuto.velvet.model.entity.Product;
import com.uniminuto.velvet.model.entity.User;

@Mapper(componentModel = "spring", 
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {LocalDateTime.class})
public interface InventoryMovementMapper {

  @Mapping(target = "inventoryStock", source = "inventoryStockId", qualifiedByName = "inventoryStockIdToInventoryStock")
  @Mapping(target = "product", source = "productId", qualifiedByName = "productIdToProduct")
  @Mapping(target = "order", source = "orderId", qualifiedByName = "orderIdToOrder")
  @Mapping(target = "createdBy", source = "createdById", qualifiedByName = "userIdToUser")
  @Mapping(target = "movementDate", expression = "java(LocalDateTime.now())")
  InventoryMovement toEntity(InventoryMovementDTO.CreateInventoryMovement dto);

  @Mapping(target = "movementType", source = "movementType")
  @Mapping(target = "quantity", source = "quantity")
  @Mapping(target = "unitCost", source = "unitCost")
  @Mapping(target = "reference", source = "reference")
  @Mapping(target = "notes", source = "notes")
  @Mapping(target = "order", source = "orderId", qualifiedByName = "orderIdToOrder")
  void updateEntityFromDto(InventoryMovementDTO.UpdateInventoryMovement dto, @MappingTarget InventoryMovement entity);

  @Mapping(target = "inventoryStockId", source = "inventoryStock.id")
  @Mapping(target = "productId", source = "product.id")
  @Mapping(target = "productName", source = "product.name")
  @Mapping(target = "currentStock", source = "inventoryStock.currentStock")
  @Mapping(target = "createdById", source = "createdBy.id")
  @Mapping(target = "createdByUsername", source = "createdBy.username")
  @Mapping(target = "orderId", source = "order.id")
  InventoryMovementDTO.DetailsInventoryMovement toDetailsDto(InventoryMovement entity);

  @Mapping(target = "productId", source = "product.id")
  @Mapping(target = "productName", source = "product.name")
  @Mapping(target = "currentStock", source = "inventoryStock.currentStock")
  InventoryMovementDTO.SimpleInventoryMovement toSimpleDto(InventoryMovement entity);

  List<InventoryMovementDTO.DetailsInventoryMovement> toDetailsDtoList(List<InventoryMovement> entities);
  
  List<InventoryMovementDTO.SimpleInventoryMovement> toSimpleDtoList(List<InventoryMovement> entities);

  @Named("inventoryStockIdToInventoryStock")
  default InventoryStock inventoryStockIdToInventoryStock(Long inventoryStockId) {
    if (inventoryStockId == null) {
      return null;
    }
    InventoryStock inventoryStock = new InventoryStock();
    inventoryStock.setId(inventoryStockId);
    return inventoryStock;
  }

  @Named("productIdToProduct")
  default Product productIdToProduct(Long productId) {
    if (productId == null) {
      return null;
    }
    Product product = new Product();
    product.setId(productId);
    return product;
  }

  @Named("orderIdToOrder")
  default Order orderIdToOrder(Long orderId) {
    if (orderId == null) {
      return null;
    }
    Order order = new Order();
    order.setId(orderId);
    return order;
  }

  @Named("userIdToUser")
  default User userIdToUser(Long userId) {
    if (userId == null) {
      return null;
    }
    User user = new User();
    user.setId(userId);
    return user;
  }
}