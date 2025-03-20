package com.uniminuto.velvet.model.dto;

import java.util.List;

import com.uniminuto.velvet.model.dto.OrderDTO.SimpleOrder;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TablesDTO {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CreateTable {
    @NotBlank(message = "El número de la mesa es obligatorio")
    private String number;
    
    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String description;

    @NotNull(message = "La capacidad de la mesa es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    private Integer capacity;

    @NotNull(message = "El ID de la ubicación es obligatorio")
    private Long locationId;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdateTable {
    @NotNull(message = "El ID es obligatorio")
    private Long id;

    @NotBlank(message = "El número de la mesa es obligatorio")
    private String number;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String description;

    @NotNull(message = "La capacidad de la mesa es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    private Integer capacity;

    @NotNull(message = "El ID de la ubicación es obligatorio")
    private Long locationId;

    @NotNull(message = "El estado de la mesa es obligatorio")
    private Boolean active;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DetailsTable {
    private Long id;
    private String number;
    private String description;
    private Integer capacity;
    private String locationName;
    private List<SimpleOrder> ordersNumbers;
    private Boolean active;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SimpleTable {
    private Long id;
    private String number;
    private Integer capacity;
    private String locationName;
    private Boolean active;
  }
}