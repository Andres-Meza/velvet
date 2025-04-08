package com.uniminuto.velvet.service.interfaces;

import com.uniminuto.velvet.model.dto.InventoryLimitsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface InventoryLimitsService {

    /**
     * Crear nuevos límites de inventario
     *
     * @param dto Datos para crear límites de inventario
     * @return Detalles de los límites creados
     */
    InventoryLimitsDTO.DetailsInventoryLimits create(InventoryLimitsDTO.CreateInventoryLimits dto);

    /**
     * Actualizar límites de inventario existentes
     *
     * @param dto Datos para actualizar límites de inventario
     * @return Detalles de los límites actualizados
     */
    InventoryLimitsDTO.DetailsInventoryLimits update(InventoryLimitsDTO.UpdateInventoryLimits dto);

    /**
     * Eliminar límites de inventario por ID
     *
     * @param id ID de los límites a eliminar
     */
    void delete(Long id);

    /**
     * Buscar límites de inventario por ID
     *
     * @param id ID de los límites a buscar
     * @return Detalles de los límites encontrados
     */
    Optional<InventoryLimitsDTO.DetailsInventoryLimits> findById(Long id);

    /**
     * Buscar límites de inventario por producto y ubicación
     *
     * @param productId ID del producto
     * @param locationId ID de la ubicación
     * @return Detalles de los límites encontrados
     */
    Optional<InventoryLimitsDTO.DetailsInventoryLimits> findByProductAndLocation(Long productId, Long locationId);

    /**
     * Buscar todos los límites de inventario con paginación
     *
     * @param pageable Información de paginación
     * @return Página de límites de inventario
     */
    Page<InventoryLimitsDTO.SimpleInventoryLimits> findAll(Pageable pageable);

    /**
     * Buscar límites de inventario por producto
     *
     * @param productId ID del producto
     * @return Lista de límites de inventario
     */
    List<InventoryLimitsDTO.SimpleInventoryLimits> findByProductId(Long productId);

    /**
     * Buscar límites de inventario por ubicación
     *
     * @param locationId ID de la ubicación
     * @return Lista de límites de inventario
     */
    List<InventoryLimitsDTO.SimpleInventoryLimits> findByLocationId(Long locationId);

    /**
     * Verificar si existen límites para un producto y una ubicación
     *
     * @param productId ID del producto
     * @param locationId ID de la ubicación
     * @return true si existen límites, false en caso contrario
     */
    boolean existsByProductAndLocation(Long productId, Long locationId);
}
