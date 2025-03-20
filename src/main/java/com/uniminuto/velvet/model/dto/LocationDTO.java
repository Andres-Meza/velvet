package com.uniminuto.velvet.model.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class LocationDTO {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CreateLocation {
    @NotBlank(message = "El nombre de la sede es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 5, max = 255, message = "La dirección debe tener entre 5 y 255 caracteres")
    private String address;

    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String description;

    @Pattern(regexp = "^[0-9]*$", message = "El teléfono debe contener solo números")
    @Size(max = 20, message = "El teléfono no puede exceder los 20 caracteres")
    private String phone;

    @Builder.Default
    private Boolean active = true;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdateLocation {
    @NotNull(message = "El ID de la sede es obligatorio")
    private Long id;

    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @Size(min = 5, max = 255, message = "La dirección debe tener entre 5 y 255 caracteres")
    private String address;

    @Pattern(regexp = "^[0-9]*$", message = "El teléfono debe contener solo números")
    @Size(max = 20, message = "El teléfono no puede exceder los 20 caracteres")
    private String phone;

    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String description;

    private Boolean active;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DetailsLocation {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String description;
    private Boolean active;
    private Integer totalUsers;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SimpleLocation {
    private Long id;
    private String name;
    private String address;
    private Boolean active;
  }
}