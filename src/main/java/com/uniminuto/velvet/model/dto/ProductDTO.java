package com.uniminuto.velvet.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProductDTO {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CreateProduct {
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;
    
    @NotNull(message = "El tipo de producto es obligatorio")
    private Long productTypeId;
    
    @NotNull(message = "La subcategoría es obligatoria")
    private Long subCategoryId;

    @NotNull(message = "La unidad de medida es obligatoria")
    private Long unitOfMeasureId;

    @NotNull(message = "El precio de compra es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de compra debe ser mayor que cero")
    private BigDecimal purchasePrice;

    @NotNull(message = "El precio de venta es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de venta debe ser mayor que cero")
    private BigDecimal salePrice;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdateProduct {
    @NotNull(message = "El ID es obligatorio")
    private Long id;

    private String name;
    
    private String description;
    
    private Long productTypeId;
    
    private Long subCategoryId;
    
    private Long unitOfMeasureId;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de venta debe ser mayor que cero")
    private BigDecimal salePrice;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de compra debe ser mayor que cero")
    private BigDecimal purchasePrice;

    private Boolean active;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DetailsProduct {
    private Long id;
    private String name;
    private String description;
    private String productTypeName;
    private String categoryName;
    private String subCategoryName;
    private String unitOfMeasureName;
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SimpleProduct {
    private Long id;
    private String name;
    private String productTypeName;
    private String categoryName;
    private String subCategoryName;
    private BigDecimal salePrice;
    private Boolean active;
  }
}