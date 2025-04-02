package com.uniminuto.velvet.service.impl;

import com.uniminuto.velvet.exception.ResourceNotFoundException;
import com.uniminuto.velvet.model.dto.SubCategoryDTO;
import com.uniminuto.velvet.model.entity.SubCategory;
import com.uniminuto.velvet.model.mapper.SubCategoryMapper;
import com.uniminuto.velvet.model.repository.CategoryRepository;
import com.uniminuto.velvet.model.repository.SubCategoryRepository;
import com.uniminuto.velvet.service.interfaces.SubCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubCategoryServiceImpl implements SubCategoryService {

  private final SubCategoryRepository subCategoryRepository;
  private final CategoryRepository categoryRepository;
  private final SubCategoryMapper subCategoryMapper;

  @Override
  @Transactional
  public SubCategoryDTO.DetailsSubCategory create(SubCategoryDTO.CreateSubCategory createDTO) {
    log.debug("Creando subcategoría con nombre: {}", createDTO.getName());

    // Verificar si existe la categoría
    categoryRepository.findById(createDTO.getCategoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + createDTO.getCategoryId()));

    // Convertir DTO a entidad
    SubCategory subCategory = subCategoryMapper.toEntity(createDTO);

    // Guardar en base de datos
    SubCategory savedSubCategory = subCategoryRepository.save(subCategory);

    // Convertir entidad a DTO de respuesta
    return subCategoryMapper.toDetailsDTO(savedSubCategory);
  }

  @Override
  @Transactional
  public SubCategoryDTO.DetailsSubCategory update(SubCategoryDTO.UpdateSubCategory updateDTO) {
    log.debug("Actualizando subcategoría con ID: {}", updateDTO.getId());

    // Verificar si existe la categoría
    categoryRepository.findById(updateDTO.getCategoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + updateDTO.getCategoryId()));

    // Buscar la subcategoría existente
    SubCategory existingSubCategory = subCategoryRepository.findById(updateDTO.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Subcategoría no encontrada con ID: " + updateDTO.getId()));

    // Actualizar la entidad con los datos del DTO
    subCategoryMapper.updateEntityFromDTO(updateDTO, existingSubCategory);

    // Guardar los cambios
    SubCategory updatedSubCategory = subCategoryRepository.save(existingSubCategory);

    // Convertir entidad actualizada a DTO de respuesta
    return subCategoryMapper.toDetailsDTO(updatedSubCategory);
  }

  @Override
  @Transactional
  public boolean deleteById(Long id) {
    log.debug("Eliminando subcategoría con ID: {}", id);

    if (!subCategoryRepository.existsById(id)) {
      log.warn("Intento de eliminar subcategoría inexistente con ID: {}", id);
      return false;
    }

    subCategoryRepository.deleteById(id);
    return true;
  }

  @Override
  public Optional<SubCategoryDTO.DetailsSubCategory> findById(Long id) {
    log.debug("Buscando subcategoría con ID: {}", id);

    return subCategoryRepository.findById(id)
            .map(subCategoryMapper::toDetailsDTO);
  }

  @Override
  public Page<SubCategoryDTO.DetailsSubCategory> findAll(Pageable pageable) {
    log.debug("Obteniendo todas las subcategorías con paginación");

    return subCategoryRepository.findAll(pageable)
            .map(subCategoryMapper::toDetailsDTO);
  }

  @Override
  public List<SubCategoryDTO.SimpleSubCategory> findAllByCategoryId(Long categoryId) {
    log.debug("Obteniendo subcategorías para la categoría con ID: {}", categoryId);

    // Verificar si existe la categoría
    if (!categoryRepository.existsById(categoryId)) {
      throw new ResourceNotFoundException("Categoría no encontrada con ID: " + categoryId);
    }

    return subCategoryRepository.findByCategoryId(categoryId)
            .stream()
            .map(subCategoryMapper::toSimpleDTO)
            .collect(Collectors.toList());
  }

  @Override
  public Page<SubCategoryDTO.DetailsSubCategory> findByNameContaining(String name, Pageable pageable) {
    log.debug("Buscando subcategorías que contengan: '{}'", name);

    return subCategoryRepository.findByNameContainingIgnoreCase(name, pageable)
            .map(subCategoryMapper::toDetailsDTO);
  }
}