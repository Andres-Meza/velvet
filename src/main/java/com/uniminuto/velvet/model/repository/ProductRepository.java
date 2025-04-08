package com.uniminuto.velvet.model.repository;

import com.uniminuto.velvet.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Encuentra productos por tipo de producto
     */
    Page<Product> findByProductTypeId(Long productTypeId, Pageable pageable);

    /**
     * Encuentra productos por subcategoría
     */
    Page<Product> findBySubCategoryId(Long subCategoryId, Pageable pageable);

    /**
     * Encuentra productos por categoría (a través de la subcategoría)
     */
    Page<Product> findBySubCategoryCategoryId(Long categoryId, Pageable pageable);

    /**
     * Busca productos que contengan el texto en nombre o descripción
     */
    Page<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String name, String description, Pageable pageable);

    /**
     * Verifica si existe un producto con el nombre exacto (ignorando mayúsculas/minúsculas)
     */
    boolean existsByNameIgnoreCase(String name);
}