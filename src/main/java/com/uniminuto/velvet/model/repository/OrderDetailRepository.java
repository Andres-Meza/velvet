package com.uniminuto.velvet.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uniminuto.velvet.model.entity.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    /**
     * Encuentra todos los detalles asociados a una orden específica
     *
     * @param orderId ID de la orden
     * @return Lista de detalles de la orden
     */
    List<OrderDetail> findByOrderId(Long orderId);

    /**
     * Encuentra todos los detalles asociados a un producto específico
     *
     * @param productId ID del producto
     * @return Lista de detalles que contienen el producto
     */
    List<OrderDetail> findByProductId(Long productId);

    /**
     * Elimina todos los detalles asociados a una orden específica
     *
     * @param orderId ID de la orden
     */
    void deleteByOrderId(Long orderId);
}