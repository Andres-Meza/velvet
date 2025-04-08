package com.uniminuto.velvet.service.interfaces;

import com.uniminuto.velvet.model.dto.ProductDTO.CreateProduct;
import com.uniminuto.velvet.model.dto.ProductDTO.DetailsProduct;
import com.uniminuto.velvet.model.dto.ProductDTO.SimpleProduct;
import com.uniminuto.velvet.model.dto.ProductDTO.UpdateProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {

    /**
     * Crea un nuevo producto
     *
     * @param createProductDTO datos del producto a crear
     * @return el producto creado con detalles
     */
    DetailsProduct createProduct(CreateProduct createProductDTO);

    /**
     * Actualiza un producto existente
     *
     * @param updateProductDTO datos a actualizar
     * @return el producto actualizado con detalles
     */
    DetailsProduct updateProduct(UpdateProduct updateProductDTO);

    /**
     * Obtiene un producto por su ID
     *
     * @param id identificador del producto
     * @return Optional con detalles del producto o vacío si no existe
     */
    Optional<DetailsProduct> getProductById(Long id);

    /**
     * Obtiene una lista paginada de productos
     *
     * @param pageable configuración de paginación
     * @return página de productos con información básica
     */
    Page<SimpleProduct> getAllProducts(Pageable pageable);

    /**
     * Obtiene productos por su tipo de producto
     *
     * @param productTypeId identificador del tipo de producto
     * @param pageable configuración de paginación
     * @return página de productos con información básica
     */
    Page<SimpleProduct> getProductsByProductType(Long productTypeId, Pageable pageable);

    /**
     * Obtiene productos por subcategoría
     *
     * @param subCategoryId identificador de la subcategoría
     * @param pageable configuración de paginación
     * @return página de productos con información básica
     */
    Page<SimpleProduct> getProductsBySubCategory(Long subCategoryId, Pageable pageable);

    /**
     * Obtiene productos por categoría
     *
     * @param categoryId identificador de la categoría
     * @param pageable configuración de paginación
     * @return página de productos con información básica
     */
    Page<SimpleProduct> getProductsByCategory(Long categoryId, Pageable pageable);

    /**
     * Cambia el estado de activación de un producto
     *
     * @param id identificador del producto
     * @param active nuevo estado de activación
     * @return producto actualizado con detalles
     */
    DetailsProduct changeProductStatus(Long id, Boolean active);

    /**
     * Busca productos por nombre o descripción
     *
     * @param query texto a buscar
     * @param pageable configuración de paginación
     * @return página de productos con información básica
     */
    Page<SimpleProduct> searchProducts(String query, Pageable pageable);

    /**
     * Verifica si existe un producto con el nombre especificado
     *
     * @param name nombre del producto
     * @return true si existe, false en caso contrario
     */
    boolean existsByName(String name);
}
