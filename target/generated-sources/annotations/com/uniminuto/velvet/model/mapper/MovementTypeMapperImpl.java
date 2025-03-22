package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.MovementTypeDTO;
import com.uniminuto.velvet.model.entity.MovementType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T13:54:51-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class MovementTypeMapperImpl implements MovementTypeMapper {

    @Override
    public MovementTypeDTO.SimpleMovementType toSimpleDTO(MovementType entity) {
        if ( entity == null ) {
            return null;
        }

        MovementTypeDTO.SimpleMovementType.SimpleMovementTypeBuilder simpleMovementType = MovementTypeDTO.SimpleMovementType.builder();

        simpleMovementType.id( entity.getId() );
        simpleMovementType.name( entity.getName() );
        simpleMovementType.description( entity.getDescription() );
        simpleMovementType.affectsStock( entity.getAffectsStock() );

        return simpleMovementType.build();
    }

    @Override
    public MovementTypeDTO.DetailsMovementType toDetailsDTO(MovementType entity) {
        if ( entity == null ) {
            return null;
        }

        MovementTypeDTO.DetailsMovementType.DetailsMovementTypeBuilder detailsMovementType = MovementTypeDTO.DetailsMovementType.builder();

        detailsMovementType.inventoryMovements( mapInventoryMovementNames( entity.getInventoryMovements() ) );
        detailsMovementType.id( entity.getId() );
        detailsMovementType.name( entity.getName() );
        detailsMovementType.description( entity.getDescription() );
        detailsMovementType.affectsStock( entity.getAffectsStock() );

        return detailsMovementType.build();
    }

    @Override
    public MovementType toEntity(MovementTypeDTO.CreateMovementType dto) {
        if ( dto == null ) {
            return null;
        }

        MovementType.MovementTypeBuilder movementType = MovementType.builder();

        movementType.name( dto.getName() );
        movementType.description( dto.getDescription() );
        movementType.affectsStock( dto.getAffectsStock() );

        return movementType.build();
    }

    @Override
    public void updateEntityFromDTO(MovementTypeDTO.UpdateMovementType dto, MovementType entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setName( dto.getName() );
        entity.setDescription( dto.getDescription() );
        entity.setAffectsStock( dto.getAffectsStock() );
    }
}
