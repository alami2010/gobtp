package com.gosoft.gobtp.service;

import com.gosoft.gobtp.service.dto.MateriauManquantDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.gosoft.gobtp.domain.MateriauManquant}.
 */
public interface MateriauManquantService {
    /**
     * Save a materiauManquant.
     *
     * @param materiauManquantDTO the entity to save.
     * @return the persisted entity.
     */
    MateriauManquantDTO save(MateriauManquantDTO materiauManquantDTO);

    /**
     * Updates a materiauManquant.
     *
     * @param materiauManquantDTO the entity to update.
     * @return the persisted entity.
     */
    MateriauManquantDTO update(MateriauManquantDTO materiauManquantDTO);

    /**
     * Partially updates a materiauManquant.
     *
     * @param materiauManquantDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MateriauManquantDTO> partialUpdate(MateriauManquantDTO materiauManquantDTO);

    /**
     * Get all the materiauManquants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MateriauManquantDTO> findAll(Pageable pageable);

    /**
     * Get the "id" materiauManquant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MateriauManquantDTO> findOne(String id);

    /**
     * Delete the "id" materiauManquant.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
