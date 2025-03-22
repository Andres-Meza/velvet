package com.uniminuto.velvet.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.uniminuto.velvet.model.dto.RolePermissionDTO;
import com.uniminuto.velvet.model.entity.Permission;
import com.uniminuto.velvet.model.entity.Role;
import com.uniminuto.velvet.model.entity.RolePermission;
import com.uniminuto.velvet.model.entity.User;

@Mapper(componentModel = "spring")
public interface RolePermissionMapper {

  RolePermissionMapper INSTANCE = Mappers.getMapper(RolePermissionMapper.class);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "role", source = "roleId", qualifiedByName = "roleIdToRole")
  @Mapping(target = "permission", source = "permissionId", qualifiedByName = "permissionIdToPermission")
  @Mapping(target = "assignedBy", source = "assignedById", qualifiedByName = "userIdToUser")
  @Mapping(target = "assignedAt", expression = "java(java.time.LocalDateTime.now())")
  RolePermission toEntity(RolePermissionDTO.AssignRolePermission assignRolePermissionDTO);

  @Mapping(target = "roleName", source = "role.name")
  @Mapping(target = "permissionName", source = "permission.name")
  @Mapping(target = "assignedByUsername", source = "assignedBy.username")
  RolePermissionDTO.DetailsRolePermission toDetailsDTO(RolePermission rolePermission);

  List<RolePermissionDTO.DetailsRolePermission> toDetailsDTOList(List<RolePermission> rolePermissions);

  @Named("roleIdToRole")
  default Role roleIdToRole(Long roleId) {
    if (roleId == null) {
      return null;
    }
    Role role = new Role();
    role.setId(roleId);
    return role;
  }

  @Named("permissionIdToPermission")
  default Permission permissionIdToPermission(Long permissionId) {
    if (permissionId == null) {
      return null;
    }
    Permission permission = new Permission();
    permission.setId(permissionId);
    return permission;
  }

  @Named("userIdToUser")
  default User userIdToUser(Long userId) {
    if (userId == null) {
      return null;
    }
    User user = new User();
    user.setId(userId);
    return user;
  }
}