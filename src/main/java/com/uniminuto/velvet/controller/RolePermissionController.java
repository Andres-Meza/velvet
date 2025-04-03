package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.exception.ResourceNotFoundException;
import com.uniminuto.velvet.model.dto.RolePermissionDTO;
import com.uniminuto.velvet.model.entity.RolePermission;
import com.uniminuto.velvet.service.interfaces.RolePermissionService;
import com.uniminuto.velvet.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v4/role-permissions")
@RequiredArgsConstructor
public class RolePermissionController {

    private final RolePermissionService rolePermissionService;

    @PostMapping
    public ResponseEntity<ApiResponse<RolePermissionDTO.DetailsRolePermission>> assignPermissionToRole(
            @Valid @RequestBody RolePermissionDTO.AssignRolePermission assignRolePermissionDTO) {

        RolePermission rolePermission = rolePermissionService.assignPermissionToRole(assignRolePermissionDTO);
        RolePermissionDTO.DetailsRolePermission details = rolePermissionService.getDetailsById(rolePermission.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la asignación de permiso"));

        ApiResponse<RolePermissionDTO.DetailsRolePermission> response = new ApiResponse<>(
                true,
                "Permiso asignado al rol exitosamente",
                details
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RolePermissionDTO.DetailsRolePermission>> getById(@PathVariable Long id) {
        RolePermissionDTO.DetailsRolePermission details = rolePermissionService.getDetailsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación de rol-permiso no encontrada con ID: " + id));

        ApiResponse<RolePermissionDTO.DetailsRolePermission> response = new ApiResponse<>(
                true,
                "Asignación de rol-permiso encontrada",
                details
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/roles/{roleId}/permissions")
    public ResponseEntity<ApiResponse<List<RolePermissionDTO.DetailsRolePermission>>> getPermissionsByRoleId(
            @PathVariable Long roleId) {

        List<RolePermissionDTO.DetailsRolePermission> permissions = rolePermissionService.getPermissionsByRoleId(roleId);

        ApiResponse<List<RolePermissionDTO.DetailsRolePermission>> response = new ApiResponse<>(
                true,
                "Permisos del rol obtenidos exitosamente",
                permissions
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/permissions/{permissionId}/roles")
    public ResponseEntity<ApiResponse<List<RolePermissionDTO.DetailsRolePermission>>> getRolesByPermissionId(
            @PathVariable Long permissionId) {

        List<RolePermissionDTO.DetailsRolePermission> roles = rolePermissionService.getRolesByPermissionId(permissionId);

        ApiResponse<List<RolePermissionDTO.DetailsRolePermission>> response = new ApiResponse<>(
                true,
                "Roles con el permiso obtenidos exitosamente",
                roles
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/check")
    public ResponseEntity<ApiResponse<Boolean>> checkPermission(
            @RequestParam Long roleId,
            @RequestParam Long permissionId) {

        boolean hasPermission = rolePermissionService.hasPermission(roleId, permissionId);

        ApiResponse<Boolean> response = new ApiResponse<>(
                true,
                hasPermission ? "El rol tiene el permiso" : "El rol no tiene el permiso",
                hasPermission
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> removePermissionFromRole(@PathVariable Long id) {
        rolePermissionService.removePermissionFromRole(id);

        ApiResponse<Void> response = new ApiResponse<>(
                true,
                "Permiso removido del rol exitosamente",
                null
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/roles/{roleId}/permissions")
    public ResponseEntity<ApiResponse<Void>> removeAllPermissionsFromRole(@PathVariable Long roleId) {
        rolePermissionService.removeAllPermissionsFromRole(roleId);

        ApiResponse<Void> response = new ApiResponse<>(
                true,
                "Todos los permisos del rol han sido removidos exitosamente",
                null
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RolePermissionDTO.DetailsRolePermission>>> getAllRolePermissions() {
        List<RolePermissionDTO.DetailsRolePermission> allRolePermissions = rolePermissionService.getAllRolePermissions();

        ApiResponse<List<RolePermissionDTO.DetailsRolePermission>> response = new ApiResponse<>(
                true,
                "Todas las asignaciones de roles y permisos obtenidas exitosamente",
                allRolePermissions
        );

        return ResponseEntity.ok(response);
    }
}
