package com.uniminuto.velvet.service.impl;

import com.uniminuto.velvet.exception.ResourceNotFoundException;
import com.uniminuto.velvet.model.dto.OrderDetailDTO.*;
import com.uniminuto.velvet.model.entity.Order;
import com.uniminuto.velvet.model.entity.OrderDetail;
import com.uniminuto.velvet.model.entity.Product;
import com.uniminuto.velvet.model.mapper.OrderDetailMapper;
import com.uniminuto.velvet.model.repository.OrderDetailRepository;
import com.uniminuto.velvet.model.repository.OrderRepository;
import com.uniminuto.velvet.model.repository.ProductRepository;
import com.uniminuto.velvet.service.interfaces.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    @Transactional
    public OrderDetailResponse createOrderDetail(Long orderId, CreateOrderDetail dto) {
        // Verificar que la orden existe
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada con ID: " + orderId));

        // Verificar que el producto existe
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + dto.getProductId()));

        // Convertir DTO a entidad
        OrderDetail orderDetail = orderDetailMapper.toEntity(dto);
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);

        // Calcular subtotal
        orderDetailMapper.calculateSubtotal(orderDetail);

        // Guardar en base de datos
        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);

        // Retornar respuesta
        return orderDetailMapper.toDetailResponse(savedOrderDetail);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderDetailResponse> getOrderDetailById(Long id) {
        return orderDetailRepository.findById(id)
                .map(orderDetailMapper::toDetailResponse);
    }

    @Override
    @Transactional
    public OrderDetailResponse updateOrderDetail(UpdateOrderDetail dto) {
        // Verificar que el detalle existe
        OrderDetail existingDetail = orderDetailRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Detalle de orden no encontrado con ID: " + dto.getId()));

        // Actualizar campos desde DTO
        orderDetailMapper.updateFromDto(dto, existingDetail);

        // Si se cambió el producto, actualizarlo
        if (dto.getProductId() != null && !dto.getProductId().equals(existingDetail.getProduct().getId())) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + dto.getProductId()));
            existingDetail.setProduct(product);
        }

        // Si se cambió la orden, actualizarla
        if (dto.getOrderId() != null && !dto.getOrderId().equals(existingDetail.getOrder().getId())) {
            Order order = orderRepository.findById(dto.getOrderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada con ID: " + dto.getOrderId()));
            existingDetail.setOrder(order);
        }

        // Recalcular subtotal
        orderDetailMapper.calculateSubtotal(existingDetail);

        // Guardar cambios
        OrderDetail updatedDetail = orderDetailRepository.save(existingDetail);

        // Retornar respuesta
        return orderDetailMapper.toDetailResponse(updatedDetail);
    }

    @Override
    @Transactional
    public boolean deleteOrderDetail(Long id) {
        if (orderDetailRepository.existsById(id)) {
            orderDetailRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDetailResponse> getOrderDetailsByOrderId(Long orderId) {
        // Verificar que la orden existe
        if (!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("Orden no encontrada con ID: " + orderId);
        }

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        return orderDetailMapper.toDetailResponseList(orderDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SimpleOrderDetail> getSimpleOrderDetailsByOrderId(Long orderId) {
        // Verificar que la orden existe
        if (!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("Orden no encontrada con ID: " + orderId);
        }

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        return orderDetailMapper.toSimpleDetailList(orderDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDetailsByProduct> getOrderDetailsByProduct(Long productId) {
        List<OrderDetail> orderDetails;

        if (productId != null) {
            // Si se proporciona un ID de producto específico
            orderDetails = orderDetailRepository.findByProductId(productId);

            if (orderDetails.isEmpty()) {
                throw new ResourceNotFoundException("No se encontraron detalles para el producto con ID: " + productId);
            }

            // Agregar todas las estadísticas para este producto
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + productId));

            return List.of(orderDetailMapper.toOrderDetailsByProductWithAggregation(
                    orderDetails, productId, product.getName()));
        } else {
            // Si no se proporciona un ID, agrupar por producto
            orderDetails = orderDetailRepository.findAll();

            // Agrupar por producto
            Map<Long, List<OrderDetail>> detailsByProduct = orderDetails.stream()
                    .collect(Collectors.groupingBy(detail -> detail.getProduct().getId()));

            // Crear estadísticas para cada producto
            return detailsByProduct.entrySet().stream()
                    .map(entry -> {
                        Long pId = entry.getKey();
                        List<OrderDetail> details = entry.getValue();
                        String productName = details.get(0).getProduct().getName();

                        return orderDetailMapper.toOrderDetailsByProductWithAggregation(
                                details, pId, productName);
                    })
                    .collect(Collectors.toList());
        }
    }

    @Override
    @Transactional
    public void deleteOrderDetailsByOrderId(Long orderId) {
        // Verificar que la orden existe
        if (!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("Orden no encontrada con ID: " + orderId);
        }

        orderDetailRepository.deleteByOrderId(orderId);
    }
}