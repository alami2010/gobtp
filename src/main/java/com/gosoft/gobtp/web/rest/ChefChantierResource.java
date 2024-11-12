package com.gosoft.gobtp.web.rest;

import com.gosoft.gobtp.repository.ChefChantierRepository;
import com.gosoft.gobtp.service.ChefChantierService;
import com.gosoft.gobtp.service.dto.ChefChantierDTO;
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
 * REST controller for managing {@link com.gosoft.gobtp.domain.ChefChantier}.
 */
@RestController
@RequestMapping("/api/chef-chantiers")
public class ChefChantierResource {

    private static final Logger LOG = LoggerFactory.getLogger(ChefChantierResource.class);

    private static final String ENTITY_NAME = "chefChantier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChefChantierService chefChantierService;

    private final ChefChantierRepository chefChantierRepository;

    public ChefChantierResource(ChefChantierService chefChantierService, ChefChantierRepository chefChantierRepository) {
        this.chefChantierService = chefChantierService;
        this.chefChantierRepository = chefChantierRepository;
    }

    /**
     * {@code POST  /chef-chantiers} : Create a new chefChantier.
     *
     * @param chefChantierDTO the chefChantierDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chefChantierDTO, or with status {@code 400 (Bad Request)} if the chefChantier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ChefChantierDTO> createChefChantier(@Valid @RequestBody ChefChantierDTO chefChantierDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save ChefChantier : {}", chefChantierDTO);
        if (chefChantierDTO.getId() != null) {
            throw new BadRequestAlertException("A new chefChantier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(chefChantierDTO.getInternalUser())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        chefChantierDTO = chefChantierService.save(chefChantierDTO);
        return ResponseEntity.created(new URI("/api/chef-chantiers/" + chefChantierDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, chefChantierDTO.getId()))
            .body(chefChantierDTO);
    }

    /**
     * {@code PUT  /chef-chantiers/:id} : Updates an existing chefChantier.
     *
     * @param id the id of the chefChantierDTO to save.
     * @param chefChantierDTO the chefChantierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chefChantierDTO,
     * or with status {@code 400 (Bad Request)} if the chefChantierDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chefChantierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ChefChantierDTO> updateChefChantier(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ChefChantierDTO chefChantierDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ChefChantier : {}, {}", id, chefChantierDTO);
        if (chefChantierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chefChantierDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chefChantierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        chefChantierDTO = chefChantierService.update(chefChantierDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, chefChantierDTO.getId()))
            .body(chefChantierDTO);
    }

    /**
     * {@code PATCH  /chef-chantiers/:id} : Partial updates given fields of an existing chefChantier, field will ignore if it is null
     *
     * @param id the id of the chefChantierDTO to save.
     * @param chefChantierDTO the chefChantierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chefChantierDTO,
     * or with status {@code 400 (Bad Request)} if the chefChantierDTO is not valid,
     * or with status {@code 404 (Not Found)} if the chefChantierDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the chefChantierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChefChantierDTO> partialUpdateChefChantier(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ChefChantierDTO chefChantierDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ChefChantier partially : {}, {}", id, chefChantierDTO);
        if (chefChantierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chefChantierDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chefChantierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChefChantierDTO> result = chefChantierService.partialUpdate(chefChantierDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, chefChantierDTO.getId())
        );
    }

    /**
     * {@code GET  /chef-chantiers} : get all the chefChantiers.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chefChantiers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ChefChantierDTO>> getAllChefChantiers(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of ChefChantiers");
        Page<ChefChantierDTO> page;
        if (eagerload) {
            page = chefChantierService.findAllWithEagerRelationships(pageable);
        } else {
            page = chefChantierService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /chef-chantiers/:id} : get the "id" chefChantier.
     *
     * @param id the id of the chefChantierDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chefChantierDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ChefChantierDTO> getChefChantier(@PathVariable("id") String id) {
        LOG.debug("REST request to get ChefChantier : {}", id);
        Optional<ChefChantierDTO> chefChantierDTO = chefChantierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chefChantierDTO);
    }

    /**
     * {@code DELETE  /chef-chantiers/:id} : delete the "id" chefChantier.
     *
     * @param id the id of the chefChantierDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChefChantier(@PathVariable("id") String id) {
        LOG.debug("REST request to delete ChefChantier : {}", id);
        chefChantierService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
