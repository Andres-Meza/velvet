package com.uniminuto.velvet.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class InventoryLimitsDTO {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CreateInventoryLimits {
    @NotNull(message = "El ID del producto es obligatorio")
    @Min(value = 1, message = "El ID del producto debe ser un valor válido")
    private Long productId;
    
    @NotNull(message = "El ID de la ubicación es obligatorio")
    @Min(value = 1, message = "El ID de la ubicación debe ser un valor válido")
    private Long locationId;
    
    @NotNull(message = "El stock mínimo es obligatorio")
    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    private Long minStock;
    
    @Min(value = 1, message = "El stock máximo debe ser mayor a 0")
    private Long maxStock;
  }
  
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdateInventoryLimits {
    @NotNull(message = "El ID de los límites de inventario es obligatorio")
    @Min(value = 1, message = "El ID debe ser mayor a 0")
    private Long id;
    
    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    private Long minStock;
    
    @Min(value = 1, message = "El stock máximo debe ser mayor a 0")
    private Long maxStock;
  }
  
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DetailsInventoryLimits {
    private Long id;
    private Long productId;
    private String productName;
    private String productSku;
    private Long locationId;
    private String locationName;
    private Long minStock;
    private Long maxStock;
  }
  
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SimpleInventoryLimits {
    private Long id;
    private String productName;
    private String locationName;
    private Long minStock;
    private Long maxStock;
  }
}