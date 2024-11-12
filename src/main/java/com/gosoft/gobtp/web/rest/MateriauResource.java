package com.gosoft.gobtp.web.rest;

import com.gosoft.gobtp.repository.MateriauRepository;
import com.gosoft.gobtp.service.MateriauService;
import com.gosoft.gobtp.service.dto.MateriauDTO;
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
 * REST controller for managing {@link com.gosoft.gobtp.domain.Materiau}.
 */
@RestController
@RequestMapping("/api/materiaus")
public class MateriauResource {

    private static final Logger LOG = LoggerFactory.getLogger(MateriauResource.class);

    private static final String ENTITY_NAME = "materiau";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MateriauService materiauService;

    private final MateriauRepository materiauRepository;

    public MateriauResource(MateriauService materiauService, MateriauRepository materiauRepository) {
        this.materiauService = materiauService;
        this.materiauRepository = materiauRepository;
    }

    /**
     * {@code POST  /materiaus} : Create a new materiau.
     *
     * @param materiauDTO the materiauDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materiauDTO, or with status {@code 400 (Bad Request)} if the materiau has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MateriauDTO> createMateriau(@Valid @RequestBody MateriauDTO materiauDTO) throws URISyntaxException {
        LOG.debug("REST request to save Materiau : {}", materiauDTO);
        if (materiauDTO.getId() != null) {
            throw new BadRequestAlertException("A new materiau cannot already have an ID", ENTITY_NAME, "idexists");
        }
        materiauDTO = materiauService.save(materiauDTO);
        return ResponseEntity.created(new URI("/api/materiaus/" + materiauDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, materiauDTO.getId()))
            .body(materiauDTO);
    }

    /**
     * {@code PUT  /materiaus/:id} : Updates an existing materiau.
     *
     * @param id the id of the materiauDTO to save.
     * @param materiauDTO the materiauDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materiauDTO,
     * or with status {@code 400 (Bad Request)} if the materiauDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materiauDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MateriauDTO> updateMateriau(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody MateriauDTO materiauDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Materiau : {}, {}", id, materiauDTO);
        if (materiauDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materiauDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materiauRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        materiauDTO = materiauService.update(materiauDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, materiauDTO.getId()))
            .body(materiauDTO);
    }

    /**
     * {@code PATCH  /materiaus/:id} : Partial updates given fields of an existing materiau, field will ignore if it is null
     *
     * @param id the id of the materiauDTO to save.
     * @param materiauDTO the materiauDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materiauDTO,
     * or with status {@code 400 (Bad Request)} if the materiauDTO is not valid,
     * or with status {@code 404 (Not Found)} if the materiauDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the materiauDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MateriauDTO> partialUpdateMateriau(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody MateriauDTO materiauDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Materiau partially : {}, {}", id, materiauDTO);
        if (materiauDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materiauDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materiauRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MateriauDTO> result = materiauService.partialUpdate(materiauDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, materiauDTO.getId())
        );
    }

    /**
     * {@code GET  /materiaus} : get all the materiaus.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materiaus in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MateriauDTO>> getAllMateriaus(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Materiaus");
        Page<MateriauDTO> page = materiauService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /materiaus/:id} : get the "id" materiau.
     *
     * @param id the id of the materiauDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materiauDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MateriauDTO> getMateriau(@PathVariable("id") String id) {
        LOG.debug("REST request to get Materiau : {}", id);
        Optional<MateriauDTO> materiauDTO = materiauService.findOne(id);
        return ResponseUtil.wrapOrNotFound(materiauDTO);
    }

    /**
     * {@code DELETE  /materiaus/:id} : delete the "id" materiau.
     *
     * @param id the id of the materiauDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMateriau(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Materiau : {}", id);
        materiauService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
