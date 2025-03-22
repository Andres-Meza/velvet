package com.uniminuto.velvet.model.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.uniminuto.velvet.model.dto.PermissionDTO;
import com.uniminuto.velvet.model.dto.RoleDTO;
import com.uniminuto.velvet.model.entity.Permission;
import com.uniminuto.velvet.model.entity.Resource;
import com.uniminuto.velvet.model.entity.RolePermission;

@Mapper(componentModel = "spring", uses = {ResourceMapper.class})
public interface PermissionMapper {

  PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "resource", source = "resourceId", qualifiedByName = "resourceIdToResource")
  @Mapping(target = "rolePermissions", ignore = true)
  Permission toEntity(PermissionDTO.CreatePermission createPermissionDTO);

  @Mapping(target = "resource", source = "resourceId", qualifiedByName = "resourceIdToResource")
  @Mapping(target = "rolePermissions", ignore = true)
  void updateEntityFromDto(PermissionDTO.UpdatePermission updatePermissionDTO, @MappingTarget Permission permission);

  @Mapping(target = "resource", source = "resource")
  @Mapping(target = "roles", expression = "java(mapRolePermissionsToRoles(permission.getRolePermissions()))")
  PermissionDTO.DetailsPermission toDetailsDTO(Permission permission);

  @Named("permissionToSimplePermission")
  default PermissionDTO.SimplePermission toSimpleDTO(Permission permission) {
    if (permission == null) {
      return null;
    }
    return PermissionDTO.SimplePermission.builder()
      .id(permission.getId())
      .name(permission.getName())
      .description(permission.getDescription())
      .build();
  }

  List<PermissionDTO.SimplePermission> toSimpleDTOList(List<Permission> permissions);

  @Named("resourceIdToResource")
  default Resource resourceIdToResource(Long resourceId) {
    if (resourceId == null) {
      return null;
    }
    Resource resource = new Resource();
    resource.setId(resourceId);
    return resource;
  }

  // Método auxiliar para mapear RolePermissions a SimpleRole
  default Set<RoleDTO.DetailsRole> mapRolePermissionsToRoles(Set<RolePermission> rolePermissions) {
    if (rolePermissions == null) {
      return java.util.Collections.emptySet();
    }
    
    return rolePermissions.stream()
      .map(rp -> {
        return RoleDTO.DetailsRole.builder()
          .id(rp.getRole().getId())
          .name(rp.getRole().getName())
          .description(rp.getRole().getDescription())
          .build();
      })
      .collect(Collectors.toSet());
  }
}