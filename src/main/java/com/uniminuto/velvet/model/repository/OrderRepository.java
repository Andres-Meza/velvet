package com.uniminuto.velvet.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uniminuto.velvet.model.entity.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Busca una orden por su número
     * @param orderNumber Número de la orden
     * @return Optional con la orden si existe
     */
    Optional<Order> findByOrderNumber(String orderNumber);

    /**
     * Busca órdenes por ID de ubicación
     * @param locationId ID de la ubicación
     * @return Lista de órdenes
     */
    List<Order> findByLocationId(Long locationId);

    /**
     * Busca órdenes por ID de usuario
     * @param userId ID del usuario
     * @return Lista de órdenes
     */
    List<Order> findByUserId(Long userId);
}