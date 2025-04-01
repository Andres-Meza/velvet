package com.uniminuto.velvet.service.impl;

import com.uniminuto.velvet.model.dto.RoleDTO;
import com.uniminuto.velvet.model.entity.Role;
import com.uniminuto.velvet.model.mapper.RoleMapper;
import com.uniminuto.velvet.model.repository.RoleRepository;
import com.uniminuto.velvet.service.interfaces.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;
  private final RoleMapper roleMapper;

  @Override
  @Transactional
  public RoleDTO.SimpleRole createRole(RoleDTO.CreateRole createRole) {
    // Verificar si ya existe un recurso con el mismo nombre
    if (roleRepository.existsByName((createRole.getName()))) {
      throw new RuntimeException("Ya existe un tipo de producto con este nombre");
    }

    // Convertir DTO a entidad
    Role role = roleMapper.toEntity(createRole);
    
    // Guardar en base de datos
    Role savedRole = roleRepository.save(role);
    
    // Convertir entidad guardada a DTO de respuesta
    return roleMapper.toSimpleDto(savedRole);
  }

  @Override
  @Transactional
  public RoleDTO.SimpleRole updateRole(RoleDTO.UpdateRole updateRole) {
    // Buscar el recurso existente
    Role existingRole = roleRepository.findById(updateRole.getId())
      .orElseThrow(() -> new RuntimeException("Tipo de producto no encontrado"));

    // Verificar si el nuevo nombre ya existe (si es diferente)
    if (!existingRole.getName().equals(updateRole.getName()) &&
        roleRepository.existsByName(updateRole.getName())) {
      throw new RuntimeException("Ya existe un tipo de producto con este nombre");
    }

    // Actualizar la entidad
    roleMapper.updateEntityFromDto(updateRole, existingRole);
    
    // Guardar cambios
    Role updatedRole = roleRepository.save(existingRole);
    
    // Convertir a DTO de respuesta
    return roleMapper.toSimpleDto(updatedRole);
  }

  @Override
  public RoleDTO.DetailsRole getRoleById(Long id) {
    // Buscar recurso por ID
    Role role = roleRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Tipo de producto no encontrado"));
    
    // Convertir a DTO de detalle
    return roleMapper.toDetailsDto(role);
  }

  @Override
  public List<RoleDTO.SimpleRole> getAllRoles() {
    // Obtener todos los recursos y convertir a lista de respuestas
    List<Role> roles = roleRepository.findAll();
    return roleMapper.toSimpleDtoList(roles);
  }

  @Override
  public List<RoleDTO.DetailsRole> getAllRolesWithDetails() {
    // Obtener todos los recursos con detalles
    List<Role> roles = roleRepository.findAll();
    return roleMapper.toDetailsDtoList(roles);
  }

  @Override
  @Transactional
  public boolean deleteRole(Long id) {
    // Verificar si el recurso existe
    Optional<Role> roleOptional = roleRepository.findById(id);
    
    if (roleOptional.isPresent()) {
      roleRepository.delete(roleOptional.get());
      return true;
    }
    
    return false;
  }

  @Override
  public boolean existsByName(String name) {
    // Verificar existencia de un recurso por nombre
    return roleRepository.existsByName(name);
  }
}