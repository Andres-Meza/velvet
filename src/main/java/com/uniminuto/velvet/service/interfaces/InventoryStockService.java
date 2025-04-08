package com.uniminuto.velvet.service.interfaces;

import com.uniminuto.velvet.model.dto.InventoryStockDTO.CreateInventoryStock;
import com.uniminuto.velvet.model.dto.InventoryStockDTO.UpdateInventoryStock;
import com.uniminuto.velvet.model.dto.InventoryStockDTO.DetailsInventoryStock;
import com.uniminuto.velvet.model.dto.InventoryStockDTO.SimpleInventoryStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface InventoryStockService {

    /**
     * Crea un nuevo registro de inventario
     *
     * @param createInventoryStock datos para crear el nuevo inventario
     * @return detalles del inventario creado
     */
    DetailsInventoryStock createInventoryStock(CreateInventoryStock createInventoryStock);

    /**
     * Actualiza un registro de inventario existente
     *
     * @param updateInventoryStock datos para actualizar el inventario
     * @return detalles del inventario actualizado
     */
    DetailsInventoryStock updateInventoryStock(UpdateInventoryStock updateInventoryStock);

    /**
     * Obtiene un registro de inventario por su ID
     *
     * @param id ID del inventario a buscar
     * @return detalles del inventario si existe
     */
    Optional<DetailsInventoryStock> getInventoryStockById(Long id);

    /**
     * Obtiene todos los registros de inventario de forma paginada
     *
     * @param pageable configuración de paginación
     * @return página con registros de inventario
     */
    Page<SimpleInventoryStock> getAllInventoryStocks(Pageable pageable);

    /**
     * Obtiene los registros de inventario por producto
     *
     * @param productId ID del producto
     * @return lista de inventarios para el producto
     */
    List<SimpleInventoryStock> getInventoryStocksByProductId(Long productId);

    /**
     * Obtiene los registros de inventario por ubicación
     *
     * @param locationId ID de la ubicación
     * @return lista de inventarios para la ubicación
     */
    List<SimpleInventoryStock> getInventoryStocksByLocationId(Long locationId);

    /**
     * Elimina un registro de inventario
     *
     * @param id ID del inventario a eliminar
     * @return true si se eliminó correctamente
     */
    boolean deleteInventoryStock(Long id);

    /**
     * Actualiza el stock de un producto en una ubicación específica
     *
     * @param productId ID del producto
     * @param locationId ID de la ubicación
     * @param quantity cantidad a añadir (positiva) o restar (negativa)
     * @return detalles del inventario actualizado
     */
    DetailsInventoryStock updateStockQuantity(Long productId, Long locationId, Long quantity);

    /**
     * Verifica si hay suficiente stock para un producto en una ubicación
     *
     * @param productId ID del producto
     * @param locationId ID de la ubicación
     * @param quantity cantidad requerida
     * @return true si hay suficiente stock
     */
    boolean hasEnoughStock(Long productId, Long locationId, Long quantity);
}
