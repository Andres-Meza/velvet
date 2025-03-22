package com.uniminuto.velvet.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.uniminuto.velvet.model.dto.CategoryDTO.CategoryDetail;
import com.uniminuto.velvet.model.dto.CategoryDTO.CategoryResponse;
import com.uniminuto.velvet.model.dto.CategoryDTO.CreateCategory;
import com.uniminuto.velvet.model.dto.CategoryDTO.UpdateCategory;
import com.uniminuto.velvet.model.entity.Category;

@Mapper(componentModel = "spring", uses = {SubCategoryMapper.class})
public interface CategoryMapper {
  
  CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
  
  // Convertir de CreateCategory a Category
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "subCategories", ignore = true)
  Category toEntity(CreateCategory dto);
  
  // Convertir de UpdateCategory a Category
  @Mapping(target = "subCategories", ignore = true)
  Category toEntity(UpdateCategory dto);
  
  // Actualizar un Category existente con datos de UpdateCategory
  @Mapping(target = "subCategories", ignore = true)
  void updateEntityFromDto(UpdateCategory dto, @MappingTarget Category entity);
  
  // Convertir de Category a CategoryResponse
  CategoryResponse toCategoryResponse(Category entity);
  
  // Convertir lista de Category a lista de CategoryResponse
  List<CategoryResponse> toCategoryResponseList(List<Category> entities);
  
  // Convertir de Category a CategoryDetail
  @Mapping(target = "subCategories", source = "subCategories")
  CategoryDetail toCategoryDetail(Category entity);
  
  // Convertir lista de Category a lista de CategoryDetail
  List<CategoryDetail> toCategoryDetailList(List<Category> entities);
}