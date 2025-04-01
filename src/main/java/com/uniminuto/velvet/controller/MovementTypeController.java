package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.model.dto.MovementTypeDTO.CreateMovementType;
import com.uniminuto.velvet.model.dto.MovementTypeDTO.DetailsMovementType;
import com.uniminuto.velvet.model.dto.MovementTypeDTO.SimpleMovementType;
import com.uniminuto.velvet.model.dto.MovementTypeDTO.UpdateMovementType;
import com.uniminuto.velvet.service.interfaces.MovementTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v4/movementTypes")
@RequiredArgsConstructor
public class MovementTypeController {

  private final MovementTypeService movementTypeService;

  /**
   * Crear un nuevo tipo de movimiento.
   * 
   * @param createMovementType DTO con los datos para crear el tipo de movimiento
   * @return Respuesta con el tipo de movimiento creado y estado HTTP 201
   */
  @PostMapping
  public ResponseEntity<SimpleMovementType> createMovementType(
    @Valid @RequestBody CreateMovementType createMovementType
  ) {
    SimpleMovementType createdMovementType = movementTypeService.createMovementType(createMovementType);
    return new ResponseEntity<>(createdMovementType, HttpStatus.CREATED);
  }

  /**
   * Actualizar un tipo de movimiento existente.
   * 
   * @param updateMovementType DTO con los datos para actualizar el tipo de movimiento
   * @return Respuesta con el tipo de movimiento actualizado y estado HTTP 200
   */
  @PutMapping
  public ResponseEntity<SimpleMovementType> updateMovementType(
    @Valid @RequestBody UpdateMovementType updateMovementType
  ) {
    SimpleMovementType updatedMovementType = movementTypeService.updateMovementType(updateMovementType);
    return ResponseEntity.ok(updatedMovementType);
  }

  /**
   * Obtener un tipo de movimiento por su ID.
   * 
   * @param id Identificador del tipo de movimiento
   * @return Detalles del tipo de movimiento y estado HTTP 200
   */
  @GetMapping("/{id}")
  public ResponseEntity<DetailsMovementType> getMovementTypeById(
    @PathVariable Long id
  ) {
    DetailsMovementType movementType = movementTypeService.getMovementTypeById(id);
    return ResponseEntity.ok(movementType);
  }

  /**
   * Obtener todos los tipos de movimiento (versión básica).
   * 
   * @return Lista de tipos de movimiento y estado HTTP 200
   */
  @GetMapping
  public ResponseEntity<List<SimpleMovementType>> getAllCategories() {
    List<SimpleMovementType> movementTypes = movementTypeService.getAllMovementTypes();
    return ResponseEntity.ok(movementTypes);
  }

  /**
   * Obtener todos los tipos de movimiento con detalles completos.
   * 
   * @return Lista de tipos de movimiento con detalles y estado HTTP 200
   */
  @GetMapping("/details")
  public ResponseEntity<List<DetailsMovementType>> getAllCategoriesWithDetails() {
    List<DetailsMovementType> movementTypes = movementTypeService.getAllMovementTypesWithDetails();
    return ResponseEntity.ok(movementTypes);
  }

  /**
   * Eliminar un tipo de movimiento por su ID.
   * 
   * @param id Identificador del tipo de movimiento a eliminar
   * @return Estado HTTP 204 si la eliminación fue exitosa
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMovementType(
    @PathVariable Long id
  ) {
    boolean deleted = movementTypeService.deleteMovementType(id);
    return deleted 
      ? ResponseEntity.noContent().build()
      : ResponseEntity.notFound().build();
  }

  /**
   * Verificar si existe un tipo de movimiento por nombre.
   * 
   * @param name Nombre del tipo de movimiento a verificar
   * @return Respuesta booleana y estado HTTP 200
   */
  @GetMapping("/exists")
  public ResponseEntity<Boolean> existsByName(
    @RequestParam String name
  ) {
    boolean exists = movementTypeService.existsByName(name);
    return ResponseEntity.ok(exists);
  }
}