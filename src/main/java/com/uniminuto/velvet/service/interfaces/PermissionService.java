package com.uniminuto.velvet.service.interfaces;

import com.uniminuto.velvet.model.dto.PermissionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PermissionService {

    /**
     * Crea un nuevo permiso
     *
     * @param createPermissionDTO datos del permiso a crear
     * @return permiso creado con detalles
     */
    PermissionDTO.DetailsPermission createPermission(PermissionDTO.CreatePermission createPermissionDTO);

    /**
     * Actualiza un permiso existente
     *
     * @param updatePermissionDTO datos del permiso a actualizar
     * @return permiso actualizado con detalles
     */
    PermissionDTO.DetailsPermission updatePermission(PermissionDTO.UpdatePermission updatePermissionDTO);

    /**
     * Elimina un permiso por su id
     *
     * @param id identificador del permiso
     */
    void deletePermission(Long id);

    /**
     * Busca un permiso por su id
     *
     * @param id identificador del permiso
     * @return Optional con el permiso si existe
     */
    Optional<PermissionDTO.DetailsPermission> findPermissionById(Long id);

    /**
     * Busca un permiso por su nombre
     *
     * @param name nombre del permiso
     * @return Optional con el permiso si existe
     */
    Optional<PermissionDTO.DetailsPermission> findPermissionByName(String name);

    /**
     * Lista todos los permisos en formato simple
     *
     * @return lista de permisos simples
     */
    List<PermissionDTO.SimplePermission> findAllPermissions();

    /**
     * Lista todos los permisos paginados en formato simple
     *
     * @param pageable configuración de paginación
     * @return página de permisos simples
     */
    Page<PermissionDTO.SimplePermission> findAllPermissionsPaged(Pageable pageable);

    /**
     * Busca todos los permisos asociados a un recurso
     *
     * @param resourceId identificador del recurso
     * @return lista de permisos simples
     */
    List<PermissionDTO.SimplePermission> findPermissionsByResourceId(Long resourceId);

    /**
     * Verifica si existe un permiso con el nombre especificado
     *
     * @param name nombre del permiso
     * @return true si existe, false en caso contrario
     */
    boolean existsByName(String name);

    /**
     * Verifica si existe un permiso con el id especificado
     *
     * @param id identificador del permiso
     * @return true si existe, false en caso contrario
     */
    boolean existsById(Long id);
}
