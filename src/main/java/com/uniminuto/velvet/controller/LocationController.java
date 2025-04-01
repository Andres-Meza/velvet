package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.model.dto.LocationDTO.CreateLocation;
import com.uniminuto.velvet.model.dto.LocationDTO.DetailsLocation;
import com.uniminuto.velvet.model.dto.LocationDTO.SimpleLocation;
import com.uniminuto.velvet.model.dto.LocationDTO.UpdateLocation;
import com.uniminuto.velvet.service.interfaces.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v4/locations")
@RequiredArgsConstructor
public class LocationController {

  private final LocationService locationService;

  /**
   * Crear un nuevo tipo de producto.
   * 
   * @param createLocation DTO con los datos para crear el tipo de producto
   * @return Respuesta con el tipo de producto creada y estado HTTP 201
   */
  @PostMapping
  public ResponseEntity<SimpleLocation> createLocation(
    @Valid @RequestBody CreateLocation createLocation
  ) {
    SimpleLocation createdLocation = locationService.createLocation(createLocation);
    return new ResponseEntity<>(createdLocation, HttpStatus.CREATED);
  }

  /**
   * Actualizar un tipo de producto existente.
   * 
   * @param updateLocation DTO con los datos para actualizar el tipo de producto
   * @return Respuesta con el tipo de producto actualizada y estado HTTP 200
   */
  @PutMapping
  public ResponseEntity<SimpleLocation> updateLocation(
    @Valid @RequestBody UpdateLocation updateLocation
  ) {
    SimpleLocation updatedLocation = locationService.updateLocation(updateLocation);
    return ResponseEntity.ok(updatedLocation);
  }

  /**
   * Obtener un tipo de producto por su ID.
   * 
   * @param id Identificador del tipo de producto
   * @return Detalles del tipo de producto y estado HTTP 200
   */
  @GetMapping("/{id}")
  public ResponseEntity<DetailsLocation> getLocationById(
    @PathVariable Long id
  ) {
    DetailsLocation location = locationService.getLocationById(id);
    return ResponseEntity.ok(location);
  }

  /**
   * Obtener todos los tipos de producto (versión básica).
   * 
   * @return Lista de tipos de producto y estado HTTP 200
   */
  @GetMapping
  public ResponseEntity<List<SimpleLocation>> getAllCategories() {
    List<SimpleLocation> locations = locationService.getAllLocations();
    return ResponseEntity.ok(locations);
  }

  /**
   * Obtener todos los tipos de producto con detalles completos.
   * 
   * @return Lista de tipos de producto con detalles y estado HTTP 200
   */
  @GetMapping("/details")
  public ResponseEntity<List<DetailsLocation>> getAllCategoriesWithDetails() {
    List<DetailsLocation> locations = locationService.getAllLocationsWithDetails();
    return ResponseEntity.ok(locations);
  }

  /**
   * Eliminar un tipo de producto por su ID.
   * 
   * @param id Identificador del tipo de producto a eliminar
   * @return Estado HTTP 204 si la eliminación fue exitosa
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteLocation(
    @PathVariable Long id
  ) {
    boolean deleted = locationService.deleteLocation(id);
    return deleted 
      ? ResponseEntity.noContent().build()
      : ResponseEntity.notFound().build();
  }

  /**
   * Verificar si existe un tipo de producto por nombre.
   * 
   * @param name Nombre del tipo de producto a verificar
   * @return Respuesta booleana y estado HTTP 200
   */
  @GetMapping("/exists")
  public ResponseEntity<Boolean> existsByName(
    @RequestParam String name
  ) {
    boolean exists = locationService.existsByName(name);
    return ResponseEntity.ok(exists);
  }
}