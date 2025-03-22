package com.uniminuto.velvet.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.uniminuto.velvet.model.dto.ProductCompositionDTO;
import com.uniminuto.velvet.model.entity.Product;
import com.uniminuto.velvet.model.entity.ProductComposition;
import com.uniminuto.velvet.model.entity.UnitOfMeasure;

@Mapper(componentModel = "spring", 
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductCompositionMapper {

  ProductComposition toEntity(ProductCompositionDTO.CreateProductComposition dto);

  void updateEntityFromDto(ProductCompositionDTO.UpdateProductComposition dto, @MappingTarget ProductComposition entity);

  @Mapping(target = "compositeProductId", source = "compositeProduct.id")
  @Mapping(target = "compositeProductName", source = "compositeProduct.name")
  @Mapping(target = "ingredientProductId", source = "ingredientProduct.id")
  @Mapping(target = "ingredientProductName", source = "ingredientProduct.name")
  @Mapping(target = "unitOfMeasureId", source = "unitOfMeasure.id")
  @Mapping(target = "unitOfMeasureName", source = "unitOfMeasure.name")
  @Mapping(target = "unitOfMeasureSymbol", source = "unitOfMeasure.symbol")
  ProductCompositionDTO.DetailsProductComposition toDetailsDto(ProductComposition entity);

  @Mapping(target = "ingredientProductId", source = "ingredientProduct.id")
  @Mapping(target = "ingredientProductName", source = "ingredientProduct.name")
  @Mapping(target = "unitOfMeasureSymbol", source = "unitOfMeasure.symbol")
  ProductCompositionDTO.SimpleProductComposition toSimpleDto(ProductComposition entity);

  List<ProductCompositionDTO.DetailsProductComposition> toDetailsDtoList(List<ProductComposition> entities);
  
  List<ProductCompositionDTO.SimpleProductComposition> toSimpleDtoList(List<ProductComposition> entities);

  @Named("productIdToProduct")
  default Product productIdToProduct(Long productId) {
    if (productId == null) {
      return null;
    }
    Product product = new Product();
    product.setId(productId);
    return product;
  }

  @Named("unitOfMeasureIdToUnitOfMeasure")
  default UnitOfMeasure unitOfMeasureIdToUnitOfMeasure(Long unitOfMeasureId) {
    if (unitOfMeasureId == null) {
      return null;
    }
    UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
    unitOfMeasure.setId(unitOfMeasureId);
    return unitOfMeasure;
  }
}