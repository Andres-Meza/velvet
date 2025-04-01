package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.LocationDTO;
import com.uniminuto.velvet.model.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocationMapper {
  
  LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);
  
  // Convertir de CreateLocation a Location
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "inventoryStocks", ignore = true)
  @Mapping(target = "users", ignore = true)
  Location toEntity(LocationDTO.CreateLocation dto);
  
  // Convertir de UpdateLocation a Location
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "inventoryStocks", ignore = true)
  @Mapping(target = "users", ignore = true)
  Location toEntity(LocationDTO.UpdateLocation dto);
  
  // Actualizar un Location existente con datos de UpdateLocation
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "inventoryStocks", ignore = true)
  @Mapping(target = "users", ignore = true)
  void updateEntityFromDto(LocationDTO.UpdateLocation dto, @MappingTarget Location entity);
  
  // Convertir de Location a SimpleLocation
  LocationDTO.SimpleLocation toSimpleDto(Location entity);
  
  // Convertir de Location a DetailsLocation
  @Mapping(target = "totalUsers", source = "entity", qualifiedByName = "countUsers")
  LocationDTO.DetailsLocation toDetailsDto(Location entity);
  
  // Convertir lista de Location a lista de SimpleLocation
  List<LocationDTO.SimpleLocation> toSimpleDtoList(List<Location> entities);

  List<LocationDTO.DetailsLocation> toDetailsDtoList(List<Location> entities);
  
  // Método auxiliar para contar usuarios
  @Named("countUsers")
  default Integer countUsers(Location location) {
    if (location == null || location.getUsers() == null) {
      return 0;
    }
    return location.getUsers().size();
  }
}