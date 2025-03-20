package com.uniminuto.velvet.model.dto;

import java.util.HashSet;
import java.util.Set;

import com.uniminuto.velvet.model.dto.RoleDTO.DetailsRole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PermissionDTO {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CreatePermission {
    @NotBlank(message = "El nombre del permiso es obligatorio")
    @Size(max = 255, message = "El nombre del permiso no puede exceder los 255 caracteres")
    private String name;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String description;
    
    @NotNull(message = "El ID del recurso es obligatorio")
    private Long resourceId;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdatePermission {
    private Long id;

    @Size(max = 255, message = "El nombre del permiso no puede exceder los 255 caracteres")
    private String name;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String description;

    private Long resourceId;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DetailsPermission {
    private Long id;
    private String name;
    private String description;
    private ResourceDTO.SimpleResource resource;
    
    @Builder.Default
    private Set<DetailsRole> roles = new HashSet<>();
  }
  
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SimplePermission {
    private Long id;
    private String name;
    private String description;
  }
}