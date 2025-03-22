package com.uniminuto.velvet.model.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.uniminuto.velvet.model.dto.RoleDTO;
import com.uniminuto.velvet.model.entity.Role;
import com.uniminuto.velvet.model.entity.RolePermission;

@Mapper(componentModel = "spring")
public interface RoleMapper {
  
  RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);
  
  // Convertir de CreateRole a Role
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "rolePermissions", ignore = true)
  Role toEntity(RoleDTO.CreateRole dto);
  
  // Convertir de UpdateRole a Role
  @Mapping(target = "rolePermissions", ignore = true)
  Role toEntity(RoleDTO.UpdateRole dto);
  
  // Actualizar un Role existente con datos de UpdateRole
  @Mapping(target = "rolePermissions", ignore = true)
  void updateEntityFromDto(RoleDTO.UpdateRole dto, @MappingTarget Role entity);
  
  // Convertir de Role a SimpleRole
  RoleDTO.SimpleRole toSimpleDto(Role entity);
  
  // Convertir de Role a DetailsRole
  @Mapping(target = "permissions", source = "rolePermissions", qualifiedByName = "extractPermissions")
  RoleDTO.DetailsRole toDetailsDto(Role entity);
  
  // Convertir lista de Role a lista de SimpleRole
  List<RoleDTO.SimpleRole> toSimpleDtoList(List<Role> entities);
  
  // Convertir lista de Role a lista de DetailsRole
  List<RoleDTO.DetailsRole> toDetailsDtoList(List<Role> entities);
  
  // Método auxiliar para extraer permisos
  @Named("extractPermissions")
  default Set<String> extractPermissions(Set<RolePermission> rolePermissions) {
    if (rolePermissions == null || rolePermissions.isEmpty()) {
      return Set.of();
    }
    return rolePermissions.stream()
      .map(rolePermission -> rolePermission.getPermission().getName())
      .collect(Collectors.toSet());
  }
}