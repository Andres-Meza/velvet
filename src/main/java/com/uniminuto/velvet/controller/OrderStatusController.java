package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.model.dto.OrderStatusDTO.CreateOrderStatus;
import com.uniminuto.velvet.model.dto.OrderStatusDTO.DetailsOrderStatus;
import com.uniminuto.velvet.model.dto.OrderStatusDTO.UpdateOrderStatus;
import com.uniminuto.velvet.service.interfaces.OrderStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v4/orderStatuses")
@RequiredArgsConstructor
public class OrderStatusController {

  private final OrderStatusService orderStatusService;

  /**
   * Crear un nuevo estado de orden.
   * 
   * @param createOrderStatus DTO con los datos para crear el estado de orden
   * @return Respuesta con el estado de orden creado y estado HTTP 201
   */
  @PostMapping
  public ResponseEntity<DetailsOrderStatus> createOrderStatus(
    @Valid @RequestBody CreateOrderStatus createOrderStatus
  ) {
    DetailsOrderStatus createdOrderStatus = orderStatusService.createOrderStatus(createOrderStatus);
    return new ResponseEntity<>(createdOrderStatus, HttpStatus.CREATED);
  }

  /**
   * Actualizar un estado de orden existente.
   * 
   * @param updateOrderStatus DTO con los datos para actualizar el estado de orden
   * @return Respuesta con el estado de orden actualizado y estado HTTP 200
   */
  @PutMapping
  public ResponseEntity<DetailsOrderStatus> updateOrderStatus(
    @Valid @RequestBody UpdateOrderStatus updateOrderStatus
  ) {
    DetailsOrderStatus updatedOrderStatus = orderStatusService.updateOrderStatus(updateOrderStatus);
    return ResponseEntity.ok(updatedOrderStatus);
  }

  /**
   * Obtener un estado de orden por su ID.
   * 
   * @param id Identificador del estado de orden
   * @return Detalles del estado de orden y estado HTTP 200
   */
  @GetMapping("/{id}")
  public ResponseEntity<DetailsOrderStatus> getOrderStatusById(
    @PathVariable Long id
  ) {
    DetailsOrderStatus orderStatus = orderStatusService.getOrderStatusById(id);
    return ResponseEntity.ok(orderStatus);
  }

  /**
   * Obtener todos los estados de orden (versión básica).
   * 
   * @return Lista de estados de orden y estado HTTP 200
   */
  @GetMapping
  public ResponseEntity<List<DetailsOrderStatus>> getAllCategories() {
    List<DetailsOrderStatus> orderStatuses = orderStatusService.getAllOrderStatuses();
    return ResponseEntity.ok(orderStatuses);
  }

  /**
   * Eliminar un estado de orden por su ID.
   * 
   * @param id Identificador del estado de orden a eliminar
   * @return Estado HTTP 204 si la eliminación fue exitosa
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrderStatus(
    @PathVariable Long id
  ) {
    boolean deleted = orderStatusService.deleteOrderStatus(id);
    return deleted 
      ? ResponseEntity.noContent().build()
      : ResponseEntity.notFound().build();
  }

  /**
   * Verificar si existe un estado de orden por nombre.
   * 
   * @param name Nombre del estado de orden a verificar
   * @return Respuesta booleana y estado HTTP 200
   */
  @GetMapping("/exists")
  public ResponseEntity<Boolean> existsByName(
    @RequestParam String name
  ) {
    boolean exists = orderStatusService.existsByName(name);
    return ResponseEntity.ok(exists);
  }
}