package com.uniminuto.velvet.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

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

  /**
   * Convierte un IngredientItem a una entidad ProductComposition
   */
  @Mapping(target = "compositeProduct", source = "compositeProductId", qualifiedByName = "productIdToProduct")
  @Mapping(target = "ingredientProduct", source = "dto.ingredientProductId", qualifiedByName = "productIdToProduct")
  @Mapping(target = "unitOfMeasure", source = "dto.unitOfMeasureId", qualifiedByName = "unitOfMeasureIdToUnitOfMeasure")
  @Mapping(target = "quantity", source = "dto.quantity")
  ProductComposition ingredientItemToEntity(Long compositeProductId, ProductCompositionDTO.IngredientItem dto);

  /**
   * Método para actualizar una entidad existente desde un DTO de actualización
   */
  @Mapping(target = "unitOfMeasure", source = "unitOfMeasureId", qualifiedByName = "unitOfMeasureIdToUnitOfMeasure")
  void updateEntityFromDto(ProductCompositionDTO.UpdateProductComposition dto, @MappingTarget ProductComposition entity);

  /**
   * Convierte una entidad a un DTO detallado
   */
  @Mapping(target = "compositeProductId", source = "compositeProduct.id")
  @Mapping(target = "compositeProductName", source = "compositeProduct.name")
  @Mapping(target = "ingredientProductId", source = "ingredientProduct.id")
  @Mapping(target = "ingredientProductName", source = "ingredientProduct.name")
  @Mapping(target = "unitOfMeasureId", source = "unitOfMeasure.id")
  @Mapping(target = "unitOfMeasureName", source = "unitOfMeasure.name")
  @Mapping(target = "unitOfMeasureSymbol", source = "unitOfMeasure.symbol")
  ProductCompositionDTO.DetailsProductComposition toDetailsDto(ProductComposition entity);

  /**
   * Convierte una entidad a un DTO simple
   */
  @Mapping(target = "ingredientProductId", source = "ingredientProduct.id")
  @Mapping(target = "ingredientProductName", source = "ingredientProduct.name")
  @Mapping(target = "unitOfMeasureSymbol", source = "unitOfMeasure.symbol")
  ProductCompositionDTO.SimpleProductComposition toSimpleDto(ProductComposition entity);

  /**
   * Convierte una lista de entidades a una lista de DTOs detallados
   */
  List<ProductCompositionDTO.DetailsProductComposition> toDetailsDtoList(List<ProductComposition> entities);

  /**
   * Convierte una lista de entidades a una lista de DTOs simples
   */
  List<ProductCompositionDTO.SimpleProductComposition> toSimpleDtoList(List<ProductComposition> entities);

  /**
   * Método default para convertir un CreateCompositeProduct a una lista de entidades ProductComposition
   */
  default List<ProductComposition> toEntities(ProductCompositionDTO.CreateCompositeProduct dto) {
    if (dto == null || dto.getIngredients() == null) {
      return null;
    }

    return dto.getIngredients().stream()
            .map(ingredient -> ingredientItemToEntity(dto.getCompositeProductId(), ingredient))
            .collect(Collectors.toList());
  }

  /**
   * Método de utilidad para convertir un ID de producto a una entidad Product
   */
  @Named("productIdToProduct")
  default Product productIdToProduct(Long productId) {
    if (productId == null) {
      return null;
    }
    Product product = new Product();
    product.setId(productId);
    return product;
  }

  /**
   * Método de utilidad para convertir un ID de unidad de medida a una entidad UnitOfMeasure
   */
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