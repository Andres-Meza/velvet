package com.uniminuto.velvet.service.interfaces;

import com.uniminuto.velvet.model.dto.OrderDetailDTO.*;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {

    /**
     * Crea un nuevo detalle de orden para una orden específica
     *
     * @param orderId ID de la orden a la que pertenece el detalle
     * @param dto     Datos del detalle de orden a crear
     * @return Detalle de orden creado
     */
    OrderDetailResponse createOrderDetail(Long orderId, CreateOrderDetail dto);

    /**
     * Obtiene un detalle de orden por su ID
     *
     * @param id ID del detalle de orden
     * @return Detalle de orden si existe
     */
    Optional<OrderDetailResponse> getOrderDetailById(Long id);

    /**
     * Actualiza un detalle de orden existente
     *
     * @param dto Datos para actualizar el detalle de orden
     * @return Detalle de orden actualizado
     */
    OrderDetailResponse updateOrderDetail(UpdateOrderDetail dto);

    /**
     * Elimina un detalle de orden por su ID
     *
     * @param id ID del detalle de orden a eliminar
     * @return true si se eliminó correctamente, false si no existe
     */
    boolean deleteOrderDetail(Long id);

    /**
     * Obtiene todos los detalles de una orden específica
     *
     * @param orderId ID de la orden
     * @return Lista de detalles de la orden
     */
    List<OrderDetailResponse> getOrderDetailsByOrderId(Long orderId);

    /**
     * Obtiene una versión simplificada de los detalles de una orden
     *
     * @param orderId ID de la orden
     * @return Lista simplificada de detalles de la orden
     */
    List<SimpleOrderDetail> getSimpleOrderDetailsByOrderId(Long orderId);

    /**
     * Obtiene estadísticas de ventas por producto
     *
     * @param productId ID del producto (opcional)
     * @return Estadísticas de ventas del producto
     */
    List<OrderDetailsByProduct> getOrderDetailsByProduct(Long productId);

    /**
     * Elimina todos los detalles asociados a una orden
     *
     * @param orderId ID de la orden
     */
    void deleteOrderDetailsByOrderId(Long orderId);
}