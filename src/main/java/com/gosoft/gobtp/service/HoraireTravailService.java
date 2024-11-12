package com.gosoft.gobtp.service;

import com.gosoft.gobtp.service.dto.HoraireTravailDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.gosoft.gobtp.domain.HoraireTravail}.
 */
public interface HoraireTravailService {
    /**
     * Save a horaireTravail.
     *
     * @param horaireTravailDTO the entity to save.
     * @return the persisted entity.
     */
    HoraireTravailDTO save(HoraireTravailDTO horaireTravailDTO);

    /**
     * Updates a horaireTravail.
     *
     * @param horaireTravailDTO the entity to update.
     * @return the persisted entity.
     */
    HoraireTravailDTO update(HoraireTravailDTO horaireTravailDTO);

    /**
     * Partially updates a horaireTravail.
     *
     * @param horaireTravailDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HoraireTravailDTO> partialUpdate(HoraireTravailDTO horaireTravailDTO);

    /**
     * Get all the horaireTravails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HoraireTravailDTO> findAll(Pageable pageable);

    /**
     * Get the "id" horaireTravail.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HoraireTravailDTO> findOne(String id);

    /**
     * Delete the "id" horaireTravail.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
