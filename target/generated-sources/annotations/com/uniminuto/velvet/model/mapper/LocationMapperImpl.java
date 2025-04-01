package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.LocationDTO;
import com.uniminuto.velvet.model.entity.Location;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-31T11:48:48-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class LocationMapperImpl implements LocationMapper {

    @Override
    public Location toEntity(LocationDTO.CreateLocation dto) {
        if ( dto == null ) {
            return null;
        }

        Location.LocationBuilder location = Location.builder();

        location.name( dto.getName() );
        location.address( dto.getAddress() );
        location.description( dto.getDescription() );
        location.phone( dto.getPhone() );
        location.active( dto.getActive() );

        return location.build();
    }

    @Override
    public Location toEntity(LocationDTO.UpdateLocation dto) {
        if ( dto == null ) {
            return null;
        }

        Location.LocationBuilder location = Location.builder();

        location.id( dto.getId() );
        location.name( dto.getName() );
        location.address( dto.getAddress() );
        location.description( dto.getDescription() );
        location.phone( dto.getPhone() );
        location.active( dto.getActive() );

        return location.build();
    }

    @Override
    public void updateEntityFromDto(LocationDTO.UpdateLocation dto, Location entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setName( dto.getName() );
        entity.setAddress( dto.getAddress() );
        entity.setDescription( dto.getDescription() );
        entity.setPhone( dto.getPhone() );
        entity.setActive( dto.getActive() );
    }

    @Override
    public LocationDTO.SimpleLocation toSimpleDto(Location entity) {
        if ( entity == null ) {
            return null;
        }

        LocationDTO.SimpleLocation.SimpleLocationBuilder simpleLocation = LocationDTO.SimpleLocation.builder();

        simpleLocation.id( entity.getId() );
        simpleLocation.name( entity.getName() );
        simpleLocation.address( entity.getAddress() );
        simpleLocation.active( entity.getActive() );

        return simpleLocation.build();
    }

    @Override
    public LocationDTO.DetailsLocation toDetailsDto(Location entity) {
        if ( entity == null ) {
            return null;
        }

        LocationDTO.DetailsLocation.DetailsLocationBuilder detailsLocation = LocationDTO.DetailsLocation.builder();

        detailsLocation.totalUsers( countUsers( entity ) );
        detailsLocation.id( entity.getId() );
        detailsLocation.name( entity.getName() );
        detailsLocation.address( entity.getAddress() );
        detailsLocation.phone( entity.getPhone() );
        detailsLocation.description( entity.getDescription() );
        detailsLocation.active( entity.getActive() );
        detailsLocation.createdAt( entity.getCreatedAt() );
        detailsLocation.updatedAt( entity.getUpdatedAt() );

        return detailsLocation.build();
    }

    @Override
    public List<LocationDTO.SimpleLocation> toSimpleDtoList(List<Location> entities) {
        if ( entities == null ) {
            return null;
        }

        List<LocationDTO.SimpleLocation> list = new ArrayList<LocationDTO.SimpleLocation>( entities.size() );
        for ( Location location : entities ) {
            list.add( toSimpleDto( location ) );
        }

        return list;
    }

    @Override
    public List<LocationDTO.DetailsLocation> toDetailsDtoList(List<Location> entities) {
        if ( entities == null ) {
            return null;
        }

        List<LocationDTO.DetailsLocation> list = new ArrayList<LocationDTO.DetailsLocation>( entities.size() );
        for ( Location location : entities ) {
            list.add( toDetailsDto( location ) );
        }

        return list;
    }
}
