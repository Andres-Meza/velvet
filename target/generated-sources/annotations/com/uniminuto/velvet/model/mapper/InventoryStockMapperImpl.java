package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.InventoryStockDTO;
import com.uniminuto.velvet.model.entity.InventoryStock;
import com.uniminuto.velvet.model.entity.Location;
import com.uniminuto.velvet.model.entity.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T13:54:52-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class InventoryStockMapperImpl implements InventoryStockMapper {

    @Override
    public InventoryStock toEntity(InventoryStockDTO.CreateInventoryStock createInventoryStockDTO) {
        if ( createInventoryStockDTO == null ) {
            return null;
        }

        InventoryStock.InventoryStockBuilder inventoryStock = InventoryStock.builder();

        inventoryStock.product( productIdToProduct( createInventoryStockDTO.getProductId() ) );
        inventoryStock.location( locationIdToLocation( createInventoryStockDTO.getLocationId() ) );
        inventoryStock.currentStock( createInventoryStockDTO.getCurrentStock() );

        return inventoryStock.build();
    }

    @Override
    public void updateEntityFromDto(InventoryStockDTO.UpdateInventoryStock updateInventoryStockDTO, InventoryStock inventoryStock) {
        if ( updateInventoryStockDTO == null ) {
            return;
        }

        inventoryStock.setProduct( productIdToProduct( updateInventoryStockDTO.getProductId() ) );
        inventoryStock.setLocation( locationIdToLocation( updateInventoryStockDTO.getLocationId() ) );
        inventoryStock.setId( updateInventoryStockDTO.getId() );
        inventoryStock.setCurrentStock( updateInventoryStockDTO.getCurrentStock() );
    }

    @Override
    public InventoryStockDTO.DetailsInventoryStock toDetailsDTO(InventoryStock inventoryStock) {
        if ( inventoryStock == null ) {
            return null;
        }

        InventoryStockDTO.DetailsInventoryStock.DetailsInventoryStockBuilder detailsInventoryStock = InventoryStockDTO.DetailsInventoryStock.builder();

        detailsInventoryStock.productName( inventoryStockProductName( inventoryStock ) );
        detailsInventoryStock.locationName( inventoryStockLocationName( inventoryStock ) );
        detailsInventoryStock.id( inventoryStock.getId() );
        detailsInventoryStock.currentStock( inventoryStock.getCurrentStock() );
        detailsInventoryStock.createdAt( inventoryStock.getCreatedAt() );
        detailsInventoryStock.updatedAt( inventoryStock.getUpdatedAt() );

        return detailsInventoryStock.build();
    }

    @Override
    public InventoryStockDTO.SimpleInventoryStock toSimpleDTO(InventoryStock inventoryStock) {
        if ( inventoryStock == null ) {
            return null;
        }

        InventoryStockDTO.SimpleInventoryStock.SimpleInventoryStockBuilder simpleInventoryStock = InventoryStockDTO.SimpleInventoryStock.builder();

        simpleInventoryStock.productName( inventoryStockProductName( inventoryStock ) );
        simpleInventoryStock.locationName( inventoryStockLocationName( inventoryStock ) );
        simpleInventoryStock.id( inventoryStock.getId() );
        simpleInventoryStock.currentStock( inventoryStock.getCurrentStock() );

        return simpleInventoryStock.build();
    }

    private String inventoryStockProductName(InventoryStock inventoryStock) {
        if ( inventoryStock == null ) {
            return null;
        }
        Product product = inventoryStock.getProduct();
        if ( product == null ) {
            return null;
        }
        String name = product.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String inventoryStockLocationName(InventoryStock inventoryStock) {
        if ( inventoryStock == null ) {
            return null;
        }
        Location location = inventoryStock.getLocation();
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
