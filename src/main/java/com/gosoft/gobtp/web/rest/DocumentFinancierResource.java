package com.gosoft.gobtp.web.rest;

import com.gosoft.gobtp.repository.DocumentFinancierRepository;
import com.gosoft.gobtp.service.DocumentFinancierService;
import com.gosoft.gobtp.service.dto.DocumentFinancierDTO;
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
 * REST controller for managing {@link com.gosoft.gobtp.domain.DocumentFinancier}.
 */
@RestController
@RequestMapping("/api/document-financiers")
public class DocumentFinancierResource {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentFinancierResource.class);

    private static final String ENTITY_NAME = "documentFinancier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentFinancierService documentFinancierService;

    private final DocumentFinancierRepository documentFinancierRepository;

    public DocumentFinancierResource(
        DocumentFinancierService documentFinancierService,
        DocumentFinancierRepository documentFinancierRepository
    ) {
        this.documentFinancierService = documentFinancierService;
        this.documentFinancierRepository = documentFinancierRepository;
    }

    /**
     * {@code POST  /document-financiers} : Create a new documentFinancier.
     *
     * @param documentFinancierDTO the documentFinancierDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentFinancierDTO, or with status {@code 400 (Bad Request)} if the documentFinancier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DocumentFinancierDTO> createDocumentFinancier(@Valid @RequestBody DocumentFinancierDTO documentFinancierDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save DocumentFinancier : {}", documentFinancierDTO);
        if (documentFinancierDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentFinancier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        documentFinancierDTO = documentFinancierService.save(documentFinancierDTO);
        return ResponseEntity.created(new URI("/api/document-financiers/" + documentFinancierDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, documentFinancierDTO.getId()))
            .body(documentFinancierDTO);
    }

    /**
     * {@code PUT  /document-financiers/:id} : Updates an existing documentFinancier.
     *
     * @param id the id of the documentFinancierDTO to save.
     * @param documentFinancierDTO the documentFinancierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentFinancierDTO,
     * or with status {@code 400 (Bad Request)} if the documentFinancierDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentFinancierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DocumentFinancierDTO> updateDocumentFinancier(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody DocumentFinancierDTO documentFinancierDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update DocumentFinancier : {}, {}", id, documentFinancierDTO);
        if (documentFinancierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentFinancierDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentFinancierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        documentFinancierDTO = documentFinancierService.update(documentFinancierDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, documentFinancierDTO.getId()))
            .body(documentFinancierDTO);
    }

    /**
     * {@code PATCH  /document-financiers/:id} : Partial updates given fields of an existing documentFinancier, field will ignore if it is null
     *
     * @param id the id of the documentFinancierDTO to save.
     * @param documentFinancierDTO the documentFinancierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentFinancierDTO,
     * or with status {@code 400 (Bad Request)} if the documentFinancierDTO is not valid,
     * or with status {@code 404 (Not Found)} if the documentFinancierDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the documentFinancierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DocumentFinancierDTO> partialUpdateDocumentFinancier(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody DocumentFinancierDTO documentFinancierDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update DocumentFinancier partially : {}, {}", id, documentFinancierDTO);
        if (documentFinancierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentFinancierDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentFinancierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DocumentFinancierDTO> result = documentFinancierService.partialUpdate(documentFinancierDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, documentFinancierDTO.getId())
        );
    }

    /**
     * {@code GET  /document-financiers} : get all the documentFinanciers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentFinanciers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DocumentFinancierDTO>> getAllDocumentFinanciers(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of DocumentFinanciers");
        Page<DocumentFinancierDTO> page = documentFinancierService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /document-financiers/:id} : get the "id" documentFinancier.
     *
     * @param id the id of the documentFinancierDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentFinancierDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DocumentFinancierDTO> getDocumentFinancier(@PathVariable("id") String id) {
        LOG.debug("REST request to get DocumentFinancier : {}", id);
        Optional<DocumentFinancierDTO> documentFinancierDTO = documentFinancierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentFinancierDTO);
    }

    /**
     * {@code DELETE  /document-financiers/:id} : delete the "id" documentFinancier.
     *
     * @param id the id of the documentFinancierDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentFinancier(@PathVariable("id") String id) {
        LOG.debug("REST request to delete DocumentFinancier : {}", id);
        documentFinancierService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
