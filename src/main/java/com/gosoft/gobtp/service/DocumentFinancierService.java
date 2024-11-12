package com.gosoft.gobtp.service;

import com.gosoft.gobtp.service.dto.DocumentFinancierDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.gosoft.gobtp.domain.DocumentFinancier}.
 */
public interface DocumentFinancierService {
    /**
     * Save a documentFinancier.
     *
     * @param documentFinancierDTO the entity to save.
     * @return the persisted entity.
     */
    DocumentFinancierDTO save(DocumentFinancierDTO documentFinancierDTO);

    /**
     * Updates a documentFinancier.
     *
     * @param documentFinancierDTO the entity to update.
     * @return the persisted entity.
     */
    DocumentFinancierDTO update(DocumentFinancierDTO documentFinancierDTO);

    /**
     * Partially updates a documentFinancier.
     *
     * @param documentFinancierDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DocumentFinancierDTO> partialUpdate(DocumentFinancierDTO documentFinancierDTO);

    /**
     * Get all the documentFinanciers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocumentFinancierDTO> findAll(Pageable pageable);

    /**
     * Get the "id" documentFinancier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumentFinancierDTO> findOne(String id);

    /**
     * Delete the "id" documentFinancier.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
