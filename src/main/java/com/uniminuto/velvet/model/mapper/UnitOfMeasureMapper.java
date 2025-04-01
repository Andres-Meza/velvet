package com.uniminuto.velvet.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.uniminuto.velvet.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.uniminuto.velvet.model.dto.UnitOfMeasureDTO;
import com.uniminuto.velvet.model.entity.UnitOfMeasure;

@Mapper(componentModel = "spring")
public interface UnitOfMeasureMapper {

  UnitOfMeasureMapper INSTANCE = Mappers.getMapper(UnitOfMeasureMapper.class);

  // Convertir de CreateUnitOfMeasure a UnitOfMeasure
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "products", ignore = true)
  UnitOfMeasure toEntity(UnitOfMeasureDTO.CreateUnitOfMeasure dto);

  // Convertir de UpdateUnitOfMeasure a UnitOfMeasure
  @Mapping(target = "products", ignore = true)
  UnitOfMeasure toEntity(UnitOfMeasureDTO.UpdateUnitOfMeasure dto);

  // Actualizar un UnitOfMeasure existente con datos de UpdateUnitOfMeasure
  @Mapping(target = "products", ignore = true)
  void updateEntityFromDto(UnitOfMeasureDTO.UpdateUnitOfMeasure dto, @MappingTarget UnitOfMeasure entity);

  // Convertir de UnitOfMeasure a SimpleUnitOfMeasure
  UnitOfMeasureDTO.SimpleUnitOfMeasure toSimpleDto(UnitOfMeasure entity);

  // Convertir de UnitOfMeasure a DetailsUnitOfMeasure
  @Mapping(target = "productName", source = "entity", qualifiedByName = "extractProductName")
  UnitOfMeasureDTO.DetailsUnitOfMeasure toDetailsDto(UnitOfMeasure entity);

  // Convertir lista de UnitOfMeasure a lista de SimpleUnitOfMeasure
  List<UnitOfMeasureDTO.SimpleUnitOfMeasure> toSimpleDtoList(List<UnitOfMeasure> entities);

  // Convertir lista de UnitOfMeasure a lista de DetailsUnitOfMeasure
  List<UnitOfMeasureDTO.DetailsUnitOfMeasure> toDetailsDtoList(List<UnitOfMeasure> entities);

  // Método auxiliar para extraer nombres de productos
  @Named("extractProductName")
  default String extractProductName(UnitOfMeasure unitOfMeasure) {
    if (unitOfMeasure == null || unitOfMeasure.getProducts() == null || unitOfMeasure.getProducts().isEmpty()) {
      return null;
    }
    return unitOfMeasure.getProducts().stream()
            .map(Product::getName)
            .collect(Collectors.joining(", "));
  }
}