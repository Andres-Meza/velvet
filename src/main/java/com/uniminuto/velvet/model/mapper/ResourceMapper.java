package com.uniminuto.velvet.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.uniminuto.velvet.model.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.uniminuto.velvet.model.dto.ResourceDTO;
import com.uniminuto.velvet.model.entity.Resource;

@Mapper(componentModel = "spring")
public interface ResourceMapper {
  
  ResourceMapper INSTANCE = Mappers.getMapper(ResourceMapper.class);
  
  // Convertir de CreateResource a Resource
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "permissions", ignore = true)
  Resource toEntity(ResourceDTO.CreateResource dto);
  
  // Convertir de UpdateResource a Resource
  @Mapping(target = "permissions", ignore = true)
  Resource toEntity(ResourceDTO.UpdateResource dto);
  
  // Actualizar un Resource existente con datos de UpdateResource
  @Mapping(target = "permissions", ignore = true)
  void updateEntityFromDto(ResourceDTO.UpdateResource dto, @MappingTarget Resource entity);
  
  // Convertir de Resource a SimpleResource
  ResourceDTO.SimpleResource toSimpleDto(Resource entity);
  
  // Convertir de Resource a DetailsResource
  @Mapping(target = "permissionName", source = "entity", qualifiedByName = "extractPermissionName")
  ResourceDTO.DetailsResource toDetailsDto(Resource entity);
  
  // Convertir lista de Resources a lista de SimpleResource
  default List<ResourceDTO.SimpleResource> toSimpleDtoList(List<Resource> entities) {
    if (entities == null) {
      return null;
    }
    return entities.stream()
      .map(this::toSimpleDto)
      .collect(Collectors.toList());
  }
  
  // Método auxiliar para extraer nombres de permisos
  @Named("extractPermissionName")
  default String extractPermissionName(Resource resource) {
    if (resource == null || resource.getPermissions() == null || resource.getPermissions().isEmpty()) {
      return null;
    }
    return resource.getPermissions().stream()
      .map(Permission::getName)
      .collect(Collectors.joining(", "));
  }

  List<ResourceDTO.DetailsResource> toResourceDetailList(List<Resource> entities);
}