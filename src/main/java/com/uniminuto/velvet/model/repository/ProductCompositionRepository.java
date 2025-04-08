package com.uniminuto.velvet.model.repository;

import com.uniminuto.velvet.model.entity.ProductComposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCompositionRepository extends JpaRepository<ProductComposition, Long> {

    /**
     * Encuentra todas las composiciones para un producto compuesto específico
     *
     * @param compositeProductId ID del producto compuesto
     * @return Lista de composiciones de productos
     */
    List<ProductComposition> findByCompositeProductId(Long compositeProductId);

    /**
     * Verifica si un producto es usado como ingrediente en alguna composición
     *
     * @param ingredientProductId ID del producto ingrediente
     * @return true si el producto es usado como ingrediente, false en caso contrario
     */
    boolean existsByIngredientProductId(Long ingredientProductId);
}