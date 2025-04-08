package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.model.dto.ProductDTO.CreateProduct;
import com.uniminuto.velvet.model.dto.ProductDTO.UpdateProduct;
import com.uniminuto.velvet.model.dto.ProductDTO.DetailsProduct;
import com.uniminuto.velvet.model.dto.ProductDTO.SimpleProduct;
import com.uniminuto.velvet.service.interfaces.ProductService;
import com.uniminuto.velvet.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v4/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<DetailsProduct>> createProduct(@RequestBody @Validated CreateProduct createProductDTO) {
        log.info("REST request para crear un nuevo producto");

        // Verificar si ya existe un producto con el mismo nombre
        if (productService.existsByName(createProductDTO.getName())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(false, "Ya existe un producto con el nombre: " + createProductDTO.getName(), null));
        }

        DetailsProduct product = productService.createProduct(createProductDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Producto creado exitosamente", product));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<DetailsProduct>> updateProduct(@RequestBody @Validated UpdateProduct updateProductDTO) {
        log.info("REST request para actualizar el producto con ID: {}", updateProductDTO.getId());

        try {
            DetailsProduct product = productService.updateProduct(updateProductDTO);
            return ResponseEntity.ok(new ApiResponse<>(true, "Producto actualizado exitosamente", product));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DetailsProduct>> getProductById(@PathVariable Long id) {
        log.info("REST request para obtener el producto con ID: {}", id);

        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok(new ApiResponse<>(true, "Producto encontrado", product)))
                .orElse(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Producto no encontrado con ID: " + id, null)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<SimpleProduct>>> getAllProducts(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        log.info("REST request para obtener todos los productos paginados");

        Page<SimpleProduct> productsPage = productService.getAllProducts(pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "Productos recuperados exitosamente", productsPage));
    }

    @GetMapping("/by-product-type/{productTypeId}")
    public ResponseEntity<ApiResponse<Page<SimpleProduct>>> getProductsByProductType(
            @PathVariable Long productTypeId,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        log.info("REST request para obtener productos por tipo de producto ID: {}", productTypeId);

        Page<SimpleProduct> productsPage = productService.getProductsByProductType(productTypeId, pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "Productos por tipo recuperados exitosamente", productsPage));
    }

    @GetMapping("/by-subcategory/{subCategoryId}")
    public ResponseEntity<ApiResponse<Page<SimpleProduct>>> getProductsBySubCategory(
            @PathVariable Long subCategoryId,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        log.info("REST request para obtener productos por subcategoría ID: {}", subCategoryId);

        Page<SimpleProduct> productsPage = productService.getProductsBySubCategory(subCategoryId, pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "Productos por subcategoría recuperados exitosamente", productsPage));
    }

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<ApiResponse<Page<SimpleProduct>>> getProductsByCategory(
            @PathVariable Long categoryId,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        log.info("REST request para obtener productos por categoría ID: {}", categoryId);

        Page<SimpleProduct> productsPage = productService.getProductsByCategory(categoryId, pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "Productos por categoría recuperados exitosamente", productsPage));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<DetailsProduct>> changeProductStatus(
            @PathVariable Long id,
            @RequestParam Boolean active) {
        log.info("REST request para cambiar estado del producto ID: {} a {}", id, active);

        try {
            DetailsProduct product = productService.changeProductStatus(id, active);
            String message = active ? "Producto activado exitosamente" : "Producto desactivado exitosamente";
            return ResponseEntity.ok(new ApiResponse<>(true, message, product));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<SimpleProduct>>> searchProducts(
            @RequestParam String query,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        log.info("REST request para buscar productos que contengan: {}", query);

        Page<SimpleProduct> productsPage = productService.searchProducts(query, pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "Búsqueda realizada exitosamente", productsPage));
    }

    @GetMapping("/check-name")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> checkNameExists(@RequestParam String name) {
        log.info("REST request para verificar si existe un producto con nombre: {}", name);

        boolean exists = productService.existsByName(name);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);

        return ResponseEntity.ok(new ApiResponse<>(true, "Verificación completada", response));
    }
}