package com.uniminuto.velvet.service.impl;

import com.uniminuto.velvet.model.dto.InventoryStockDTO.CreateInventoryStock;
import com.uniminuto.velvet.model.dto.InventoryStockDTO.UpdateInventoryStock;
import com.uniminuto.velvet.model.dto.InventoryStockDTO.DetailsInventoryStock;
import com.uniminuto.velvet.model.dto.InventoryStockDTO.SimpleInventoryStock;
import com.uniminuto.velvet.model.entity.InventoryStock;
import com.uniminuto.velvet.model.entity.Location;
import com.uniminuto.velvet.model.entity.Product;
import com.uniminuto.velvet.exception.ResourceNotFoundException;
import com.uniminuto.velvet.exception.InvalidOperationException;
import com.uniminuto.velvet.model.mapper.InventoryStockMapper;
import com.uniminuto.velvet.model.repository.InventoryStockRepository;
import com.uniminuto.velvet.model.repository.LocationRepository;
import com.uniminuto.velvet.model.repository.ProductRepository;
import com.uniminuto.velvet.service.interfaces.InventoryStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryStockServiceImpl implements InventoryStockService {

    private final InventoryStockRepository inventoryStockRepository;
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;
    private final InventoryStockMapper inventoryStockMapper;

    @Override
    @Transactional
    public DetailsInventoryStock createInventoryStock(CreateInventoryStock createInventoryStock) {
        log.info("Creando nuevo registro de inventario para producto ID: {} en ubicación ID: {}",
                createInventoryStock.getProductId(), createInventoryStock.getLocationId());

        // Verificar que el producto existe
        Product product = productRepository.findById(createInventoryStock.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + createInventoryStock.getProductId()));

        // Verificar que la ubicación existe
        Location location = locationRepository.findById(createInventoryStock.getLocationId())
                .orElseThrow(() -> new ResourceNotFoundException("Ubicación no encontrada con ID: " + createInventoryStock.getLocationId()));

        // Verificar si ya existe un registro para este producto y ubicación
        Optional<InventoryStock> existingStock = inventoryStockRepository
                .findByProductIdAndLocationId(createInventoryStock.getProductId(), createInventoryStock.getLocationId());

        if (existingStock.isPresent()) {
            throw new InvalidOperationException("Ya existe un registro de inventario para este producto en esta ubicación");
        }

        // Mapear a entidad y guardar
        InventoryStock inventoryStock = inventoryStockMapper.toEntity(createInventoryStock);
        InventoryStock savedStock = inventoryStockRepository.save(inventoryStock);

        log.info("Registro de inventario creado con ID: {}", savedStock.getId());
        return inventoryStockMapper.toDetailsDTO(savedStock);
    }

    @Override
    @Transactional
    public DetailsInventoryStock updateInventoryStock(UpdateInventoryStock updateInventoryStock) {
        log.info("Actualizando registro de inventario con ID: {}", updateInventoryStock.getId());

        // Buscar el registro existente
        InventoryStock existingStock = inventoryStockRepository.findById(updateInventoryStock.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Registro de inventario no encontrado con ID: " + updateInventoryStock.getId()));

        // Si se está cambiando producto o ubicación, verificar que no existe otro registro con esa combinación
        if ((updateInventoryStock.getProductId() != null && !updateInventoryStock.getProductId().equals(existingStock.getProduct().getId())) ||
                (updateInventoryStock.getLocationId() != null && !updateInventoryStock.getLocationId().equals(existingStock.getLocation().getId()))) {

            Long productId = updateInventoryStock.getProductId() != null ? updateInventoryStock.getProductId() : existingStock.getProduct().getId();
            Long locationId = updateInventoryStock.getLocationId() != null ? updateInventoryStock.getLocationId() : existingStock.getLocation().getId();

            Optional<InventoryStock> duplicateStock = inventoryStockRepository.findByProductIdAndLocationId(productId, locationId);
            if (duplicateStock.isPresent() && !duplicateStock.get().getId().equals(existingStock.getId())) {
                throw new InvalidOperationException("Ya existe un registro de inventario para este producto en esta ubicación");
            }

            // Verificar que el producto existe si se está actualizando
            if (updateInventoryStock.getProductId() != null) {
                productRepository.findById(updateInventoryStock.getProductId())
                        .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + updateInventoryStock.getProductId()));
            }

            // Verificar que la ubicación existe si se está actualizando
            if (updateInventoryStock.getLocationId() != null) {
                locationRepository.findById(updateInventoryStock.getLocationId())
                        .orElseThrow(() -> new ResourceNotFoundException("Ubicación no encontrada con ID: " + updateInventoryStock.getLocationId()));
            }
        }

        // Actualizar la entidad y guardar
        inventoryStockMapper.updateEntityFromDto(updateInventoryStock, existingStock);
        InventoryStock updatedStock = inventoryStockRepository.save(existingStock);

        log.info("Registro de inventario actualizado con ID: {}", updatedStock.getId());
        return inventoryStockMapper.toDetailsDTO(updatedStock);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DetailsInventoryStock> getInventoryStockById(Long id) {
        log.info("Buscando registro de inventario con ID: {}", id);
        return inventoryStockRepository.findById(id)
                .map(inventoryStockMapper::toDetailsDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SimpleInventoryStock> getAllInventoryStocks(Pageable pageable) {
        log.info("Obteniendo todos los registros de inventario paginados");
        return inventoryStockRepository.findAll(pageable)
                .map(inventoryStockMapper::toSimpleDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SimpleInventoryStock> getInventoryStocksByProductId(Long productId) {
        log.info("Buscando registros de inventario para producto ID: {}", productId);
        return inventoryStockRepository.findByProductId(productId).stream()
                .map(inventoryStockMapper::toSimpleDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SimpleInventoryStock> getInventoryStocksByLocationId(Long locationId) {
        log.info("Buscando registros de inventario para ubicación ID: {}", locationId);
        return inventoryStockRepository.findByLocationId(locationId).stream()
                .map(inventoryStockMapper::toSimpleDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean deleteInventoryStock(Long id) {
        log.info("Eliminando registro de inventario con ID: {}", id);
        if (!inventoryStockRepository.existsById(id)) {
            return false;
        }
        inventoryStockRepository.deleteById(id);
        log.info("Registro de inventario eliminado con ID: {}", id);
        return true;
    }

    @Override
    @Transactional
    public DetailsInventoryStock updateStockQuantity(Long productId, Long locationId, Long quantity) {
        log.info("Actualizando cantidad de stock para producto ID: {} en ubicación ID: {} (cantidad: {})",
                productId, locationId, quantity);

        // Buscar el registro existente
        InventoryStock stock = inventoryStockRepository.findByProductIdAndLocationId(productId, locationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró registro de inventario para producto ID: " + productId + " en ubicación ID: " + locationId));

        // Validar que el nuevo stock no sea negativo
        if (stock.getCurrentStock() + quantity < 0) {
            throw new InvalidOperationException("El stock resultante sería negativo");
        }

        // Actualizar el stock
        stock.setCurrentStock(stock.getCurrentStock() + quantity);
        InventoryStock updatedStock = inventoryStockRepository.save(stock);

        log.info("Stock actualizado: {}", updatedStock.getCurrentStock());
        return inventoryStockMapper.toDetailsDTO(updatedStock);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasEnoughStock(Long productId, Long locationId, Long quantity) {
        log.info("Verificando si hay suficiente stock ({}) para producto ID: {} en ubicación ID: {}",
                quantity, productId, locationId);

        Optional<InventoryStock> stock = inventoryStockRepository.findByProductIdAndLocationId(productId, locationId);
        if (stock.isEmpty()) {
            return false;
        }

        boolean hasEnough = stock.get().getCurrentStock() >= quantity;
        log.info("Stock disponible: {} - ¿Es suficiente? {}", stock.get().getCurrentStock(), hasEnough);
        return hasEnough;
    }
}
