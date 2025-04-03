package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.model.dto.PermissionDTO;
import com.uniminuto.velvet.service.interfaces.PermissionService;
import com.uniminuto.velvet.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v4/permissions")
@RequiredArgsConstructor
@Slf4j
public class PermissionController {

    private final PermissionService permissionService;

    /**
     * Crea un nuevo permiso
     *
     * @param createPermissionDTO datos del permiso a crear
     * @return respuesta con el permiso creado
     */
    @PostMapping
    @PreAuthorize("hasAuthority('PERMISSION_CREATE')")
    public ResponseEntity<ApiResponse<PermissionDTO.DetailsPermission>> createPermission(
            @Valid @RequestBody PermissionDTO.CreatePermission createPermissionDTO) {
        log.info("Petición para crear permiso recibida: {}", createPermissionDTO.getName());

        PermissionDTO.DetailsPermission createdPermission = permissionService.createPermission(createPermissionDTO);

        ApiResponse<PermissionDTO.DetailsPermission> response = new ApiResponse<>(
                true,
                "Permiso creado exitosamente",
                createdPermission
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Actualiza un permiso existente
     *
     * @param id ID del permiso
     * @param updatePermissionDTO datos del permiso a actualizar
     * @return respuesta con el permiso actualizado
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_UPDATE')")
    public ResponseEntity<ApiResponse<PermissionDTO.DetailsPermission>> updatePermission(
            @PathVariable Long id,
            @Valid @RequestBody PermissionDTO.UpdatePermission updatePermissionDTO) {
        log.info("Petición para actualizar permiso con ID: {}", id);

        // Asegurar que el ID en la ruta coincida con el del cuerpo
        updatePermissionDTO.setId(id);

        PermissionDTO.DetailsPermission updatedPermission = permissionService.updatePermission(updatePermissionDTO);

        ApiResponse<PermissionDTO.DetailsPermission> response = new ApiResponse<>(
                true,
                "Permiso actualizado exitosamente",
                updatedPermission
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Elimina un permiso por su ID
     *
     * @param id ID del permiso a eliminar
     * @return respuesta de confirmación
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_DELETE')")
    public ResponseEntity<ApiResponse<Void>> deletePermission(@PathVariable Long id) {
        log.info("Petición para eliminar permiso con ID: {}", id);

        permissionService.deletePermission(id);

        ApiResponse<Void> response = new ApiResponse<>(
                true,
                "Permiso eliminado exitosamente",
                null
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un permiso por su ID
     *
     * @param id ID del permiso
     * @return respuesta con el permiso encontrado
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public ResponseEntity<ApiResponse<PermissionDTO.DetailsPermission>> getPermissionById(@PathVariable Long id) {
        log.info("Petición para obtener permiso con ID: {}", id);

        return permissionService.findPermissionById(id)
                .map(permission -> {
                    ApiResponse<PermissionDTO.DetailsPermission> response = new ApiResponse<>(
                            true,
                            "Permiso encontrado",
                            permission
                    );
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse<>(false, "Permiso no encontrado", null)
                ));
    }

    /**
     * Lista todos los permisos
     *
     * @return respuesta con la lista de permisos
     */
    @GetMapping
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public ResponseEntity<ApiResponse<List<PermissionDTO.SimplePermission>>> getAllPermissions() {
        log.info("Petición para listar todos los permisos");

        List<PermissionDTO.SimplePermission> permissions = permissionService.findAllPermissions();

        ApiResponse<List<PermissionDTO.SimplePermission>> response = new ApiResponse<>(
                true,
                "Permisos listados exitosamente",
                permissions
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Lista permisos con paginación
     *
     * @param pageable configuración de paginación
     * @return respuesta con la página de permisos
     */
    @GetMapping("/paged")
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public ResponseEntity<ApiResponse<Page<PermissionDTO.SimplePermission>>> getAllPermissionsPaged(Pageable pageable) {
        log.info("Petición para listar permisos paginados: {}", pageable);

        Page<PermissionDTO.SimplePermission> permissions = permissionService.findAllPermissionsPaged(pageable);

        ApiResponse<Page<PermissionDTO.SimplePermission>> response = new ApiResponse<>(
                true,
                "Permisos paginados listados exitosamente",
                permissions
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Busca permisos por nombre
     *
     * @param name nombre del permiso
     * @return respuesta con el permiso encontrado
     */
    @GetMapping("/by-name")
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public ResponseEntity<ApiResponse<PermissionDTO.DetailsPermission>> getPermissionByName(@RequestParam String name) {
        log.info("Petición para buscar permiso por nombre: {}", name);

        return permissionService.findPermissionByName(name)
                .map(permission -> {
                    ApiResponse<PermissionDTO.DetailsPermission> response = new ApiResponse<>(
                            true,
                            "Permiso encontrado",
                            permission
                    );
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse<>(false, "Permiso no encontrado", null)
                ));
    }

    /**
     * Busca permisos por recurso
     *
     * @param resourceId ID del recurso
     * @return respuesta con la lista de permisos
     */
    @GetMapping("/by-resource/{resourceId}")
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public ResponseEntity<ApiResponse<List<PermissionDTO.SimplePermission>>> getPermissionsByResourceId(
            @PathVariable Long resourceId) {
        log.info("Petición para buscar permisos por recurso ID: {}", resourceId);

        List<PermissionDTO.SimplePermission> permissions = permissionService.findPermissionsByResourceId(resourceId);

        ApiResponse<List<PermissionDTO.SimplePermission>> response = new ApiResponse<>(
                true,
                "Permisos por recurso listados exitosamente",
                permissions
        );

        return ResponseEntity.ok(response);
    }
}
