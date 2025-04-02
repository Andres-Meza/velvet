package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.model.dto.SubCategoryDTO;
import com.uniminuto.velvet.service.interfaces.SubCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v4/subcategories")
@RequiredArgsConstructor
@Tag(name = "Subcategorías", description = "API para gestionar subcategorías")
public class SubCategoryController {

  private final SubCategoryService subCategoryService;

  @Operation(summary = "Crear una nueva subcategoría")
  @ApiResponses({
          @ApiResponse(responseCode = "201", description = "Subcategoría creada correctamente"),
          @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
          @ApiResponse(responseCode = "404", description = "Categoría padre no encontrada", content = @Content)
  })
  @PostMapping
  public ResponseEntity<SubCategoryDTO.DetailsSubCategory> createSubCategory(
          @Valid @RequestBody SubCategoryDTO.CreateSubCategory createDTO) {
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(subCategoryService.create(createDTO));
  }

  @Operation(summary = "Actualizar una subcategoría existente")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Subcategoría actualizada correctamente"),
          @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
          @ApiResponse(responseCode = "404", description = "Subcategoría o categoría padre no encontrada", content = @Content)
  })
  @PutMapping
  public ResponseEntity<SubCategoryDTO.DetailsSubCategory> updateSubCategory(
          @Valid @RequestBody SubCategoryDTO.UpdateSubCategory updateDTO) {
    return ResponseEntity.ok(subCategoryService.update(updateDTO));
  }

  @Operation(summary = "Eliminar una subcategoría por su ID")
  @ApiResponses({
          @ApiResponse(responseCode = "204", description = "Subcategoría eliminada correctamente"),
          @ApiResponse(responseCode = "404", description = "Subcategoría no encontrada", content = @Content)
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSubCategory(
          @Parameter(description = "ID de la subcategoría") @PathVariable Long id) {
    if (subCategoryService.deleteById(id)) {
      return ResponseEntity.noContent().build();
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subcategoría no encontrada con ID: " + id);
    }
  }

  @Operation(summary = "Obtener una subcategoría por su ID")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Subcategoría encontrada"),
          @ApiResponse(responseCode = "404", description = "Subcategoría no encontrada", content = @Content)
  })
  @GetMapping("/{id}")
  public ResponseEntity<SubCategoryDTO.DetailsSubCategory> getSubCategoryById(
          @Parameter(description = "ID de la subcategoría") @PathVariable Long id) {
    return subCategoryService.findById(id)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Subcategoría no encontrada con ID: " + id));
  }

  @Operation(summary = "Obtener todas las subcategorías con paginación")
  @GetMapping
  public ResponseEntity<Page<SubCategoryDTO.DetailsSubCategory>> getAllSubCategories(
          @PageableDefault(size = 10, sort = "name") Pageable pageable) {
    return ResponseEntity.ok(subCategoryService.findAll(pageable));
  }

  @Operation(summary = "Obtener subcategorías por nombre (conteniendo texto)")
  @GetMapping("/search")
  public ResponseEntity<Page<SubCategoryDTO.DetailsSubCategory>> searchSubCategories(
          @Parameter(description = "Texto a buscar en el nombre") @RequestParam String name,
          @PageableDefault(size = 10, sort = "name") Pageable pageable) {
    return ResponseEntity.ok(subCategoryService.findByNameContaining(name, pageable));
  }

  @Operation(summary = "Obtener todas las subcategorías de una categoría específica")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Lista de subcategorías"),
          @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content)
  })
  @GetMapping("/category/{categoryId}")
  public ResponseEntity<List<SubCategoryDTO.SimpleSubCategory>> getSubCategoriesByCategoryId(
          @Parameter(description = "ID de la categoría") @PathVariable Long categoryId) {
    return ResponseEntity.ok(subCategoryService.findAllByCategoryId(categoryId));
  }
}