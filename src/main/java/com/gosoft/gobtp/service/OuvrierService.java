package com.gosoft.gobtp.service;

import com.gosoft.gobtp.service.dto.OuvrierDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.gosoft.gobtp.domain.Ouvrier}.
 */
public interface OuvrierService {
    /**
     * Save a ouvrier.
     *
     * @param ouvrierDTO the entity to save.
     * @return the persisted entity.
     */
    OuvrierDTO save(OuvrierDTO ouvrierDTO);

    /**
     * Updates a ouvrier.
     *
     * @param ouvrierDTO the entity to update.
     * @return the persisted entity.
     */
    OuvrierDTO update(OuvrierDTO ouvrierDTO);

    /**
     * Partially updates a ouvrier.
     *
     * @param ouvrierDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OuvrierDTO> partialUpdate(OuvrierDTO ouvrierDTO);

    /**
     * Get all the ouvriers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OuvrierDTO> findAll(Pageable pageable);

    /**
     * Get all the ouvriers with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OuvrierDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" ouvrier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OuvrierDTO> findOne(String id);

    /**
     * Delete the "id" ouvrier.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
