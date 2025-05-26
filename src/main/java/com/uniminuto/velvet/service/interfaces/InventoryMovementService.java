package com.uniminuto.velvet.service.interfaces;

import com.uniminuto.velvet.model.dto.InventoryMovementDTO;
import com.uniminuto.velvet.model.entity.MovementType;

import java.util.List;
import java.util.Optional;

public interface InventoryMovementService {

    /**
     * Crea un nuevo movimiento de inventario
     *
     * @param dto Datos del movimiento a crear
     * @return Movimiento creado con detalles
     */
    InventoryMovementDTO.DetailsInventoryMovement createMovement(InventoryMovementDTO.CreateInventoryMovement dto);

    /**
     * Actualiza un movimiento de inventario existente
     *
     * @param dto Datos para actualizar el movimiento
     * @return Movimiento actualizado con detalles
     */
    InventoryMovementDTO.DetailsInventoryMovement updateMovement(InventoryMovementDTO.UpdateInventoryMovement dto);

    /**
     * Obtiene un movimiento de inventario por su ID
     *
     * @param id ID del movimiento
     * @return Optional con los detalles del movimiento si existe
     */
    Optional<InventoryMovementDTO.DetailsInventoryMovement> getMovementById(Long id);

    /**
     * Elimina un movimiento de inventario por su ID
     *
     * @param id ID del movimiento a eliminar
     * @return true si se eliminó correctamente, false si no existía
     */
    boolean deleteMovement(Long id);

    /**
     * Obtiene todos los movimientos de inventario
     *
     * @return Lista de movimientos con detalles
     */
    List<InventoryMovementDTO.DetailsInventoryMovement> getAllMovements();

    /**
     * Obtiene los movimientos de inventario por tipo de movimiento
     *
     * @param movementType Tipo de movimiento
     * @return Lista de movimientos con detalles
     */
    List<InventoryMovementDTO.DetailsInventoryMovement> getMovementsByType(MovementType movementType);

    /**
     * Obtiene los movimientos de inventario para un producto específico
     *
     * @param productId ID del producto
     * @return Lista de movimientos con detalles
     */
    List<InventoryMovementDTO.DetailsInventoryMovement> getMovementsByProductId(Long productId);

    /**
     * Obtiene los movimientos de inventario para un inventario específico
     *
     * @param inventoryStockId ID del inventario
     * @return Lista de movimientos con detalles
     */
    List<InventoryMovementDTO.DetailsInventoryMovement> getMovementsByInventoryStockId(Long inventoryStockId);

    /**
     * Obtiene los movimientos de inventario relacionados con una orden
     *
     * @param orderId ID de la orden
     * @return Lista de movimientos con detalles
     */
    List<InventoryMovementDTO.DetailsInventoryMovement> getMovementsByOrderId(Long orderId);

    /**
     * Obtiene los movimientos de inventario creados por un usuario específico
     *
     * @param userId ID del usuario
     * @return Lista de movimientos con detalles
     */
    List<InventoryMovementDTO.DetailsInventoryMovement> getMovementsByUserId(Long userId);

    /**
     * Obtiene una versión simplificada de todos los movimientos de inventario
     *
     * @return Lista de movimientos simplificados
     */
    List<InventoryMovementDTO.SimpleInventoryMovement> getAllMovementsSimple();
}