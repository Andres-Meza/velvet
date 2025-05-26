package com.uniminuto.velvet.service.impl;

import com.uniminuto.velvet.exception.ResourceNotFoundException;
import com.uniminuto.velvet.model.dto.InventoryMovementDTO;
import com.uniminuto.velvet.model.entity.InventoryMovement;
import com.uniminuto.velvet.model.entity.InventoryStock;
import com.uniminuto.velvet.model.entity.MovementType;
import com.uniminuto.velvet.model.mapper.InventoryMovementMapper;
import com.uniminuto.velvet.model.repository.InventoryMovementRepository;
import com.uniminuto.velvet.model.repository.InventoryStockRepository;
import com.uniminuto.velvet.service.interfaces.InventoryMovementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryMovementServiceImpl implements InventoryMovementService {

    private final InventoryMovementRepository inventoryMovementRepository;
    private final InventoryStockRepository inventoryStockRepository;
    private final InventoryMovementMapper inventoryMovementMapper;

    @Override
    @Transactional
    public InventoryMovementDTO.DetailsInventoryMovement createMovement(
            InventoryMovementDTO.CreateInventoryMovement dto) {
        log.info("Creando movimiento de inventario para producto: {}", dto.getProductId());

        // Verificar existencia del inventario
        InventoryStock inventoryStock = inventoryStockRepository.findById(dto.getInventoryStockId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró el inventario con ID: " + dto.getInventoryStockId()));

        // Convertir DTO a entidad
        InventoryMovement movement = inventoryMovementMapper.toEntity(dto);

        // Actualizar el stock según el tipo de movimiento
        updateInventoryStock(inventoryStock, movement);

        // Guardar el movimiento
        InventoryMovement savedMovement = inventoryMovementRepository.save(movement);

        log.info("Movimiento de inventario creado con ID: {}", savedMovement.getId());
        return inventoryMovementMapper.toDetailsDto(savedMovement);
    }

    @Override
    @Transactional
    public InventoryMovementDTO.DetailsInventoryMovement updateMovement(
            InventoryMovementDTO.UpdateInventoryMovement dto) {
        log.info("Actualizando movimiento de inventario ID: {}", dto.getId());

        // Buscar el movimiento existente
        InventoryMovement existingMovement = inventoryMovementRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró el movimiento con ID: " + dto.getId()));

        // Guardar valores originales para restaurar el stock si es necesario
        MovementType originalType = existingMovement.getMovementType();
        Integer originalQuantity = existingMovement.getQuantity();
        InventoryStock inventoryStock = existingMovement.getInventoryStock();

        // Revertir el movimiento original en el stock
        revertInventoryStockMovement(inventoryStock, originalType, originalQuantity);

        // Actualizar la entidad con los nuevos valores
        inventoryMovementMapper.updateEntityFromDto(dto, existingMovement);

        // Aplicar el nuevo movimiento al stock
        updateInventoryStock(inventoryStock, existingMovement);

        // Guardar los cambios
        InventoryMovement updatedMovement = inventoryMovementRepository.save(existingMovement);

        log.info("Movimiento de inventario actualizado ID: {}", updatedMovement.getId());
        return inventoryMovementMapper.toDetailsDto(updatedMovement);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InventoryMovementDTO.DetailsInventoryMovement> getMovementById(Long id) {
        log.debug("Buscando movimiento de inventario por ID: {}", id);
        return inventoryMovementRepository.findById(id)
                .map(inventoryMovementMapper::toDetailsDto);
    }

    @Override
    @Transactional
    public boolean deleteMovement(Long id) {
        log.info("Eliminando movimiento de inventario ID: {}", id);

        Optional<InventoryMovement> movementOpt = inventoryMovementRepository.findById(id);
        if (movementOpt.isPresent()) {
            InventoryMovement movement = movementOpt.get();

            // Revertir el efecto en el stock
            InventoryStock inventoryStock = movement.getInventoryStock();
            revertInventoryStockMovement(inventoryStock, movement.getMovementType(), movement.getQuantity());

            // Guardar cambios en el stock
            inventoryStockRepository.save(inventoryStock);

            // Eliminar el movimiento
            inventoryMovementRepository.deleteById(id);
            log.info("Movimiento de inventario eliminado ID: {}", id);
            return true;
        }

        log.warn("No se pudo eliminar el movimiento de inventario ID: {} - No encontrado", id);
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryMovementDTO.DetailsInventoryMovement> getAllMovements() {
        log.debug("Obteniendo todos los movimientos de inventario");
        return inventoryMovementRepository.findAll().stream()
                .map(inventoryMovementMapper::toDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryMovementDTO.DetailsInventoryMovement> getMovementsByType(MovementType movementType) {
        log.debug("Obteniendo movimientos de inventario por tipo: {}", movementType);
        return inventoryMovementRepository.findByMovementType(movementType).stream()
                .map(inventoryMovementMapper::toDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryMovementDTO.DetailsInventoryMovement> getMovementsByProductId(Long productId) {
        log.debug("Obteniendo movimientos de inventario por producto ID: {}", productId);
        return inventoryMovementRepository.findByProductId(productId).stream()
                .map(inventoryMovementMapper::toDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryMovementDTO.DetailsInventoryMovement> getMovementsByInventoryStockId(Long inventoryStockId) {
        log.debug("Obteniendo movimientos de inventario por inventario ID: {}", inventoryStockId);
        return inventoryMovementRepository.findByInventoryStockId(inventoryStockId).stream()
                .map(inventoryMovementMapper::toDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryMovementDTO.DetailsInventoryMovement> getMovementsByOrderId(Long orderId) {
        log.debug("Obteniendo movimientos de inventario por orden ID: {}", orderId);
        return inventoryMovementRepository.findByOrderId(orderId).stream()
                .map(inventoryMovementMapper::toDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryMovementDTO.DetailsInventoryMovement> getMovementsByUserId(Long userId) {
        log.debug("Obteniendo movimientos de inventario por usuario ID: {}", userId);
        return inventoryMovementRepository.findByCreatedById(userId).stream()
                .map(inventoryMovementMapper::toDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryMovementDTO.SimpleInventoryMovement> getAllMovementsSimple() {
        log.debug("Obteniendo versión simplificada de todos los movimientos de inventario");
        return inventoryMovementRepository.findAll().stream()
                .map(inventoryMovementMapper::toSimpleDto)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza el stock de inventario basado en el tipo de movimiento
     *
     * @param inventoryStock Stock a actualizar
     * @param movement       Movimiento a aplicar
     */
    private void updateInventoryStock(InventoryStock inventoryStock, InventoryMovement movement) {
        Long currentStock = inventoryStock.getCurrentStock();
        Integer quantity = movement.getQuantity();

        // Actualizar stock según el tipo de movimiento
        switch (movement.getMovementType().getName()) {
            case "ENTRADA":
            case "COMPRA":
            case "DEVOLUCION_CLIENTE":
                inventoryStock.setCurrentStock(currentStock + quantity);
                break;
            case "SALIDA":
            case "VENTA":
            case "DEVOLUCION_PROVEEDOR":
            case "BAJA":
                if (currentStock < quantity) {
                    throw new IllegalStateException("Stock insuficiente para realizar el movimiento");
                }
                inventoryStock.setCurrentStock(currentStock - quantity);
                break;
            default:
                log.warn("Tipo de movimiento no reconocido: {}", movement.getMovementType().getName());
        }

        // Guardar los cambios en el stock
        inventoryStockRepository.save(inventoryStock);
    }

    /**
     * Revierte el efecto de un movimiento en el stock de inventario
     *
     * @param inventoryStock Stock a actualizar
     * @param movementType   Tipo de movimiento a revertir
     * @param quantity       Cantidad a revertir
     */
    private void revertInventoryStockMovement(InventoryStock inventoryStock, MovementType movementType, Integer quantity) {
        Long currentStock = inventoryStock.getCurrentStock();

        // Revertir el efecto según el tipo de movimiento
        switch (movementType.getName()) {
            case "ENTRADA":
            case "COMPRA":
            case "DEVOLUCION_CLIENTE":
                if (currentStock < quantity) {
                    throw new IllegalStateException("No se puede revertir el movimiento: stock insuficiente");
                }
                inventoryStock.setCurrentStock(currentStock - quantity);
                break;
            case "SALIDA":
            case "VENTA":
            case "DEVOLUCION_PROVEEDOR":
            case "BAJA":
                inventoryStock.setCurrentStock(currentStock + quantity);
                break;
            default:
                log.warn("Tipo de movimiento no reconocido para revertir: {}", movementType.getName());
        }

        // Guardar los cambios en el stock
        inventoryStockRepository.save(inventoryStock);
    }
}