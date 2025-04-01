package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.model.dto.DocumentTypeDTO.CreateDocumentType;
import com.uniminuto.velvet.model.dto.DocumentTypeDTO.DocumentTypeDetail;
import com.uniminuto.velvet.model.dto.DocumentTypeDTO.UpdateDocumentType;
import com.uniminuto.velvet.service.interfaces.DocumentTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v4/documentTypes")
@RequiredArgsConstructor
public class DocumentTypeController {

  private final DocumentTypeService roleService;

  /**
   * Crear un nuevo rol.
   * 
   * @param createDocumentType DTO con los datos para crear el rol
   * @return Respuesta con el rol creado y estado HTTP 201
   */
  @PostMapping
  public ResponseEntity<DocumentTypeDetail> createDocumentType(
    @Valid @RequestBody CreateDocumentType createDocumentType
  ) {
    DocumentTypeDetail createdDocumentType = roleService.createDocumentType(createDocumentType);
    return new ResponseEntity<>(createdDocumentType, HttpStatus.CREATED);
  }

  /**
   * Actualizar un rol existente.
   * 
   * @param updateDocumentType DTO con los datos para actualizar el rol
   * @return Respuesta con el rol actualizado y estado HTTP 200
   */
  @PutMapping
  public ResponseEntity<DocumentTypeDetail> updateDocumentType(
    @Valid @RequestBody UpdateDocumentType updateDocumentType
  ) {
    DocumentTypeDetail updatedDocumentType = roleService.updateDocumentType(updateDocumentType);
    return ResponseEntity.ok(updatedDocumentType);
  }

  /**
   * Obtener un rol por su ID.
   * 
   * @param id Identificador del rol
   * @return Detalles del rol y estado HTTP 200
   */
  @GetMapping("/{id}")
  public ResponseEntity<DocumentTypeDetail> getDocumentTypeById(
    @PathVariable Long id
  ) {
    DocumentTypeDetail role = roleService.getDocumentTypeById(id);
    return ResponseEntity.ok(role);
  }

  /**
   * Obtener todos los roles (versión básica).
   * 
   * @return Lista de roles y estado HTTP 200
   */
  @GetMapping
  public ResponseEntity<List<DocumentTypeDetail>> getAllCategories() {
    List<DocumentTypeDetail> roles = roleService.getAllDocumentTypes();
    return ResponseEntity.ok(roles);
  }

  /**
   * Eliminar un rol por su ID.
   * 
   * @param id Identificador del rol a eliminar
   * @return Estado HTTP 204 si la eliminación fue exitosa
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDocumentType(
    @PathVariable Long id
  ) {
    boolean deleted = roleService.deleteDocumentType(id);
    return deleted 
      ? ResponseEntity.noContent().build()
      : ResponseEntity.notFound().build();
  }

  /**
   * Verificar si existe un rol por nombre.
   * 
   * @param name Nombre del rol a verificar
   * @return Respuesta booleana y estado HTTP 200
   */
  @GetMapping("/exists")
  public ResponseEntity<Boolean> existsByName(
    @RequestParam String name
  ) {
    boolean exists = roleService.existsByName(name);
    return ResponseEntity.ok(exists);
  }
}