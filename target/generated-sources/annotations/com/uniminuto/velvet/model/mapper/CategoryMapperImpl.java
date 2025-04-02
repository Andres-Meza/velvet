package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.CategoryDTO;
import com.uniminuto.velvet.model.dto.SubCategoryDTO;
import com.uniminuto.velvet.model.entity.Category;
import com.uniminuto.velvet.model.entity.SubCategory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-02T09:15:13-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Autowired
    private SubCategoryMapper subCategoryMapper;

    @Override
    public Category toEntity(CategoryDTO.CreateCategory dto) {
        if ( dto == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.name( dto.getName() );

        return category.build();
    }

    @Override
    public Category toEntity(CategoryDTO.UpdateCategory dto) {
        if ( dto == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.id( dto.getId() );
        category.name( dto.getName() );

        return category.build();
    }

    @Override
    public void updateEntityFromDto(CategoryDTO.UpdateCategory dto, Category entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setName( dto.getName() );
    }

    @Override
    public CategoryDTO.CategoryResponse toCategoryResponse(Category entity) {
        if ( entity == null ) {
            return null;
        }

        CategoryDTO.CategoryResponse.CategoryResponseBuilder categoryResponse = CategoryDTO.CategoryResponse.builder();

        categoryResponse.id( entity.getId() );
        categoryResponse.name( entity.getName() );

        return categoryResponse.build();
    }

    @Override
    public List<CategoryDTO.CategoryResponse> toCategoryResponseList(List<Category> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CategoryDTO.CategoryResponse> list = new ArrayList<CategoryDTO.CategoryResponse>( entities.size() );
        for ( Category category : entities ) {
            list.add( toCategoryResponse( category ) );
        }

        return list;
    }

    @Override
    public CategoryDTO.CategoryDetail toCategoryDetail(Category entity) {
        if ( entity == null ) {
            return null;
        }

        CategoryDTO.CategoryDetail.CategoryDetailBuilder categoryDetail = CategoryDTO.CategoryDetail.builder();

        categoryDetail.subCategories( subCategorySetToDetailsSubCategoryList( entity.getSubCategories() ) );
        categoryDetail.id( entity.getId() );
        categoryDetail.name( entity.getName() );

        return categoryDetail.build();
    }

    @Override
    public List<CategoryDTO.CategoryDetail> toCategoryDetailList(List<Category> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CategoryDTO.CategoryDetail> list = new ArrayList<CategoryDTO.CategoryDetail>( entities.size() );
        for ( Category category : entities ) {
            list.add( toCategoryDetail( category ) );
        }

        return list;
    }

    protected List<SubCategoryDTO.DetailsSubCategory> subCategorySetToDetailsSubCategoryList(Set<SubCategory> set) {
        if ( set == null ) {
            return null;
        }

        List<SubCategoryDTO.DetailsSubCategory> list = new ArrayList<SubCategoryDTO.DetailsSubCategory>( set.size() );
        for ( SubCategory subCategory : set ) {
            list.add( subCategoryMapper.toDetailsDTO( subCategory ) );
        }

        return list;
    }
}
