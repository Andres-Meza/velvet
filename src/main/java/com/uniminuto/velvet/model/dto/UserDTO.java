package com.uniminuto.velvet.model.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserDTO {
  /** DTO para Crear Usuario **/
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CreateUser {
    @NotNull(message = "El tipo de documento es obligatorio")
    private Long documentTypeId; // Cambiado de TypeDocument a Long para coincidir con DocumentType
    
    @NotBlank(message = "El número de documento es obligatorio")
    @Size(min = 5, max = 20, message = "El documento debe tener entre 5 y 20 caracteres")
    private String document;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100)
    private String name;
    
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 100)
    private String lastName;
    
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 50)
    private String username;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    private String email;
    
    @NotNull(message = "El rol es obligatorio")
    private Long roleId;
    
    @NotNull(message = "La sede es obligatoria")
    private Long locationId;
  }
  
  /** DTO para Actualizar Usuario **/
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdateUser {
    @NotNull(message = "El ID es obligatorio")
    private Long id;
    
    @NotNull(message = "El tipo de documento es obligatorio")
    private Long documentTypeId; // Cambiado de TypeDocument a Long
    
    @NotBlank(message = "El número de documento es obligatorio")
    @Size(min = 5, max = 20)
    private String document;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100)
    private String name;
    
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 100)
    private String lastName;
    
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 50)
    private String username;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    private String email;
    
    @NotNull(message = "El rol es obligatorio")
    private Long roleId;
    
    @NotNull(message = "El estado es obligatorio")
    private Boolean active;
    
    @NotNull(message = "La sede es obligatoria")
    private Long locationId;
  }
  
  /** DTO para Actualizar Contraseña **/
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdatePassword {
    @NotNull(message = "El ID de usuario es obligatorio")
    private Long userId;
    
    @NotBlank(message = "La contraseña actual es obligatoria")
    private String currentPassword;
    
    @NotBlank(message = "La nueva contraseña es obligatoria")
    @Size(min = 6, message = "La nueva contraseña debe tener al menos 6 caracteres")
    private String newPassword;
    
    @NotBlank(message = "La confirmación de contraseña es obligatoria")
    private String confirmPassword;
  }
  
  /** DTO para Detalles del Usuario (sin exponer información sensible) **/
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UserDetails {
    private Long id;
    private String documentTypeName;
    private String document;
    private String name;
    private String lastName;
    private String username;
    private String email;
    private String roleName;
    private String locationName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;
    private Boolean active;
  }
}