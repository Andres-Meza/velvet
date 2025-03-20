package com.uniminuto.velvet.model.dto;


import jakarta.validation.constraints.*;
import lombok.*;

public class SubCategoryDTO {
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CreateSubCategory {
    @NotBlank(message = "El nombre de la subcategoría es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;
    
    @NotNull(message = "El ID de la categoría es obligatorio")
    private Long categoryId;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdateSubCategory {
    @NotNull(message = "El ID es obligatorio")
    private Long id;
    
    @NotBlank(message = "El nombre de la subcategoría es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;
    
    @NotNull(message = "El ID de la categoría es obligatorio")
    private Long categoryId;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DetailsSubCategory {
    private Long id;
    private String name;
    private String categoryName;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SimpleSubCategory {
    private Long id;
    private String name;
  }
}