package com.gosoft.gobtp.web.rest;

import com.gosoft.gobtp.repository.ChantierRepository;
import com.gosoft.gobtp.service.ChantierService;
import com.gosoft.gobtp.service.dto.ChantierDTO;
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
 * REST controller for managing {@link com.gosoft.gobtp.domain.Chantier}.
 */
@RestController
@RequestMapping("/api/chantiers")
public class ChantierResource {

    private static final Logger LOG = LoggerFactory.getLogger(ChantierResource.class);

    private static final String ENTITY_NAME = "chantier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChantierService chantierService;

    private final ChantierRepository chantierRepository;

    public ChantierResource(ChantierService chantierService, ChantierRepository chantierRepository) {
        this.chantierService = chantierService;
        this.chantierRepository = chantierRepository;
    }

    /**
     * {@code POST  /chantiers} : Create a new chantier.
     *
     * @param chantierDTO the chantierDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chantierDTO, or with status {@code 400 (Bad Request)} if the chantier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ChantierDTO> createChantier(@Valid @RequestBody ChantierDTO chantierDTO) throws URISyntaxException {
        LOG.debug("REST request to save Chantier : {}", chantierDTO);
        if (chantierDTO.getId() != null) {
            throw new BadRequestAlertException("A new chantier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        chantierDTO = chantierService.save(chantierDTO);
        return ResponseEntity.created(new URI("/api/chantiers/" + chantierDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, chantierDTO.getId()))
            .body(chantierDTO);
    }

    /**
     * {@code PUT  /chantiers/:id} : Updates an existing chantier.
     *
     * @param id the id of the chantierDTO to save.
     * @param chantierDTO the chantierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chantierDTO,
     * or with status {@code 400 (Bad Request)} if the chantierDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chantierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ChantierDTO> updateChantier(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ChantierDTO chantierDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Chantier : {}, {}", id, chantierDTO);
        if (chantierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chantierDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chantierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        chantierDTO = chantierService.update(chantierDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, chantierDTO.getId()))
            .body(chantierDTO);
    }

    /**
     * {@code PATCH  /chantiers/:id} : Partial updates given fields of an existing chantier, field will ignore if it is null
     *
     * @param id the id of the chantierDTO to save.
     * @param chantierDTO the chantierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chantierDTO,
     * or with status {@code 400 (Bad Request)} if the chantierDTO is not valid,
     * or with status {@code 404 (Not Found)} if the chantierDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the chantierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChantierDTO> partialUpdateChantier(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ChantierDTO chantierDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Chantier partially : {}, {}", id, chantierDTO);
        if (chantierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chantierDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chantierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChantierDTO> result = chantierService.partialUpdate(chantierDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, chantierDTO.getId())
        );
    }

    /**
     * {@code GET  /chantiers} : get all the chantiers.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chantiers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ChantierDTO>> getAllChantiers(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of Chantiers");
        Page<ChantierDTO> page;
        if (eagerload) {
            page = chantierService.findAllWithEagerRelationships(pageable);
        } else {
            page = chantierService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /chantiers/:id} : get the "id" chantier.
     *
     * @param id the id of the chantierDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chantierDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ChantierDTO> getChantier(@PathVariable("id") String id) {
        LOG.debug("REST request to get Chantier : {}", id);
        Optional<ChantierDTO> chantierDTO = chantierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chantierDTO);
    }

    /**
     * {@code DELETE  /chantiers/:id} : delete the "id" chantier.
     *
     * @param id the id of the chantierDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChantier(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Chantier : {}", id);
        chantierService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
