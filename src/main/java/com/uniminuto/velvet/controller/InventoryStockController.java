package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.model.dto.InventoryStockDTO.CreateInventoryStock;
import com.uniminuto.velvet.model.dto.InventoryStockDTO.UpdateInventoryStock;
import com.uniminuto.velvet.model.dto.InventoryStockDTO.DetailsInventoryStock;
import com.uniminuto.velvet.model.dto.InventoryStockDTO.SimpleInventoryStock;
import com.uniminuto.velvet.exception.ResourceNotFoundException;
import com.uniminuto.velvet.service.interfaces.InventoryStockService;
import com.uniminuto.velvet.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v4/inventory-stocks")
@RequiredArgsConstructor
@Slf4j
public class InventoryStockController {

    private final InventoryStockService inventoryStockService;

    @PostMapping
    public ResponseEntity<ApiResponse<DetailsInventoryStock>> createInventoryStock(
            @Valid @RequestBody CreateInventoryStock createInventoryStock) {
        log.info("Solicitud para crear nuevo registro de inventario");
        DetailsInventoryStock createdStock = inventoryStockService.createInventoryStock(createInventoryStock);
        ApiResponse<DetailsInventoryStock> response = new ApiResponse<>(
                true,
                "Registro de inventario creado exitosamente",
                createdStock
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<DetailsInventoryStock>> updateInventoryStock(
            @Valid @RequestBody UpdateInventoryStock updateInventoryStock) {
        log.info("Solicitud para actualizar registro de inventario con ID: {}", updateInventoryStock.getId());
        DetailsInventoryStock updatedStock = inventoryStockService.updateInventoryStock(updateInventoryStock);
        ApiResponse<DetailsInventoryStock> response = new ApiResponse<>(
                true,
                "Registro de inventario actualizado exitosamente",
                updatedStock
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DetailsInventoryStock>> getInventoryStockById(@PathVariable Long id) {
        log.info("Solicitud para obtener registro de inventario con ID: {}", id);
        return inventoryStockService.getInventoryStockById(id)
                .map(stock -> {
                    ApiResponse<DetailsInventoryStock> response = new ApiResponse<>(
                            true,
                            "Registro de inventario encontrado",
                            stock
                    );
                    return ResponseEntity.ok(response);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Registro de inventario no encontrado con ID: " + id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<SimpleInventoryStock>>> getAllInventoryStocks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        log.info("Solicitud para listar todos los registros de inventario");

        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<SimpleInventoryStock> stockPage = inventoryStockService.getAllInventoryStocks(pageable);

        ApiResponse<Page<SimpleInventoryStock>> response = new ApiResponse<>(
                true,
                "Registros de inventario obtenidos exitosamente",
                stockPage
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-product/{productId}")
    public ResponseEntity<ApiResponse<List<SimpleInventoryStock>>> getInventoryStocksByProductId(
            @PathVariable Long productId) {
        log.info("Solicitud para listar registros de inventario por producto ID: {}", productId);
        List<SimpleInventoryStock> stocks = inventoryStockService.getInventoryStocksByProductId(productId);
        ApiResponse<List<SimpleInventoryStock>> response = new ApiResponse<>(
                true,
                "Registros de inventario para el producto obtenidos exitosamente",
                stocks
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-location/{locationId}")
    public ResponseEntity<ApiResponse<List<SimpleInventoryStock>>> getInventoryStocksByLocationId(
            @PathVariable Long locationId) {
        log.info("Solicitud para listar registros de inventario por ubicación ID: {}", locationId);
        List<SimpleInventoryStock> stocks = inventoryStockService.getInventoryStocksByLocationId(locationId);
        ApiResponse<List<SimpleInventoryStock>> response = new ApiResponse<>(
                true,
                "Registros de inventario para la ubicación obtenidos exitosamente",
                stocks
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteInventoryStock(@PathVariable Long id) {
        log.info("Solicitud para eliminar registro de inventario con ID: {}", id);
        boolean deleted = inventoryStockService.deleteInventoryStock(id);

        if (!deleted) {
            throw new ResourceNotFoundException("Registro de inventario no encontrado con ID: " + id);
        }

        ApiResponse<Void> response = new ApiResponse<>(
                true,
                "Registro de inventario eliminado exitosamente",
                null
        );
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/update-stock")
    public ResponseEntity<ApiResponse<DetailsInventoryStock>> updateStockQuantity(
            @RequestParam Long productId,
            @RequestParam Long locationId,
            @RequestParam Long quantity) {
        log.info("Solicitud para actualizar cantidad de stock: producto ID: {}, ubicación ID: {}, cantidad: {}",
                productId, locationId, quantity);

        DetailsInventoryStock updatedStock = inventoryStockService.updateStockQuantity(productId, locationId, quantity);

        String message = quantity >= 0 ?
                "Stock incrementado exitosamente" :
                "Stock reducido exitosamente";

        ApiResponse<DetailsInventoryStock> response = new ApiResponse<>(
                true,
                message,
                updatedStock
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-stock")
    public ResponseEntity<ApiResponse<Boolean>> hasEnoughStock(
            @RequestParam Long productId,
            @RequestParam Long locationId,
            @RequestParam Long quantity) {
        log.info("Solicitud para verificar disponibilidad de stock: producto ID: {}, ubicación ID: {}, cantidad requerida: {}",
                productId, locationId, quantity);

        boolean hasEnough = inventoryStockService.hasEnoughStock(productId, locationId, quantity);

        String message = hasEnough ?
                "Stock suficiente disponible" :
                "Stock insuficiente";

        ApiResponse<Boolean> response = new ApiResponse<>(
                true,
                message,
                hasEnough
        );
        return ResponseEntity.ok(response);
    }
}