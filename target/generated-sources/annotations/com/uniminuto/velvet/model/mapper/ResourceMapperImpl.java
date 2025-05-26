package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.ResourceDTO;
import com.uniminuto.velvet.model.entity.Resource;
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
public class ResourceMapperImpl implements ResourceMapper {

    @Override
    public Resource toEntity(ResourceDTO.CreateResource dto) {
        if ( dto == null ) {
            return null;
        }

        Resource.ResourceBuilder resource = Resource.builder();

        resource.name( dto.getName() );

        return resource.build();
    }

    @Override
    public Resource toEntity(ResourceDTO.UpdateResource dto) {
        if ( dto == null ) {
            return null;
        }

        Resource.ResourceBuilder resource = Resource.builder();

        resource.id( dto.getId() );
        resource.name( dto.getName() );

        return resource.build();
    }

    @Override
    public void updateEntityFromDto(ResourceDTO.UpdateResource dto, Resource entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setName( dto.getName() );
    }

    @Override
    public ResourceDTO.SimpleResource toSimpleDto(Resource entity) {
        if ( entity == null ) {
            return null;
        }

        ResourceDTO.SimpleResource.SimpleResourceBuilder simpleResource = ResourceDTO.SimpleResource.builder();

        simpleResource.id( entity.getId() );
        simpleResource.name( entity.getName() );

        return simpleResource.build();
    }

    @Override
    public ResourceDTO.DetailsResource toDetailsDto(Resource entity) {
        if ( entity == null ) {
            return null;
        }

        ResourceDTO.DetailsResource.DetailsResourceBuilder detailsResource = ResourceDTO.DetailsResource.builder();

        detailsResource.permissionName( extractPermissionName( entity ) );
        detailsResource.id( entity.getId() );
        detailsResource.name( entity.getName() );

        return detailsResource.build();
    }

    @Override
    public List<ResourceDTO.DetailsResource> toResourceDetailList(List<Resource> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ResourceDTO.DetailsResource> list = new ArrayList<ResourceDTO.DetailsResource>( entities.size() );
        for ( Resource resource : entities ) {
            list.add( toDetailsDto( resource ) );
        }

        return list;
    }
}
