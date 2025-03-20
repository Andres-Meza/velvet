package com.uniminuto.velvet.model.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class RolePermissionDTO {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AssignRolePermission {
    @NotNull(message = "El ID del rol es obligatorio")
    @Min(value = 1, message = "El ID del rol debe ser válido")
    private Long roleId;

    @NotNull(message = "El ID del permiso es obligatorio")
    @Min(value = 1, message = "El ID del permiso debe ser válido")
    private Long permissionId;

    @NotNull(message = "El ID del usuario asignador es obligatorio")
    @Min(value = 1, message = "El ID del usuario asignador debe ser válido")
    private Long assignedById;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DetailsRolePermission {
    private Long id;
    private String roleName;
    private String permissionName;
    private String assignedByUsername;
    private LocalDateTime assignedAt;
  }
}