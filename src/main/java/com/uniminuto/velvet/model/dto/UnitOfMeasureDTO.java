package com.uniminuto.velvet.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UnitOfMeasureDTO {
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CreateUnitOfMeasure {
    @NotBlank(message = "El nombre de la unidad de medida es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @NotBlank(message = "El símbolo de la unidad de medida es obligatorio")
    @Size(min = 1, max = 10, message = "El símbolo debe tener entre 1 y 10 caracteres")
    private String symbol;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdateUnitOfMeasure {
    @NotNull(message = "El ID es obligatorio")
    private Long id;
    
    @NotBlank(message = "El nombre de la unidad de medida es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @NotBlank(message = "El símbolo de la unidad de medida es obligatorio")
    @Size(min = 1, max = 10, message = "El símbolo debe tener entre 1 y 10 caracteres")
    private String symbol;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DetailsUnitOfMeasure {
    private Long id;
    private String name;
    private String symbol;
    private String productName;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SimpleUnitOfMeasure {
    private Long id;
    private String name;
    private String symbol;
  }
}