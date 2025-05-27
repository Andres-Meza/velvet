package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.ProductTypeDTO;
import com.uniminuto.velvet.model.entity.ProductType;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-26T21:15:42-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class ProductTypeMapperImpl implements ProductTypeMapper {

    @Override
    public ProductType toEntity(ProductTypeDTO.CreateProductType dto) {
        if ( dto == null ) {
            return null;
        }

        ProductType.ProductTypeBuilder productType = ProductType.builder();

        productType.name( dto.getName() );
        productType.description( dto.getDescription() );

        return productType.build();
    }

    @Override
    public ProductType toEntity(ProductTypeDTO.UpdateProductType dto) {
        if ( dto == null ) {
            return null;
        }

        ProductType.ProductTypeBuilder productType = ProductType.builder();

        productType.id( dto.getId() );
        productType.name( dto.getName() );
        productType.description( dto.getDescription() );

        return productType.build();
    }

    @Override
    public void updateEntityFromDto(ProductTypeDTO.UpdateProductType dto, ProductType entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setName( dto.getName() );
        entity.setDescription( dto.getDescription() );
    }

    @Override
    public ProductTypeDTO.SimpleProductType toSimpleDto(ProductType entity) {
        if ( entity == null ) {
            return null;
        }

        ProductTypeDTO.SimpleProductType.SimpleProductTypeBuilder simpleProductType = ProductTypeDTO.SimpleProductType.builder();

        simpleProductType.id( entity.getId() );
        simpleProductType.name( entity.getName() );
        simpleProductType.description( entity.getDescription() );

        return simpleProductType.build();
    }

    @Override
    public ProductTypeDTO.DetailsProductType toDetailsDto(ProductType entity) {
        if ( entity == null ) {
            return null;
        }

        ProductTypeDTO.DetailsProductType.DetailsProductTypeBuilder detailsProductType = ProductTypeDTO.DetailsProductType.builder();

        detailsProductType.productName( extractProductName( entity ) );
        detailsProductType.id( entity.getId() );
        detailsProductType.name( entity.getName() );
        detailsProductType.description( entity.getDescription() );

        return detailsProductType.build();
    }

    @Override
    public List<ProductTypeDTO.DetailsProductType> toDetailsDtoList(List<ProductType> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ProductTypeDTO.DetailsProductType> list = new ArrayList<ProductTypeDTO.DetailsProductType>( entities.size() );
        for ( ProductType productType : entities ) {
            list.add( toDetailsDto( productType ) );
        }

        return list;
    }

    @Override
    public List<ProductTypeDTO.SimpleProductType> toSimpleDtoList(List<ProductType> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ProductTypeDTO.SimpleProductType> list = new ArrayList<ProductTypeDTO.SimpleProductType>( entities.size() );
        for ( ProductType productType : entities ) {
            list.add( toSimpleDto( productType ) );
        }

        return list;
    }
}
