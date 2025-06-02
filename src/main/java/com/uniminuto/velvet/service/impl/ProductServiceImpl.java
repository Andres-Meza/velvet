package com.uniminuto.velvet.service.impl;

import com.uniminuto.velvet.model.dto.ProductDTO.CreateProduct;
import com.uniminuto.velvet.model.dto.ProductDTO.UpdateProduct;
import com.uniminuto.velvet.model.dto.ProductDTO.DetailsProduct;
import com.uniminuto.velvet.model.dto.ProductDTO.SimpleProduct;
import com.uniminuto.velvet.model.entity.Product;
import com.uniminuto.velvet.exception.ResourceNotFoundException;
import com.uniminuto.velvet.model.mapper.ProductMapper;
import com.uniminuto.velvet.model.repository.ProductRepository;
import com.uniminuto.velvet.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public DetailsProduct createProduct(CreateProduct createProductDTO) {
        log.info("Creando nuevo producto con nombre: {}", createProductDTO.getName());

        Product product = productMapper.toEntity(createProductDTO);
        Product savedProduct = productRepository.save(product);

        log.info("Producto creado con ID: {}", savedProduct.getId());
        return productMapper.toDetailsDTO(savedProduct);
    }

    @Override
    public DetailsProduct updateProduct(UpdateProduct updateProductDTO) {
        log.info("Actualizando producto con ID: {}", updateProductDTO.getId());

        Product product = productRepository.findById(updateProductDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + updateProductDTO.getId()));

        productMapper.updateEntityFromDto(updateProductDTO, product);
        Product updatedProduct = productRepository.save(product);

        log.info("Producto actualizado correctamente");
        return productMapper.toDetailsDTO(updatedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DetailsProduct> getProductById(Long id) {
        log.info("Buscando producto con ID: {}", id);
        return productRepository.findById(id).map(productMapper::toDetailsDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SimpleProduct> getAllProducts(Pageable pageable) {
        log.info("Obteniendo productos paginados");
        return productRepository.findAll(pageable).map(productMapper::toSimpleDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SimpleProduct> getProductsByProductType(Long productTypeId, Pageable pageable) {
        log.info("Buscando productos por tipo de producto ID: {}", productTypeId);
        return productRepository.findByProductTypeId(productTypeId, pageable).map(productMapper::toSimpleDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SimpleProduct> getProductsBySubCategory(Long subCategoryId, Pageable pageable) {
        log.info("Buscando productos por subcategoría ID: {}", subCategoryId);
        return productRepository.findBySubCategoryId(subCategoryId, pageable).map(productMapper::toSimpleDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SimpleProduct> getProductsByCategory(Long categoryId, Pageable pageable) {
        log.info("Buscando productos por categoría ID: {}", categoryId);
        return productRepository.findBySubCategoryCategoryId(categoryId, pageable).map(productMapper::toSimpleDTO);
    }

    @Override
    public DetailsProduct changeProductStatus(Long id, Boolean active) {
        log.info("Cambiando estado de producto ID: {} a {}", id, active);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        product.setActive(active);
        Product updatedProduct = productRepository.save(product);

        log.info("Estado de producto actualizado correctamente");
        return productMapper.toDetailsDTO(updatedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SimpleProduct> searchProducts(String query, Pageable pageable) {
        log.info("Buscando productos que contengan: {}", query);

        String searchTerm = "%" + query.toLowerCase() + "%";
        return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(searchTerm, searchTerm, pageable)
                .map(productMapper::toSimpleDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        log.info("Verificando si existe producto con nombre: {}", name);
        return productRepository.existsByNameIgnoreCase(name);
    }

    // ✅ NUEVO MÉTODO
    @Override
    @Transactional(readOnly = true)
    public Page<SimpleProduct> getProductsByStatus(boolean active, Pageable pageable) {
        log.info("Obteniendo productos con estado activo={}", active);
        return productRepository.findByActive(active, pageable).map(productMapper::toSimpleDTO);
    }
}
