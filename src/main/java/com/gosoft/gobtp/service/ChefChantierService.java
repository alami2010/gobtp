package com.gosoft.gobtp.service;

import com.gosoft.gobtp.service.dto.ChefChantierDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.gosoft.gobtp.domain.ChefChantier}.
 */
public interface ChefChantierService {
    /**
     * Save a chefChantier.
     *
     * @param chefChantierDTO the entity to save.
     * @return the persisted entity.
     */
    ChefChantierDTO save(ChefChantierDTO chefChantierDTO);

    /**
     * Updates a chefChantier.
     *
     * @param chefChantierDTO the entity to update.
     * @return the persisted entity.
     */
    ChefChantierDTO update(ChefChantierDTO chefChantierDTO);

    /**
     * Partially updates a chefChantier.
     *
     * @param chefChantierDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ChefChantierDTO> partialUpdate(ChefChantierDTO chefChantierDTO);

    /**
     * Get all the chefChantiers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChefChantierDTO> findAll(Pageable pageable);

    /**
     * Get all the chefChantiers with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChefChantierDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" chefChantier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChefChantierDTO> findOne(String id);

    /**
     * Delete the "id" chefChantier.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
