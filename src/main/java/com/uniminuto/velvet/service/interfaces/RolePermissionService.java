package com.uniminuto.velvet.service.interfaces;

import com.uniminuto.velvet.model.dto.RolePermissionDTO;
import com.uniminuto.velvet.model.entity.RolePermission;

import java.util.List;
import java.util.Optional;

public interface RolePermissionService {
    /**
     * Asigna un permiso a un rol.
     *
     * @param assignRolePermissionDTO DTO con la información para asignar el permiso al rol
     * @return El objeto RolePermission creado
     */
    RolePermission assignPermissionToRole(RolePermissionDTO.AssignRolePermission assignRolePermissionDTO);

    /**
     * Obtiene un RolePermission por su ID.
     *
     * @param id ID del RolePermission
     * @return Optional con el RolePermission si existe
     */
    Optional<RolePermission> findById(Long id);

    /**
     * Obtiene detalles de un RolePermission por su ID.
     *
     * @param id ID del RolePermission
     * @return DTO con los detalles del RolePermission
     */
    Optional<RolePermissionDTO.DetailsRolePermission> getDetailsById(Long id);

    /**
     * Obtiene todos los permisos asignados a un rol.
     *
     * @param roleId ID del rol
     * @return Lista de DTOs con los detalles de los permisos asignados
     */
    List<RolePermissionDTO.DetailsRolePermission> getPermissionsByRoleId(Long roleId);

    /**
     * Obtiene todos los roles a los que se ha asignado un permiso.
     *
     * @param permissionId ID del permiso
     * @return Lista de DTOs con los detalles de los roles asignados
     */
    List<RolePermissionDTO.DetailsRolePermission> getRolesByPermissionId(Long permissionId);

    /**
     * Verifica si un rol tiene un permiso específico.
     *
     * @param roleId ID del rol
     * @param permissionId ID del permiso
     * @return true si el rol tiene el permiso, false en caso contrario
     */
    boolean hasPermission(Long roleId, Long permissionId);

    /**
     * Elimina la asignación de un permiso a un rol.
     *
     * @param rolePermissionId ID de la asignación rol-permiso
     */
    void removePermissionFromRole(Long rolePermissionId);

    /**
     * Elimina todas las asignaciones de permisos para un rol específico.
     *
     * @param roleId ID del rol
     */
    void removeAllPermissionsFromRole(Long roleId);

    /**
     * Obtiene todas las asignaciones de roles y permisos.
     *
     * @return Lista de DTOs con los detalles de todas las asignaciones
     */
    List<RolePermissionDTO.DetailsRolePermission> getAllRolePermissions();
}