package com.uniminuto.velvet.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.uniminuto.velvet.model.dto.ProductTypeDTO;
import com.uniminuto.velvet.model.entity.ProductType;

@Mapper(componentModel = "spring")
public interface ProductTypeMapper {
  
  ProductTypeMapper INSTANCE = Mappers.getMapper(ProductTypeMapper.class);
  
  // Convertir de CreateProductType a ProductType
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "description", ignore = true)
  @Mapping(target = "products", ignore = true)
  ProductType toEntity(ProductTypeDTO.CreateProductType dto);
  
  // Convertir de UpdateProductType a ProductType
  @Mapping(target = "products", ignore = true)
  ProductType toEntity(ProductTypeDTO.UpdateProductType dto);
  
  // Actualizar un ProductType existente con datos de UpdateProductType
  @Mapping(target = "products", ignore = true)
  void updateEntityFromDto(ProductTypeDTO.UpdateProductType dto, @MappingTarget ProductType entity);
  
  // Convertir de ProductType a SimpleProductType
  ProductTypeDTO.SimpleProductType toSimpleDto(ProductType entity);
  
  // Convertir de ProductType a DetailsProductType
  @Mapping(target = "productName", source = "entity", qualifiedByName = "extractProductName")
  ProductTypeDTO.DetailsProductType toDetailsDto(ProductType entity);
  
  // Convertir lista de ProductType a lista de SimpleProductType
  List<ProductTypeDTO.SimpleProductType> toSimpleDtoList(List<ProductType> entities);
  
  // Método auxiliar para extraer nombres de productos
  @Named("extractProductName")
  default String extractProductName(ProductType productType) {
    if (productType == null || productType.getProducts() == null || productType.getProducts().isEmpty()) {
      return null;
    }
    return productType.getProducts().stream()
      .map(product -> product.getName())
      .collect(Collectors.joining(", "));
  }
}