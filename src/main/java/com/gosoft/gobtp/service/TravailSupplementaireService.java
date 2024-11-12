package com.gosoft.gobtp.service;

import com.gosoft.gobtp.service.dto.TravailSupplementaireDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.gosoft.gobtp.domain.TravailSupplementaire}.
 */
public interface TravailSupplementaireService {
    /**
     * Save a travailSupplementaire.
     *
     * @param travailSupplementaireDTO the entity to save.
     * @return the persisted entity.
     */
    TravailSupplementaireDTO save(TravailSupplementaireDTO travailSupplementaireDTO);

    /**
     * Updates a travailSupplementaire.
     *
     * @param travailSupplementaireDTO the entity to update.
     * @return the persisted entity.
     */
    TravailSupplementaireDTO update(TravailSupplementaireDTO travailSupplementaireDTO);

    /**
     * Partially updates a travailSupplementaire.
     *
     * @param travailSupplementaireDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TravailSupplementaireDTO> partialUpdate(TravailSupplementaireDTO travailSupplementaireDTO);

    /**
     * Get all the travailSupplementaires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TravailSupplementaireDTO> findAll(Pageable pageable);

    /**
     * Get the "id" travailSupplementaire.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TravailSupplementaireDTO> findOne(String id);

    /**
     * Delete the "id" travailSupplementaire.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
