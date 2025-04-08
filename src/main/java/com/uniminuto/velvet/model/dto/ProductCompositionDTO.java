package com.uniminuto.velvet.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class ProductCompositionDTO {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CreateCompositeProduct {
    @NotNull(message = "El ID del producto compuesto es obligatorio")
    @Min(value = 1, message = "El ID del producto compuesto debe ser un valor válido")
    private Long compositeProductId;

    @NotEmpty(message = "Debe incluir al menos un ingrediente")
    @Valid
    private List<IngredientItem> ingredients;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class IngredientItem {
    @NotNull(message = "El ID del producto ingrediente es obligatorio")
    @Min(value = 1, message = "El ID del producto ingrediente debe ser un valor válido")
    private Long ingredientProductId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private Double quantity;

    @NotNull(message = "La unidad de medida es obligatoria")
    @Min(value = 1, message = "El ID de la unidad de medida debe ser un valor válido")
    private Long unitOfMeasureId;
  }
  
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdateProductComposition {
    @NotNull(message = "El ID de la composición es obligatorio")
    @Min(value = 1, message = "El ID debe ser mayor a 0")
    private Long id;
    
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private Double quantity;
    
    @Min(value = 1, message = "El ID de la unidad de medida debe ser un valor válido")
    private Long unitOfMeasureId;
  }
  
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DetailsProductComposition {
    private Long id;
    private Long compositeProductId;
    private String compositeProductName;
    private Long ingredientProductId;
    private String ingredientProductName;
    private Double quantity;
    private Long unitOfMeasureId;
    private String unitOfMeasureName;
    private String unitOfMeasureSymbol;
  }
  
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SimpleProductComposition {
    private Long id;
    private Long ingredientProductId;
    private String ingredientProductName;
    private Double quantity;
    private String unitOfMeasureSymbol;
  }
}