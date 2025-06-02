package com.uniminuto.velvet.service.interfaces;

import com.uniminuto.velvet.model.dto.ProductDTO.CreateProduct;
import com.uniminuto.velvet.model.dto.ProductDTO.DetailsProduct;
import com.uniminuto.velvet.model.dto.ProductDTO.SimpleProduct;
import com.uniminuto.velvet.model.dto.ProductDTO.UpdateProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {

    DetailsProduct createProduct(CreateProduct createProductDTO);

    DetailsProduct updateProduct(UpdateProduct updateProductDTO);

    Optional<DetailsProduct> getProductById(Long id);

    Page<SimpleProduct> getAllProducts(Pageable pageable);

    Page<SimpleProduct> getProductsByProductType(Long productTypeId, Pageable pageable);

    Page<SimpleProduct> getProductsBySubCategory(Long subCategoryId, Pageable pageable);

    Page<SimpleProduct> getProductsByCategory(Long categoryId, Pageable pageable);

    DetailsProduct changeProductStatus(Long id, Boolean active);

    Page<SimpleProduct> searchProducts(String query, Pageable pageable);

    boolean existsByName(String name);

    Page<SimpleProduct> getProductsByStatus(boolean active, Pageable pageable);
}
