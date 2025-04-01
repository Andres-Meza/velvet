package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.SubCategoryDTO;
import com.uniminuto.velvet.model.entity.Category;
import com.uniminuto.velvet.model.entity.SubCategory;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-26T22:46:03-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class SubCategoryMapperImpl implements SubCategoryMapper {

    @Override
    public SubCategoryDTO.SimpleSubCategory toSimpleDTO(SubCategory entity) {
        if ( entity == null ) {
            return null;
        }

        SubCategoryDTO.SimpleSubCategory.SimpleSubCategoryBuilder simpleSubCategory = SubCategoryDTO.SimpleSubCategory.builder();

        simpleSubCategory.id( entity.getId() );
        simpleSubCategory.name( entity.getName() );

        return simpleSubCategory.build();
    }

    @Override
    public SubCategoryDTO.DetailsSubCategory toDetailsDTO(SubCategory entity) {
        if ( entity == null ) {
            return null;
        }

        SubCategoryDTO.DetailsSubCategory.DetailsSubCategoryBuilder detailsSubCategory = SubCategoryDTO.DetailsSubCategory.builder();

        detailsSubCategory.categoryName( entityCategoryName( entity ) );
        detailsSubCategory.id( entity.getId() );
        detailsSubCategory.name( entity.getName() );

        return detailsSubCategory.build();
    }

    @Override
    public SubCategory toEntity(SubCategoryDTO.CreateSubCategory dto) {
        if ( dto == null ) {
            return null;
        }

        SubCategory subCategory = new SubCategory();

        subCategory.setCategory( mapToCategory( dto.getCategoryId() ) );
        subCategory.setName( dto.getName() );

        return subCategory;
    }

    @Override
    public void updateEntityFromDTO(SubCategoryDTO.UpdateSubCategory dto, SubCategory entity) {
        if ( dto == null ) {
            return;
        }

        entity.setCategory( mapToCategory( dto.getCategoryId() ) );
        entity.setId( dto.getId() );
        entity.setName( dto.getName() );
    }

    private String entityCategoryName(SubCategory subCategory) {
        if ( subCategory == null ) {
            return null;
        }
        Category category = subCategory.getCategory();
        if ( category == null ) {
            return null;
        }
        String name = category.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
