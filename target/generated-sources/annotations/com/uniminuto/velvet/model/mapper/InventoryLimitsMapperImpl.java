package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.InventoryLimitsDTO;
import com.uniminuto.velvet.model.entity.InventoryLimits;
import com.uniminuto.velvet.model.entity.Location;
import com.uniminuto.velvet.model.entity.Product;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-26T21:15:43-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class InventoryLimitsMapperImpl implements InventoryLimitsMapper {

    @Override
    public InventoryLimits toEntity(InventoryLimitsDTO.CreateInventoryLimits dto) {
        if ( dto == null ) {
            return null;
        }

        InventoryLimits.InventoryLimitsBuilder inventoryLimits = InventoryLimits.builder();

        inventoryLimits.product( productIdToProduct( dto.getProductId() ) );
        inventoryLimits.location( locationIdToLocation( dto.getLocationId() ) );
        inventoryLimits.minStock( dto.getMinStock() );
        inventoryLimits.maxStock( dto.getMaxStock() );

        return inventoryLimits.build();
    }

    @Override
    public void updateEntityFromDto(InventoryLimitsDTO.UpdateInventoryLimits dto, InventoryLimits entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getMinStock() != null ) {
            entity.setMinStock( dto.getMinStock() );
        }
        if ( dto.getMaxStock() != null ) {
            entity.setMaxStock( dto.getMaxStock() );
        }
        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
    }

    @Override
    public InventoryLimitsDTO.DetailsInventoryLimits toDetailsDto(InventoryLimits entity) {
        if ( entity == null ) {
            return null;
        }

        InventoryLimitsDTO.DetailsInventoryLimits.DetailsInventoryLimitsBuilder detailsInventoryLimits = InventoryLimitsDTO.DetailsInventoryLimits.builder();

        detailsInventoryLimits.productId( entityProductId( entity ) );
        detailsInventoryLimits.productName( entityProductName( entity ) );
        detailsInventoryLimits.locationId( entityLocationId( entity ) );
        detailsInventoryLimits.locationName( entityLocationName( entity ) );
        detailsInventoryLimits.id( entity.getId() );
        detailsInventoryLimits.minStock( entity.getMinStock() );
        detailsInventoryLimits.maxStock( entity.getMaxStock() );

        return detailsInventoryLimits.build();
    }

    @Override
    public InventoryLimitsDTO.SimpleInventoryLimits toSimpleDto(InventoryLimits entity) {
        if ( entity == null ) {
            return null;
        }

        InventoryLimitsDTO.SimpleInventoryLimits.SimpleInventoryLimitsBuilder simpleInventoryLimits = InventoryLimitsDTO.SimpleInventoryLimits.builder();

        simpleInventoryLimits.productName( entityProductName( entity ) );
        simpleInventoryLimits.locationName( entityLocationName( entity ) );
        simpleInventoryLimits.id( entity.getId() );
        simpleInventoryLimits.minStock( entity.getMinStock() );
        simpleInventoryLimits.maxStock( entity.getMaxStock() );

        return simpleInventoryLimits.build();
    }

    @Override
    public List<InventoryLimitsDTO.DetailsInventoryLimits> toDetailsDtoList(List<InventoryLimits> entities) {
        if ( entities == null ) {
            return null;
        }

        List<InventoryLimitsDTO.DetailsInventoryLimits> list = new ArrayList<InventoryLimitsDTO.DetailsInventoryLimits>( entities.size() );
        for ( InventoryLimits inventoryLimits : entities ) {
            list.add( toDetailsDto( inventoryLimits ) );
        }

        return list;
    }

    @Override
    public List<InventoryLimitsDTO.SimpleInventoryLimits> toSimpleDtoList(List<InventoryLimits> entities) {
        if ( entities == null ) {
            return null;
        }

        List<InventoryLimitsDTO.SimpleInventoryLimits> list = new ArrayList<InventoryLimitsDTO.SimpleInventoryLimits>( entities.size() );
        for ( InventoryLimits inventoryLimits : entities ) {
            list.add( toSimpleDto( inventoryLimits ) );
        }

        return list;
    }

    private Long entityProductId(InventoryLimits inventoryLimits) {
        if ( inventoryLimits == null ) {
            return null;
        }
        Product product = inventoryLimits.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityProductName(InventoryLimits inventoryLimits) {
        if ( inventoryLimits == null ) {
            return null;
        }
        Product product = inventoryLimits.getProduct();
        if ( product == null ) {
            return null;
        }
        String name = product.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long entityLocationId(InventoryLimits inventoryLimits) {
        if ( inventoryLimits == null ) {
            return null;
        }
        Location location = inventoryLimits.getLocation();
        if ( location == null ) {
            return null;
        }
        Long id = location.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityLocationName(InventoryLimits inventoryLimits) {
        if ( inventoryLimits == null ) {
            return null;
        }
        Location location = inventoryLimits.getLocation();
        if ( location == null ) {
            return null;
        }
        String name = location.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
