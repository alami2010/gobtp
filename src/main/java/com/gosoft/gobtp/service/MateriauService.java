package com.gosoft.gobtp.service;

import com.gosoft.gobtp.service.dto.MateriauDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.gosoft.gobtp.domain.Materiau}.
 */
public interface MateriauService {
    /**
     * Save a materiau.
     *
     * @param materiauDTO the entity to save.
     * @return the persisted entity.
     */
    MateriauDTO save(MateriauDTO materiauDTO);

    /**
     * Updates a materiau.
     *
     * @param materiauDTO the entity to update.
     * @return the persisted entity.
     */
    MateriauDTO update(MateriauDTO materiauDTO);

    /**
     * Partially updates a materiau.
     *
     * @param materiauDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MateriauDTO> partialUpdate(MateriauDTO materiauDTO);

    /**
     * Get all the materiaus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MateriauDTO> findAll(Pageable pageable);

    /**
     * Get the "id" materiau.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MateriauDTO> findOne(String id);

    /**
     * Delete the "id" materiau.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
