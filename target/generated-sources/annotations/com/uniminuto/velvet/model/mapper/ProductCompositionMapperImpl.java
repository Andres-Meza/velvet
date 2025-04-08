package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.ProductCompositionDTO;
import com.uniminuto.velvet.model.entity.Product;
import com.uniminuto.velvet.model.entity.ProductComposition;
import com.uniminuto.velvet.model.entity.UnitOfMeasure;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-07T22:03:42-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class ProductCompositionMapperImpl implements ProductCompositionMapper {

    @Override
    public ProductComposition ingredientItemToEntity(Long compositeProductId, ProductCompositionDTO.IngredientItem dto) {
        if ( compositeProductId == null && dto == null ) {
            return null;
        }

        ProductComposition.ProductCompositionBuilder productComposition = ProductComposition.builder();

        if ( dto != null ) {
            productComposition.ingredientProduct( productIdToProduct( dto.getIngredientProductId() ) );
            productComposition.unitOfMeasure( unitOfMeasureIdToUnitOfMeasure( dto.getUnitOfMeasureId() ) );
            productComposition.quantity( dto.getQuantity() );
        }
        productComposition.compositeProduct( productIdToProduct( compositeProductId ) );

        return productComposition.build();
    }

    @Override
    public void updateEntityFromDto(ProductCompositionDTO.UpdateProductComposition dto, ProductComposition entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getUnitOfMeasureId() != null ) {
            entity.setUnitOfMeasure( unitOfMeasureIdToUnitOfMeasure( dto.getUnitOfMeasureId() ) );
        }
        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getQuantity() != null ) {
            entity.setQuantity( dto.getQuantity() );
        }
    }

    @Override
    public ProductCompositionDTO.DetailsProductComposition toDetailsDto(ProductComposition entity) {
        if ( entity == null ) {
            return null;
        }

        ProductCompositionDTO.DetailsProductComposition.DetailsProductCompositionBuilder detailsProductComposition = ProductCompositionDTO.DetailsProductComposition.builder();

        detailsProductComposition.compositeProductId( entityCompositeProductId( entity ) );
        detailsProductComposition.compositeProductName( entityCompositeProductName( entity ) );
        detailsProductComposition.ingredientProductId( entityIngredientProductId( entity ) );
        detailsProductComposition.ingredientProductName( entityIngredientProductName( entity ) );
        detailsProductComposition.unitOfMeasureId( entityUnitOfMeasureId( entity ) );
        detailsProductComposition.unitOfMeasureName( entityUnitOfMeasureName( entity ) );
        detailsProductComposition.unitOfMeasureSymbol( entityUnitOfMeasureSymbol( entity ) );
        detailsProductComposition.id( entity.getId() );
        detailsProductComposition.quantity( entity.getQuantity() );

        return detailsProductComposition.build();
    }

    @Override
    public ProductCompositionDTO.SimpleProductComposition toSimpleDto(ProductComposition entity) {
        if ( entity == null ) {
            return null;
        }

        ProductCompositionDTO.SimpleProductComposition.SimpleProductCompositionBuilder simpleProductComposition = ProductCompositionDTO.SimpleProductComposition.builder();

        simpleProductComposition.ingredientProductId( entityIngredientProductId( entity ) );
        simpleProductComposition.ingredientProductName( entityIngredientProductName( entity ) );
        simpleProductComposition.unitOfMeasureSymbol( entityUnitOfMeasureSymbol( entity ) );
        simpleProductComposition.id( entity.getId() );
        simpleProductComposition.quantity( entity.getQuantity() );

        return simpleProductComposition.build();
    }

    @Override
    public List<ProductCompositionDTO.DetailsProductComposition> toDetailsDtoList(List<ProductComposition> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ProductCompositionDTO.DetailsProductComposition> list = new ArrayList<ProductCompositionDTO.DetailsProductComposition>( entities.size() );
        for ( ProductComposition productComposition : entities ) {
            list.add( toDetailsDto( productComposition ) );
        }

        return list;
    }

    @Override
    public List<ProductCompositionDTO.SimpleProductComposition> toSimpleDtoList(List<ProductComposition> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ProductCompositionDTO.SimpleProductComposition> list = new ArrayList<ProductCompositionDTO.SimpleProductComposition>( entities.size() );
        for ( ProductComposition productComposition : entities ) {
            list.add( toSimpleDto( productComposition ) );
        }

        return list;
    }

    private Long entityCompositeProductId(ProductComposition productComposition) {
        if ( productComposition == null ) {
            return null;
        }
        Product compositeProduct = productComposition.getCompositeProduct();
        if ( compositeProduct == null ) {
            return null;
        }
        Long id = compositeProduct.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityCompositeProductName(ProductComposition productComposition) {
        if ( productComposition == null ) {
            return null;
        }
        Product compositeProduct = productComposition.getCompositeProduct();
        if ( compositeProduct == null ) {
            return null;
        }
        String name = compositeProduct.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long entityIngredientProductId(ProductComposition productComposition) {
        if ( productComposition == null ) {
            return null;
        }
        Product ingredientProduct = productComposition.getIngredientProduct();
        if ( ingredientProduct == null ) {
            return null;
        }
        Long id = ingredientProduct.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityIngredientProductName(ProductComposition productComposition) {
        if ( productComposition == null ) {
            return null;
        }
        Product ingredientProduct = productComposition.getIngredientProduct();
        if ( ingredientProduct == null ) {
            return null;
        }
        String name = ingredientProduct.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long entityUnitOfMeasureId(ProductComposition productComposition) {
        if ( productComposition == null ) {
            return null;
        }
        UnitOfMeasure unitOfMeasure = productComposition.getUnitOfMeasure();
        if ( unitOfMeasure == null ) {
            return null;
        }
        Long id = unitOfMeasure.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityUnitOfMeasureName(ProductComposition productComposition) {
        if ( productComposition == null ) {
            return null;
        }
        UnitOfMeasure unitOfMeasure = productComposition.getUnitOfMeasure();
        if ( unitOfMeasure == null ) {
            return null;
        }
        String name = unitOfMeasure.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String entityUnitOfMeasureSymbol(ProductComposition productComposition) {
        if ( productComposition == null ) {
            return null;
        }
        UnitOfMeasure unitOfMeasure = productComposition.getUnitOfMeasure();
        if ( unitOfMeasure == null ) {
            return null;
        }
        String symbol = unitOfMeasure.getSymbol();
        if ( symbol == null ) {
            return null;
        }
        return symbol;
    }
}
