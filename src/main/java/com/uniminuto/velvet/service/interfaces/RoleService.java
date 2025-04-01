package com.uniminuto.velvet.service.interfaces;

import com.uniminuto.velvet.model.dto.RoleDTO;

import java.util.List;

public interface RoleService {
	RoleDTO.SimpleRole createRole(RoleDTO.CreateRole createRole);
	RoleDTO.SimpleRole updateRole(RoleDTO.UpdateRole updateRole);
	RoleDTO.DetailsRole getRoleById(Long id);
	List<RoleDTO.SimpleRole> getAllRoles();
	List<RoleDTO.DetailsRole> getAllRolesWithDetails();
	boolean deleteRole(Long id);
	boolean existsByName(String name);
}