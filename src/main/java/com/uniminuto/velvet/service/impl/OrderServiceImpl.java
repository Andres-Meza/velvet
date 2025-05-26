package com.uniminuto.velvet.service.impl;

import com.uniminuto.velvet.model.dto.OrderDTO.CreateOrder;
import com.uniminuto.velvet.model.dto.OrderDTO.DetailsOrder;
import com.uniminuto.velvet.model.dto.OrderDTO.SimpleOrder;
import com.uniminuto.velvet.model.dto.OrderDTO.UpdateOrder;
import com.uniminuto.velvet.model.dto.OrderDTO.UpdateOrderStatus;
import com.uniminuto.velvet.model.dto.OrderDTO.UpdatePaymentStatus;
import com.uniminuto.velvet.model.entity.Order;
import com.uniminuto.velvet.model.entity.OrderDetail;
import com.uniminuto.velvet.model.mapper.OrderDetailMapper;
import com.uniminuto.velvet.model.mapper.OrderMapper;
import com.uniminuto.velvet.model.repository.OrderRepository;
import com.uniminuto.velvet.model.repository.OrderStatusRepository;
import com.uniminuto.velvet.model.repository.PaymentMethodRepository;
import com.uniminuto.velvet.model.repository.TablesRepository;
import com.uniminuto.velvet.model.repository.UserRepository;
import com.uniminuto.velvet.model.repository.LocationRepository;
import com.uniminuto.velvet.service.interfaces.OrderService;
import com.uniminuto.velvet.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final TablesRepository tableRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final OrderMapper orderMapper;
    private final OrderDetailMapper orderDetailMapper;

    @Override
    @Transactional
    public DetailsOrder createOrder(CreateOrder createOrderDTO) {
        log.info("Creando nueva orden con usuario ID: {}, ubicación ID: {}",
                createOrderDTO.getUserId(), createOrderDTO.getLocationId());

        // Validar que existen las entidades relacionadas
        userRepository.findById(createOrderDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + createOrderDTO.getUserId()));

        locationRepository.findById(createOrderDTO.getLocationId())
                .orElseThrow(() -> new ResourceNotFoundException("Ubicación no encontrada con ID: " + createOrderDTO.getLocationId()));

        if (createOrderDTO.getTableId() != null) {
            tableRepository.findById(createOrderDTO.getTableId())
                    .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada con ID: " + createOrderDTO.getTableId()));
        }

        orderStatusRepository.findById(createOrderDTO.getOrderStatusId())
                .orElseThrow(() -> new ResourceNotFoundException("Estado de orden no encontrado con ID: " + createOrderDTO.getOrderStatusId()));

        if (createOrderDTO.getPaymentMethodId() != null) {
            paymentMethodRepository.findById(createOrderDTO.getPaymentMethodId())
                    .orElseThrow(() -> new ResourceNotFoundException("Método de pago no encontrado con ID: " + createOrderDTO.getPaymentMethodId()));
        }

        // Mapear DTO a entidad
        Order newOrder = orderMapper.toEntity(createOrderDTO);

        // Generar número de orden único
        newOrder.setOrderNumber(generateOrderNumber());
        newOrder.setTotalAmount(createOrderDTO.getTotalAmount());
        newOrder.setPaid(createOrderDTO.isPaid());  // Nota: el getter sigue siendo isPaid por convención de Java

        // Guardar la orden primero para obtener su ID
        Order savedOrder = orderRepository.save(newOrder);

        // Procesar detalles de la orden si existen
        if (createOrderDTO.getOrderDetails() != null && !createOrderDTO.getOrderDetails().isEmpty()) {
            createOrderDTO.getOrderDetails().forEach(detailDTO -> {
                OrderDetail detail = orderDetailMapper.toEntity(detailDTO);
                detail.setOrder(savedOrder);
                savedOrder.addOrderDetail(detail);
            });

            // Guardar la orden actualizada con sus detalles
            orderRepository.save(savedOrder);
        }

        log.info("Orden creada exitosamente con ID: {} y número: {}",
                savedOrder.getId(), savedOrder.getOrderNumber());

        return orderMapper.toDetailsDTO(savedOrder);
    }

    @Override
    @Transactional
    public DetailsOrder updateOrder(UpdateOrder updateOrderDTO) {
        log.info("Actualizando orden con ID: {}", updateOrderDTO.getId());

        Order existingOrder = orderRepository.findById(updateOrderDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada con ID: " + updateOrderDTO.getId()));

        // Validar entidades relacionadas si se proporcionan IDs
        if (updateOrderDTO.getOrderStatusId() != null) {
            orderStatusRepository.findById(updateOrderDTO.getOrderStatusId())
                    .orElseThrow(() -> new ResourceNotFoundException("Estado de orden no encontrado con ID: " + updateOrderDTO.getOrderStatusId()));
        }

        if (updateOrderDTO.getPaymentMethodId() != null) {
            paymentMethodRepository.findById(updateOrderDTO.getPaymentMethodId())
                    .orElseThrow(() -> new ResourceNotFoundException("Método de pago no encontrado con ID: " + updateOrderDTO.getPaymentMethodId()));
        }

        // Actualizar la orden con los datos del DTO
        orderMapper.updateOrderFromDto(updateOrderDTO, existingOrder);

        // Actualizar campos adicionales que no maneja el mapper
        if (updateOrderDTO.getTotalAmount() != null) {
            existingOrder.setTotalAmount(updateOrderDTO.getTotalAmount());
        }

        if (updateOrderDTO.getPaid() != null) {
            existingOrder.setPaid(updateOrderDTO.getPaid());
        }

        if (updateOrderDTO.getCompletedDate() != null) {
            existingOrder.setCompletedDate(updateOrderDTO.getCompletedDate());
        }

        // Actualizar detalles de la orden si se proporcionan
        if (updateOrderDTO.getOrderDetails() != null && !updateOrderDTO.getOrderDetails().isEmpty()) {
            // Opción 1: Eliminar todos los detalles existentes y agregar los nuevos
            existingOrder.getOrderDetails().clear();

            updateOrderDTO.getOrderDetails().forEach(detailDTO -> {
                OrderDetail detail = orderDetailMapper.toEntity(detailDTO);
                detail.setOrder(existingOrder);
                existingOrder.addOrderDetail(detail);
            });
        }

        Order updatedOrder = orderRepository.save(existingOrder);
        log.info("Orden actualizada exitosamente con ID: {}", updatedOrder.getId());

        return orderMapper.toDetailsDTO(updatedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public DetailsOrder getOrderById(Long id) {
        log.info("Buscando orden por ID: {}", id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada con ID: " + id));

        return orderMapper.toDetailsDTO(order);
    }

    @Override
    @Transactional(readOnly = true)
    public DetailsOrder getOrderByNumber(String orderNumber) {
        log.info("Buscando orden por número: {}", orderNumber);

        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada con número: " + orderNumber));

        return orderMapper.toDetailsDTO(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SimpleOrder> getAllOrders() {
        log.info("Obteniendo todas las órdenes");
        List<Order> orders = orderRepository.findAll();
        return orderMapper.toSimpleDTOList(orders);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SimpleOrder> getOrdersByLocationId(Long locationId) {
        log.info("Buscando órdenes por ubicación ID: {}", locationId);

        // Validar que la ubicación existe
        locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Ubicación no encontrada con ID: " + locationId));

        List<Order> orders = orderRepository.findByLocationId(locationId);
        return orderMapper.toSimpleDTOList(orders);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SimpleOrder> getOrdersByUserId(Long userId) {
        log.info("Buscando órdenes por usuario ID: {}", userId);

        // Validar que el usuario existe
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + userId));

        List<Order> orders = orderRepository.findByUserId(userId);
        return orderMapper.toSimpleDTOList(orders);
    }

    @Override
    @Transactional
    public DetailsOrder updateOrderStatus(UpdateOrderStatus updateOrderStatusDTO) {
        log.info("Actualizando estado de orden con ID: {}", updateOrderStatusDTO.getId());

        Order existingOrder = orderRepository.findById(updateOrderStatusDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada con ID: " + updateOrderStatusDTO.getId()));

        // Validar que el estado existe
        orderStatusRepository.findById(updateOrderStatusDTO.getOrderStatusId())
                .orElseThrow(() -> new ResourceNotFoundException("Estado de orden no encontrado con ID: " + updateOrderStatusDTO.getOrderStatusId()));

        // Actualizar el estado
        existingOrder.getOrderStatus().setId(updateOrderStatusDTO.getOrderStatusId());  // Corregido: Usar setId directamente ya que el mapper tiene un error

        // Actualizar fecha de completado si se proporciona
        if (updateOrderStatusDTO.getCompletedDate() != null) {
            existingOrder.setCompletedDate(updateOrderStatusDTO.getCompletedDate());
        }

        Order updatedOrder = orderRepository.save(existingOrder);
        log.info("Estado de orden actualizado exitosamente para orden ID: {}", updatedOrder.getId());

        return orderMapper.toDetailsDTO(updatedOrder);
    }

    @Override
    @Transactional
    public DetailsOrder updatePaymentStatus(UpdatePaymentStatus updatePaymentStatusDTO) {
        log.info("Actualizando estado de pago para orden con ID: {}", updatePaymentStatusDTO.getId());

        Order existingOrder = orderRepository.findById(updatePaymentStatusDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada con ID: " + updatePaymentStatusDTO.getId()));

        // Validar que el método de pago existe
        paymentMethodRepository.findById(updatePaymentStatusDTO.getPaymentMethodId())
                .orElseThrow(() -> new ResourceNotFoundException("Método de pago no encontrado con ID: " + updatePaymentStatusDTO.getPaymentMethodId()));

        // Actualizar el estado de pago
        orderMapper.updatePaymentStatus(updatePaymentStatusDTO, existingOrder);
        existingOrder.setPaid(updatePaymentStatusDTO.isPaid());

        // Si se marca como pagada, registrar fecha de completado si no existe
        if (updatePaymentStatusDTO.isPaid() && existingOrder.getCompletedDate() == null) {
            existingOrder.setCompletedDate(LocalDateTime.now());
        }

        Order updatedOrder = orderRepository.save(existingOrder);
        log.info("Estado de pago actualizado exitosamente para orden ID: {}", updatedOrder.getId());

        return orderMapper.toDetailsDTO(updatedOrder);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        log.info("Eliminando orden con ID: {}", id);

        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada con ID: " + id));

        orderRepository.delete(existingOrder);
        log.info("Orden eliminada exitosamente con ID: {}", id);
    }

    /**
     * Genera un número de orden único basado en UUID
     * @return Número de orden formateado
     */
    private String generateOrderNumber() {
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        return "ORD-" + uuid;
    }
}