package com.uniminuto.velvet.service.interfaces;

import com.uniminuto.velvet.model.dto.OrderDTO.CreateOrder;
import com.uniminuto.velvet.model.dto.OrderDTO.DetailsOrder;
import com.uniminuto.velvet.model.dto.OrderDTO.SimpleOrder;
import com.uniminuto.velvet.model.dto.OrderDTO.UpdateOrder;
import com.uniminuto.velvet.model.dto.OrderDTO.UpdateOrderStatus;
import com.uniminuto.velvet.model.dto.OrderDTO.UpdatePaymentStatus;

import java.util.List;

public interface OrderService {

    /**
     * Crea una nueva orden
     * @param createOrderDTO DTO con datos para la creación
     * @return DTO con detalles de la orden creada
     */
    DetailsOrder createOrder(CreateOrder createOrderDTO);

    /**
     * Actualiza una orden existente
     * @param updateOrderDTO DTO con datos para la actualización
     * @return DTO con detalles de la orden actualizada
     */
    DetailsOrder updateOrder(UpdateOrder updateOrderDTO);

    /**
     * Obtiene una orden por su ID
     * @param id ID de la orden
     * @return DTO con detalles de la orden
     */
    DetailsOrder getOrderById(Long id);

    /**
     * Obtiene una orden por su número
     * @param orderNumber Número de la orden
     * @return DTO con detalles de la orden
     */
    DetailsOrder getOrderByNumber(String orderNumber);

    /**
     * Obtiene todas las órdenes
     * @return Lista de DTOs simplificados de órdenes
     */
    List<SimpleOrder> getAllOrders();

    /**
     * Obtiene órdenes por ID de ubicación
     * @param locationId ID de la ubicación
     * @return Lista de DTOs simplificados de órdenes
     */
    List<SimpleOrder> getOrdersByLocationId(Long locationId);

    /**
     * Obtiene órdenes por ID de usuario
     * @param userId ID del usuario
     * @return Lista de DTOs simplificados de órdenes
     */
    List<SimpleOrder> getOrdersByUserId(Long userId);

    /**
     * Actualiza el estado de una orden
     * @param updateOrderStatusDTO DTO con datos para actualizar el estado
     * @return DTO con detalles de la orden actualizada
     */
    DetailsOrder updateOrderStatus(UpdateOrderStatus updateOrderStatusDTO);

    /**
     * Actualiza el estado de pago de una orden
     * @param updatePaymentStatusDTO DTO con datos para actualizar el pago
     * @return DTO con detalles de la orden actualizada
     */
    DetailsOrder updatePaymentStatus(UpdatePaymentStatus updatePaymentStatusDTO);

    /**
     * Elimina una orden por su ID
     * @param id ID de la orden a eliminar
     */
    void deleteOrder(Long id);
}
