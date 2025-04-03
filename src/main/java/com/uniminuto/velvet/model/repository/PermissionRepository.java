package com.uniminuto.velvet.model.repository;

import com.uniminuto.velvet.model.entity.Permission;
import com.uniminuto.velvet.model.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    /**
     * Busca un permiso por su nombre
     *
     * @param name nombre del permiso
     * @return Optional con el permiso si existe
     */
    Optional<Permission> findByName(String name);

    /**
     * Verifica si existe un permiso con el nombre especificado
     *
     * @param name nombre del permiso
     * @return true si existe, false en caso contrario
     */
    boolean existsByName(String name);

    /**
     * Busca permisos por recurso
     *
     * @param resource entidad recurso
     * @return lista de permisos asociados al recurso
     */
    List<Permission> findByResource(Resource resource);

    /**
     * Busca permisos por el ID del recurso
     *
     * @param resourceId ID del recurso
     * @return lista de permisos asociados al recurso
     */
    List<Permission> findByResourceId(Long resourceId);
}