package com.uniminuto.velvet.model.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class OrderDetailDTO {
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CreateOrderDetail {
    // Se elimina la referencia al orderId ya que será asignado por el servicio
    @NotNull(message = "El ID del producto es obligatorio")
    private Long productId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer quantity;

    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio unitario debe ser mayor que cero")
    private BigDecimal unitPrice;

    private String productNotes;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdateOrderDetail {
    @NotNull(message = "El ID del detalle de orden es obligatorio")
    private Long id;

    private Long orderId;

    private Long productId;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer quantity;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio unitario debe ser mayor que cero")
    private BigDecimal unitPrice;

    private String productNotes;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class OrderDetailResponse {
    private Long id;
    private Long orderId;
    private String orderNumber;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    private String productNotes;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SimpleOrderDetail {
    private Long id;
    private Long orderId;
    private String orderNumber;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class OrderDetailsByProduct {
    private Long productId;
    private String productName;
    private Long totalOrders;
    private Integer totalQuantity;
    private BigDecimal totalRevenue;
  }
}