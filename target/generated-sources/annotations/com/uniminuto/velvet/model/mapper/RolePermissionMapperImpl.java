package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.RolePermissionDTO;
import com.uniminuto.velvet.model.entity.Permission;
import com.uniminuto.velvet.model.entity.Role;
import com.uniminuto.velvet.model.entity.RolePermission;
import com.uniminuto.velvet.model.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T13:54:52-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class RolePermissionMapperImpl implements RolePermissionMapper {

    @Override
    public RolePermission toEntity(RolePermissionDTO.AssignRolePermission assignRolePermissionDTO) {
        if ( assignRolePermissionDTO == null ) {
            return null;
        }

        RolePermission.RolePermissionBuilder rolePermission = RolePermission.builder();

        rolePermission.role( roleIdToRole( assignRolePermissionDTO.getRoleId() ) );
        rolePermission.permission( permissionIdToPermission( assignRolePermissionDTO.getPermissionId() ) );
        rolePermission.assignedBy( userIdToUser( assignRolePermissionDTO.getAssignedById() ) );

        rolePermission.assignedAt( java.time.LocalDateTime.now() );

        return rolePermission.build();
    }

    @Override
    public RolePermissionDTO.DetailsRolePermission toDetailsDTO(RolePermission rolePermission) {
        if ( rolePermission == null ) {
            return null;
        }

        RolePermissionDTO.DetailsRolePermission.DetailsRolePermissionBuilder detailsRolePermission = RolePermissionDTO.DetailsRolePermission.builder();

        detailsRolePermission.roleName( rolePermissionRoleName( rolePermission ) );
        detailsRolePermission.permissionName( rolePermissionPermissionName( rolePermission ) );
        detailsRolePermission.assignedByUsername( rolePermissionAssignedByUsername( rolePermission ) );
        detailsRolePermission.id( rolePermission.getId() );
        detailsRolePermission.assignedAt( rolePermission.getAssignedAt() );

        return detailsRolePermission.build();
    }

    @Override
    public List<RolePermissionDTO.DetailsRolePermission> toDetailsDTOList(List<RolePermission> rolePermissions) {
        if ( rolePermissions == null ) {
            return null;
        }

        List<RolePermissionDTO.DetailsRolePermission> list = new ArrayList<RolePermissionDTO.DetailsRolePermission>( rolePermissions.size() );
        for ( RolePermission rolePermission : rolePermissions ) {
            list.add( toDetailsDTO( rolePermission ) );
        }

        return list;
    }

    private String rolePermissionRoleName(RolePermission rolePermission) {
        if ( rolePermission == null ) {
            return null;
        }
        Role role = rolePermission.getRole();
        if ( role == null ) {
            return null;
        }
        String name = role.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String rolePermissionPermissionName(RolePermission rolePermission) {
        if ( rolePermission == null ) {
            return null;
        }
        Permission permission = rolePermission.getPermission();
        if ( permission == null ) {
            return null;
        }
        String name = permission.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String rolePermissionAssignedByUsername(RolePermission rolePermission) {
        if ( rolePermission == null ) {
            return null;
        }
        User assignedBy = rolePermission.getAssignedBy();
        if ( assignedBy == null ) {
            return null;
        }
        String username = assignedBy.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }
}
