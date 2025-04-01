package com.uniminuto.velvet.service.impl;

import com.uniminuto.velvet.model.dto.OrderStatusDTO;
import com.uniminuto.velvet.model.entity.OrderStatus;
import com.uniminuto.velvet.model.mapper.OrderStatusMapper;
import com.uniminuto.velvet.model.repository.OrderStatusRepository;
import com.uniminuto.velvet.service.interfaces.OrderStatusService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {

  private final OrderStatusRepository orderStatusRepository;
  private final OrderStatusMapper orderStatusMapper;

  @Override
  @Transactional
  public OrderStatusDTO.DetailsOrderStatus createOrderStatus(OrderStatusDTO.CreateOrderStatus createOrderStatus) {
    // Verificar si ya existe un recurso con el mismo nombre
    if (orderStatusRepository.existsByName((createOrderStatus.getName()))) {
      throw new RuntimeException("Ya existe un tipo de producto con este nombre");
    }

    // Convertir DTO a entidad
    OrderStatus orderStatus = orderStatusMapper.toEntity(createOrderStatus);
    
    // Guardar en base de datos
    OrderStatus savedOrderStatus = orderStatusRepository.save(orderStatus);
    
    // Convertir entidad guardada a DTO de respuesta
    return orderStatusMapper.toDetailsDTO(savedOrderStatus);
  }

  @Override
  @Transactional
  public OrderStatusDTO.DetailsOrderStatus updateOrderStatus(OrderStatusDTO.UpdateOrderStatus updateOrderStatus) {
    // Buscar el recurso existente
    OrderStatus existingOrderStatus = orderStatusRepository.findById(updateOrderStatus.getId())
      .orElseThrow(() -> new RuntimeException("Tipo de producto no encontrado"));

    // Verificar si el nuevo nombre ya existe (si es diferente)
    if (!existingOrderStatus.getName().equals(updateOrderStatus.getName()) &&
        orderStatusRepository.existsByName(updateOrderStatus.getName())) {
      throw new RuntimeException("Ya existe un tipo de producto con este nombre");
    }

    // Actualizar la entidad
    orderStatusMapper.updateEntityFromDto(updateOrderStatus, existingOrderStatus);
    
    // Guardar cambios
    OrderStatus updatedOrderStatus = orderStatusRepository.save(existingOrderStatus);
    
    // Convertir a DTO de respuesta
    return orderStatusMapper.toDetailsDTO(updatedOrderStatus);
  }

  @Override
  public OrderStatusDTO.DetailsOrderStatus getOrderStatusById(Long id) {
    // Buscar recurso por ID
    OrderStatus orderStatus = orderStatusRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Tipo de producto no encontrado"));
    
    // Convertir a DTO de detalle
    return orderStatusMapper.toDetailsDTO(orderStatus);
  }

  @Override
  public List<OrderStatusDTO.DetailsOrderStatus> getAllOrderStatuses() {
    // Obtener todos los recursos y convertir a lista de respuestas
    List<OrderStatus> orderStatuses = orderStatusRepository.findAll();
    return orderStatusMapper.toDetailsDTOList(orderStatuses);
  }

  @Override
  @Transactional
  public boolean deleteOrderStatus(Long id) {
    // Verificar si el recurso existe
    Optional<OrderStatus> orderStatusOptional = orderStatusRepository.findById(id);
    
    if (orderStatusOptional.isPresent()) {
      orderStatusRepository.delete(orderStatusOptional.get());
      return true;
    }
    
    return false;
  }

  @Override
  public boolean existsByName(String name) {
    // Verificar existencia de un recurso por nombre
    return orderStatusRepository.existsByName(name);
  }
}