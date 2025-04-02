package com.uniminuto.velvet.service.interfaces;

import com.uniminuto.velvet.model.dto.TablesDTO;

import java.util.List;

public interface TablesService {

    /**
     * Crea una nueva mesa
     * @param createTableDTO DTO con información para crear la mesa
     * @return DTO con detalles de la mesa creada
     */
    TablesDTO.DetailsTable createTable(TablesDTO.CreateTable createTableDTO);

    /**
     * Actualiza una mesa existente
     * @param updateTableDTO DTO con información actualizada
     * @return DTO con detalles de la mesa actualizada
     */
    TablesDTO.DetailsTable updateTable(TablesDTO.UpdateTable updateTableDTO);

    /**
     * Obtiene una mesa por su ID
     * @param id ID de la mesa
     * @return DTO con detalles de la mesa
     */
    TablesDTO.DetailsTable getTableById(Long id);

    /**
     * Obtiene todas las mesas
     * @return Lista de DTOs simples de mesas
     */
    List<TablesDTO.SimpleTable> getAllTables();

    /**
     * Obtiene todas las mesas por ubicación
     * @param locationId ID de la ubicación
     * @return Lista de DTOs simples de mesas
     */
    List<TablesDTO.SimpleTable> getTablesByLocation(Long locationId);

    /**
     * Obtiene mesas disponibles por capacidad
     * @param capacity Capacidad mínima requerida
     * @return Lista de DTOs simples de mesas
     */
    List<TablesDTO.SimpleTable> getAvailableTablesByCapacity(Integer capacity);

    /**
     * Desactiva una mesa
     * @param id ID de la mesa a desactivar
     * @return true si se desactivó correctamente
     */
    boolean deactivateTable(Long id);

    /**
     * Verifica si existe una mesa con el mismo número en la misma ubicación
     * @param number Número de mesa
     * @param locationId ID de la ubicación
     * @return true si ya existe
     */
    boolean existsByNumberAndLocation(String number, Long locationId);
}