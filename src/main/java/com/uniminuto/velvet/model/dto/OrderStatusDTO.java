package com.uniminuto.velvet.model.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class OrderStatusDTO {
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CreateOrderStatus {
    @NotBlank(message = "El nombre del estado de la orden es obligatorio")
    @Size(max = 50, message = "El nombre del estado de la orden no puede exceder los 50 caracteres")
    private String name;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String description;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdateOrderStatus {
    private Long id;

    @Size(max = 50, message = "El nombre del estado de la orden no puede exceder los 50 caracteres")
    private String name;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String description;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DetailsOrderStatus {
    private Long id;
    private String name;
    private String description;
    private Set<String> orders;
  }
}
