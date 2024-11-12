package com.gosoft.gobtp.web.rest;

import com.gosoft.gobtp.repository.OuvrierRepository;
import com.gosoft.gobtp.service.OuvrierService;
import com.gosoft.gobtp.service.dto.OuvrierDTO;
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
 * REST controller for managing {@link com.gosoft.gobtp.domain.Ouvrier}.
 */
@RestController
@RequestMapping("/api/ouvriers")
public class OuvrierResource {

    private static final Logger LOG = LoggerFactory.getLogger(OuvrierResource.class);

    private static final String ENTITY_NAME = "ouvrier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OuvrierService ouvrierService;

    private final OuvrierRepository ouvrierRepository;

    public OuvrierResource(OuvrierService ouvrierService, OuvrierRepository ouvrierRepository) {
        this.ouvrierService = ouvrierService;
        this.ouvrierRepository = ouvrierRepository;
    }

    /**
     * {@code POST  /ouvriers} : Create a new ouvrier.
     *
     * @param ouvrierDTO the ouvrierDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ouvrierDTO, or with status {@code 400 (Bad Request)} if the ouvrier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OuvrierDTO> createOuvrier(@Valid @RequestBody OuvrierDTO ouvrierDTO) throws URISyntaxException {
        LOG.debug("REST request to save Ouvrier : {}", ouvrierDTO);
        if (ouvrierDTO.getId() != null) {
            throw new BadRequestAlertException("A new ouvrier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(ouvrierDTO.getInternalUser())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        ouvrierDTO = ouvrierService.save(ouvrierDTO);
        return ResponseEntity.created(new URI("/api/ouvriers/" + ouvrierDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ouvrierDTO.getId()))
            .body(ouvrierDTO);
    }

    /**
     * {@code PUT  /ouvriers/:id} : Updates an existing ouvrier.
     *
     * @param id the id of the ouvrierDTO to save.
     * @param ouvrierDTO the ouvrierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ouvrierDTO,
     * or with status {@code 400 (Bad Request)} if the ouvrierDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ouvrierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OuvrierDTO> updateOuvrier(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody OuvrierDTO ouvrierDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Ouvrier : {}, {}", id, ouvrierDTO);
        if (ouvrierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ouvrierDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ouvrierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ouvrierDTO = ouvrierService.update(ouvrierDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ouvrierDTO.getId()))
            .body(ouvrierDTO);
    }

    /**
     * {@code PATCH  /ouvriers/:id} : Partial updates given fields of an existing ouvrier, field will ignore if it is null
     *
     * @param id the id of the ouvrierDTO to save.
     * @param ouvrierDTO the ouvrierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ouvrierDTO,
     * or with status {@code 400 (Bad Request)} if the ouvrierDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ouvrierDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ouvrierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OuvrierDTO> partialUpdateOuvrier(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody OuvrierDTO ouvrierDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Ouvrier partially : {}, {}", id, ouvrierDTO);
        if (ouvrierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ouvrierDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ouvrierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OuvrierDTO> result = ouvrierService.partialUpdate(ouvrierDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ouvrierDTO.getId())
        );
    }

    /**
     * {@code GET  /ouvriers} : get all the ouvriers.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ouvriers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<OuvrierDTO>> getAllOuvriers(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of Ouvriers");
        Page<OuvrierDTO> page;
        if (eagerload) {
            page = ouvrierService.findAllWithEagerRelationships(pageable);
        } else {
            page = ouvrierService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ouvriers/:id} : get the "id" ouvrier.
     *
     * @param id the id of the ouvrierDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ouvrierDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OuvrierDTO> getOuvrier(@PathVariable("id") String id) {
        LOG.debug("REST request to get Ouvrier : {}", id);
        Optional<OuvrierDTO> ouvrierDTO = ouvrierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ouvrierDTO);
    }

    /**
     * {@code DELETE  /ouvriers/:id} : delete the "id" ouvrier.
     *
     * @param id the id of the ouvrierDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOuvrier(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Ouvrier : {}", id);
        ouvrierService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
