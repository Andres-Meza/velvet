package com.uniminuto.velvet.service.impl;

import com.uniminuto.velvet.model.dto.TablesDTO;
import com.uniminuto.velvet.model.entity.Location;
import com.uniminuto.velvet.model.entity.Tables;
import com.uniminuto.velvet.exception.ResourceNotFoundException;
import com.uniminuto.velvet.exception.UniqueConstraintViolationException;
import com.uniminuto.velvet.model.mapper.TablesMapper;
import com.uniminuto.velvet.model.repository.LocationRepository;
import com.uniminuto.velvet.model.repository.TablesRepository;
import com.uniminuto.velvet.service.interfaces.TablesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TablesServiceImpl implements TablesService {

    private final TablesRepository tablesRepository;
    private final LocationRepository locationRepository;
    private final TablesMapper tablesMapper;

    @Override
    @Transactional
    public TablesDTO.DetailsTable createTable(TablesDTO.CreateTable createTableDTO) {
        log.info("Creando nueva mesa: {}", createTableDTO);

        // Verificar si la ubicación existe
        Location location = locationRepository.findById(createTableDTO.getLocationId())
                .orElseThrow(() -> new ResourceNotFoundException("Ubicación no encontrada con ID: " + createTableDTO.getLocationId()));

        // Verificar si ya existe una mesa con el mismo número en la misma ubicación
        if (existsByNumberAndLocation(createTableDTO.getNumber(), createTableDTO.getLocationId())) {
            throw new UniqueConstraintViolationException("Ya existe una mesa con el número " + createTableDTO.getNumber() +
                    " en esta ubicación");
        }

        // Mapear y guardar entidad
        Tables table = tablesMapper.toEntity(createTableDTO);
        table.setLocation(location);
        Tables savedTable = tablesRepository.save(table);

        log.info("Mesa creada con éxito: ID={}", savedTable.getId());
        return tablesMapper.toDetailsDTO(savedTable);
    }

    @Override
    @Transactional
    public TablesDTO.DetailsTable updateTable(TablesDTO.UpdateTable updateTableDTO) {
        log.info("Actualizando mesa con ID: {}", updateTableDTO.getId());

        // Verificar si la ubicación existe
        Location location = locationRepository.findById(updateTableDTO.getLocationId())
                .orElseThrow(() -> new ResourceNotFoundException("Ubicación no encontrada con ID: " + updateTableDTO.getLocationId()));

        // Buscar la mesa existente
        Tables existingTable = tablesRepository.findById(updateTableDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada con ID: " + updateTableDTO.getId()));

        // Verificar duplicidad solo si el número o la ubicación cambiaron
        if (!existingTable.getNumber().equals(updateTableDTO.getNumber()) ||
                !existingTable.getLocation().getId().equals(updateTableDTO.getLocationId())) {

            if (tablesRepository.existsByNumberAndLocationIdAndIdNot(
                    updateTableDTO.getNumber(), updateTableDTO.getLocationId(), updateTableDTO.getId())) {
                throw new UniqueConstraintViolationException("Ya existe otra mesa con el número " +
                        updateTableDTO.getNumber() + " en esta ubicación");
            }
        }

        // Actualizar la entidad
        tablesMapper.updateEntityFromDTO(updateTableDTO, existingTable);
        existingTable.setLocation(location);
        Tables updatedTable = tablesRepository.save(existingTable);

        log.info("Mesa actualizada con éxito: ID={}", updatedTable.getId());
        return tablesMapper.toDetailsDTO(updatedTable);
    }

    @Override
    @Transactional(readOnly = true)
    public TablesDTO.DetailsTable getTableById(Long id) {
        log.info("Buscando mesa con ID: {}", id);

        Tables table = tablesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada con ID: " + id));

        return tablesMapper.toDetailsDTO(table);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TablesDTO.SimpleTable> getAllTables() {
        log.info("Obteniendo todas las mesas");

        return tablesRepository.findAll().stream()
                .map(tablesMapper::toSimpleDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TablesDTO.SimpleTable> getTablesByLocation(Long locationId) {
        log.info("Obteniendo mesas para la ubicación con ID: {}", locationId);

        // Verificar si la ubicación existe
        if (!locationRepository.existsById(locationId)) {
            throw new ResourceNotFoundException("Ubicación no encontrada con ID: " + locationId);
        }

        return tablesRepository.findByLocationId(locationId).stream()
                .map(tablesMapper::toSimpleDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TablesDTO.SimpleTable> getAvailableTablesByCapacity(Integer capacity) {
        log.info("Buscando mesas disponibles con capacidad mínima: {}", capacity);

        return tablesRepository.findByActiveIsTrueAndCapacityGreaterThanEqual(capacity).stream()
                .map(tablesMapper::toSimpleDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean deactivateTable(Long id) {
        log.info("Desactivando mesa con ID: {}", id);

        Tables table = tablesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada con ID: " + id));

        // Verificar si ya está desactivada
        if (!table.getActive()) {
            log.info("La mesa ya está desactivada: ID={}", id);
            return false;
        }

        table.setActive(false);
        tablesRepository.save(table);

        log.info("Mesa desactivada con éxito: ID={}", id);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNumberAndLocation(String number, Long locationId) {
        return tablesRepository.existsByNumberAndLocationId(number, locationId);
    }
}
