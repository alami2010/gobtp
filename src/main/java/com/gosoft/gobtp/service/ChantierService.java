package com.gosoft.gobtp.service;

import com.gosoft.gobtp.service.dto.ChantierDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.gosoft.gobtp.domain.Chantier}.
 */
public interface ChantierService {
    /**
     * Save a chantier.
     *
     * @param chantierDTO the entity to save.
     * @return the persisted entity.
     */
    ChantierDTO save(ChantierDTO chantierDTO);

    /**
     * Updates a chantier.
     *
     * @param chantierDTO the entity to update.
     * @return the persisted entity.
     */
    ChantierDTO update(ChantierDTO chantierDTO);

    /**
     * Partially updates a chantier.
     *
     * @param chantierDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ChantierDTO> partialUpdate(ChantierDTO chantierDTO);

    /**
     * Get all the chantiers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChantierDTO> findAll(Pageable pageable);

    /**
     * Get all the chantiers with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChantierDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" chantier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChantierDTO> findOne(String id);

    /**
     * Delete the "id" chantier.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
