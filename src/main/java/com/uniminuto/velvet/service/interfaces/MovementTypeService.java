package com.uniminuto.velvet.service.interfaces;

import com.uniminuto.velvet.model.dto.MovementTypeDTO;

import java.util.List;

public interface MovementTypeService {
	MovementTypeDTO.SimpleMovementType createMovementType(MovementTypeDTO.CreateMovementType createMovementType);
	MovementTypeDTO.SimpleMovementType updateMovementType(MovementTypeDTO.UpdateMovementType updateMovementType);
	MovementTypeDTO.DetailsMovementType getMovementTypeById(Long id);
	List<MovementTypeDTO.SimpleMovementType> getAllMovementTypes();
	List<MovementTypeDTO.DetailsMovementType> getAllMovementTypesWithDetails();
	boolean deleteMovementType(Long id);
	boolean existsByName(String name);
}