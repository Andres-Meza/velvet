
package com.uniminuto.velvet.service.interfaces;

import com.uniminuto.velvet.model.dto.DocumentTypeDTO;

import java.util.List;

public interface DocumentTypeService {
	DocumentTypeDTO.DocumentTypeDetail createDocumentType(DocumentTypeDTO.CreateDocumentType createDocumentType);
	DocumentTypeDTO.DocumentTypeDetail updateDocumentType(DocumentTypeDTO.UpdateDocumentType updateDocumentType);
	DocumentTypeDTO.DocumentTypeDetail getDocumentTypeById(Long id);
	List<DocumentTypeDTO.DocumentTypeDetail> getAllDocumentTypes();
	boolean deleteDocumentType(Long id);
	boolean existsByName(String name);
}