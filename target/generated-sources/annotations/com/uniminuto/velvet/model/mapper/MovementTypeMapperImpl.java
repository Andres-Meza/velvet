package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.MovementTypeDTO;
import com.uniminuto.velvet.model.entity.MovementType;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-25T15:31:59-0500",
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

    @Override
    public List<MovementTypeDTO.SimpleMovementType> toSimpleDtoList(List<MovementType> entities) {
        if ( entities == null ) {
            return null;
        }

        List<MovementTypeDTO.SimpleMovementType> list = new ArrayList<MovementTypeDTO.SimpleMovementType>( entities.size() );
        for ( MovementType movementType : entities ) {
            list.add( toSimpleDTO( movementType ) );
        }

        return list;
    }

    @Override
    public List<MovementTypeDTO.DetailsMovementType> toDetailsDtoList(List<MovementType> entities) {
        if ( entities == null ) {
            return null;
        }

        List<MovementTypeDTO.DetailsMovementType> list = new ArrayList<MovementTypeDTO.DetailsMovementType>( entities.size() );
        for ( MovementType movementType : entities ) {
            list.add( toDetailsDTO( movementType ) );
        }

        return list;
    }
}
