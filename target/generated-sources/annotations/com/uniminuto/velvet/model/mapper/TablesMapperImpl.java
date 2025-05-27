package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.TablesDTO;
import com.uniminuto.velvet.model.entity.Location;
import com.uniminuto.velvet.model.entity.Tables;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-26T21:15:42-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class TablesMapperImpl implements TablesMapper {

    @Override
    public TablesDTO.SimpleTable toSimpleDTO(Tables entity) {
        if ( entity == null ) {
            return null;
        }

        TablesDTO.SimpleTable.SimpleTableBuilder simpleTable = TablesDTO.SimpleTable.builder();

        simpleTable.locationName( entityLocationName( entity ) );
        simpleTable.id( entity.getId() );
        simpleTable.number( entity.getNumber() );
        simpleTable.capacity( entity.getCapacity() );
        simpleTable.active( entity.getActive() );

        return simpleTable.build();
    }

    @Override
    public TablesDTO.DetailsTable toDetailsDTO(Tables entity) {
        if ( entity == null ) {
            return null;
        }

        TablesDTO.DetailsTable.DetailsTableBuilder detailsTable = TablesDTO.DetailsTable.builder();

        detailsTable.locationName( entityLocationName( entity ) );
        detailsTable.ordersNumbers( mapOrdersToSimple( entity.getOrders() ) );
        detailsTable.id( entity.getId() );
        detailsTable.number( entity.getNumber() );
        detailsTable.description( entity.getDescription() );
        detailsTable.capacity( entity.getCapacity() );
        detailsTable.active( entity.getActive() );

        return detailsTable.build();
    }

    @Override
    public Tables toEntity(TablesDTO.CreateTable dto) {
        if ( dto == null ) {
            return null;
        }

        Tables.TablesBuilder tables = Tables.builder();

        tables.location( mapToLocation( dto.getLocationId() ) );
        tables.number( dto.getNumber() );
        tables.description( dto.getDescription() );
        tables.capacity( dto.getCapacity() );

        tables.active( true );

        return tables.build();
    }

    @Override
    public void updateEntityFromDTO(TablesDTO.UpdateTable dto, Tables entity) {
        if ( dto == null ) {
            return;
        }

        entity.setLocation( mapToLocation( dto.getLocationId() ) );
        entity.setId( dto.getId() );
        entity.setNumber( dto.getNumber() );
        entity.setDescription( dto.getDescription() );
        entity.setCapacity( dto.getCapacity() );
        entity.setActive( dto.getActive() );
    }

    private String entityLocationName(Tables tables) {
        if ( tables == null ) {
            return null;
        }
        Location location = tables.getLocation();
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
