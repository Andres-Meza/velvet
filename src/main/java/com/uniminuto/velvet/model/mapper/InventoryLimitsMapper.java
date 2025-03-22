package com.uniminuto.velvet.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import com.uniminuto.velvet.model.dto.InventoryLimitsDTO;
import com.uniminuto.velvet.model.entity.InventoryLimits;
import com.uniminuto.velvet.model.entity.Location;
import com.uniminuto.velvet.model.entity.Product;

@Mapper(
  componentModel = "spring", 
  unmappedTargetPolicy = ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  imports = {java.util.Collections.class}
)
@Component
public interface InventoryLimitsMapper {

  @Mapping(target = "product", source = "productId", qualifiedByName = "productIdToProduct")
  @Mapping(target = "location", source = "locationId", qualifiedByName = "locationIdToLocation")
  InventoryLimits toEntity(InventoryLimitsDTO.CreateInventoryLimits dto);

  @Mapping(target = "minStock", source = "minStock")
  @Mapping(target = "maxStock", source = "maxStock")
  void updateEntityFromDto(InventoryLimitsDTO.UpdateInventoryLimits dto, @MappingTarget InventoryLimits entity);

  @Mapping(target = "productId", source = "product.id")
  @Mapping(target = "productName", source = "product.name")
  @Mapping(target = "locationId", source = "location.id")
  @Mapping(target = "locationName", source = "location.name")
  InventoryLimitsDTO.DetailsInventoryLimits toDetailsDto(InventoryLimits entity);

  @Mapping(target = "productName", source = "product.name")
  @Mapping(target = "locationName", source = "location.name")
  InventoryLimitsDTO.SimpleInventoryLimits toSimpleDto(InventoryLimits entity);

  List<InventoryLimitsDTO.DetailsInventoryLimits> toDetailsDtoList(List<InventoryLimits> entities);
  
  List<InventoryLimitsDTO.SimpleInventoryLimits> toSimpleDtoList(List<InventoryLimits> entities);

  @Named("productIdToProduct")
  default Product productIdToProduct(Long productId) {
    if (productId == null) {
      return null;
    }
    Product product = new Product();
    product.setId(productId);
    return product;
  }

  @Named("locationIdToLocation")
  default Location locationIdToLocation(Long locationId) {
    if (locationId == null) {
      return null;
    }
    Location location = new Location();
    location.setId(locationId);
    return location;
  }
}