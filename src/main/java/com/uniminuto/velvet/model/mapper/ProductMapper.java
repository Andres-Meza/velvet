package com.uniminuto.velvet.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.uniminuto.velvet.model.dto.ProductDTO.CreateProduct;
import com.uniminuto.velvet.model.dto.ProductDTO.DetailsProduct;
import com.uniminuto.velvet.model.dto.ProductDTO.SimpleProduct;
import com.uniminuto.velvet.model.dto.ProductDTO.UpdateProduct;
import com.uniminuto.velvet.model.entity.Product;
import com.uniminuto.velvet.model.entity.ProductType;
import com.uniminuto.velvet.model.entity.SubCategory;
import com.uniminuto.velvet.model.entity.UnitOfMeasure;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "active", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "inventoryStocks", ignore = true)
  @Mapping(target = "orderDetails", ignore = true)
  @Mapping(target = "components", ignore = true)
  @Mapping(target = "usedIn", ignore = true)
  @Mapping(target = "productType", source = "productTypeId", qualifiedByName = "productTypeIdToProductType")
  @Mapping(target = "subCategory", source = "subCategoryId", qualifiedByName = "subCategoryIdToSubCategory")
  @Mapping(target = "unitOfMeasure", source = "unitOfMeasureId", qualifiedByName = "unitOfMeasureIdToUnitOfMeasure")
  Product toEntity(CreateProduct createProductDTO);

  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "inventoryStocks", ignore = true)
  @Mapping(target = "orderDetails", ignore = true)
  @Mapping(target = "components", ignore = true)
  @Mapping(target = "usedIn", ignore = true)
  @Mapping(target = "productType", source = "productTypeId", qualifiedByName = "productTypeIdToProductType")
  @Mapping(target = "subCategory", source = "subCategoryId", qualifiedByName = "subCategoryIdToSubCategory")
  @Mapping(target = "unitOfMeasure", source = "unitOfMeasureId", qualifiedByName = "unitOfMeasureIdToUnitOfMeasure")
  void updateEntityFromDto(UpdateProduct updateProductDTO, @MappingTarget Product product);

  @Mapping(target = "productTypeName", source = "productType.name")
  @Mapping(target = "subCategoryName", source = "subCategory.name")
  @Mapping(target = "categoryName", expression = "java(product.getCategory() != null ? product.getCategory().getName() : null)")
  @Mapping(target = "unitOfMeasureName", source = "unitOfMeasure.name")
  DetailsProduct toDetailsDTO(Product product);

  @Mapping(target = "productTypeName", source = "productType.name")
  @Mapping(target = "subCategoryName", source = "subCategory.name")
  @Mapping(target = "categoryName", expression = "java(product.getCategory() != null ? product.getCategory().getName() : null)")
  SimpleProduct toSimpleDTO(Product product);

  @Named("productTypeIdToProductType")
  default ProductType productTypeIdToProductType(Long id) {
    if (id == null) {
      return null;
    }
    ProductType productType = new ProductType();
    productType.setId(id);
    return productType;
  }

  @Named("subCategoryIdToSubCategory")
  default SubCategory subCategoryIdToSubCategory(Long id) {
    if (id == null) {
      return null;
    }
    SubCategory subCategory = new SubCategory();
    subCategory.setId(id);
    return subCategory;
  }

  @Named("unitOfMeasureIdToUnitOfMeasure")
  default UnitOfMeasure unitOfMeasureIdToUnitOfMeasure(Long id) {
    if (id == null) {
      return null;
    }
    UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
    unitOfMeasure.setId(id);
    return unitOfMeasure;
  }
}