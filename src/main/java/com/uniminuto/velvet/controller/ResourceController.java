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

import com.uniminuto.velvet.model.dto.ResourceDTO.CreateResource;
import com.uniminuto.velvet.model.dto.ResourceDTO.DetailsResource;
import com.uniminuto.velvet.model.dto.ResourceDTO.SimpleResource;
import com.uniminuto.velvet.model.dto.ResourceDTO.UpdateResource;
import com.uniminuto.velvet.service.interfaces.ResourceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v4/resources")
@RequiredArgsConstructor
public class ResourceController {

  private final ResourceService resourceService;

  /**
   * Crear un nuevo recurso.
   * 
   * @param createResource DTO con los datos para crear el recurso
   * @return Respuesta con el recurso creado y estado HTTP 201
   */
  @PostMapping
  public ResponseEntity<SimpleResource> createResource(
    @Valid @RequestBody CreateResource createResource
  ) {
    SimpleResource createdResource = resourceService.createResource(createResource);
    return new ResponseEntity<>(createdResource, HttpStatus.CREATED);
  }

  /**
   * Actualizar un recurso existente.
   * 
   * @param updateResource DTO con los datos para actualizar el recurso
   * @return Respuesta con el recurso actualizado y estado HTTP 200
   */
  @PutMapping
  public ResponseEntity<SimpleResource> updateResource(
    @Valid @RequestBody UpdateResource updateResource
  ) {
    SimpleResource updatedResource = resourceService.updateResource(updateResource);
    return ResponseEntity.ok(updatedResource);
  }

  /**
   * Obtener un recurso por su ID.
   * 
   * @param id Identificador del recurso
   * @return Detalles del recurso y estado HTTP 200
   */
  @GetMapping("/{id}")
  public ResponseEntity<DetailsResource> getResourceById(
    @PathVariable Long id
  ) {
    DetailsResource resource = resourceService.getResourceById(id);
    return ResponseEntity.ok(resource);
  }

  /**
   * Obtener todos los recursos (versión básica).
   * 
   * @return Lista de recursos y estado HTTP 200
   */
  @GetMapping
  public ResponseEntity<List<SimpleResource>> getAllCategories() {
    List<SimpleResource> resources = resourceService.getAllResources();
    return ResponseEntity.ok(resources);
  }

  /**
   * Obtener todos los recursos con detalles completos.
   * 
   * @return Lista de recursos con detalles y estado HTTP 200
   */
  @GetMapping("/details")
  public ResponseEntity<List<DetailsResource>> getAllCategoriesWithDetails() {
    List<DetailsResource> resources = resourceService.getAllResourcesWithDetails();
    return ResponseEntity.ok(resources);
  }

  /**
   * Eliminar un recurso por su ID.
   * 
   * @param id Identificador del recurso a eliminar
   * @return Estado HTTP 204 si la eliminación fue exitosa
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteResource(
    @PathVariable Long id
  ) {
    boolean deleted = resourceService.deleteResource(id);
    return deleted 
      ? ResponseEntity.noContent().build()
      : ResponseEntity.notFound().build();
  }

  /**
   * Verificar si existe un recurso por nombre.
   * 
   * @param name Nombre del recurso a verificar
   * @return Respuesta booleana y estado HTTP 200
   */
  @GetMapping("/exists")
  public ResponseEntity<Boolean> existsByName(
    @RequestParam String name
  ) {
    boolean exists = resourceService.existsByName(name);
    return ResponseEntity.ok(exists);
  }
}