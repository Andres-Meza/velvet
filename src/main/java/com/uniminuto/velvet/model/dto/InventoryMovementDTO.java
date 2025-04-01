package com.uniminuto.velvet.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.uniminuto.velvet.model.entity.MovementType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class InventoryMovementDTO {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CreateInventoryMovement {
    @NotNull(message = "El ID del inventario es obligatorio")
    @Min(value = 1, message = "El ID del inventario debe ser un valor válido")
    private Long inventoryStockId;

    @NotNull(message = "El ID del producto es obligatorio")
    @Min(value = 1, message = "El ID del producto debe ser un valor válido")
    private Long productId;

    @NotNull(message = "El tipo de movimiento es obligatorio")
    private MovementType movementType;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer quantity;

    @DecimalMin(value = "0.0", message = "El costo unitario no puede ser negativo")
    @Digits(integer = 10, fraction = 2, message = "El costo unitario debe tener hasta 10 dígitos enteros y 2 decimales")
    private BigDecimal unitCost;

    @Size(max = 255, message = "La referencia no puede exceder los 255 caracteres")
    private String reference;

    @Size(max = 500, message = "Las notas no pueden exceder los 500 caracteres")
    private String notes;

    private Long orderId;

    @NotNull(message = "El ID del usuario creador es obligatorio")
    @Min(value = 1, message = "El ID del usuario creador debe ser un valor válido")
    private Long createdById;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdateInventoryMovement {
    @NotNull(message = "El ID del movimiento es obligatorio")
    @Min(value = 1, message = "El ID debe ser mayor a 0")
    private Long id;

    @NotNull(message = "El tipo de movimiento es obligatorio")
    private MovementType movementType;

    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer quantity;

    @DecimalMin(value = "0.0", message = "El costo unitario no puede ser negativo")
    @Digits(integer = 10, fraction = 2, message = "El costo unitario debe tener hasta 10 dígitos enteros y 2 decimales")
    private BigDecimal unitCost;

    @Size(max = 255, message = "La referencia no puede exceder los 255 caracteres")
    private String reference;

    @Size(max = 500, message = "Las notas no pueden exceder los 500 caracteres")
    private String notes;

    @Min(value = 1, message = "El ID de la orden debe ser un valor válido")
    private Long orderId;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DetailsInventoryMovement {
    private Long id;
    private Long inventoryStockId;
    private Long productId;
    private String productName;
    private MovementType movementType;
    private Integer quantity;
    private BigDecimal unitCost;
    private Long currentStock;
    private String reference;
    private String notes;
    private Long createdById;
    private String createdByUsername;
    private Long orderId;
    private String orderReference;
    private LocalDateTime movementDate;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SimpleInventoryMovement {
    private Long id;
    private Long productId;
    private String productName;
    private MovementType movementType;
    private Integer quantity;
    private BigDecimal unitCost;
    private Long currentStock;
    private String reference;
    private LocalDateTime movementDate;
  }
}