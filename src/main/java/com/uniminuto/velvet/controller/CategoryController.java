package com.uniminuto.velvet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uniminuto.velvet.model.dto.CategoryDTO.CategoryDetail;
import com.uniminuto.velvet.model.dto.CategoryDTO.CategoryResponse;
import com.uniminuto.velvet.model.dto.CategoryDTO.CreateCategory;
import com.uniminuto.velvet.model.dto.CategoryDTO.UpdateCategory;
import com.uniminuto.velvet.service.interfaces.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v4/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  /**
   * Crear una nueva categoría.
   * 
   * @param createCategory DTO con los datos para crear la categoría
   * @return Respuesta con la categoría creada y estado HTTP 201
   */
  @PostMapping
  public ResponseEntity<CategoryResponse> createCategory(
    @Valid @RequestBody CreateCategory createCategory
  ) {
    CategoryResponse createdCategory = categoryService.createCategory(createCategory);
    return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
  }

  /**
   * Actualizar una categoría existente.
   * 
   * @param updateCategory DTO con los datos para actualizar la categoría
   * @return Respuesta con la categoría actualizada y estado HTTP 200
   */
  @PutMapping
  public ResponseEntity<CategoryResponse> updateCategory(
    @Valid @RequestBody UpdateCategory updateCategory
  ) {
    CategoryResponse updatedCategory = categoryService.updateCategory(updateCategory);
    return ResponseEntity.ok(updatedCategory);
  }

  /**
   * Obtener una categoría por su ID.
   * 
   * @param id Identificador de la categoría
   * @return Detalles de la categoría y estado HTTP 200
   */
  @GetMapping("/{id}")
  public ResponseEntity<CategoryDetail> getCategoryById(
    @PathVariable Long id
  ) {
    CategoryDetail category = categoryService.getCategoryById(id);
    return ResponseEntity.ok(category);
  }

  /**
   * Obtener todas las categorías (versión básica).
   * 
   * @return Lista de categorías y estado HTTP 200
   */
  @GetMapping
  public ResponseEntity<List<CategoryResponse>> getAllCategories() {
    List<CategoryResponse> categories = categoryService.getAllCategories();
    return ResponseEntity.ok(categories);
  }

  /**
   * Obtener todas las categorías con detalles completos.
   * 
   * @return Lista de categorías con detalles y estado HTTP 200
   */
  @GetMapping("/details")
  public ResponseEntity<List<CategoryDetail>> getAllCategoriesWithDetails() {
    List<CategoryDetail> categories = categoryService.getAllCategoriesWithDetails();
    return ResponseEntity.ok(categories);
  }

  /**
   * Eliminar una categoría por su ID.
   * 
   * @param id Identificador de la categoría a eliminar
   * @return Estado HTTP 204 si la eliminación fue exitosa
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(
    @PathVariable Long id
  ) {
    boolean deleted = categoryService.deleteCategory(id);
    return deleted 
      ? ResponseEntity.noContent().build()
      : ResponseEntity.notFound().build();
  }

  /**
   * Verificar si existe una categoría por nombre.
   * 
   * @param name Nombre de la categoría a verificar
   * @return Respuesta booleana y estado HTTP 200
   */
  @GetMapping("/exists")
  public ResponseEntity<Boolean> existsByName(
    @RequestParam String name
  ) {
    boolean exists = categoryService.existsByName(name);
    return ResponseEntity.ok(exists);
  }
}