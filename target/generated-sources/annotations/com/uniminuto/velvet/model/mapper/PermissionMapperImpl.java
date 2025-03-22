package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.PermissionDTO;
import com.uniminuto.velvet.model.entity.Permission;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T13:54:52-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class PermissionMapperImpl implements PermissionMapper {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public Permission toEntity(PermissionDTO.CreatePermission createPermissionDTO) {
        if ( createPermissionDTO == null ) {
            return null;
        }

        Permission.PermissionBuilder permission = Permission.builder();

        permission.resource( resourceIdToResource( createPermissionDTO.getResourceId() ) );
        permission.name( createPermissionDTO.getName() );
        permission.description( createPermissionDTO.getDescription() );

        return permission.build();
    }

    @Override
    public void updateEntityFromDto(PermissionDTO.UpdatePermission updatePermissionDTO, Permission permission) {
        if ( updatePermissionDTO == null ) {
            return;
        }

        permission.setResource( resourceIdToResource( updatePermissionDTO.getResourceId() ) );
        permission.setId( updatePermissionDTO.getId() );
        permission.setName( updatePermissionDTO.getName() );
        permission.setDescription( updatePermissionDTO.getDescription() );
    }

    @Override
    public PermissionDTO.DetailsPermission toDetailsDTO(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionDTO.DetailsPermission.DetailsPermissionBuilder detailsPermission = PermissionDTO.DetailsPermission.builder();

        detailsPermission.resource( resourceMapper.toSimpleDto( permission.getResource() ) );
        detailsPermission.id( permission.getId() );
        detailsPermission.name( permission.getName() );
        detailsPermission.description( permission.getDescription() );

        detailsPermission.roles( mapRolePermissionsToRoles(permission.getRolePermissions()) );

        return detailsPermission.build();
    }

    @Override
    public List<PermissionDTO.SimplePermission> toSimpleDTOList(List<Permission> permissions) {
        if ( permissions == null ) {
            return null;
        }

        List<PermissionDTO.SimplePermission> list = new ArrayList<PermissionDTO.SimplePermission>( permissions.size() );
        for ( Permission permission : permissions ) {
            list.add( permissionToSimplePermission( permission ) );
        }

        return list;
    }

    protected PermissionDTO.SimplePermission permissionToSimplePermission(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionDTO.SimplePermission.SimplePermissionBuilder simplePermission = PermissionDTO.SimplePermission.builder();

        simplePermission.id( permission.getId() );
        simplePermission.name( permission.getName() );
        simplePermission.description( permission.getDescription() );

        return simplePermission.build();
    }
}
