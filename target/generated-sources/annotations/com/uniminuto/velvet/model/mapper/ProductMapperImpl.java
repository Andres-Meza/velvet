package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.ProductDTO;
import com.uniminuto.velvet.model.entity.Product;
import com.uniminuto.velvet.model.entity.ProductType;
import com.uniminuto.velvet.model.entity.SubCategory;
import com.uniminuto.velvet.model.entity.UnitOfMeasure;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T13:54:52-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toEntity(ProductDTO.CreateProduct createProductDTO) {
        if ( createProductDTO == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.productType( productTypeIdToProductType( createProductDTO.getProductTypeId() ) );
        product.subCategory( subCategoryIdToSubCategory( createProductDTO.getSubCategoryId() ) );
        product.unitOfMeasure( unitOfMeasureIdToUnitOfMeasure( createProductDTO.getUnitOfMeasureId() ) );
        product.name( createProductDTO.getName() );
        product.description( createProductDTO.getDescription() );
        product.purchasePrice( createProductDTO.getPurchasePrice() );
        product.salePrice( createProductDTO.getSalePrice() );

        return product.build();
    }

    @Override
    public void updateEntityFromDto(ProductDTO.UpdateProduct updateProductDTO, Product product) {
        if ( updateProductDTO == null ) {
            return;
        }

        product.setProductType( productTypeIdToProductType( updateProductDTO.getProductTypeId() ) );
        product.setSubCategory( subCategoryIdToSubCategory( updateProductDTO.getSubCategoryId() ) );
        product.setUnitOfMeasure( unitOfMeasureIdToUnitOfMeasure( updateProductDTO.getUnitOfMeasureId() ) );
        product.setId( updateProductDTO.getId() );
        product.setName( updateProductDTO.getName() );
        product.setDescription( updateProductDTO.getDescription() );
        product.setPurchasePrice( updateProductDTO.getPurchasePrice() );
        product.setSalePrice( updateProductDTO.getSalePrice() );
        product.setActive( updateProductDTO.getActive() );
    }

    @Override
    public ProductDTO.DetailsProduct toDetailsDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDTO.DetailsProduct.DetailsProductBuilder detailsProduct = ProductDTO.DetailsProduct.builder();

        detailsProduct.productTypeName( productProductTypeName( product ) );
        detailsProduct.subCategoryName( productSubCategoryName( product ) );
        detailsProduct.unitOfMeasureName( productUnitOfMeasureName( product ) );
        detailsProduct.id( product.getId() );
        detailsProduct.name( product.getName() );
        detailsProduct.description( product.getDescription() );
        detailsProduct.purchasePrice( product.getPurchasePrice() );
        detailsProduct.salePrice( product.getSalePrice() );
        detailsProduct.active( product.getActive() );
        detailsProduct.createdAt( product.getCreatedAt() );
        detailsProduct.updatedAt( product.getUpdatedAt() );

        detailsProduct.categoryName( product.getCategory() != null ? product.getCategory().getName() : null );

        return detailsProduct.build();
    }

    @Override
    public ProductDTO.SimpleProduct toSimpleDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDTO.SimpleProduct.SimpleProductBuilder simpleProduct = ProductDTO.SimpleProduct.builder();

        simpleProduct.productTypeName( productProductTypeName( product ) );
        simpleProduct.subCategoryName( productSubCategoryName( product ) );
        simpleProduct.id( product.getId() );
        simpleProduct.name( product.getName() );
        simpleProduct.salePrice( product.getSalePrice() );
        simpleProduct.active( product.getActive() );

        simpleProduct.categoryName( product.getCategory() != null ? product.getCategory().getName() : null );

        return simpleProduct.build();
    }

    private String productProductTypeName(Product product) {
        if ( product == null ) {
            return null;
        }
        ProductType productType = product.getProductType();
        if ( productType == null ) {
            return null;
        }
        String name = productType.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String productSubCategoryName(Product product) {
        if ( product == null ) {
            return null;
        }
        SubCategory subCategory = product.getSubCategory();
        if ( subCategory == null ) {
            return null;
        }
        String name = subCategory.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String productUnitOfMeasureName(Product product) {
        if ( product == null ) {
            return null;
        }
        UnitOfMeasure unitOfMeasure = product.getUnitOfMeasure();
        if ( unitOfMeasure == null ) {
            return null;
        }
        String name = unitOfMeasure.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
