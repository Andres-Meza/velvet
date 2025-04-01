package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.model.dto.PaymentMethodDTO.CreatePaymentMethod;
import com.uniminuto.velvet.model.dto.PaymentMethodDTO.DetailsPaymentMethod;
import com.uniminuto.velvet.model.dto.PaymentMethodDTO.SimplePaymentMethod;
import com.uniminuto.velvet.model.dto.PaymentMethodDTO.UpdatePaymentMethod;
import com.uniminuto.velvet.service.interfaces.PaymentMethodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v4/paymentMethods")
@RequiredArgsConstructor
public class PaymentMethodController {

  private final PaymentMethodService paymentMethodService;

  /**
   * Crear un nuevo método de pago.
   * 
   * @param createPaymentMethod DTO con los datos para crear el método de pago
   * @return Respuesta con el método de pago creado y estado HTTP 201
   */
  @PostMapping
  public ResponseEntity<SimplePaymentMethod> createPaymentMethod(
    @Valid @RequestBody CreatePaymentMethod createPaymentMethod
  ) {
    SimplePaymentMethod createdPaymentMethod = paymentMethodService.createPaymentMethod(createPaymentMethod);
    return new ResponseEntity<>(createdPaymentMethod, HttpStatus.CREATED);
  }

  /**
   * Actualizar un método de pago existente.
   * 
   * @param updatePaymentMethod DTO con los datos para actualizar el método de pago
   * @return Respuesta con el método de pago actualizado y estado HTTP 200
   */
  @PutMapping
  public ResponseEntity<SimplePaymentMethod> updatePaymentMethod(
    @Valid @RequestBody UpdatePaymentMethod updatePaymentMethod
  ) {
    SimplePaymentMethod updatedPaymentMethod = paymentMethodService.updatePaymentMethod(updatePaymentMethod);
    return ResponseEntity.ok(updatedPaymentMethod);
  }

  /**
   * Obtener un método de pago por su ID.
   * 
   * @param id Identificador del método de pago
   * @return Detalles del método de pago y estado HTTP 200
   */
  @GetMapping("/{id}")
  public ResponseEntity<DetailsPaymentMethod> getPaymentMethodById(
    @PathVariable Long id
  ) {
    DetailsPaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(id);
    return ResponseEntity.ok(paymentMethod);
  }

  /**
   * Obtener todos los paymentMethods (versión básica).
   * 
   * @return Lista de paymentMethods y estado HTTP 200
   */
  @GetMapping
  public ResponseEntity<List<SimplePaymentMethod>> getAllCategories() {
    List<SimplePaymentMethod> paymentMethods = paymentMethodService.getAllPaymentMethods();
    return ResponseEntity.ok(paymentMethods);
  }

  /**
   * Obtener todos los paymentMethods con detalles completos.
   * 
   * @return Lista de paymentMethods con detalles y estado HTTP 200
   */
  @GetMapping("/details")
  public ResponseEntity<List<DetailsPaymentMethod>> getAllCategoriesWithDetails() {
    List<DetailsPaymentMethod> paymentMethods = paymentMethodService.getAllPaymentMethodsWithDetails();
    return ResponseEntity.ok(paymentMethods);
  }

  /**
   * Eliminar un método de pago por su ID.
   * 
   * @param id Identificador del método de pago a eliminar
   * @return Estado HTTP 204 si la eliminación fue exitosa
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePaymentMethod(
    @PathVariable Long id
  ) {
    boolean deleted = paymentMethodService.deletePaymentMethod(id);
    return deleted 
      ? ResponseEntity.noContent().build()
      : ResponseEntity.notFound().build();
  }

  /**
   * Verificar si existe un método de pago por nombre.
   * 
   * @param name Nombre del método de pago a verificar
   * @return Respuesta booleana y estado HTTP 200
   */
  @GetMapping("/exists")
  public ResponseEntity<Boolean> existsByName(
    @RequestParam String name
  ) {
    boolean exists = paymentMethodService.existsByName(name);
    return ResponseEntity.ok(exists);
  }
}