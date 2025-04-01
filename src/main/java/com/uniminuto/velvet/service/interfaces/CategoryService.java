package com.uniminuto.velvet.service.interfaces;

import java.util.List;

import com.uniminuto.velvet.model.dto.CategoryDTO.CategoryDetail;
import com.uniminuto.velvet.model.dto.CategoryDTO.CategoryResponse;
import com.uniminuto.velvet.model.dto.CategoryDTO.CreateCategory;
import com.uniminuto.velvet.model.dto.CategoryDTO.UpdateCategory;

public interface CategoryService {
	CategoryResponse createCategory(CreateCategory createCategory);
	CategoryResponse updateCategory(UpdateCategory updateCategory);
	CategoryDetail getCategoryById(Long id);
	List<CategoryResponse> getAllCategories();
	List<CategoryDetail> getAllCategoriesWithDetails();
	boolean deleteCategory(Long id);
	boolean existsByName(String name);
}