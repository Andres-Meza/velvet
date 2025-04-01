package com.uniminuto.velvet.service.impl;

import com.uniminuto.velvet.model.dto.DocumentTypeDTO;
import com.uniminuto.velvet.model.entity.DocumentType;
import com.uniminuto.velvet.model.mapper.DocumentTypeMapper;
import com.uniminuto.velvet.model.repository.DocumentTypeRepository;
import com.uniminuto.velvet.service.interfaces.DocumentTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentTypeServiceImpl implements DocumentTypeService {

  private final DocumentTypeRepository documentTypeRepository;
  private final DocumentTypeMapper documentTypeMapper;

  @Override
  @Transactional
  public DocumentTypeDTO.DocumentTypeDetail createDocumentType(DocumentTypeDTO.CreateDocumentType createDocumentType) {
    // Verificar si ya existe un recurso con el mismo nombre
    if (documentTypeRepository.existsByName((createDocumentType.getName()))) {
      throw new RuntimeException("Ya existe un tipo de producto con este nombre");
    }

    // Convertir DTO a entidad
    DocumentType documentType = documentTypeMapper.toEntity(createDocumentType);
    
    // Guardar en base de datos
    DocumentType savedDocumentType = documentTypeRepository.save(documentType);
    
    // Convertir entidad guardada a DTO de respuesta
    return documentTypeMapper.toDetailDTO(savedDocumentType);
  }

  @Override
  @Transactional
  public DocumentTypeDTO.DocumentTypeDetail updateDocumentType(DocumentTypeDTO.UpdateDocumentType updateDocumentType) {
    // Buscar el recurso existente
    DocumentType existingDocumentType = documentTypeRepository.findById(updateDocumentType.getId())
      .orElseThrow(() -> new RuntimeException("Tipo de producto no encontrado"));

    // Verificar si el nuevo nombre ya existe (si es diferente)
    if (!existingDocumentType.getName().equals(updateDocumentType.getName()) &&
        documentTypeRepository.existsByName(updateDocumentType.getName())) {
      throw new RuntimeException("Ya existe un tipo de producto con este nombre");
    }

    // Actualizar la entidad
    documentTypeMapper.updateEntityFromDto(updateDocumentType, existingDocumentType);
    
    // Guardar cambios
    DocumentType updatedDocumentType = documentTypeRepository.save(existingDocumentType);
    
    // Convertir a DTO de respuesta
    return documentTypeMapper.toDetailDTO(updatedDocumentType);
  }

  @Override
  public DocumentTypeDTO.DocumentTypeDetail getDocumentTypeById(Long id) {
    // Buscar recurso por ID
    DocumentType documentType = documentTypeRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Tipo de producto no encontrado"));
    
    // Convertir a DTO de detalle
    return documentTypeMapper.toDetailDTO(documentType);
  }

  @Override
  public List<DocumentTypeDTO.DocumentTypeDetail> getAllDocumentTypes() {
    // Obtener todos los recursos y convertir a lista de respuestas
    List<DocumentType> documentTypes = documentTypeRepository.findAll();
    return documentTypeMapper.toDetailDTOList(documentTypes);
  }

  @Override
  @Transactional
  public boolean deleteDocumentType(Long id) {
    // Verificar si el recurso existe
    Optional<DocumentType> documentTypeOptional = documentTypeRepository.findById(id);
    
    if (documentTypeOptional.isPresent()) {
      documentTypeRepository.delete(documentTypeOptional.get());
      return true;
    }
    
    return false;
  }

  @Override
  public boolean existsByName(String name) {
    // Verificar existencia de un recurso por nombre
    return documentTypeRepository.existsByName(name);
  }
}