package com.uniminuto.velvet.model.dto;

import java.util.Set;

import jakarta.validation.constraints.*;
import lombok.*;

public class MovementTypeDTO {
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CreateMovementType {
    @NotBlank(message = "El nombre del tipo de movimiento es obligatorio")
    @Size(max = 50, message = "El nombre del tipo de movimiento no puede exceder los 50 caracteres")
    private String name;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String description;
    
    @NotNull(message = "Debe especificar si afecta al stock")
    private Boolean affectsStock;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdateMovementType {
    private Long id;

    @Size(max = 50, message = "El nombre del tipo de movimiento no puede exceder los 50 caracteres")
    private String name;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String description;
    
    private Boolean affectsStock;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DetailsMovementType {
    private Long id;
    private String name;
    private String description;
    private Boolean affectsStock;
    private Set<String> inventoryMovements;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SimpleMovementType {
    private Long id;
    private String name;
    private String description;
    private Boolean affectsStock;
  }
}