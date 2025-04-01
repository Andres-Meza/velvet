package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.model.dto.ProductTypeDTO.CreateProductType;
import com.uniminuto.velvet.model.dto.ProductTypeDTO.DetailsProductType;
import com.uniminuto.velvet.model.dto.ProductTypeDTO.SimpleProductType;
import com.uniminuto.velvet.model.dto.ProductTypeDTO.UpdateProductType;
import com.uniminuto.velvet.service.interfaces.ProductTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v4/productTypes")
@RequiredArgsConstructor
public class ProductTypeController {

  private final ProductTypeService productTypeService;

  /**
   * Crear un nuevo tipo de producto.
   * 
   * @param createProductType DTO con los datos para crear el tipo de producto
   * @return Respuesta con el tipo de producto creada y estado HTTP 201
   */
  @PostMapping
  public ResponseEntity<SimpleProductType> createProductType(
    @Valid @RequestBody CreateProductType createProductType
  ) {
    SimpleProductType createdProductType = productTypeService.createProductType(createProductType);
    return new ResponseEntity<>(createdProductType, HttpStatus.CREATED);
  }

  /**
   * Actualizar un tipo de producto existente.
   * 
   * @param updateProductType DTO con los datos para actualizar el tipo de producto
   * @return Respuesta con el tipo de producto actualizada y estado HTTP 200
   */
  @PutMapping
  public ResponseEntity<SimpleProductType> updateProductType(
    @Valid @RequestBody UpdateProductType updateProductType
  ) {
    SimpleProductType updatedProductType = productTypeService.updateProductType(updateProductType);
    return ResponseEntity.ok(updatedProductType);
  }

  /**
   * Obtener un tipo de producto por su ID.
   * 
   * @param id Identificador del tipo de producto
   * @return Detalles del tipo de producto y estado HTTP 200
   */
  @GetMapping("/{id}")
  public ResponseEntity<DetailsProductType> getProductTypeById(
    @PathVariable Long id
  ) {
    DetailsProductType productType = productTypeService.getProductTypeById(id);
    return ResponseEntity.ok(productType);
  }

  /**
   * Obtener todos los tipos de producto (versión básica).
   * 
   * @return Lista de tipos de producto y estado HTTP 200
   */
  @GetMapping
  public ResponseEntity<List<SimpleProductType>> getAllCategories() {
    List<SimpleProductType> productTypes = productTypeService.getAllProductTypes();
    return ResponseEntity.ok(productTypes);
  }

  /**
   * Obtener todos los tipos de producto con detalles completos.
   * 
   * @return Lista de tipos de producto con detalles y estado HTTP 200
   */
  @GetMapping("/details")
  public ResponseEntity<List<DetailsProductType>> getAllCategoriesWithDetails() {
    List<DetailsProductType> productTypes = productTypeService.getAllProductTypesWithDetails();
    return ResponseEntity.ok(productTypes);
  }

  /**
   * Eliminar un tipo de producto por su ID.
   * 
   * @param id Identificador del tipo de producto a eliminar
   * @return Estado HTTP 204 si la eliminación fue exitosa
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProductType(
    @PathVariable Long id
  ) {
    boolean deleted = productTypeService.deleteProductType(id);
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
    boolean exists = productTypeService.existsByName(name);
    return ResponseEntity.ok(exists);
  }
}