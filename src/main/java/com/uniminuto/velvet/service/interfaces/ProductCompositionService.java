package com.uniminuto.velvet.service.interfaces;

import com.uniminuto.velvet.model.dto.ProductCompositionDTO;

import java.util.List;
import java.util.Optional;

public interface ProductCompositionService {

    /**
     * Crea múltiples composiciones de producto para un producto compuesto
     *
     * @param createDTO DTO con los datos para crear las composiciones
     * @return Lista con los detalles de las composiciones creadas
     * @throws IllegalArgumentException si el producto compuesto no es de tipo compuesto
     * @throws IllegalArgumentException si algún producto ingrediente no existe
     * @throws IllegalArgumentException si alguna unidad de medida no existe
     */
    List<ProductCompositionDTO.DetailsProductComposition> createCompositeProduct(
            ProductCompositionDTO.CreateCompositeProduct createDTO);

    /**
     * Actualiza una composición de producto existente
     *
     * @param updateDTO DTO con los datos para actualizar la composición
     * @return DTO con los detalles de la composición actualizada
     * @throws IllegalArgumentException si la composición no existe
     * @throws IllegalArgumentException si la unidad de medida no existe
     */
    ProductCompositionDTO.DetailsProductComposition updateProductComposition(
            ProductCompositionDTO.UpdateProductComposition updateDTO);

    /**
     * Elimina una composición de producto por su ID
     *
     * @param id ID de la composición a eliminar
     * @throws IllegalArgumentException si la composición no existe
     */
    void deleteProductComposition(Long id);

    /**
     * Busca una composición de producto por su ID
     *
     * @param id ID de la composición a buscar
     * @return Optional con los detalles de la composición encontrada o vacío si no existe
     */
    Optional<ProductCompositionDTO.DetailsProductComposition> findProductCompositionById(Long id);

    /**
     * Obtiene todas las composiciones de productos
     *
     * @return Lista con los detalles de todas las composiciones de productos
     */
    List<ProductCompositionDTO.DetailsProductComposition> findAllProductCompositions();

    /**
     * Obtiene todas las composiciones para un producto compuesto específico
     *
     * @param compositeProductId ID del producto compuesto
     * @return Lista con los detalles simples de las composiciones del producto
     * @throws IllegalArgumentException si el producto compuesto no existe
     */
    List<ProductCompositionDTO.SimpleProductComposition> findCompositionsForProduct(Long compositeProductId);

    /**
     * Verifica si un producto es compuesto
     *
     * @param productId ID del producto a verificar
     * @return true si el producto es compuesto, false en caso contrario
     */
    boolean isProductComposite(Long productId);

    /**
     * Verifica si un producto es un ingrediente en alguna composición
     *
     * @param productId ID del producto a verificar
     * @return true si el producto es ingrediente en alguna composición, false en caso contrario
     */
    boolean isProductUsedAsIngredient(Long productId);
}