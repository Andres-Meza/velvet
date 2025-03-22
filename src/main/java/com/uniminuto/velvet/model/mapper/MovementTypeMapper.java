package com.uniminuto.velvet.model.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.uniminuto.velvet.model.dto.MovementTypeDTO;
import com.uniminuto.velvet.model.entity.InventoryMovement;
import com.uniminuto.velvet.model.entity.MovementType;

@Mapper(componentModel = "spring")
public interface MovementTypeMapper {
  
  MovementTypeMapper INSTANCE = Mappers.getMapper(MovementTypeMapper.class);
  
  // Entity -> DTO (SimpleMovementType)
  MovementTypeDTO.SimpleMovementType toSimpleDTO(MovementType entity);
  
  // Entity -> DTO (DetailsMovementType)
  @Mapping(target = "inventoryMovements", source = "inventoryMovements", qualifiedByName = "mapInventoryMovementNames")
  MovementTypeDTO.DetailsMovementType toDetailsDTO(MovementType entity);
  
  // DTO (CreateMovementType) -> Entity
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "inventoryMovements", ignore = true)
  MovementType toEntity(MovementTypeDTO.CreateMovementType dto);
  
  // DTO (UpdateMovementType) -> Entity (actualización)
  @Mapping(target = "inventoryMovements", ignore = true)
  void updateEntityFromDTO(MovementTypeDTO.UpdateMovementType dto, @MappingTarget MovementType entity);
  
  // Método personalizado para convertir los InventoryMovement en Set<String>
  @Named("mapInventoryMovementNames")
  default Set<String> mapInventoryMovementNames(Set<InventoryMovement> inventoryMovements) {
    if (inventoryMovements == null) {
      return new HashSet<>();
    }
    return inventoryMovements.stream()
      .map(movement -> movement.getId().toString())
      .collect(Collectors.toSet());
  }
}