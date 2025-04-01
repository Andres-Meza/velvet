package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.model.dto.UnitOfMeasureDTO.CreateUnitOfMeasure;
import com.uniminuto.velvet.model.dto.UnitOfMeasureDTO.DetailsUnitOfMeasure;
import com.uniminuto.velvet.model.dto.UnitOfMeasureDTO.SimpleUnitOfMeasure;
import com.uniminuto.velvet.model.dto.UnitOfMeasureDTO.UpdateUnitOfMeasure;
import com.uniminuto.velvet.service.interfaces.UnitOfMeasureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v4/unitsOfMeasure")
@RequiredArgsConstructor
public class UnitOfMeasureController {

  private final UnitOfMeasureService unitOfMeasureService;

  /**
   * Crear una nueva unidad de medida.
   * 
   * @param createUnitOfMeasure DTO con los datos para crear la unidad de medida
   * @return Respuesta con la unidad de medida creada y estado HTTP 201
   */
  @PostMapping
  public ResponseEntity<SimpleUnitOfMeasure> createUnitOfMeasure(
    @Valid @RequestBody CreateUnitOfMeasure createUnitOfMeasure
  ) {
    SimpleUnitOfMeasure createdUnitOfMeasure = unitOfMeasureService.createUnitOfMeasure(createUnitOfMeasure);
    return new ResponseEntity<>(createdUnitOfMeasure, HttpStatus.CREATED);
  }

  /**
   * Actualizar una unidad de medida existente.
   * 
   * @param updateUnitOfMeasure DTO con los datos para actualizar la unidad de medida
   * @return Respuesta con la unidad de medida actualizada y estado HTTP 200
   */
  @PutMapping
  public ResponseEntity<SimpleUnitOfMeasure> updateUnitOfMeasure(
    @Valid @RequestBody UpdateUnitOfMeasure updateUnitOfMeasure
  ) {
    SimpleUnitOfMeasure updatedUnitOfMeasure = unitOfMeasureService.updateUnitOfMeasure(updateUnitOfMeasure);
    return ResponseEntity.ok(updatedUnitOfMeasure);
  }

  /**
   * Obtener una unidad de medida por su ID.
   * 
   * @param id Identificador de la unidad de medida
   * @return Detalles de la unidad de medida y estado HTTP 200
   */
  @GetMapping("/{id}")
  public ResponseEntity<DetailsUnitOfMeasure> getUnitOfMeasureById(
    @PathVariable Long id
  ) {
    DetailsUnitOfMeasure unitOfMeasure = unitOfMeasureService.getUnitOfMeasureById(id);
    return ResponseEntity.ok(unitOfMeasure);
  }

  /**
   * Obtener todas las unidades de medida (versión básica).
   * 
   * @return Lista de unidades de medida y estado HTTP 200
   */
  @GetMapping
  public ResponseEntity<List<SimpleUnitOfMeasure>> getAllCategories() {
    List<SimpleUnitOfMeasure> unitOfMeasures = unitOfMeasureService.getAllUnitOfMeasures();
    return ResponseEntity.ok(unitOfMeasures);
  }

  /**
   * Obtener todas las unidades de medida con detalles completos.
   * 
   * @return Lista de unidades de medida con detalles y estado HTTP 200
   */
  @GetMapping("/details")
  public ResponseEntity<List<DetailsUnitOfMeasure>> getAllCategoriesWithDetails() {
    List<DetailsUnitOfMeasure> unitOfMeasures = unitOfMeasureService.getAllUnitOfMeasuresWithDetails();
    return ResponseEntity.ok(unitOfMeasures);
  }

  /**
   * Eliminar una unidad de medida por su ID.
   * 
   * @param id Identificador de la unidad de medida a eliminar
   * @return Estado HTTP 204 si la eliminación fue exitosa
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUnitOfMeasure(
    @PathVariable Long id
  ) {
    boolean deleted = unitOfMeasureService.deleteUnitOfMeasure(id);
    return deleted 
      ? ResponseEntity.noContent().build()
      : ResponseEntity.notFound().build();
  }

  /**
   * Verificar si existe una unidad de medida por nombre.
   * 
   * @param name Nombre de la unidad de medida a verificar
   * @return Respuesta booleana y estado HTTP 200
   */
  @GetMapping("/exists")
  public ResponseEntity<Boolean> existsByName(
    @RequestParam String name
  ) {
    boolean exists = unitOfMeasureService.existsByName(name);
    return ResponseEntity.ok(exists);
  }
}