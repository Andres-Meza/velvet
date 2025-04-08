package com.uniminuto.velvet.service.impl;

import com.uniminuto.velvet.model.dto.ProductCompositionDTO;
import com.uniminuto.velvet.model.entity.Product;
import com.uniminuto.velvet.model.entity.ProductComposition;
import com.uniminuto.velvet.model.entity.UnitOfMeasure;
import com.uniminuto.velvet.model.mapper.ProductCompositionMapper;
import com.uniminuto.velvet.model.repository.ProductCompositionRepository;
import com.uniminuto.velvet.model.repository.ProductRepository;
import com.uniminuto.velvet.model.repository.UnitOfMeasureRepository;
import com.uniminuto.velvet.service.interfaces.ProductCompositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductCompositionServiceImpl implements ProductCompositionService {

    private final ProductCompositionRepository productCompositionRepository;
    private final ProductRepository productRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final ProductCompositionMapper productCompositionMapper;

    // Constantes para tipos de producto
    private static final String PRODUCT_TYPE_COMPOSITE = "Compuesto";
    private static final String PRODUCT_TYPE_SIMPLE = "Simple";

    @Override
    public List<ProductCompositionDTO.DetailsProductComposition> createCompositeProduct(
            ProductCompositionDTO.CreateCompositeProduct createDTO) {

        log.info("Creando nueva composición para producto compuesto ID: {} con {} ingredientes",
                createDTO.getCompositeProductId(), createDTO.getIngredients().size());

        // Verificar que el producto compuesto exista y sea de tipo COMPOSITE
        Product compositeProduct = productRepository.findById(createDTO.getCompositeProductId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "El producto compuesto con ID " + createDTO.getCompositeProductId() + " no existe"));

        if (!PRODUCT_TYPE_COMPOSITE.equals(compositeProduct.getProductType().getName())) {
            throw new IllegalArgumentException(
                    "El producto con ID " + createDTO.getCompositeProductId() + " no es de tipo compuesto");
        }

        List<ProductComposition> compositions = new ArrayList<>();

        // Procesar cada ingrediente
        for (ProductCompositionDTO.IngredientItem ingredient : createDTO.getIngredients()) {
            // Verificar que el producto ingrediente exista
            Product ingredientProduct = productRepository.findById(ingredient.getIngredientProductId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "El producto ingrediente con ID " + ingredient.getIngredientProductId() + " no existe"));

            // Verificar que la unidad de medida exista
            UnitOfMeasure unitOfMeasure = unitOfMeasureRepository.findById(ingredient.getUnitOfMeasureId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "La unidad de medida con ID " + ingredient.getUnitOfMeasureId() + " no existe"));

            // Crear la composición
            ProductComposition composition = new ProductComposition();
            composition.setCompositeProduct(compositeProduct);
            composition.setIngredientProduct(ingredientProduct);
            composition.setQuantity(ingredient.getQuantity());
            composition.setUnitOfMeasure(unitOfMeasure);

            compositions.add(composition);
        }

        // Guardar todas las composiciones
        List<ProductComposition> savedCompositions = productCompositionRepository.saveAll(compositions);

        log.info("Se han creado {} composiciones para el producto compuesto ID: {}",
                savedCompositions.size(), createDTO.getCompositeProductId());

        return savedCompositions.stream()
                .map(productCompositionMapper::toDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductCompositionDTO.DetailsProductComposition updateProductComposition(
            ProductCompositionDTO.UpdateProductComposition updateDTO) {

        log.info("Actualizando composición de producto con ID: {}", updateDTO.getId());

        // Buscar la composición existente
        ProductComposition composition = productCompositionRepository.findById(updateDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "La composición de producto con ID " + updateDTO.getId() + " no existe"));

        // Actualizar la cantidad si se proporciona
        if (updateDTO.getQuantity() != null) {
            composition.setQuantity(updateDTO.getQuantity());
        }

        // Actualizar la unidad de medida si se proporciona
        if (updateDTO.getUnitOfMeasureId() != null) {
            UnitOfMeasure unitOfMeasure = unitOfMeasureRepository.findById(updateDTO.getUnitOfMeasureId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "La unidad de medida con ID " + updateDTO.getUnitOfMeasureId() + " no existe"));
            composition.setUnitOfMeasure(unitOfMeasure);
        }

        ProductComposition updatedComposition = productCompositionRepository.save(composition);

        log.info("Composición de producto actualizada con ID: {}", updatedComposition.getId());

        return productCompositionMapper.toDetailsDto(updatedComposition);
    }

    @Override
    public void deleteProductComposition(Long id) {
        log.info("Eliminando composición de producto con ID: {}", id);

        if (!productCompositionRepository.existsById(id)) {
            throw new IllegalArgumentException("La composición de producto con ID " + id + " no existe");
        }

        productCompositionRepository.deleteById(id);

        log.info("Composición de producto eliminada con ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductCompositionDTO.DetailsProductComposition> findProductCompositionById(Long id) {
        log.debug("Buscando composición de producto con ID: {}", id);

        return productCompositionRepository.findById(id)
                .map(productCompositionMapper::toDetailsDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductCompositionDTO.DetailsProductComposition> findAllProductCompositions() {
        log.debug("Obteniendo todas las composiciones de productos");

        List<ProductComposition> compositions = productCompositionRepository.findAll();

        return productCompositionMapper.toDetailsDtoList(compositions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductCompositionDTO.SimpleProductComposition> findCompositionsForProduct(Long compositeProductId) {
        log.debug("Obteniendo composiciones para el producto compuesto con ID: {}", compositeProductId);

        // Verificar que el producto exista
        if (!productRepository.existsById(compositeProductId)) {
            throw new IllegalArgumentException("El producto con ID " + compositeProductId + " no existe");
        }

        List<ProductComposition> compositions = productCompositionRepository.findByCompositeProductId(compositeProductId);

        return productCompositionMapper.toSimpleDtoList(compositions);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isProductComposite(Long productId) {
        log.debug("Verificando si el producto con ID: {} es compuesto", productId);

        return productRepository.findById(productId)
                .map(product -> PRODUCT_TYPE_COMPOSITE.equals(product.getProductType().getName()))
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isProductUsedAsIngredient(Long productId) {
        log.debug("Verificando si el producto con ID: {} es usado como ingrediente", productId);

        return productCompositionRepository.existsByIngredientProductId(productId);
    }
}