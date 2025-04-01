package com.uniminuto.velvet.service.interfaces;

import com.uniminuto.velvet.model.dto.ResourceDTO;

import java.util.List;

public interface ResourceService {
	ResourceDTO.SimpleResource createResource(ResourceDTO.CreateResource createResource);
	ResourceDTO.SimpleResource updateResource(ResourceDTO.UpdateResource updateResource);
	ResourceDTO.DetailsResource getResourceById(Long id);
	List<ResourceDTO.SimpleResource> getAllResources();
	List<ResourceDTO.DetailsResource> getAllResourcesWithDetails();
	boolean deleteResource(Long id);
	boolean existsByName(String name);
}