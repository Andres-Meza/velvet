package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.model.dto.TablesDTO;
import com.uniminuto.velvet.util.ApiResponse;
import com.uniminuto.velvet.exception.ResourceNotFoundException;
import com.uniminuto.velvet.exception.UniqueConstraintViolationException;
import com.uniminuto.velvet.service.interfaces.TablesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v4/tables")
@RequiredArgsConstructor
@Slf4j
public class TablesController {

    private final TablesService tablesService;

    @PostMapping
    public ResponseEntity<ApiResponse<TablesDTO.DetailsTable>> createTable(
            @Valid @RequestBody TablesDTO.CreateTable createTableDTO) {
        log.info("Solicitud para crear nueva mesa recibida: {}", createTableDTO);

        try {
            TablesDTO.DetailsTable createdTable = tablesService.createTable(createTableDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Mesa creada exitosamente", createdTable));
        } catch (UniqueConstraintViolationException e) {
            log.warn("Error de restricción única al crear mesa: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (ResourceNotFoundException e) {
            log.warn("Recurso no encontrado al crear mesa: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error al crear mesa: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al crear la mesa: " + e.getMessage(), null));
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<TablesDTO.DetailsTable>> updateTable(
            @Valid @RequestBody TablesDTO.UpdateTable updateTableDTO) {
        log.info("Solicitud para actualizar mesa recibida: {}", updateTableDTO);

        try {
            TablesDTO.DetailsTable updatedTable = tablesService.updateTable(updateTableDTO);
            return ResponseEntity.ok(new ApiResponse<>(true, "Mesa actualizada exitosamente", updatedTable));
        } catch (UniqueConstraintViolationException e) {
            log.warn("Error de restricción única al actualizar mesa: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (ResourceNotFoundException e) {
            log.warn("Recurso no encontrado al actualizar mesa: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error al actualizar mesa: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al actualizar la mesa: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TablesDTO.DetailsTable>> getTableById(@PathVariable Long id) {
        log.info("Solicitud para obtener mesa con ID: {}", id);

        try {
            TablesDTO.DetailsTable table = tablesService.getTableById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Mesa encontrada", table));
        } catch (ResourceNotFoundException e) {
            log.warn("Mesa no encontrada con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error al obtener mesa con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener la mesa: " + e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TablesDTO.SimpleTable>>> getAllTables() {
        log.info("Solicitud para obtener todas las mesas");

        try {
            List<TablesDTO.SimpleTable> tables = tablesService.getAllTables();
            return ResponseEntity.ok(new ApiResponse<>(true,
                    tables.isEmpty() ? "No hay mesas registradas" : "Mesas encontradas", tables));
        } catch (Exception e) {
            log.error("Error al obtener todas las mesas: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener las mesas: " + e.getMessage(), null));
        }
    }

    @GetMapping("/location/{locationId}")
    public ResponseEntity<ApiResponse<List<TablesDTO.SimpleTable>>> getTablesByLocation(
            @PathVariable Long locationId) {
        log.info("Solicitud para obtener mesas por ubicación con ID: {}", locationId);

        try {
            List<TablesDTO.SimpleTable> tables = tablesService.getTablesByLocation(locationId);
            return ResponseEntity.ok(new ApiResponse<>(true,
                    tables.isEmpty() ? "No hay mesas para esta ubicación" : "Mesas encontradas", tables));
        } catch (ResourceNotFoundException e) {
            log.warn("Ubicación no encontrada con ID {}: {}", locationId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error al obtener mesas por ubicación con ID {}: {}", locationId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener las mesas: " + e.getMessage(), null));
        }
    }

    @GetMapping("/available/{capacity}")
    public ResponseEntity<ApiResponse<List<TablesDTO.SimpleTable>>> getAvailableTablesByCapacity(
            @PathVariable Integer capacity) {
        log.info("Solicitud para obtener mesas disponibles con capacidad mínima: {}", capacity);

        try {
            List<TablesDTO.SimpleTable> tables = tablesService.getAvailableTablesByCapacity(capacity);
            return ResponseEntity.ok(new ApiResponse<>(true,
                    tables.isEmpty() ? "No hay mesas disponibles con esa capacidad" : "Mesas disponibles encontradas", tables));
        } catch (Exception e) {
            log.error("Error al obtener mesas disponibles por capacidad {}: {}", capacity, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener las mesas disponibles: " + e.getMessage(), null));
        }
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<ApiResponse<Void>> deactivateTable(@PathVariable Long id) {
        log.info("Solicitud para desactivar mesa con ID: {}", id);

        try {
            boolean deactivated = tablesService.deactivateTable(id);
            if (deactivated) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Mesa desactivada exitosamente", null));
            } else {
                return ResponseEntity.ok(new ApiResponse<>(true, "La mesa ya estaba desactivada", null));
            }
        } catch (ResourceNotFoundException e) {
            log.warn("Mesa no encontrada para desactivar con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error al desactivar mesa con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al desactivar la mesa: " + e.getMessage(), null));
        }
    }

    @GetMapping("/check-exists")
    public ResponseEntity<ApiResponse<Boolean>> checkTableExists(
            @RequestParam String number, @RequestParam Long locationId) {
        log.info("Verificando existencia de mesa con número: {} en ubicación: {}", number, locationId);

        try {
            boolean exists = tablesService.existsByNumberAndLocation(number, locationId);
            String message = exists ? "Ya existe una mesa con este número en esta ubicación"
                    : "No existe una mesa con este número en esta ubicación";
            return ResponseEntity.ok(new ApiResponse<>(true, message, exists));
        } catch (Exception e) {
            log.error("Error al verificar existencia de mesa: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al verificar la existencia de la mesa: " + e.getMessage(), null));
        }
    }
}