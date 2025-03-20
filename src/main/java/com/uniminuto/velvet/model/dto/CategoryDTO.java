package com.uniminuto.velvet.model.dto;

import java.util.List;

import com.uniminuto.velvet.model.dto.SubCategoryDTO.DetailsSubCategory;

import jakarta.validation.constraints.*;
import lombok.*;

public class CategoryDTO {
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CreateCategory {
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdateCategory {
    @Min(value = 1, message = "El ID de la categoría es obligatorio")
    private Long id;
		
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CategoryResponse {
    private Long id;
    private String name;
  }
	
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CategoryDetail {
    private Long id;
    private String name;
    private List<DetailsSubCategory> subCategories;
  }
}