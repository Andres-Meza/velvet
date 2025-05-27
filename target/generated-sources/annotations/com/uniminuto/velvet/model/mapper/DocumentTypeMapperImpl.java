package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.DocumentTypeDTO;
import com.uniminuto.velvet.model.entity.DocumentType;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-26T21:15:42-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class DocumentTypeMapperImpl implements DocumentTypeMapper {

    @Override
    public DocumentType toEntity(DocumentTypeDTO.CreateDocumentType createDocumentTypeDTO) {
        if ( createDocumentTypeDTO == null ) {
            return null;
        }

        DocumentType.DocumentTypeBuilder documentType = DocumentType.builder();

        documentType.name( createDocumentTypeDTO.getName() );
        documentType.description( createDocumentTypeDTO.getDescription() );

        return documentType.build();
    }

    @Override
    public void updateEntityFromDto(DocumentTypeDTO.UpdateDocumentType updateDocumentTypeDTO, DocumentType documentType) {
        if ( updateDocumentTypeDTO == null ) {
            return;
        }

        documentType.setId( updateDocumentTypeDTO.getId() );
        documentType.setName( updateDocumentTypeDTO.getName() );
        documentType.setDescription( updateDocumentTypeDTO.getDescription() );
    }

    @Override
    public DocumentTypeDTO.DocumentTypeDetail toDetailDTO(DocumentType documentType) {
        if ( documentType == null ) {
            return null;
        }

        DocumentTypeDTO.DocumentTypeDetail.DocumentTypeDetailBuilder documentTypeDetail = DocumentTypeDTO.DocumentTypeDetail.builder();

        documentTypeDetail.id( documentType.getId() );
        documentTypeDetail.name( documentType.getName() );
        documentTypeDetail.description( documentType.getDescription() );

        return documentTypeDetail.build();
    }

    @Override
    public List<DocumentTypeDTO.DocumentTypeDetail> toDetailDTOList(List<DocumentType> documentTypes) {
        if ( documentTypes == null ) {
            return null;
        }

        List<DocumentTypeDTO.DocumentTypeDetail> list = new ArrayList<DocumentTypeDTO.DocumentTypeDetail>( documentTypes.size() );
        for ( DocumentType documentType : documentTypes ) {
            list.add( toDetailDTO( documentType ) );
        }

        return list;
    }
}
