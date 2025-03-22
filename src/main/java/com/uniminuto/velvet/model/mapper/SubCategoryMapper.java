package com.uniminuto.velvet.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.uniminuto.velvet.model.dto.SubCategoryDTO;
import com.uniminuto.velvet.model.entity.Category;
import com.uniminuto.velvet.model.entity.SubCategory;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface SubCategoryMapper {
  
  // Entity -> SimpleSubCategory DTO
  SubCategoryDTO.SimpleSubCategory toSimpleDTO(SubCategory entity);
  
  // Entity -> DetailsSubCategory DTO
  @Mapping(target = "categoryName", source = "category.name")
  SubCategoryDTO.DetailsSubCategory toDetailsDTO(SubCategory entity);
  
  // CreateSubCategory DTO -> Entity
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapToCategory")
  SubCategory toEntity(SubCategoryDTO.CreateSubCategory dto);
  
  // UpdateSubCategory DTO -> Entity update
  @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapToCategory")
  void updateEntityFromDTO(SubCategoryDTO.UpdateSubCategory dto, @MappingTarget SubCategory entity);
  
  // Método para mapear categoryId a Category utilizando una anotación Named
  @Named("mapToCategory")
  default Category mapToCategory(Long categoryId) {
    if (categoryId == null) {
      return null;
    }
    
    // Creamos una Category solo con el ID para asociación
    Category category = new Category();
    category.setId(categoryId);
    return category;
  }
}