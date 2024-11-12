package com.gosoft.gobtp.service.impl;

import com.gosoft.gobtp.domain.DocumentFinancier;
import com.gosoft.gobtp.repository.DocumentFinancierRepository;
import com.gosoft.gobtp.service.DocumentFinancierService;
import com.gosoft.gobtp.service.dto.DocumentFinancierDTO;
import com.gosoft.gobtp.service.mapper.DocumentFinancierMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.gosoft.gobtp.domain.DocumentFinancier}.
 */
@Service
public class DocumentFinancierServiceImpl implements DocumentFinancierService {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentFinancierServiceImpl.class);

    private final DocumentFinancierRepository documentFinancierRepository;

    private final DocumentFinancierMapper documentFinancierMapper;

    public DocumentFinancierServiceImpl(
        DocumentFinancierRepository documentFinancierRepository,
        DocumentFinancierMapper documentFinancierMapper
    ) {
        this.documentFinancierRepository = documentFinancierRepository;
        this.documentFinancierMapper = documentFinancierMapper;
    }

    @Override
    public DocumentFinancierDTO save(DocumentFinancierDTO documentFinancierDTO) {
        LOG.debug("Request to save DocumentFinancier : {}", documentFinancierDTO);
        DocumentFinancier documentFinancier = documentFinancierMapper.toEntity(documentFinancierDTO);
        documentFinancier = documentFinancierRepository.save(documentFinancier);
        return documentFinancierMapper.toDto(documentFinancier);
    }

    @Override
    public DocumentFinancierDTO update(DocumentFinancierDTO documentFinancierDTO) {
        LOG.debug("Request to update DocumentFinancier : {}", documentFinancierDTO);
        DocumentFinancier documentFinancier = documentFinancierMapper.toEntity(documentFinancierDTO);
        documentFinancier = documentFinancierRepository.save(documentFinancier);
        return documentFinancierMapper.toDto(documentFinancier);
    }

    @Override
    public Optional<DocumentFinancierDTO> partialUpdate(DocumentFinancierDTO documentFinancierDTO) {
        LOG.debug("Request to partially update DocumentFinancier : {}", documentFinancierDTO);

        return documentFinancierRepository
            .findById(documentFinancierDTO.getId())
            .map(existingDocumentFinancier -> {
                documentFinancierMapper.partialUpdate(existingDocumentFinancier, documentFinancierDTO);

                return existingDocumentFinancier;
            })
            .map(documentFinancierRepository::save)
            .map(documentFinancierMapper::toDto);
    }

    @Override
    public Page<DocumentFinancierDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all DocumentFinanciers");
        return documentFinancierRepository.findAll(pageable).map(documentFinancierMapper::toDto);
    }

    @Override
    public Optional<DocumentFinancierDTO> findOne(String id) {
        LOG.debug("Request to get DocumentFinancier : {}", id);
        return documentFinancierRepository.findById(id).map(documentFinancierMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete DocumentFinancier : {}", id);
        documentFinancierRepository.deleteById(id);
    }
}
