package com.uniminuto.velvet.service.interfaces;

import com.uniminuto.velvet.model.dto.UnitOfMeasureDTO;

import java.util.List;

public interface UnitOfMeasureService {
	UnitOfMeasureDTO.SimpleUnitOfMeasure createUnitOfMeasure(UnitOfMeasureDTO.CreateUnitOfMeasure createUnitOfMeasure);
	UnitOfMeasureDTO.SimpleUnitOfMeasure updateUnitOfMeasure(UnitOfMeasureDTO.UpdateUnitOfMeasure updateUnitOfMeasure);
	UnitOfMeasureDTO.DetailsUnitOfMeasure getUnitOfMeasureById(Long id);
	List<UnitOfMeasureDTO.SimpleUnitOfMeasure> getAllUnitOfMeasures();
	List<UnitOfMeasureDTO.DetailsUnitOfMeasure> getAllUnitOfMeasuresWithDetails();
	boolean deleteUnitOfMeasure(Long id);
	boolean existsByName(String name);
}