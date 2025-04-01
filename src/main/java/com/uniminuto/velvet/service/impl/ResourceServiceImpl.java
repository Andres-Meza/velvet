package com.uniminuto.velvet.service.impl;

import com.uniminuto.velvet.model.dto.ResourceDTO;
import com.uniminuto.velvet.model.entity.Resource;
import com.uniminuto.velvet.model.mapper.ResourceMapper;
import com.uniminuto.velvet.model.repository.ResourceRepository;
import com.uniminuto.velvet.service.interfaces.ResourceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

  private final ResourceRepository resourceRepository;
  private final ResourceMapper resourceMapper;

  @Override
  @Transactional
  public ResourceDTO.SimpleResource createResource(ResourceDTO.CreateResource createResource) {
    // Verificar si ya existe un recurso con el mismo nombre
    if (resourceRepository.existsByName((createResource.getName()))) {
      throw new RuntimeException("Ya existe una categoría con este nombre");
    }

    // Convertir DTO a entidad
    Resource resource = resourceMapper.toEntity(createResource);
    
    // Guardar en base de datos
    Resource savedResource = resourceRepository.save(resource);
    
    // Convertir entidad guardada a DTO de respuesta
    return resourceMapper.toSimpleDto(savedResource);
  }

  @Override
  @Transactional
  public ResourceDTO.SimpleResource updateResource(ResourceDTO.UpdateResource updateResource) {
    // Buscar el recurso existente
    Resource existingResource = resourceRepository.findById(updateResource.getId())
      .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

    // Verificar si el nuevo nombre ya existe (si es diferente)
    if (!existingResource.getName().equals(updateResource.getName()) &&
        resourceRepository.existsByName(updateResource.getName())) {
      throw new RuntimeException("Ya existe una categoría con este nombre");
    }

    // Actualizar la entidad
    resourceMapper.updateEntityFromDto(updateResource, existingResource);
    
    // Guardar cambios
    Resource updatedResource = resourceRepository.save(existingResource);
    
    // Convertir a DTO de respuesta
    return resourceMapper.toSimpleDto(updatedResource);
  }

  @Override
  public ResourceDTO.DetailsResource getResourceById(Long id) {
    // Buscar recurso por ID
    Resource resource = resourceRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    
    // Convertir a DTO de detalle
    return resourceMapper.toDetailsDto(resource);
  }

  @Override
  public List<ResourceDTO.SimpleResource> getAllResources() {
    // Obtener todos los recursos y convertir a lista de respuestas
    List<Resource> resources = resourceRepository.findAll();
    return resourceMapper.toSimpleDtoList(resources);
  }

  @Override
  public List<ResourceDTO.DetailsResource> getAllResourcesWithDetails() {
    // Obtener todos los recursos con detalles
    List<Resource> resources = resourceRepository.findAll();
    return resourceMapper.toResourceDetailList(resources);
  }

  @Override
  @Transactional
  public boolean deleteResource(Long id) {
    // Verificar si el recurso existe
    Optional<Resource> resourceOptional = resourceRepository.findById(id);
    
    if (resourceOptional.isPresent()) {
      resourceRepository.delete(resourceOptional.get());
      return true;
    }
    
    return false;
  }

  @Override
  public boolean existsByName(String name) {
    // Verificar existencia de un recurso por nombre
    return resourceRepository.existsByName(name);
  }
}