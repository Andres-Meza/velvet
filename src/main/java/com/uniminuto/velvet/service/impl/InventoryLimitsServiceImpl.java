package com.uniminuto.velvet.service.impl;

import com.uniminuto.velvet.model.dto.InventoryLimitsDTO;
import com.uniminuto.velvet.model.entity.InventoryLimits;
import com.uniminuto.velvet.model.entity.Location;
import com.uniminuto.velvet.model.entity.Product;
import com.uniminuto.velvet.exception.ResourceNotFoundException;
import com.uniminuto.velvet.exception.DuplicateResourceException;
import com.uniminuto.velvet.model.mapper.InventoryLimitsMapper;
import com.uniminuto.velvet.model.repository.InventoryLimitsRepository;
import com.uniminuto.velvet.model.repository.LocationRepository;
import com.uniminuto.velvet.model.repository.ProductRepository;
import com.uniminuto.velvet.service.interfaces.InventoryLimitsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InventoryLimitsServiceImpl implements InventoryLimitsService {

    private final InventoryLimitsRepository inventoryLimitsRepository;
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;
    private final InventoryLimitsMapper inventoryLimitsMapper;

    @Override
    public InventoryLimitsDTO.DetailsInventoryLimits create(InventoryLimitsDTO.CreateInventoryLimits dto) {
        log.debug("Creando límites de inventario: {}", dto);

        // Verificar si el producto existe
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + dto.getProductId()));

        // Verificar si la ubicación existe
        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new ResourceNotFoundException("Ubicación no encontrada con ID: " + dto.getLocationId()));

        // Verificar si ya existen límites para este producto y ubicación
        if (inventoryLimitsRepository.existsByProductIdAndLocationId(dto.getProductId(), dto.getLocationId())) {
            throw new DuplicateResourceException("Ya existen límites de inventario para este producto y ubicación");
        }

        // Validar niveles de stock
        if (dto.getMinStock() != null && dto.getMaxStock() != null && dto.getMinStock() >= dto.getMaxStock()) {
            throw new IllegalArgumentException("El stock mínimo debe ser menor que el stock máximo");
        }

        // Crear entidad y guardar
        InventoryLimits entity = inventoryLimitsMapper.toEntity(dto);
        entity.setProduct(product);
        entity.setLocation(location);

        InventoryLimits saved = inventoryLimitsRepository.save(entity);
        log.info("Límites de inventario creados con ID: {}", saved.getId());

        return inventoryLimitsMapper.toDetailsDto(saved);
    }

    @Override
    public InventoryLimitsDTO.DetailsInventoryLimits update(InventoryLimitsDTO.UpdateInventoryLimits dto) {
        log.debug("Actualizando límites de inventario ID: {}", dto.getId());

        // Buscar entidad existente
        InventoryLimits existing = inventoryLimitsRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Límites de inventario no encontrados con ID: " + dto.getId()));

        // Validar niveles de stock si ambos están presentes
        Long minStock = dto.getMinStock() != null ? dto.getMinStock() : existing.getMinStock();
        Long maxStock = dto.getMaxStock() != null ? dto.getMaxStock() : existing.getMaxStock();

        if (minStock != null && maxStock != null && minStock >= maxStock) {
            throw new IllegalArgumentException("El stock mínimo debe ser menor que el stock máximo");
        }

        // Actualizar entidad
        inventoryLimitsMapper.updateEntityFromDto(dto, existing);
        InventoryLimits updated = inventoryLimitsRepository.save(existing);
        log.info("Límites de inventario actualizados con ID: {}", updated.getId());

        return inventoryLimitsMapper.toDetailsDto(updated);
    }

    @Override
    public void delete(Long id) {
        log.debug("Eliminando límites de inventario ID: {}", id);

        if (!inventoryLimitsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Límites de inventario no encontrados con ID: " + id);
        }

        inventoryLimitsRepository.deleteById(id);
        log.info("Límites de inventario eliminados con ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InventoryLimitsDTO.DetailsInventoryLimits> findById(Long id) {
        log.debug("Buscando límites de inventario por ID: {}", id);

        return inventoryLimitsRepository.findById(id)
                .map(inventoryLimitsMapper::toDetailsDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InventoryLimitsDTO.DetailsInventoryLimits> findByProductAndLocation(Long productId, Long locationId) {
        log.debug("Buscando límites de inventario por producto ID: {} y ubicación ID: {}", productId, locationId);

        return inventoryLimitsRepository.findByProductIdAndLocationId(productId, locationId)
                .map(inventoryLimitsMapper::toDetailsDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InventoryLimitsDTO.SimpleInventoryLimits> findAll(Pageable pageable) {
        log.debug("Buscando todos los límites de inventario con paginación");

        return inventoryLimitsRepository.findAll(pageable)
                .map(inventoryLimitsMapper::toSimpleDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryLimitsDTO.SimpleInventoryLimits> findByProductId(Long productId) {
        log.debug("Buscando límites de inventario por producto ID: {}", productId);

        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + productId);
        }

        List<InventoryLimits> limits = inventoryLimitsRepository.findByProductId(productId);
        return inventoryLimitsMapper.toSimpleDtoList(limits);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryLimitsDTO.SimpleInventoryLimits> findByLocationId(Long locationId) {
        log.debug("Buscando límites de inventario por ubicación ID: {}", locationId);

        if (!locationRepository.existsById(locationId)) {
            throw new ResourceNotFoundException("Ubicación no encontrada con ID: " + locationId);
        }

        List<InventoryLimits> limits = inventoryLimitsRepository.findByLocationId(locationId);
        return inventoryLimitsMapper.toSimpleDtoList(limits);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByProductAndLocation(Long productId, Long locationId) {
        return inventoryLimitsRepository.existsByProductIdAndLocationId(productId, locationId);
    }
}
