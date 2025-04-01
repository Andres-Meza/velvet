package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.InventoryMovementDTO;
import com.uniminuto.velvet.model.entity.InventoryMovement;
import com.uniminuto.velvet.model.entity.InventoryStock;
import com.uniminuto.velvet.model.entity.Order;
import com.uniminuto.velvet.model.entity.Product;
import com.uniminuto.velvet.model.entity.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-26T22:46:03-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class InventoryMovementMapperImpl implements InventoryMovementMapper {

    @Override
    public InventoryMovement toEntity(InventoryMovementDTO.CreateInventoryMovement dto) {
        if ( dto == null ) {
            return null;
        }

        InventoryMovement.InventoryMovementBuilder inventoryMovement = InventoryMovement.builder();

        inventoryMovement.inventoryStock( inventoryStockIdToInventoryStock( dto.getInventoryStockId() ) );
        inventoryMovement.product( productIdToProduct( dto.getProductId() ) );
        inventoryMovement.order( orderIdToOrder( dto.getOrderId() ) );
        inventoryMovement.createdBy( userIdToUser( dto.getCreatedById() ) );
        inventoryMovement.movementType( dto.getMovementType() );
        inventoryMovement.quantity( dto.getQuantity() );
        inventoryMovement.unitCost( dto.getUnitCost() );
        inventoryMovement.reference( dto.getReference() );
        inventoryMovement.notes( dto.getNotes() );

        inventoryMovement.movementDate( LocalDateTime.now() );

        return inventoryMovement.build();
    }

    @Override
    public void updateEntityFromDto(InventoryMovementDTO.UpdateInventoryMovement dto, InventoryMovement entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getMovementType() != null ) {
            entity.setMovementType( dto.getMovementType() );
        }
        if ( dto.getQuantity() != null ) {
            entity.setQuantity( dto.getQuantity() );
        }
        if ( dto.getUnitCost() != null ) {
            entity.setUnitCost( dto.getUnitCost() );
        }
        if ( dto.getReference() != null ) {
            entity.setReference( dto.getReference() );
        }
        if ( dto.getNotes() != null ) {
            entity.setNotes( dto.getNotes() );
        }
        if ( dto.getOrderId() != null ) {
            entity.setOrder( orderIdToOrder( dto.getOrderId() ) );
        }
        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
    }

    @Override
    public InventoryMovementDTO.DetailsInventoryMovement toDetailsDto(InventoryMovement entity) {
        if ( entity == null ) {
            return null;
        }

        InventoryMovementDTO.DetailsInventoryMovement.DetailsInventoryMovementBuilder detailsInventoryMovement = InventoryMovementDTO.DetailsInventoryMovement.builder();

        detailsInventoryMovement.inventoryStockId( entityInventoryStockId( entity ) );
        detailsInventoryMovement.productId( entityProductId( entity ) );
        detailsInventoryMovement.productName( entityProductName( entity ) );
        detailsInventoryMovement.currentStock( entityInventoryStockCurrentStock( entity ) );
        detailsInventoryMovement.createdById( entityCreatedById( entity ) );
        detailsInventoryMovement.createdByUsername( entityCreatedByUsername( entity ) );
        detailsInventoryMovement.orderId( entityOrderId( entity ) );
        detailsInventoryMovement.id( entity.getId() );
        detailsInventoryMovement.movementType( entity.getMovementType() );
        detailsInventoryMovement.quantity( entity.getQuantity() );
        detailsInventoryMovement.unitCost( entity.getUnitCost() );
        detailsInventoryMovement.reference( entity.getReference() );
        detailsInventoryMovement.notes( entity.getNotes() );
        detailsInventoryMovement.movementDate( entity.getMovementDate() );

        return detailsInventoryMovement.build();
    }

    @Override
    public InventoryMovementDTO.SimpleInventoryMovement toSimpleDto(InventoryMovement entity) {
        if ( entity == null ) {
            return null;
        }

        InventoryMovementDTO.SimpleInventoryMovement.SimpleInventoryMovementBuilder simpleInventoryMovement = InventoryMovementDTO.SimpleInventoryMovement.builder();

        simpleInventoryMovement.productId( entityProductId( entity ) );
        simpleInventoryMovement.productName( entityProductName( entity ) );
        simpleInventoryMovement.currentStock( entityInventoryStockCurrentStock( entity ) );
        simpleInventoryMovement.id( entity.getId() );
        simpleInventoryMovement.movementType( entity.getMovementType() );
        simpleInventoryMovement.quantity( entity.getQuantity() );
        simpleInventoryMovement.unitCost( entity.getUnitCost() );
        simpleInventoryMovement.reference( entity.getReference() );
        simpleInventoryMovement.movementDate( entity.getMovementDate() );

        return simpleInventoryMovement.build();
    }

    @Override
    public List<InventoryMovementDTO.DetailsInventoryMovement> toDetailsDtoList(List<InventoryMovement> entities) {
        if ( entities == null ) {
            return null;
        }

        List<InventoryMovementDTO.DetailsInventoryMovement> list = new ArrayList<InventoryMovementDTO.DetailsInventoryMovement>( entities.size() );
        for ( InventoryMovement inventoryMovement : entities ) {
            list.add( toDetailsDto( inventoryMovement ) );
        }

        return list;
    }

    @Override
    public List<InventoryMovementDTO.SimpleInventoryMovement> toSimpleDtoList(List<InventoryMovement> entities) {
        if ( entities == null ) {
            return null;
        }

        List<InventoryMovementDTO.SimpleInventoryMovement> list = new ArrayList<InventoryMovementDTO.SimpleInventoryMovement>( entities.size() );
        for ( InventoryMovement inventoryMovement : entities ) {
            list.add( toSimpleDto( inventoryMovement ) );
        }

        return list;
    }

    private Long entityInventoryStockId(InventoryMovement inventoryMovement) {
        if ( inventoryMovement == null ) {
            return null;
        }
        InventoryStock inventoryStock = inventoryMovement.getInventoryStock();
        if ( inventoryStock == null ) {
            return null;
        }
        Long id = inventoryStock.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityProductId(InventoryMovement inventoryMovement) {
        if ( inventoryMovement == null ) {
            return null;
        }
        Product product = inventoryMovement.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityProductName(InventoryMovement inventoryMovement) {
        if ( inventoryMovement == null ) {
            return null;
        }
        Product product = inventoryMovement.getProduct();
        if ( product == null ) {
            return null;
        }
        String name = product.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long entityInventoryStockCurrentStock(InventoryMovement inventoryMovement) {
        if ( inventoryMovement == null ) {
            return null;
        }
        InventoryStock inventoryStock = inventoryMovement.getInventoryStock();
        if ( inventoryStock == null ) {
            return null;
        }
        Long currentStock = inventoryStock.getCurrentStock();
        if ( currentStock == null ) {
            return null;
        }
        return currentStock;
    }

    private Long entityCreatedById(InventoryMovement inventoryMovement) {
        if ( inventoryMovement == null ) {
            return null;
        }
        User createdBy = inventoryMovement.getCreatedBy();
        if ( createdBy == null ) {
            return null;
        }
        Long id = createdBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityCreatedByUsername(InventoryMovement inventoryMovement) {
        if ( inventoryMovement == null ) {
            return null;
        }
        User createdBy = inventoryMovement.getCreatedBy();
        if ( createdBy == null ) {
            return null;
        }
        String username = createdBy.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }

    private Long entityOrderId(InventoryMovement inventoryMovement) {
        if ( inventoryMovement == null ) {
            return null;
        }
        Order order = inventoryMovement.getOrder();
        if ( order == null ) {
            return null;
        }
        Long id = order.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
