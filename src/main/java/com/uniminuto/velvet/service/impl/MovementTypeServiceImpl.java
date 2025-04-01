package com.uniminuto.velvet.service.impl;

import com.uniminuto.velvet.model.dto.MovementTypeDTO;
import com.uniminuto.velvet.model.entity.MovementType;
import com.uniminuto.velvet.model.mapper.MovementTypeMapper;
import com.uniminuto.velvet.model.repository.MovementTypeRepository;
import com.uniminuto.velvet.service.interfaces.MovementTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MovementTypeServiceImpl implements MovementTypeService {

  private final MovementTypeRepository movementTypeRepository;
  private final MovementTypeMapper movementTypeMapper;

  @Override
  @Transactional
  public MovementTypeDTO.SimpleMovementType createMovementType(MovementTypeDTO.CreateMovementType createMovementType) {
    // Verificar si ya existe un recurso con el mismo nombre
    if (movementTypeRepository.existsByName((createMovementType.getName()))) {
      throw new RuntimeException("Ya existe un tipo de producto con este nombre");
    }

    // Convertir DTO a entidad
    MovementType movementType = movementTypeMapper.toEntity(createMovementType);
    
    // Guardar en base de datos
    MovementType savedMovementType = movementTypeRepository.save(movementType);
    
    // Convertir entidad guardada a DTO de respuesta
    return movementTypeMapper.toSimpleDTO(savedMovementType);
  }

  @Override
  @Transactional
  public MovementTypeDTO.SimpleMovementType updateMovementType(MovementTypeDTO.UpdateMovementType updateMovementType) {
    // Buscar el recurso existente
    MovementType existingMovementType = movementTypeRepository.findById(updateMovementType.getId())
      .orElseThrow(() -> new RuntimeException("Tipo de producto no encontrado"));

    // Verificar si el nuevo nombre ya existe (si es diferente)
    if (!existingMovementType.getName().equals(updateMovementType.getName()) &&
        movementTypeRepository.existsByName(updateMovementType.getName())) {
      throw new RuntimeException("Ya existe un tipo de producto con este nombre");
    }

    // Actualizar la entidad
    movementTypeMapper.updateEntityFromDTO(updateMovementType, existingMovementType);
    
    // Guardar cambios
    MovementType updatedMovementType = movementTypeRepository.save(existingMovementType);
    
    // Convertir a DTO de respuesta
    return movementTypeMapper.toSimpleDTO(updatedMovementType);
  }

  @Override
  public MovementTypeDTO.DetailsMovementType getMovementTypeById(Long id) {
    // Buscar recurso por ID
    MovementType movementType = movementTypeRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Tipo de producto no encontrado"));
    
    // Convertir a DTO de detalle
    return movementTypeMapper.toDetailsDTO(movementType);
  }

  @Override
  public List<MovementTypeDTO.SimpleMovementType> getAllMovementTypes() {
    // Obtener todos los recursos y convertir a lista de respuestas
    List<MovementType> movementTypes = movementTypeRepository.findAll();
    return movementTypeMapper.toSimpleDtoList(movementTypes);
  }

  @Override
  public List<MovementTypeDTO.DetailsMovementType> getAllMovementTypesWithDetails() {
    // Obtener todos los recursos con detalles
    List<MovementType> movementTypes = movementTypeRepository.findAll();
    return movementTypeMapper.toDetailsDtoList(movementTypes);
  }

  @Override
  @Transactional
  public boolean deleteMovementType(Long id) {
    // Verificar si el recurso existe
    Optional<MovementType> movementTypeOptional = movementTypeRepository.findById(id);
    
    if (movementTypeOptional.isPresent()) {
      movementTypeRepository.delete(movementTypeOptional.get());
      return true;
    }
    
    return false;
  }

  @Override
  public boolean existsByName(String name) {
    // Verificar existencia de un recurso por nombre
    return movementTypeRepository.existsByName(name);
  }
}