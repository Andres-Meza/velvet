package com.uniminuto.velvet.service.impl;

import com.uniminuto.velvet.model.dto.LocationDTO;
import com.uniminuto.velvet.model.entity.Location;
import com.uniminuto.velvet.model.mapper.LocationMapper;
import com.uniminuto.velvet.model.repository.LocationRepository;
import com.uniminuto.velvet.service.interfaces.LocationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

  private final LocationRepository locationRepository;
  private final LocationMapper locationMapper;

  @Override
  @Transactional
  public LocationDTO.SimpleLocation createLocation(LocationDTO.CreateLocation createLocation) {
    // Verificar si ya existe un recurso con el mismo nombre
    if (locationRepository.existsByName((createLocation.getName()))) {
      throw new RuntimeException("Ya existe un tipo de producto con este nombre");
    }

    // Convertir DTO a entidad
    Location location = locationMapper.toEntity(createLocation);
    
    // Guardar en base de datos
    Location savedLocation = locationRepository.save(location);
    
    // Convertir entidad guardada a DTO de respuesta
    return locationMapper.toSimpleDto(savedLocation);
  }

  @Override
  @Transactional
  public LocationDTO.SimpleLocation updateLocation(LocationDTO.UpdateLocation updateLocation) {
    // Buscar el recurso existente
    Location existingLocation = locationRepository.findById(updateLocation.getId())
      .orElseThrow(() -> new RuntimeException("Tipo de producto no encontrado"));

    // Verificar si el nuevo nombre ya existe (si es diferente)
    if (!existingLocation.getName().equals(updateLocation.getName()) &&
        locationRepository.existsByName(updateLocation.getName())) {
      throw new RuntimeException("Ya existe un tipo de producto con este nombre");
    }

    // Actualizar la entidad
    locationMapper.updateEntityFromDto(updateLocation, existingLocation);
    
    // Guardar cambios
    Location updatedLocation = locationRepository.save(existingLocation);
    
    // Convertir a DTO de respuesta
    return locationMapper.toSimpleDto(updatedLocation);
  }

  @Override
  public LocationDTO.DetailsLocation getLocationById(Long id) {
    // Buscar recurso por ID
    Location location = locationRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Tipo de producto no encontrado"));
    
    // Convertir a DTO de detalle
    return locationMapper.toDetailsDto(location);
  }

  @Override
  public List<LocationDTO.SimpleLocation> getAllLocations() {
    // Obtener todos los recursos y convertir a lista de respuestas
    List<Location> locations = locationRepository.findAll();
    return locationMapper.toSimpleDtoList(locations);
  }

  @Override
  public List<LocationDTO.DetailsLocation> getAllLocationsWithDetails() {
    // Obtener todos los recursos con detalles
    List<Location> locations = locationRepository.findAll();
    return locationMapper.toDetailsDtoList(locations);
  }

  @Override
  @Transactional
  public boolean deleteLocation(Long id) {
    // Verificar si el recurso existe
    Optional<Location> locationOptional = locationRepository.findById(id);
    
    if (locationOptional.isPresent()) {
      locationRepository.delete(locationOptional.get());
      return true;
    }
    
    return false;
  }

  @Override
  public boolean existsByName(String name) {
    // Verificar existencia de un recurso por nombre
    return locationRepository.existsByName(name);
  }
}