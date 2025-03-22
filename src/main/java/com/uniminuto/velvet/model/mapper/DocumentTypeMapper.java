package com.uniminuto.velvet.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.uniminuto.velvet.model.dto.DocumentTypeDTO;
import com.uniminuto.velvet.model.entity.DocumentType;

@Mapper(componentModel = "spring")
public interface DocumentTypeMapper {

  DocumentTypeMapper INSTANCE = Mappers.getMapper(DocumentTypeMapper.class);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "users", ignore = true)
  DocumentType toEntity(DocumentTypeDTO.CreateDocumentType createDocumentTypeDTO);

  @Mapping(target = "users", ignore = true)
  void updateEntityFromDto(DocumentTypeDTO.UpdateDocumentType updateDocumentTypeDTO, @MappingTarget DocumentType documentType);

  DocumentTypeDTO.DocumentTypeDetail toDetailDTO(DocumentType documentType);

  List<DocumentTypeDTO.DocumentTypeDetail> toDetailDTOList(List<DocumentType> documentTypes);
}