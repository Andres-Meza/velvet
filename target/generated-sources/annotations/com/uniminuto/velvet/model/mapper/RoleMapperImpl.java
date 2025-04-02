package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.RoleDTO;
import com.uniminuto.velvet.model.entity.Role;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-02T09:15:12-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Role toEntity(RoleDTO.CreateRole dto) {
        if ( dto == null ) {
            return null;
        }

        Role.RoleBuilder role = Role.builder();

        role.name( dto.getName() );
        role.description( dto.getDescription() );

        return role.build();
    }

    @Override
    public Role toEntity(RoleDTO.UpdateRole dto) {
        if ( dto == null ) {
            return null;
        }

        Role.RoleBuilder role = Role.builder();

        role.id( dto.getId() );
        role.name( dto.getName() );
        role.description( dto.getDescription() );

        return role.build();
    }

    @Override
    public void updateEntityFromDto(RoleDTO.UpdateRole dto, Role entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setName( dto.getName() );
        entity.setDescription( dto.getDescription() );
    }

    @Override
    public RoleDTO.SimpleRole toSimpleDto(Role entity) {
        if ( entity == null ) {
            return null;
        }

        RoleDTO.SimpleRole.SimpleRoleBuilder simpleRole = RoleDTO.SimpleRole.builder();

        simpleRole.id( entity.getId() );
        simpleRole.name( entity.getName() );
        simpleRole.description( entity.getDescription() );

        return simpleRole.build();
    }

    @Override
    public RoleDTO.DetailsRole toDetailsDto(Role entity) {
        if ( entity == null ) {
            return null;
        }

        RoleDTO.DetailsRole.DetailsRoleBuilder detailsRole = RoleDTO.DetailsRole.builder();

        detailsRole.permissions( extractPermissions( entity.getRolePermissions() ) );
        detailsRole.id( entity.getId() );
        detailsRole.name( entity.getName() );
        detailsRole.description( entity.getDescription() );

        return detailsRole.build();
    }

    @Override
    public List<RoleDTO.SimpleRole> toSimpleDtoList(List<Role> entities) {
        if ( entities == null ) {
            return null;
        }

        List<RoleDTO.SimpleRole> list = new ArrayList<RoleDTO.SimpleRole>( entities.size() );
        for ( Role role : entities ) {
            list.add( toSimpleDto( role ) );
        }

        return list;
    }

    @Override
    public List<RoleDTO.DetailsRole> toDetailsDtoList(List<Role> entities) {
        if ( entities == null ) {
            return null;
        }

        List<RoleDTO.DetailsRole> list = new ArrayList<RoleDTO.DetailsRole>( entities.size() );
        for ( Role role : entities ) {
            list.add( toDetailsDto( role ) );
        }

        return list;
    }
}
