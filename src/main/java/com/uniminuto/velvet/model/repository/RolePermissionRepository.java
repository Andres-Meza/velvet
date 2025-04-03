package com.uniminuto.velvet.model.repository;

import com.uniminuto.velvet.model.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    /**
     * Busca una asignación de rol-permiso por IDs de rol y permiso.
     *
     * @param roleId ID del rol
     * @param permissionId ID del permiso
     * @return Optional con la asignación si existe
     */
    Optional<RolePermission> findByRoleIdAndPermissionId(Long roleId, Long permissionId);

    /**
     * Verifica si existe una asignación para un rol y un permiso específicos.
     *
     * @param roleId ID del rol
     * @param permissionId ID del permiso
     * @return true si existe la asignación, false en caso contrario
     */
    boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId);

    /**
     * Busca todas las asignaciones de permisos para un rol específico.
     *
     * @param roleId ID del rol
     * @return Lista de asignaciones rol-permiso
     */
    List<RolePermission> findByRoleId(Long roleId);

    /**
     * Busca todas las asignaciones de roles para un permiso específico.
     *
     * @param permissionId ID del permiso
     * @return Lista de asignaciones rol-permiso
     */
    List<RolePermission> findByPermissionId(Long permissionId);

    /**
     * Elimina todas las asignaciones para un rol específico.
     *
     * @param roleId ID del rol
     */
    void deleteByRoleId(Long roleId);

    /**
     * Elimina todas las asignaciones para un permiso específico.
     *
     * @param permissionId ID del permiso
     */
    void deleteByPermissionId(Long permissionId);
}