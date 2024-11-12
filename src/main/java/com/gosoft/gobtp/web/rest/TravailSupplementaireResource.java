package com.gosoft.gobtp.web.rest;

import com.gosoft.gobtp.repository.TravailSupplementaireRepository;
import com.gosoft.gobtp.service.TravailSupplementaireService;
import com.gosoft.gobtp.service.dto.TravailSupplementaireDTO;
import com.gosoft.gobtp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.gosoft.gobtp.domain.TravailSupplementaire}.
 */
@RestController
@RequestMapping("/api/travail-supplementaires")
public class TravailSupplementaireResource {

    private static final Logger LOG = LoggerFactory.getLogger(TravailSupplementaireResource.class);

    private static final String ENTITY_NAME = "travailSupplementaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TravailSupplementaireService travailSupplementaireService;

    private final TravailSupplementaireRepository travailSupplementaireRepository;

    public TravailSupplementaireResource(
        TravailSupplementaireService travailSupplementaireService,
        TravailSupplementaireRepository travailSupplementaireRepository
    ) {
        this.travailSupplementaireService = travailSupplementaireService;
        this.travailSupplementaireRepository = travailSupplementaireRepository;
    }

    /**
     * {@code POST  /travail-supplementaires} : Create a new travailSupplementaire.
     *
     * @param travailSupplementaireDTO the travailSupplementaireDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new travailSupplementaireDTO, or with status {@code 400 (Bad Request)} if the travailSupplementaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TravailSupplementaireDTO> createTravailSupplementaire(
        @Valid @RequestBody TravailSupplementaireDTO travailSupplementaireDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save TravailSupplementaire : {}", travailSupplementaireDTO);
        if (travailSupplementaireDTO.getId() != null) {
            throw new BadRequestAlertException("A new travailSupplementaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        travailSupplementaireDTO = travailSupplementaireService.save(travailSupplementaireDTO);
        return ResponseEntity.created(new URI("/api/travail-supplementaires/" + travailSupplementaireDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, travailSupplementaireDTO.getId()))
            .body(travailSupplementaireDTO);
    }

    /**
     * {@code PUT  /travail-supplementaires/:id} : Updates an existing travailSupplementaire.
     *
     * @param id the id of the travailSupplementaireDTO to save.
     * @param travailSupplementaireDTO the travailSupplementaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated travailSupplementaireDTO,
     * or with status {@code 400 (Bad Request)} if the travailSupplementaireDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the travailSupplementaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TravailSupplementaireDTO> updateTravailSupplementaire(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody TravailSupplementaireDTO travailSupplementaireDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update TravailSupplementaire : {}, {}", id, travailSupplementaireDTO);
        if (travailSupplementaireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, travailSupplementaireDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!travailSupplementaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        travailSupplementaireDTO = travailSupplementaireService.update(travailSupplementaireDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, travailSupplementaireDTO.getId()))
            .body(travailSupplementaireDTO);
    }

    /**
     * {@code PATCH  /travail-supplementaires/:id} : Partial updates given fields of an existing travailSupplementaire, field will ignore if it is null
     *
     * @param id the id of the travailSupplementaireDTO to save.
     * @param travailSupplementaireDTO the travailSupplementaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated travailSupplementaireDTO,
     * or with status {@code 400 (Bad Request)} if the travailSupplementaireDTO is not valid,
     * or with status {@code 404 (Not Found)} if the travailSupplementaireDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the travailSupplementaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TravailSupplementaireDTO> partialUpdateTravailSupplementaire(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody TravailSupplementaireDTO travailSupplementaireDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update TravailSupplementaire partially : {}, {}", id, travailSupplementaireDTO);
        if (travailSupplementaireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, travailSupplementaireDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!travailSupplementaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TravailSupplementaireDTO> result = travailSupplementaireService.partialUpdate(travailSupplementaireDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, travailSupplementaireDTO.getId())
        );
    }

    /**
     * {@code GET  /travail-supplementaires} : get all the travailSupplementaires.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of travailSupplementaires in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TravailSupplementaireDTO>> getAllTravailSupplementaires(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of TravailSupplementaires");
        Page<TravailSupplementaireDTO> page = travailSupplementaireService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /travail-supplementaires/:id} : get the "id" travailSupplementaire.
     *
     * @param id the id of the travailSupplementaireDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the travailSupplementaireDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TravailSupplementaireDTO> getTravailSupplementaire(@PathVariable("id") String id) {
        LOG.debug("REST request to get TravailSupplementaire : {}", id);
        Optional<TravailSupplementaireDTO> travailSupplementaireDTO = travailSupplementaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(travailSupplementaireDTO);
    }

    /**
     * {@code DELETE  /travail-supplementaires/:id} : delete the "id" travailSupplementaire.
     *
     * @param id the id of the travailSupplementaireDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTravailSupplementaire(@PathVariable("id") String id) {
        LOG.debug("REST request to delete TravailSupplementaire : {}", id);
        travailSupplementaireService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
