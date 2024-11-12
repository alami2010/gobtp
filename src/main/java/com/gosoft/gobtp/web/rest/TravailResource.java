package com.gosoft.gobtp.web.rest;

import com.gosoft.gobtp.repository.TravailRepository;
import com.gosoft.gobtp.service.TravailService;
import com.gosoft.gobtp.service.dto.TravailDTO;
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
 * REST controller for managing {@link com.gosoft.gobtp.domain.Travail}.
 */
@RestController
@RequestMapping("/api/travails")
public class TravailResource {

    private static final Logger LOG = LoggerFactory.getLogger(TravailResource.class);

    private static final String ENTITY_NAME = "travail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TravailService travailService;

    private final TravailRepository travailRepository;

    public TravailResource(TravailService travailService, TravailRepository travailRepository) {
        this.travailService = travailService;
        this.travailRepository = travailRepository;
    }

    /**
     * {@code POST  /travails} : Create a new travail.
     *
     * @param travailDTO the travailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new travailDTO, or with status {@code 400 (Bad Request)} if the travail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TravailDTO> createTravail(@Valid @RequestBody TravailDTO travailDTO) throws URISyntaxException {
        LOG.debug("REST request to save Travail : {}", travailDTO);
        if (travailDTO.getId() != null) {
            throw new BadRequestAlertException("A new travail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        travailDTO = travailService.save(travailDTO);
        return ResponseEntity.created(new URI("/api/travails/" + travailDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, travailDTO.getId()))
            .body(travailDTO);
    }

    /**
     * {@code PUT  /travails/:id} : Updates an existing travail.
     *
     * @param id the id of the travailDTO to save.
     * @param travailDTO the travailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated travailDTO,
     * or with status {@code 400 (Bad Request)} if the travailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the travailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TravailDTO> updateTravail(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody TravailDTO travailDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Travail : {}, {}", id, travailDTO);
        if (travailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, travailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!travailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        travailDTO = travailService.update(travailDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, travailDTO.getId()))
            .body(travailDTO);
    }

    /**
     * {@code PATCH  /travails/:id} : Partial updates given fields of an existing travail, field will ignore if it is null
     *
     * @param id the id of the travailDTO to save.
     * @param travailDTO the travailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated travailDTO,
     * or with status {@code 400 (Bad Request)} if the travailDTO is not valid,
     * or with status {@code 404 (Not Found)} if the travailDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the travailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TravailDTO> partialUpdateTravail(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody TravailDTO travailDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Travail partially : {}, {}", id, travailDTO);
        if (travailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, travailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!travailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TravailDTO> result = travailService.partialUpdate(travailDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, travailDTO.getId())
        );
    }

    /**
     * {@code GET  /travails} : get all the travails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of travails in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TravailDTO>> getAllTravails(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Travails");
        Page<TravailDTO> page = travailService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /travails/:id} : get the "id" travail.
     *
     * @param id the id of the travailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the travailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TravailDTO> getTravail(@PathVariable("id") String id) {
        LOG.debug("REST request to get Travail : {}", id);
        Optional<TravailDTO> travailDTO = travailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(travailDTO);
    }

    /**
     * {@code DELETE  /travails/:id} : delete the "id" travail.
     *
     * @param id the id of the travailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTravail(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Travail : {}", id);
        travailService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
