package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.model.dto.ProductCompositionDTO;
import com.uniminuto.velvet.util.ApiResponse;
import com.uniminuto.velvet.service.interfaces.ProductCompositionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v4/product-compositions")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ProductCompositionController {

    private final ProductCompositionService productCompositionService;

    /**
     * Crea composiciones para un producto compuesto
     *
     * @param createDTO datos para crear las composiciones
     * @return respuesta con los detalles de las composiciones creadas
     */
    @PostMapping
    public ResponseEntity<ApiResponse<List<ProductCompositionDTO.DetailsProductComposition>>> createCompositeProduct(
            @Valid @RequestBody ProductCompositionDTO.CreateCompositeProduct createDTO) {

        log.info("REST request para crear composiciones para el producto compuesto ID: {}",
                createDTO.getCompositeProductId());

        List<ProductCompositionDTO.DetailsProductComposition> result =
                productCompositionService.createCompositeProduct(createDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Composiciones de producto creadas exitosamente", result));
    }

    /**
     * Actualiza una composición de producto existente
     *
     * @param updateDTO datos para actualizar la composición
     * @return respuesta con los detalles de la composición actualizada
     */
    @PutMapping
    public ResponseEntity<ApiResponse<ProductCompositionDTO.DetailsProductComposition>> updateProductComposition(
            @Valid @RequestBody ProductCompositionDTO.UpdateProductComposition updateDTO) {

        log.info("REST request para actualizar la composición de producto con ID: {}", updateDTO.getId());

        ProductCompositionDTO.DetailsProductComposition result =
                productCompositionService.updateProductComposition(updateDTO);

        return ResponseEntity
                .ok(new ApiResponse<>(true, "Composición de producto actualizada exitosamente", result));
    }

    /**
     * Elimina una composición de producto por su ID
     *
     * @param id ID de la composición a eliminar
     * @return respuesta con mensaje de éxito
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProductComposition(@PathVariable Long id) {

        log.info("REST request para eliminar la composición de producto con ID: {}", id);

        productCompositionService.deleteProductComposition(id);

        return ResponseEntity
                .ok(new ApiResponse<>(true, "Composición de producto eliminada exitosamente", null));
    }

    /**
     * Obtiene una composición de producto por su ID
     *
     * @param id ID de la composición a buscar
     * @return respuesta con los detalles de la composición encontrada
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductCompositionDTO.DetailsProductComposition>> getProductCompositionById(
            @PathVariable Long id) {

        log.info("REST request para obtener la composición de producto con ID: {}", id);

        return productCompositionService.findProductCompositionById(id)
                .map(dto -> ResponseEntity
                        .ok(new ApiResponse<>(true, "Composición de producto encontrada", dto)))
                .orElse(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Composición de producto no encontrada con ID: " + id, null)));
    }

    /**
     * Obtiene todas las composiciones de productos
     *
     * @return respuesta con la lista de todas las composiciones
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductCompositionDTO.DetailsProductComposition>>> getAllProductCompositions() {

        log.info("REST request para obtener todas las composiciones de productos");

        List<ProductCompositionDTO.DetailsProductComposition> compositions =
                productCompositionService.findAllProductCompositions();

        return ResponseEntity
                .ok(new ApiResponse<>(true, "Composiciones de productos recuperadas exitosamente", compositions));
    }

    /**
     * Obtiene todas las composiciones para un producto compuesto específico
     *
     * @param compositeProductId ID del producto compuesto
     * @return respuesta con la lista de composiciones del producto
     */
    @GetMapping("/by-product/{compositeProductId}")
    public ResponseEntity<ApiResponse<List<ProductCompositionDTO.SimpleProductComposition>>> getCompositionsForProduct(
            @PathVariable Long compositeProductId) {

        log.info("REST request para obtener composiciones del producto con ID: {}", compositeProductId);

        List<ProductCompositionDTO.SimpleProductComposition> compositions =
                productCompositionService.findCompositionsForProduct(compositeProductId);

        return ResponseEntity
                .ok(new ApiResponse<>(true, "Composiciones del producto recuperadas exitosamente", compositions));
    }

    /**
     * Verifica si un producto es compuesto
     *
     * @param productId ID del producto a verificar
     * @return respuesta con el resultado de la verificación
     */
    @GetMapping("/is-composite/{productId}")
    public ResponseEntity<ApiResponse<Boolean>> checkIfProductIsComposite(@PathVariable Long productId) {

        log.info("REST request para verificar si el producto con ID: {} es compuesto", productId);

        boolean isComposite = productCompositionService.isProductComposite(productId);

        return ResponseEntity
                .ok(new ApiResponse<>(
                        true,
                        isComposite ? "El producto es compuesto" : "El producto no es compuesto",
                        isComposite));
    }

    /**
     * Verifica si un producto es usado como ingrediente en alguna composición
     *
     * @param productId ID del producto a verificar
     * @return respuesta con el resultado de la verificación
     */
    @GetMapping("/is-ingredient/{productId}")
    public ResponseEntity<ApiResponse<Boolean>> checkIfProductIsUsedAsIngredient(@PathVariable Long productId) {

        log.info("REST request para verificar si el producto con ID: {} es usado como ingrediente", productId);

        boolean isIngredient = productCompositionService.isProductUsedAsIngredient(productId);

        return ResponseEntity
                .ok(new ApiResponse<>(
                        true,
                        isIngredient ? "El producto es usado como ingrediente" : "El producto no es usado como ingrediente",
                        isIngredient));
    }
}