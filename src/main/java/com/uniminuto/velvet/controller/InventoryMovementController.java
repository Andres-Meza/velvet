package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.model.dto.InventoryMovementDTO;
import com.uniminuto.velvet.model.entity.MovementType;
import com.uniminuto.velvet.service.interfaces.InventoryMovementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v4/inventory-movements")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Movimientos de Inventario", description = "API para gestionar movimientos de inventario")
public class InventoryMovementController {

    private final InventoryMovementService inventoryMovementService;

    @Operation(summary = "Crear un nuevo movimiento de inventario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movimiento creado correctamente",
                    content = @Content(schema = @Schema(implementation = InventoryMovementDTO.DetailsInventoryMovement.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Recurso relacionado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<InventoryMovementDTO.DetailsInventoryMovement> createMovement(
            @Valid @RequestBody InventoryMovementDTO.CreateInventoryMovement dto) {
        log.info("Petición recibida para crear movimiento de inventario para producto ID: {}", dto.getProductId());
        InventoryMovementDTO.DetailsInventoryMovement createdMovement = inventoryMovementService.createMovement(dto);
        return new ResponseEntity<>(createdMovement, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un movimiento de inventario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimiento actualizado correctamente",
                    content = @Content(schema = @Schema(implementation = InventoryMovementDTO.DetailsInventoryMovement.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Movimiento no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping
    public ResponseEntity<InventoryMovementDTO.DetailsInventoryMovement> updateMovement(
            @Valid @RequestBody InventoryMovementDTO.UpdateInventoryMovement dto) {
        log.info("Petición recibida para actualizar movimiento de inventario ID: {}", dto.getId());
        InventoryMovementDTO.DetailsInventoryMovement updatedMovement = inventoryMovementService.updateMovement(dto);
        return ResponseEntity.ok(updatedMovement);
    }

    @Operation(summary = "Obtener un movimiento de inventario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimiento encontrado",
                    content = @Content(schema = @Schema(implementation = InventoryMovementDTO.DetailsInventoryMovement.class))),
            @ApiResponse(responseCode = "404", description = "Movimiento no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<InventoryMovementDTO.DetailsInventoryMovement> getMovementById(
            @Parameter(description = "ID del movimiento", required = true) @PathVariable Long id) {
        log.info("Petición recibida para obtener movimiento de inventario ID: {}", id);
        return inventoryMovementService.getMovementById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un movimiento de inventario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Movimiento eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Movimiento no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovement(
            @Parameter(description = "ID del movimiento", required = true) @PathVariable Long id) {
        log.info("Petición recibida para eliminar movimiento de inventario ID: {}", id);
        boolean deleted = inventoryMovementService.deleteMovement(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Obtener todos los movimientos de inventario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de movimientos obtenida",
                    content = @Content(schema = @Schema(implementation = InventoryMovementDTO.DetailsInventoryMovement.class)))
    })
    @GetMapping
    public ResponseEntity<List<InventoryMovementDTO.DetailsInventoryMovement>> getAllMovements() {
        log.info("Petición recibida para obtener todos los movimientos de inventario");
        List<InventoryMovementDTO.DetailsInventoryMovement> movements = inventoryMovementService.getAllMovements();
        return ResponseEntity.ok(movements);
    }

    @Operation(summary = "Obtener movimientos de inventario por tipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de movimientos obtenida",
                    content = @Content(schema = @Schema(implementation = InventoryMovementDTO.DetailsInventoryMovement.class)))
    })
    @GetMapping("/by-type")
    public ResponseEntity<List<InventoryMovementDTO.DetailsInventoryMovement>> getMovementsByType(
            @Parameter(description = "Tipo de movimiento", required = true) @RequestParam MovementType movementType) {
        log.info("Petición recibida para obtener movimientos por tipo: {}", movementType);
        List<InventoryMovementDTO.DetailsInventoryMovement> movements = inventoryMovementService.getMovementsByType(movementType);
        return ResponseEntity.ok(movements);
    }

    @Operation(summary = "Obtener movimientos de inventario por producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de movimientos obtenida",
                    content = @Content(schema = @Schema(implementation = InventoryMovementDTO.DetailsInventoryMovement.class)))
    })
    @GetMapping("/by-product/{productId}")
    public ResponseEntity<List<InventoryMovementDTO.DetailsInventoryMovement>> getMovementsByProductId(
            @Parameter(description = "ID del producto", required = true) @PathVariable Long productId) {
        log.info("Petición recibida para obtener movimientos por producto ID: {}", productId);
        List<InventoryMovementDTO.DetailsInventoryMovement> movements = inventoryMovementService.getMovementsByProductId(productId);
        return ResponseEntity.ok(movements);
    }

    @Operation(summary = "Obtener movimientos de inventario por stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de movimientos obtenida",
                    content = @Content(schema = @Schema(implementation = InventoryMovementDTO.DetailsInventoryMovement.class)))
    })
    @GetMapping("/by-inventory/{inventoryStockId}")
    public ResponseEntity<List<InventoryMovementDTO.DetailsInventoryMovement>> getMovementsByInventoryStockId(
            @Parameter(description = "ID del stock de inventario", required = true) @PathVariable Long inventoryStockId) {
        log.info("Petición recibida para obtener movimientos por inventario ID: {}", inventoryStockId);
        List<InventoryMovementDTO.DetailsInventoryMovement> movements =
                inventoryMovementService.getMovementsByInventoryStockId(inventoryStockId);
        return ResponseEntity.ok(movements);
    }

    @Operation(summary = "Obtener movimientos de inventario por orden")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de movimientos obtenida",
                    content = @Content(schema = @Schema(implementation = InventoryMovementDTO.DetailsInventoryMovement.class)))
    })
    @GetMapping("/by-order/{orderId}")
    public ResponseEntity<List<InventoryMovementDTO.DetailsInventoryMovement>> getMovementsByOrderId(
            @Parameter(description = "ID de la orden", required = true) @PathVariable Long orderId) {
        log.info("Petición recibida para obtener movimientos por orden ID: {}", orderId);
        List<InventoryMovementDTO.DetailsInventoryMovement> movements = inventoryMovementService.getMovementsByOrderId(orderId);
        return ResponseEntity.ok(movements);
    }

    @Operation(summary = "Obtener movimientos de inventario por usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de movimientos obtenida",
                    content = @Content(schema = @Schema(implementation = InventoryMovementDTO.DetailsInventoryMovement.class)))
    })
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<InventoryMovementDTO.DetailsInventoryMovement>> getMovementsByUserId(
            @Parameter(description = "ID del usuario", required = true) @PathVariable Long userId) {
        log.info("Petición recibida para obtener movimientos por usuario ID: {}", userId);
        List<InventoryMovementDTO.DetailsInventoryMovement> movements = inventoryMovementService.getMovementsByUserId(userId);
        return ResponseEntity.ok(movements);
    }

    @Operation(summary = "Obtener una versión simplificada de todos los movimientos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de movimientos simplificados obtenida",
                    content = @Content(schema = @Schema(implementation = InventoryMovementDTO.SimpleInventoryMovement.class)))
    })
    @GetMapping("/simple")
    public ResponseEntity<List<InventoryMovementDTO.SimpleInventoryMovement>> getAllMovementsSimple() {
        log.info("Petición recibida para obtener versión simplificada de todos los movimientos");
        List<InventoryMovementDTO.SimpleInventoryMovement> movements = inventoryMovementService.getAllMovementsSimple();
        return ResponseEntity.ok(movements);
    }
}