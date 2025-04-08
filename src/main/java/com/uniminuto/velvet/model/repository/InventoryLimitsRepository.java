package com.uniminuto.velvet.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uniminuto.velvet.model.entity.InventoryLimits;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryLimitsRepository extends JpaRepository<InventoryLimits, Long> {

    /**
     * Buscar límites de inventario por producto y ubicación
     */
    Optional<InventoryLimits> findByProductIdAndLocationId(Long productId, Long locationId);

    /**
     * Verificar si existen límites para un producto y ubicación
     */
    boolean existsByProductIdAndLocationId(Long productId, Long locationId);

    /**
     * Buscar todos los límites de inventario para un producto
     */
    List<InventoryLimits> findByProductId(Long productId);

    /**
     * Buscar todos los límites de inventario para una ubicación
     */
    List<InventoryLimits> findByLocationId(Long locationId);
}