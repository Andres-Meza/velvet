package com.uniminuto.velvet.service.impl;

import com.uniminuto.velvet.model.dto.PaymentMethodDTO;
import com.uniminuto.velvet.model.entity.PaymentMethod;
import com.uniminuto.velvet.model.mapper.PaymentMethodMapper;
import com.uniminuto.velvet.model.repository.PaymentMethodRepository;
import com.uniminuto.velvet.service.interfaces.PaymentMethodService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {

  private final PaymentMethodRepository paymentMethodRepository;
  private final PaymentMethodMapper paymentMethodMapper;

  @Override
  @Transactional
  public PaymentMethodDTO.SimplePaymentMethod createPaymentMethod(PaymentMethodDTO.CreatePaymentMethod createPaymentMethod) {
    // Verificar si ya existe un recurso con el mismo nombre
    if (paymentMethodRepository.existsByName((createPaymentMethod.getName()))) {
      throw new RuntimeException("Ya existe un tipo de producto con este nombre");
    }

    // Convertir DTO a entidad
    PaymentMethod paymentMethod = paymentMethodMapper.toEntity(createPaymentMethod);
    
    // Guardar en base de datos
    PaymentMethod savedPaymentMethod = paymentMethodRepository.save(paymentMethod);
    
    // Convertir entidad guardada a DTO de respuesta
    return paymentMethodMapper.toSimpleDto(savedPaymentMethod);
  }

  @Override
  @Transactional
  public PaymentMethodDTO.SimplePaymentMethod updatePaymentMethod(PaymentMethodDTO.UpdatePaymentMethod updatePaymentMethod) {
    // Buscar el recurso existente
    PaymentMethod existingPaymentMethod = paymentMethodRepository.findById(updatePaymentMethod.getId())
      .orElseThrow(() -> new RuntimeException("Tipo de producto no encontrado"));

    // Verificar si el nuevo nombre ya existe (si es diferente)
    if (!existingPaymentMethod.getName().equals(updatePaymentMethod.getName()) &&
        paymentMethodRepository.existsByName(updatePaymentMethod.getName())) {
      throw new RuntimeException("Ya existe un tipo de producto con este nombre");
    }

    // Actualizar la entidad
    paymentMethodMapper.updateEntityFromDto(updatePaymentMethod, existingPaymentMethod);
    
    // Guardar cambios
    PaymentMethod updatedPaymentMethod = paymentMethodRepository.save(existingPaymentMethod);
    
    // Convertir a DTO de respuesta
    return paymentMethodMapper.toSimpleDto(updatedPaymentMethod);
  }

  @Override
  public PaymentMethodDTO.DetailsPaymentMethod getPaymentMethodById(Long id) {
    // Buscar recurso por ID
    PaymentMethod paymentMethod = paymentMethodRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Tipo de producto no encontrado"));
    
    // Convertir a DTO de detalle
    return paymentMethodMapper.toDetailsDto(paymentMethod);
  }

  @Override
  public List<PaymentMethodDTO.SimplePaymentMethod> getAllPaymentMethods() {
    // Obtener todos los recursos y convertir a lista de respuestas
    List<PaymentMethod> paymentMethods = paymentMethodRepository.findAll();
    return paymentMethodMapper.toSimpleDtoList(paymentMethods);
  }

  @Override
  public List<PaymentMethodDTO.DetailsPaymentMethod> getAllPaymentMethodsWithDetails() {
    // Obtener todos los recursos con detalles
    List<PaymentMethod> paymentMethods = paymentMethodRepository.findAll();
    return paymentMethodMapper.toDetailsDtoList(paymentMethods);
  }

  @Override
  @Transactional
  public boolean deletePaymentMethod(Long id) {
    // Verificar si el recurso existe
    Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(id);
    
    if (paymentMethodOptional.isPresent()) {
      paymentMethodRepository.delete(paymentMethodOptional.get());
      return true;
    }
    
    return false;
  }

  @Override
  public boolean existsByName(String name) {
    // Verificar existencia de un recurso por nombre
    return paymentMethodRepository.existsByName(name);
  }
}