package com.uniminuto.velvet.model.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PaymentMethodDTO {
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CreatePaymentMethod {
    @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(max = 50, message = "El nombre del rol no puede exceder los 50 caracteres")
    private String name;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String description;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdatePaymentMethod {
    private Long id;

    @Size(max = 50, message = "El nombre del rol no puede exceder los 50 caracteres")
    private String name;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String description;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DetailsPaymentMethod {
    private Long id;
    private String name;
    private String description;
    private Set<String> orders;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SimplePaymentMethod {
    private Long id;
    private String name;
    private String description;
  }
}