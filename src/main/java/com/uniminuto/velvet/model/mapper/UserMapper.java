package com.uniminuto.velvet.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.uniminuto.velvet.model.dto.UserDTO;
import com.uniminuto.velvet.model.entity.DocumentType;
import com.uniminuto.velvet.model.entity.Location;
import com.uniminuto.velvet.model.entity.Role;
import com.uniminuto.velvet.model.entity.User;

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {
  
  /**
   * Convierte DTO CreateUser a entidad User para la creación
   */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "documentType", source = "documentTypeId", qualifiedByName = "documentTypeIdToDocumentType")
  @Mapping(target = "role", source = "roleId", qualifiedByName = "roleIdToRole")
  @Mapping(target = "location", source = "locationId", qualifiedByName = "locationIdToLocation")
  @Mapping(target = "active", constant = "true")
  @Mapping(target = "lastLogin", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  User createUserDtoToUser(UserDTO.CreateUser createUserDto);
  
  /**
   * Actualiza una entidad User existente con los datos de UpdateUser
   */
  @Mapping(target = "documentType", source = "documentTypeId", qualifiedByName = "documentTypeIdToDocumentType")
  @Mapping(target = "role", source = "roleId", qualifiedByName = "roleIdToRole")
  @Mapping(target = "location", source = "locationId", qualifiedByName = "locationIdToLocation")
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "lastLogin", ignore = true)
  void updateUserFromDto(UserDTO.UpdateUser updateUserDto, @MappingTarget User user);
  
  /**
   * Convierte entidad User a DTO UserDetails
   */
  @Mapping(target = "documentTypeName", source = "documentType.name")
  @Mapping(target = "roleName", source = "role.name")
  @Mapping(target = "locationName", source = "location.name")
  UserDTO.UserDetails userToUserDetailsDto(User user);
  
  /**
   * Métodos para convertir IDs a entidades
   */
  @Named("documentTypeIdToDocumentType")
  default DocumentType documentTypeIdToDocumentType(Long id) {
    if (id == null) {
      return null;
    }
    DocumentType documentType = new DocumentType();
    documentType.setId(id);
    return documentType;
  }
  
  @Named("roleIdToRole")
  default Role roleIdToRole(Long id) {
    if (id == null) {
      return null;
    }
    Role role = new Role();
    role.setId(id);
    return role;
  }
  
  @Named("locationIdToLocation")
  default Location locationIdToLocation(Long id) {
    if (id == null) {
      return null;
    }
    Location location = new Location();
    location.setId(id);
    return location;
  }
}