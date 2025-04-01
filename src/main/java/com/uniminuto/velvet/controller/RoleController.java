package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.model.dto.RoleDTO.CreateRole;
import com.uniminuto.velvet.model.dto.RoleDTO.DetailsRole;
import com.uniminuto.velvet.model.dto.RoleDTO.SimpleRole;
import com.uniminuto.velvet.model.dto.RoleDTO.UpdateRole;
import com.uniminuto.velvet.service.interfaces.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v4/roles")
@RequiredArgsConstructor
public class RoleController {

  private final RoleService roleService;

  /**
   * Crear un nuevo rol.
   * 
   * @param createRole DTO con los datos para crear el rol
   * @return Respuesta con el rol creado y estado HTTP 201
   */
  @PostMapping
  public ResponseEntity<SimpleRole> createRole(
    @Valid @RequestBody CreateRole createRole
  ) {
    SimpleRole createdRole = roleService.createRole(createRole);
    return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
  }

  /**
   * Actualizar un rol existente.
   * 
   * @param updateRole DTO con los datos para actualizar el rol
   * @return Respuesta con el rol actualizado y estado HTTP 200
   */
  @PutMapping
  public ResponseEntity<SimpleRole> updateRole(
    @Valid @RequestBody UpdateRole updateRole
  ) {
    SimpleRole updatedRole = roleService.updateRole(updateRole);
    return ResponseEntity.ok(updatedRole);
  }

  /**
   * Obtener un rol por su ID.
   * 
   * @param id Identificador del rol
   * @return Detalles del rol y estado HTTP 200
   */
  @GetMapping("/{id}")
  public ResponseEntity<DetailsRole> getRoleById(
    @PathVariable Long id
  ) {
    DetailsRole role = roleService.getRoleById(id);
    return ResponseEntity.ok(role);
  }

  /**
   * Obtener todos los roles (versión básica).
   * 
   * @return Lista de roles y estado HTTP 200
   */
  @GetMapping
  public ResponseEntity<List<SimpleRole>> getAllCategories() {
    List<SimpleRole> roles = roleService.getAllRoles();
    return ResponseEntity.ok(roles);
  }

  /**
   * Obtener todos los roles con detalles completos.
   * 
   * @return Lista de roles con detalles y estado HTTP 200
   */
  @GetMapping("/details")
  public ResponseEntity<List<DetailsRole>> getAllCategoriesWithDetails() {
    List<DetailsRole> roles = roleService.getAllRolesWithDetails();
    return ResponseEntity.ok(roles);
  }

  /**
   * Eliminar un rol por su ID.
   * 
   * @param id Identificador del rol a eliminar
   * @return Estado HTTP 204 si la eliminación fue exitosa
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRole(
    @PathVariable Long id
  ) {
    boolean deleted = roleService.deleteRole(id);
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