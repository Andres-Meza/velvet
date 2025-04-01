package com.uniminuto.velvet.service.impl;

import com.uniminuto.velvet.model.dto.ProductTypeDTO;
import com.uniminuto.velvet.model.entity.ProductType;
import com.uniminuto.velvet.model.mapper.ProductTypeMapper;
import com.uniminuto.velvet.model.repository.ProductTypeRepository;
import com.uniminuto.velvet.service.interfaces.ProductTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductTypeServiceImpl implements ProductTypeService {

  private final ProductTypeRepository productTypeRepository;
  private final ProductTypeMapper productTypeMapper;

  @Override
  @Transactional
  public ProductTypeDTO.SimpleProductType createProductType(ProductTypeDTO.CreateProductType createProductType) {
    // Verificar si ya existe un recurso con el mismo nombre
    if (productTypeRepository.existsByName((createProductType.getName()))) {
      throw new RuntimeException("Ya existe un tipo de producto con este nombre");
    }

    // Convertir DTO a entidad
    ProductType productType = productTypeMapper.toEntity(createProductType);
    
    // Guardar en base de datos
    ProductType savedProductType = productTypeRepository.save(productType);
    
    // Convertir entidad guardada a DTO de respuesta
    return productTypeMapper.toSimpleDto(savedProductType);
  }

  @Override
  @Transactional
  public ProductTypeDTO.SimpleProductType updateProductType(ProductTypeDTO.UpdateProductType updateProductType) {
    // Buscar el recurso existente
    ProductType existingProductType = productTypeRepository.findById(updateProductType.getId())
      .orElseThrow(() -> new RuntimeException("Tipo de producto no encontrado"));

    // Verificar si el nuevo nombre ya existe (si es diferente)
    if (!existingProductType.getName().equals(updateProductType.getName()) &&
        productTypeRepository.existsByName(updateProductType.getName())) {
      throw new RuntimeException("Ya existe un tipo de producto con este nombre");
    }

    // Actualizar la entidad
    productTypeMapper.updateEntityFromDto(updateProductType, existingProductType);
    
    // Guardar cambios
    ProductType updatedProductType = productTypeRepository.save(existingProductType);
    
    // Convertir a DTO de respuesta
    return productTypeMapper.toSimpleDto(updatedProductType);
  }

  @Override
  public ProductTypeDTO.DetailsProductType getProductTypeById(Long id) {
    // Buscar recurso por ID
    ProductType productType = productTypeRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Tipo de producto no encontrado"));
    
    // Convertir a DTO de detalle
    return productTypeMapper.toDetailsDto(productType);
  }

  @Override
  public List<ProductTypeDTO.SimpleProductType> getAllProductTypes() {
    // Obtener todos los recursos y convertir a lista de respuestas
    List<ProductType> productTypes = productTypeRepository.findAll();
    return productTypeMapper.toSimpleDtoList(productTypes);
  }

  @Override
  public List<ProductTypeDTO.DetailsProductType> getAllProductTypesWithDetails() {
    // Obtener todos los recursos con detalles
    List<ProductType> productTypes = productTypeRepository.findAll();
    return productTypeMapper.toDetailsDtoList(productTypes);
  }

  @Override
  @Transactional
  public boolean deleteProductType(Long id) {
    // Verificar si el recurso existe
    Optional<ProductType> productTypeOptional = productTypeRepository.findById(id);
    
    if (productTypeOptional.isPresent()) {
      productTypeRepository.delete(productTypeOptional.get());
      return true;
    }
    
    return false;
  }

  @Override
  public boolean existsByName(String name) {
    // Verificar existencia de un recurso por nombre
    return productTypeRepository.existsByName(name);
  }
}