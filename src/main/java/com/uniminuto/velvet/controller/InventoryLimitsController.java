package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.model.dto.InventoryLimitsDTO;
import com.uniminuto.velvet.service.interfaces.InventoryLimitsService;
import com.uniminuto.velvet.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v4/inventory-limits")
@RequiredArgsConstructor
@Slf4j
public class InventoryLimitsController {

    private final InventoryLimitsService inventoryLimitsService;

    @PostMapping
    public ResponseEntity<ApiResponse<InventoryLimitsDTO.DetailsInventoryLimits>> create(
            @Valid @RequestBody InventoryLimitsDTO.CreateInventoryLimits dto) {
        log.info("Solicitud para crear límites de inventario: {}", dto);

        InventoryLimitsDTO.DetailsInventoryLimits created = inventoryLimitsService.create(dto);

        ApiResponse<InventoryLimitsDTO.DetailsInventoryLimits> response = new ApiResponse<>(
                true,
                "Límites de inventario creados exitosamente",
                created
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InventoryLimitsDTO.DetailsInventoryLimits>> update(
            @PathVariable Long id,
            @Valid @RequestBody InventoryLimitsDTO.UpdateInventoryLimits dto) {
        log.info("Solicitud para actualizar límites de inventario con ID: {}", id);

        // Asegurar que el ID en el path coincida con el ID en el DTO
        dto.setId(id);

        InventoryLimitsDTO.DetailsInventoryLimits updated = inventoryLimitsService.update(dto);

        ApiResponse<InventoryLimitsDTO.DetailsInventoryLimits> response = new ApiResponse<>(
                true,
                "Límites de inventario actualizados exitosamente",
                updated
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        log.info("Solicitud para eliminar límites de inventario con ID: {}", id);

        inventoryLimitsService.delete(id);

        ApiResponse<Void> response = new ApiResponse<>(
                true,
                "Límites de inventario eliminados exitosamente",
                null
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InventoryLimitsDTO.DetailsInventoryLimits>> findById(@PathVariable Long id) {
        log.info("Solicitud para buscar límites de inventario con ID: {}", id);

        return inventoryLimitsService.findById(id)
                .map(limits -> {
                    ApiResponse<InventoryLimitsDTO.DetailsInventoryLimits> response = new ApiResponse<>(
                            true,
                            "Límites de inventario encontrados",
                            limits
                    );
                    return ResponseEntity.ok(response);
                })
                .orElse(new ResponseEntity<>(
                        new ApiResponse<>(false, "No se encontraron límites de inventario con ID: " + id, null),
                        HttpStatus.NOT_FOUND
                ));
    }

    @GetMapping("/product/{productId}/location/{locationId}")
    public ResponseEntity<ApiResponse<InventoryLimitsDTO.DetailsInventoryLimits>> findByProductAndLocation(
            @PathVariable Long productId,
            @PathVariable Long locationId) {
        log.info("Solicitud para buscar límites de inventario para producto ID: {} y ubicación ID: {}",
                productId, locationId);

        return inventoryLimitsService.findByProductAndLocation(productId, locationId)
                .map(limits -> {
                    ApiResponse<InventoryLimitsDTO.DetailsInventoryLimits> response = new ApiResponse<>(
                            true,
                            "Límites de inventario encontrados",
                            limits
                    );
                    return ResponseEntity.ok(response);
                })
                .orElse(new ResponseEntity<>(
                        new ApiResponse<>(false, "No se encontraron límites de inventario para el producto y ubicación especificados", null),
                        HttpStatus.NOT_FOUND
                ));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<InventoryLimitsDTO.SimpleInventoryLimits>>> findAll(Pageable pageable) {
        log.info("Solicitud para listar todos los límites de inventario");

        Page<InventoryLimitsDTO.SimpleInventoryLimits> limits = inventoryLimitsService.findAll(pageable);

        ApiResponse<Page<InventoryLimitsDTO.SimpleInventoryLimits>> response = new ApiResponse<>(
                true,
                "Límites de inventario recuperados exitosamente",
                limits
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<List<InventoryLimitsDTO.SimpleInventoryLimits>>> findByProductId(
            @PathVariable Long productId) {
        log.info("Solicitud para buscar límites de inventario para producto ID: {}", productId);

        List<InventoryLimitsDTO.SimpleInventoryLimits> limits = inventoryLimitsService.findByProductId(productId);

        ApiResponse<List<InventoryLimitsDTO.SimpleInventoryLimits>> response = new ApiResponse<>(
                true,
                "Límites de inventario para el producto recuperados exitosamente",
                limits
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/location/{locationId}")
    public ResponseEntity<ApiResponse<List<InventoryLimitsDTO.SimpleInventoryLimits>>> findByLocationId(
            @PathVariable Long locationId) {
        log.info("Solicitud para buscar límites de inventario para ubicación ID: {}", locationId);

        List<InventoryLimitsDTO.SimpleInventoryLimits> limits = inventoryLimitsService.findByLocationId(locationId);

        ApiResponse<List<InventoryLimitsDTO.SimpleInventoryLimits>> response = new ApiResponse<>(
                true,
                "Límites de inventario para la ubicación recuperados exitosamente",
                limits
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/exists/product/{productId}/location/{locationId}")
    public ResponseEntity<ApiResponse<Boolean>> existsByProductAndLocation(
            @PathVariable Long productId,
            @PathVariable Long locationId) {
        log.info("Verificando existencia de límites para producto ID: {} y ubicación ID: {}",
                productId, locationId);

        boolean exists = inventoryLimitsService.existsByProductAndLocation(productId, locationId);

        String message = exists
                ? "Existen límites de inventario para el producto y ubicación especificados"
                : "No existen límites de inventario para el producto y ubicación especificados";

        ApiResponse<Boolean> response = new ApiResponse<>(
                true,
                message,
                exists
        );

        return ResponseEntity.ok(response);
    }
}
