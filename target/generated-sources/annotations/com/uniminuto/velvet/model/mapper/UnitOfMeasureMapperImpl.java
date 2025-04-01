package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.UnitOfMeasureDTO;
import com.uniminuto.velvet.model.entity.UnitOfMeasure;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-30T21:49:46-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class UnitOfMeasureMapperImpl implements UnitOfMeasureMapper {

    @Override
    public UnitOfMeasure toEntity(UnitOfMeasureDTO.CreateUnitOfMeasure dto) {
        if ( dto == null ) {
            return null;
        }

        UnitOfMeasure.UnitOfMeasureBuilder unitOfMeasure = UnitOfMeasure.builder();

        unitOfMeasure.name( dto.getName() );
        unitOfMeasure.symbol( dto.getSymbol() );

        return unitOfMeasure.build();
    }

    @Override
    public UnitOfMeasure toEntity(UnitOfMeasureDTO.UpdateUnitOfMeasure dto) {
        if ( dto == null ) {
            return null;
        }

        UnitOfMeasure.UnitOfMeasureBuilder unitOfMeasure = UnitOfMeasure.builder();

        unitOfMeasure.id( dto.getId() );
        unitOfMeasure.name( dto.getName() );
        unitOfMeasure.symbol( dto.getSymbol() );

        return unitOfMeasure.build();
    }

    @Override
    public void updateEntityFromDto(UnitOfMeasureDTO.UpdateUnitOfMeasure dto, UnitOfMeasure entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setName( dto.getName() );
        entity.setSymbol( dto.getSymbol() );
    }

    @Override
    public UnitOfMeasureDTO.SimpleUnitOfMeasure toSimpleDto(UnitOfMeasure entity) {
        if ( entity == null ) {
            return null;
        }

        UnitOfMeasureDTO.SimpleUnitOfMeasure.SimpleUnitOfMeasureBuilder simpleUnitOfMeasure = UnitOfMeasureDTO.SimpleUnitOfMeasure.builder();

        simpleUnitOfMeasure.id( entity.getId() );
        simpleUnitOfMeasure.name( entity.getName() );
        simpleUnitOfMeasure.symbol( entity.getSymbol() );

        return simpleUnitOfMeasure.build();
    }

    @Override
    public UnitOfMeasureDTO.DetailsUnitOfMeasure toDetailsDto(UnitOfMeasure entity) {
        if ( entity == null ) {
            return null;
        }

        UnitOfMeasureDTO.DetailsUnitOfMeasure.DetailsUnitOfMeasureBuilder detailsUnitOfMeasure = UnitOfMeasureDTO.DetailsUnitOfMeasure.builder();

        detailsUnitOfMeasure.productName( extractProductName( entity ) );
        detailsUnitOfMeasure.id( entity.getId() );
        detailsUnitOfMeasure.name( entity.getName() );
        detailsUnitOfMeasure.symbol( entity.getSymbol() );

        return detailsUnitOfMeasure.build();
    }

    @Override
    public List<UnitOfMeasureDTO.SimpleUnitOfMeasure> toSimpleDtoList(List<UnitOfMeasure> entities) {
        if ( entities == null ) {
            return null;
        }

        List<UnitOfMeasureDTO.SimpleUnitOfMeasure> list = new ArrayList<UnitOfMeasureDTO.SimpleUnitOfMeasure>( entities.size() );
        for ( UnitOfMeasure unitOfMeasure : entities ) {
            list.add( toSimpleDto( unitOfMeasure ) );
        }

        return list;
    }

    @Override
    public List<UnitOfMeasureDTO.DetailsUnitOfMeasure> toDetailsDtoList(List<UnitOfMeasure> entities) {
        if ( entities == null ) {
            return null;
        }

        List<UnitOfMeasureDTO.DetailsUnitOfMeasure> list = new ArrayList<UnitOfMeasureDTO.DetailsUnitOfMeasure>( entities.size() );
        for ( UnitOfMeasure unitOfMeasure : entities ) {
            list.add( toDetailsDto( unitOfMeasure ) );
        }

        return list;
    }
}
