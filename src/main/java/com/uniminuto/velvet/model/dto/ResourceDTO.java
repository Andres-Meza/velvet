package com.uniminuto.velvet.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;

public class ResourceDTO {
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CreateResource {
    @NotBlank(message = "El nombre del recurso es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdateResource {
    @NotNull(message = "El ID es obligatorio")
    private Long id;
    
    @NotBlank(message = "El nombre del recurso es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DetailsResource {
    private Long id;
    private String name;
    private String permissionName;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SimpleResource {
    private Long id;
    private String name;
  }
}