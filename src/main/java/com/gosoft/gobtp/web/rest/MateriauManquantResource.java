package com.gosoft.gobtp.web.rest;

import com.gosoft.gobtp.repository.MateriauManquantRepository;
import com.gosoft.gobtp.service.MateriauManquantService;
import com.gosoft.gobtp.service.dto.MateriauManquantDTO;
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
 * REST controller for managing {@link com.gosoft.gobtp.domain.MateriauManquant}.
 */
@RestController
@RequestMapping("/api/materiau-manquants")
public class MateriauManquantResource {

    private static final Logger LOG = LoggerFactory.getLogger(MateriauManquantResource.class);

    private static final String ENTITY_NAME = "materiauManquant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MateriauManquantService materiauManquantService;

    private final MateriauManquantRepository materiauManquantRepository;

    public MateriauManquantResource(
        MateriauManquantService materiauManquantService,
        MateriauManquantRepository materiauManquantRepository
    ) {
        this.materiauManquantService = materiauManquantService;
        this.materiauManquantRepository = materiauManquantRepository;
    }

    /**
     * {@code POST  /materiau-manquants} : Create a new materiauManquant.
     *
     * @param materiauManquantDTO the materiauManquantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materiauManquantDTO, or with status {@code 400 (Bad Request)} if the materiauManquant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MateriauManquantDTO> createMateriauManquant(@Valid @RequestBody MateriauManquantDTO materiauManquantDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save MateriauManquant : {}", materiauManquantDTO);
        if (materiauManquantDTO.getId() != null) {
            throw new BadRequestAlertException("A new materiauManquant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        materiauManquantDTO = materiauManquantService.save(materiauManquantDTO);
        return ResponseEntity.created(new URI("/api/materiau-manquants/" + materiauManquantDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, materiauManquantDTO.getId()))
            .body(materiauManquantDTO);
    }

    /**
     * {@code PUT  /materiau-manquants/:id} : Updates an existing materiauManquant.
     *
     * @param id the id of the materiauManquantDTO to save.
     * @param materiauManquantDTO the materiauManquantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materiauManquantDTO,
     * or with status {@code 400 (Bad Request)} if the materiauManquantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materiauManquantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MateriauManquantDTO> updateMateriauManquant(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody MateriauManquantDTO materiauManquantDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update MateriauManquant : {}, {}", id, materiauManquantDTO);
        if (materiauManquantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materiauManquantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materiauManquantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        materiauManquantDTO = materiauManquantService.update(materiauManquantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, materiauManquantDTO.getId()))
            .body(materiauManquantDTO);
    }

    /**
     * {@code PATCH  /materiau-manquants/:id} : Partial updates given fields of an existing materiauManquant, field will ignore if it is null
     *
     * @param id the id of the materiauManquantDTO to save.
     * @param materiauManquantDTO the materiauManquantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materiauManquantDTO,
     * or with status {@code 400 (Bad Request)} if the materiauManquantDTO is not valid,
     * or with status {@code 404 (Not Found)} if the materiauManquantDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the materiauManquantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MateriauManquantDTO> partialUpdateMateriauManquant(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody MateriauManquantDTO materiauManquantDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MateriauManquant partially : {}, {}", id, materiauManquantDTO);
        if (materiauManquantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materiauManquantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materiauManquantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MateriauManquantDTO> result = materiauManquantService.partialUpdate(materiauManquantDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, materiauManquantDTO.getId())
        );
    }

    /**
     * {@code GET  /materiau-manquants} : get all the materiauManquants.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materiauManquants in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MateriauManquantDTO>> getAllMateriauManquants(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of MateriauManquants");
        Page<MateriauManquantDTO> page = materiauManquantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /materiau-manquants/:id} : get the "id" materiauManquant.
     *
     * @param id the id of the materiauManquantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materiauManquantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MateriauManquantDTO> getMateriauManquant(@PathVariable("id") String id) {
        LOG.debug("REST request to get MateriauManquant : {}", id);
        Optional<MateriauManquantDTO> materiauManquantDTO = materiauManquantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(materiauManquantDTO);
    }

    /**
     * {@code DELETE  /materiau-manquants/:id} : delete the "id" materiauManquant.
     *
     * @param id the id of the materiauManquantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMateriauManquant(@PathVariable("id") String id) {
        LOG.debug("REST request to delete MateriauManquant : {}", id);
        materiauManquantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
