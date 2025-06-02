package com.uniminuto.velvet.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.uniminuto.velvet.model.dto.OrderDetailDTO.CreateOrderDetail;
import com.uniminuto.velvet.model.dto.OrderDetailDTO.OrderDetailResponse;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class OrderDTO {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CreateOrder {
    @NotNull(message = "La ubicación es obligatoria")
    private Long locationId;

    @NotNull(message = "El usuario es obligatorio")
    private Long userId;

    private Long tableId;

    private List<CreateOrderDetail> orderDetails;

    private Long paymentMethodId;

    @NotNull(message = "El estado de la orden es obligatorio")
    private Long orderStatusId;

    @Builder.Default
    private boolean paid = false;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdateOrder {
    @NotNull(message = "El ID de la orden es obligatorio")
    private Long id;

    private List<OrderDetailDTO.UpdateOrderDetail> orderDetails;

    private Long paymentMethodId;

    private Long orderStatusId;

    private LocalDateTime completedDate;

    private Boolean paid;  // Cambio de isPaid a paid

    @Positive(message = "El monto total debe ser mayor a cero")
    private BigDecimal totalAmount;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DetailsOrder {
    private Long id;
    private String orderNumber;
    private Long locationId;
    private String locationName;
    private Long userId;
    private String userName;
    private String userLastName;
    private Long tableId;
    private String tableName;
    private String orderStatusName;
    private String paymentMethodName;
    private List<OrderDetailResponse> orderDetails;
    private LocalDateTime orderDate;
    private LocalDateTime completedDate;
    private BigDecimal totalAmount;
    private boolean paid;  // Cambio de isPaid a paid
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SimpleOrder {
    private Long id;
    private String orderNumber;
    private Long locationId;
    private String locationName;
    private Long userId;
    private String userName;
    private String userLastName;
    private String orderStatusName;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private boolean paid;  // Cambio de isPaid a paid
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdateOrderStatus {
    @NotNull(message = "El ID de la orden es obligatorio")
    private Long id;

    @NotNull(message = "El estado de la orden es obligatorio")
    private Long orderStatusId;

    private LocalDateTime completedDate;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdatePaymentStatus {
    @NotNull(message = "El ID de la orden es obligatorio")
    private Long id;

    @NotNull(message = "El método de pago es obligatorio")
    private Long paymentMethodId;

    @NotNull(message = "El estado de pago es obligatorio")
    private boolean paid;  // Cambio de isPaid a paid
  }
}