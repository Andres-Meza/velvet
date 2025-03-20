package com.uniminuto.velvet.model.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class InventoryStockDTO {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CreateInventoryStock {
    @NotNull(message = "El ID del producto es obligatorio")
    @Min(value = 1, message = "El ID del producto debe ser válido")
    private Long productId;

    @NotNull(message = "El ID de la sede es obligatorio")
    @Min(value = 1, message = "El ID de la sede debe ser válido")
    private Long locationId;

    @NotNull(message = "El stock actual es obligatorio")
    @Min(value = 0, message = "El stock actual no puede ser negativo")
    private Long currentStock;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdateInventoryStock {
    @NotNull(message = "El ID del inventario es obligatorio")
    @Min(value = 1, message = "El ID del inventario debe ser válido")
    private Long id;

    private Long productId;

    private Long locationId;

    @Min(value = 0, message = "El stock actual no puede ser negativo")
    private Long currentStock;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DetailsInventoryStock {
    private Long id;
    private String productName;
    private String locationName;
    private Long currentStock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SimpleInventoryStock {
    private Long id;
    private String productName;
    private String locationName;
    private Long currentStock;
  }
}