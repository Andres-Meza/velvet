package com.uniminuto.velvet.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.uniminuto.velvet.model.dto.CategoryDTO.CategoryDetail;
import com.uniminuto.velvet.model.dto.CategoryDTO.CategoryResponse;
import com.uniminuto.velvet.model.dto.CategoryDTO.CreateCategory;
import com.uniminuto.velvet.model.dto.CategoryDTO.UpdateCategory;
import com.uniminuto.velvet.model.entity.Category;
import com.uniminuto.velvet.model.mapper.CategoryMapper;
import com.uniminuto.velvet.model.repository.CategoryRepository;
import com.uniminuto.velvet.service.interfaces.CategoryService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  @Override
  @Transactional
  public CategoryResponse createCategory(CreateCategory createCategory) {
    // Verificar si ya existe una categoría con el mismo nombre
    if (categoryRepository.existsByName(createCategory.getName())) {
      throw new RuntimeException("Ya existe una categoría con este nombre");
    }

    // Convertir DTO a entidad
    Category category = categoryMapper.toEntity(createCategory);
    
    // Guardar en base de datos
    Category savedCategory = categoryRepository.save(category);
    
    // Convertir entidad guardada a DTO de respuesta
    return categoryMapper.toCategoryResponse(savedCategory);
  }

  @Override
  @Transactional
  public CategoryResponse updateCategory(UpdateCategory updateCategory) {
    // Buscar la categoría existente
    Category existingCategory = categoryRepository.findById(updateCategory.getId())
      .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

    // Verificar si el nuevo nombre ya existe (si es diferente)
    if (!existingCategory.getName().equals(updateCategory.getName()) && 
        categoryRepository.existsByName(updateCategory.getName())) {
      throw new RuntimeException("Ya existe una categoría con este nombre");
    }

    // Actualizar la entidad
    categoryMapper.updateEntityFromDto(updateCategory, existingCategory);
    
    // Guardar cambios
    Category updatedCategory = categoryRepository.save(existingCategory);
    
    // Convertir a DTO de respuesta
    return categoryMapper.toCategoryResponse(updatedCategory);
  }

  @Override
  public CategoryDetail getCategoryById(Long id) {
    // Buscar categoría por ID
    Category category = categoryRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    
    // Convertir a DTO de detalle
    return categoryMapper.toCategoryDetail(category);
  }

  @Override
  public List<CategoryResponse> getAllCategories() {
    // Obtener todas las categorías y convertir a lista de respuestas
    List<Category> categories = categoryRepository.findAll();
    return categoryMapper.toCategoryResponseList(categories);
  }

  @Override
  public List<CategoryDetail> getAllCategoriesWithDetails() {
    // Obtener todas las categorías con detalles
    List<Category> categories = categoryRepository.findAll();
    return categoryMapper.toCategoryDetailList(categories);
  }

  @Override
  @Transactional
  public boolean deleteCategory(Long id) {
    // Verificar si la categoría existe
    Optional<Category> categoryOptional = categoryRepository.findById(id);
    
    if (categoryOptional.isPresent()) {
      categoryRepository.delete(categoryOptional.get());
      return true;
    }
    
    return false;
  }

  @Override
  public boolean existsByName(String name) {
    // Verificar existencia de categoría por nombre
    return categoryRepository.existsByName(name);
  }
}