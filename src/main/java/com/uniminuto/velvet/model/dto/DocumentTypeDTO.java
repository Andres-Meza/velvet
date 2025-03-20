package com.uniminuto.velvet.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class DocumentTypeDTO {
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CreateDocumentType {
    @NotBlank(message = "El nombre del tipo de documento es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
    private String description;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdateDocumentType {
    @Min(value = 1, message = "El ID de la categoría es obligatorio")
    private Long id;
		
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
    private String description;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DocumentTypeDetail {
    private Long id;
    private String name;
    private String description;
  }
}
