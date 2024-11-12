package com.gosoft.gobtp.service;

import com.gosoft.gobtp.service.dto.PhotoTravailDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.gosoft.gobtp.domain.PhotoTravail}.
 */
public interface PhotoTravailService {
    /**
     * Save a photoTravail.
     *
     * @param photoTravailDTO the entity to save.
     * @return the persisted entity.
     */
    PhotoTravailDTO save(PhotoTravailDTO photoTravailDTO);

    /**
     * Updates a photoTravail.
     *
     * @param photoTravailDTO the entity to update.
     * @return the persisted entity.
     */
    PhotoTravailDTO update(PhotoTravailDTO photoTravailDTO);

    /**
     * Partially updates a photoTravail.
     *
     * @param photoTravailDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PhotoTravailDTO> partialUpdate(PhotoTravailDTO photoTravailDTO);

    /**
     * Get all the photoTravails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PhotoTravailDTO> findAll(Pageable pageable);

    /**
     * Get the "id" photoTravail.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PhotoTravailDTO> findOne(String id);

    /**
     * Delete the "id" photoTravail.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
