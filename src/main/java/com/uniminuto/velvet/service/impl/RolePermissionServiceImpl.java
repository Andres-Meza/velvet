package com.uniminuto.velvet.service.impl;

import com.uniminuto.velvet.model.dto.RolePermissionDTO;
import com.uniminuto.velvet.model.entity.Permission;
import com.uniminuto.velvet.model.entity.Role;
import com.uniminuto.velvet.model.entity.RolePermission;
import com.uniminuto.velvet.model.entity.User;
import com.uniminuto.velvet.exception.ResourceNotFoundException;
import com.uniminuto.velvet.model.mapper.RolePermissionMapper;
import com.uniminuto.velvet.model.repository.PermissionRepository;
import com.uniminuto.velvet.model.repository.RolePermissionRepository;
import com.uniminuto.velvet.model.repository.RoleRepository;
import com.uniminuto.velvet.model.repository.UserRepository;
import com.uniminuto.velvet.service.interfaces.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolePermissionServiceImpl implements RolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final RolePermissionMapper rolePermissionMapper;

    @Override
    @Transactional
    public RolePermission assignPermissionToRole(RolePermissionDTO.AssignRolePermission assignRolePermissionDTO) {
        // Verificar si el rol existe
        Role role = roleRepository.findById(assignRolePermissionDTO.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con ID: " + assignRolePermissionDTO.getRoleId()));

        // Verificar si el permiso existe
        Permission permission = permissionRepository.findById(assignRolePermissionDTO.getPermissionId())
                .orElseThrow(() -> new ResourceNotFoundException("Permiso no encontrado con ID: " + assignRolePermissionDTO.getPermissionId()));

        // Verificar si el usuario asignador existe
        User assignedBy = userRepository.findById(assignRolePermissionDTO.getAssignedById())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + assignRolePermissionDTO.getAssignedById()));

        // Verificar si la asignación ya existe
        Optional<RolePermission> existingAssignment = rolePermissionRepository.findByRoleIdAndPermissionId(
                assignRolePermissionDTO.getRoleId(),
                assignRolePermissionDTO.getPermissionId());

        if (existingAssignment.isPresent()) {
            return existingAssignment.get(); // La asignación ya existe, devolver la existente
        }

        // Crear y guardar la nueva asignación
        RolePermission rolePermission = RolePermission.builder()
                .role(role)
                .permission(permission)
                .assignedBy(assignedBy)
                .build();

        return rolePermissionRepository.save(rolePermission);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RolePermission> findById(Long id) {
        return rolePermissionRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RolePermissionDTO.DetailsRolePermission> getDetailsById(Long id) {
        return rolePermissionRepository.findById(id)
                .map(rolePermissionMapper::toDetailsDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolePermissionDTO.DetailsRolePermission> getPermissionsByRoleId(Long roleId) {
        // Verificar si el rol existe
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Rol no encontrado con ID: " + roleId);
        }

        List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleId(roleId);
        return rolePermissionMapper.toDetailsDTOList(rolePermissions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolePermissionDTO.DetailsRolePermission> getRolesByPermissionId(Long permissionId) {
        // Verificar si el permiso existe
        if (!permissionRepository.existsById(permissionId)) {
            throw new ResourceNotFoundException("Permiso no encontrado con ID: " + permissionId);
        }

        List<RolePermission> rolePermissions = rolePermissionRepository.findByPermissionId(permissionId);
        return rolePermissionMapper.toDetailsDTOList(rolePermissions);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasPermission(Long roleId, Long permissionId) {
        return rolePermissionRepository.existsByRoleIdAndPermissionId(roleId, permissionId);
    }

    @Override
    @Transactional
    public void removePermissionFromRole(Long rolePermissionId) {
        if (!rolePermissionRepository.existsById(rolePermissionId)) {
            throw new ResourceNotFoundException("Asignación de rol-permiso no encontrada con ID: " + rolePermissionId);
        }
        rolePermissionRepository.deleteById(rolePermissionId);
    }

    @Override
    @Transactional
    public void removeAllPermissionsFromRole(Long roleId) {
        // Verificar si el rol existe
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Rol no encontrado con ID: " + roleId);
        }

        rolePermissionRepository.deleteByRoleId(roleId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolePermissionDTO.DetailsRolePermission> getAllRolePermissions() {
        List<RolePermission> rolePermissions = rolePermissionRepository.findAll();
        return rolePermissionMapper.toDetailsDTOList(rolePermissions);
    }
}