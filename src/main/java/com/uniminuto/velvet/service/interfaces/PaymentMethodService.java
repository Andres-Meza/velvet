package com.uniminuto.velvet.service.interfaces;

import com.uniminuto.velvet.model.dto.PaymentMethodDTO;

import java.util.List;

public interface PaymentMethodService {
	PaymentMethodDTO.SimplePaymentMethod createPaymentMethod(PaymentMethodDTO.CreatePaymentMethod createPaymentMethod);
	PaymentMethodDTO.SimplePaymentMethod updatePaymentMethod(PaymentMethodDTO.UpdatePaymentMethod updatePaymentMethod);
	PaymentMethodDTO.DetailsPaymentMethod getPaymentMethodById(Long id);
	List<PaymentMethodDTO.SimplePaymentMethod> getAllPaymentMethods();
	List<PaymentMethodDTO.DetailsPaymentMethod> getAllPaymentMethodsWithDetails();
	boolean deletePaymentMethod(Long id);
	boolean existsByName(String name);
}