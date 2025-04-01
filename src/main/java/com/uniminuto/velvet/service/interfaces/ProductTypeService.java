package com.uniminuto.velvet.service.interfaces;

import com.uniminuto.velvet.model.dto.ProductTypeDTO;

import java.util.List;

public interface ProductTypeService {
	ProductTypeDTO.SimpleProductType createProductType(ProductTypeDTO.CreateProductType createProductType);
	ProductTypeDTO.SimpleProductType updateProductType(ProductTypeDTO.UpdateProductType updateProductType);
	ProductTypeDTO.DetailsProductType getProductTypeById(Long id);
	List<ProductTypeDTO.SimpleProductType> getAllProductTypes();
	List<ProductTypeDTO.DetailsProductType> getAllProductTypesWithDetails();
	boolean deleteProductType(Long id);
	boolean existsByName(String name);
}