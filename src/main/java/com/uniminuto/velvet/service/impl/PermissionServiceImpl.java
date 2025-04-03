package com.uniminuto.velvet.service.impl;

import com.uniminuto.velvet.model.dto.PermissionDTO;
import com.uniminuto.velvet.model.entity.Permission;
import com.uniminuto.velvet.model.entity.Resource;
import com.uniminuto.velvet.exception.ResourceNotFoundException;
import com.uniminuto.velvet.exception.DuplicateResourceException;
import com.uniminuto.velvet.model.mapper.PermissionMapper;
import com.uniminuto.velvet.model.repository.PermissionRepository;
import com.uniminuto.velvet.model.repository.ResourceRepository;
import com.uniminuto.velvet.service.interfaces.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final ResourceRepository resourceRepository;
    private final PermissionMapper permissionMapper;

    @Override
    @Transactional
    public PermissionDTO.DetailsPermission createPermission(PermissionDTO.CreatePermission createPermissionDTO) {
        log.debug("Iniciando creación de permiso: {}", createPermissionDTO.getName());

        // Verificar si el permiso ya existe
        if (permissionRepository.existsByName(createPermissionDTO.getName())) {
            throw new DuplicateResourceException("Ya existe un permiso con el nombre: " + createPermissionDTO.getName());
        }

        // Verificar si el recurso existe
        resourceRepository.findById(createPermissionDTO.getResourceId())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el recurso con ID: " + createPermissionDTO.getResourceId()));

        // Mapear y guardar la entidad
        Permission permission = permissionMapper.toEntity(createPermissionDTO);
        Permission savedPermission = permissionRepository.save(permission);

        log.info("Permiso creado exitosamente con ID: {}", savedPermission.getId());
        return permissionMapper.toDetailsDTO(savedPermission);
    }

    @Override
    @Transactional
    public PermissionDTO.DetailsPermission updatePermission(PermissionDTO.UpdatePermission updatePermissionDTO) {
        log.debug("Iniciando actualización de permiso con ID: {}", updatePermissionDTO.getId());

        // Buscar permiso existente
        Permission existingPermission = permissionRepository.findById(updatePermissionDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el permiso con ID: " + updatePermissionDTO.getId()));

        // Verificar si el nombre ya está en uso por otro permiso
        if (updatePermissionDTO.getName() != null &&
                !existingPermission.getName().equals(updatePermissionDTO.getName()) &&
                permissionRepository.existsByName(updatePermissionDTO.getName())) {
            throw new DuplicateResourceException("Ya existe un permiso con el nombre: " + updatePermissionDTO.getName());
        }

        // Verificar si el recurso existe si se va a actualizar
        if (updatePermissionDTO.getResourceId() != null) {
            resourceRepository.findById(updatePermissionDTO.getResourceId())
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró el recurso con ID: " + updatePermissionDTO.getResourceId()));
        }

        // Actualizar entidad
        permissionMapper.updateEntityFromDto(updatePermissionDTO, existingPermission);
        Permission updatedPermission = permissionRepository.save(existingPermission);

        log.info("Permiso actualizado exitosamente con ID: {}", updatedPermission.getId());
        return permissionMapper.toDetailsDTO(updatedPermission);
    }

    @Override
    @Transactional
    public void deletePermission(Long id) {
        log.debug("Iniciando eliminación de permiso con ID: {}", id);

        // Verificar si el permiso existe
        if (!permissionRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encontró el permiso con ID: " + id);
        }

        // Eliminar permiso
        permissionRepository.deleteById(id);
        log.info("Permiso eliminado exitosamente con ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PermissionDTO.DetailsPermission> findPermissionById(Long id) {
        log.debug("Buscando permiso por ID: {}", id);
        return permissionRepository.findById(id)
                .map(permissionMapper::toDetailsDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PermissionDTO.DetailsPermission> findPermissionByName(String name) {
        log.debug("Buscando permiso por nombre: {}", name);
        return permissionRepository.findByName(name)
                .map(permissionMapper::toDetailsDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDTO.SimplePermission> findAllPermissions() {
        log.debug("Listando todos los permisos");
        return permissionMapper.toSimpleDTOList(permissionRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PermissionDTO.SimplePermission> findAllPermissionsPaged(Pageable pageable) {
        log.debug("Listando permisos paginados: {}", pageable);
        return permissionRepository.findAll(pageable)
                .map(permissionMapper::toSimpleDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDTO.SimplePermission> findPermissionsByResourceId(Long resourceId) {
        log.debug("Buscando permisos por recurso ID: {}", resourceId);
        Resource resource = new Resource();
        resource.setId(resourceId);

        return permissionMapper.toSimpleDTOList(permissionRepository.findByResource(resource));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return permissionRepository.existsByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return permissionRepository.existsById(id);
    }
}
