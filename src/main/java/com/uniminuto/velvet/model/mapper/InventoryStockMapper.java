package com.uniminuto.velvet.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.uniminuto.velvet.model.dto.InventoryStockDTO.CreateInventoryStock;
import com.uniminuto.velvet.model.dto.InventoryStockDTO.DetailsInventoryStock;
import com.uniminuto.velvet.model.dto.InventoryStockDTO.SimpleInventoryStock;
import com.uniminuto.velvet.model.dto.InventoryStockDTO.UpdateInventoryStock;
import com.uniminuto.velvet.model.entity.InventoryStock;
import com.uniminuto.velvet.model.entity.Location;
import com.uniminuto.velvet.model.entity.Product;

@Mapper(componentModel = "spring")
public interface InventoryStockMapper {

  InventoryStockMapper INSTANCE = Mappers.getMapper(InventoryStockMapper.class);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "product", source = "productId", qualifiedByName = "productIdToProduct")
  @Mapping(target = "location", source = "locationId", qualifiedByName = "locationIdToLocation")
  InventoryStock toEntity(CreateInventoryStock createInventoryStockDTO);

  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "product", source = "productId", qualifiedByName = "productIdToProduct")
  @Mapping(target = "location", source = "locationId", qualifiedByName = "locationIdToLocation")
  void updateEntityFromDto(UpdateInventoryStock updateInventoryStockDTO, @MappingTarget InventoryStock inventoryStock);

  @Mapping(target = "productName", source = "product.name")
  @Mapping(target = "locationName", source = "location.name")
  DetailsInventoryStock toDetailsDTO(InventoryStock inventoryStock);

  @Mapping(target = "productName", source = "product.name")
  @Mapping(target = "locationName", source = "location.name")
  SimpleInventoryStock toSimpleDTO(InventoryStock inventoryStock);

  @Named("productIdToProduct")
  default Product productIdToProduct(Long id) {
    if (id == null) {
      return null;
    }
    Product product = new Product();
    product.setId(id);
    return product;
  }

  @Named("locationIdToLocation")
  default Location locationIdToLocation(Long id) {
    if (id == null) {
      return null;
    }
    Location location = new Location();
    location.setId(id);
    return location;
  }
}