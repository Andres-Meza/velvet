package com.uniminuto.velvet.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uniminuto.velvet.model.entity.InventoryStock;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryStockRepository extends JpaRepository<InventoryStock, Long> {

    /**
     * Busca un registro de inventario por producto y ubicación
     *
     * @param productId ID del producto
     * @param locationId ID de la ubicación
     * @return registro de inventario si existe
     */
    Optional<InventoryStock> findByProductIdAndLocationId(Long productId, Long locationId);

    /**
     * Busca todos los registros de inventario para un producto
     *
     * @param productId ID del producto
     * @return lista de registros de inventario
     */
    List<InventoryStock> findByProductId(Long productId);

    /**
     * Busca todos los registros de inventario para una ubicación
     *
     * @param locationId ID de la ubicación
     * @return lista de registros de inventario
     */
    List<InventoryStock> findByLocationId(Long locationId);
}