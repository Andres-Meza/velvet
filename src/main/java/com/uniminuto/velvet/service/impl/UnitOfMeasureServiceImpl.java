package com.uniminuto.velvet.service.impl;

import com.uniminuto.velvet.model.dto.UnitOfMeasureDTO;
import com.uniminuto.velvet.model.entity.UnitOfMeasure;
import com.uniminuto.velvet.model.mapper.UnitOfMeasureMapper;
import com.uniminuto.velvet.model.repository.UnitOfMeasureRepository;
import com.uniminuto.velvet.service.interfaces.UnitOfMeasureService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

  private final UnitOfMeasureRepository unitOfMeasureRepository;
  private final UnitOfMeasureMapper unitOfMeasureMapper;

  @Override
  @Transactional
  public UnitOfMeasureDTO.SimpleUnitOfMeasure createUnitOfMeasure(UnitOfMeasureDTO.CreateUnitOfMeasure createUnitOfMeasure) {
    // Verificar si ya existe un recurso con el mismo nombre
    if (unitOfMeasureRepository.existsByName((createUnitOfMeasure.getName()))) {
      throw new RuntimeException("Ya existe una categoría con este nombre");
    }

    // Convertir DTO a entidad
    UnitOfMeasure unitOfMeasure = unitOfMeasureMapper.toEntity(createUnitOfMeasure);
    
    // Guardar en base de datos
    UnitOfMeasure savedUnitOfMeasure = unitOfMeasureRepository.save(unitOfMeasure);
    
    // Convertir entidad guardada a DTO de respuesta
    return unitOfMeasureMapper.toSimpleDto(savedUnitOfMeasure);
  }

  @Override
  @Transactional
  public UnitOfMeasureDTO.SimpleUnitOfMeasure updateUnitOfMeasure(UnitOfMeasureDTO.UpdateUnitOfMeasure updateUnitOfMeasure) {
    // Buscar el recurso existente
    UnitOfMeasure existingUnitOfMeasure = unitOfMeasureRepository.findById(updateUnitOfMeasure.getId())
      .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

    // Verificar si el nuevo nombre ya existe (si es diferente)
    if (!existingUnitOfMeasure.getName().equals(updateUnitOfMeasure.getName()) &&
        unitOfMeasureRepository.existsByName(updateUnitOfMeasure.getName())) {
      throw new RuntimeException("Ya existe una categoría con este nombre");
    }

    // Actualizar la entidad
    unitOfMeasureMapper.updateEntityFromDto(updateUnitOfMeasure, existingUnitOfMeasure);
    
    // Guardar cambios
    UnitOfMeasure updatedUnitOfMeasure = unitOfMeasureRepository.save(existingUnitOfMeasure);
    
    // Convertir a DTO de respuesta
    return unitOfMeasureMapper.toSimpleDto(updatedUnitOfMeasure);
  }

  @Override
  public UnitOfMeasureDTO.DetailsUnitOfMeasure getUnitOfMeasureById(Long id) {
    // Buscar recurso por ID
    UnitOfMeasure unitOfMeasure = unitOfMeasureRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    
    // Convertir a DTO de detalle
    return unitOfMeasureMapper.toDetailsDto(unitOfMeasure);
  }

  @Override
  public List<UnitOfMeasureDTO.SimpleUnitOfMeasure> getAllUnitOfMeasures() {
    // Obtener todos los recursos y convertir a lista de respuestas
    List<UnitOfMeasure> unitOfMeasures = unitOfMeasureRepository.findAll();
    return unitOfMeasureMapper.toSimpleDtoList(unitOfMeasures);
  }

  @Override
  public List<UnitOfMeasureDTO.DetailsUnitOfMeasure> getAllUnitOfMeasuresWithDetails() {
    // Obtener todos los recursos con detalles
    List<UnitOfMeasure> unitOfMeasures = unitOfMeasureRepository.findAll();
    return unitOfMeasureMapper.toDetailsDtoList(unitOfMeasures);
  }

  @Override
  @Transactional
  public boolean deleteUnitOfMeasure(Long id) {
    // Verificar si el recurso existe
    Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findById(id);
    
    if (unitOfMeasureOptional.isPresent()) {
      unitOfMeasureRepository.delete(unitOfMeasureOptional.get());
      return true;
    }
    
    return false;
  }

  @Override
  public boolean existsByName(String name) {
    // Verificar existencia de un recurso por nombre
    return unitOfMeasureRepository.existsByName(name);
  }
}