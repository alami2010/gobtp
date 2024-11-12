package com.gosoft.gobtp.web.rest;

import com.gosoft.gobtp.repository.PhotoTravailRepository;
import com.gosoft.gobtp.service.PhotoTravailService;
import com.gosoft.gobtp.service.dto.PhotoTravailDTO;
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
 * REST controller for managing {@link com.gosoft.gobtp.domain.PhotoTravail}.
 */
@RestController
@RequestMapping("/api/photo-travails")
public class PhotoTravailResource {

    private static final Logger LOG = LoggerFactory.getLogger(PhotoTravailResource.class);

    private static final String ENTITY_NAME = "photoTravail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhotoTravailService photoTravailService;

    private final PhotoTravailRepository photoTravailRepository;

    public PhotoTravailResource(PhotoTravailService photoTravailService, PhotoTravailRepository photoTravailRepository) {
        this.photoTravailService = photoTravailService;
        this.photoTravailRepository = photoTravailRepository;
    }

    /**
     * {@code POST  /photo-travails} : Create a new photoTravail.
     *
     * @param photoTravailDTO the photoTravailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new photoTravailDTO, or with status {@code 400 (Bad Request)} if the photoTravail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PhotoTravailDTO> createPhotoTravail(@Valid @RequestBody PhotoTravailDTO photoTravailDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save PhotoTravail : {}", photoTravailDTO);
        if (photoTravailDTO.getId() != null) {
            throw new BadRequestAlertException("A new photoTravail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        photoTravailDTO = photoTravailService.save(photoTravailDTO);
        return ResponseEntity.created(new URI("/api/photo-travails/" + photoTravailDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, photoTravailDTO.getId()))
            .body(photoTravailDTO);
    }

    /**
     * {@code PUT  /photo-travails/:id} : Updates an existing photoTravail.
     *
     * @param id the id of the photoTravailDTO to save.
     * @param photoTravailDTO the photoTravailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated photoTravailDTO,
     * or with status {@code 400 (Bad Request)} if the photoTravailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the photoTravailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PhotoTravailDTO> updatePhotoTravail(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody PhotoTravailDTO photoTravailDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PhotoTravail : {}, {}", id, photoTravailDTO);
        if (photoTravailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, photoTravailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!photoTravailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        photoTravailDTO = photoTravailService.update(photoTravailDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, photoTravailDTO.getId()))
            .body(photoTravailDTO);
    }

    /**
     * {@code PATCH  /photo-travails/:id} : Partial updates given fields of an existing photoTravail, field will ignore if it is null
     *
     * @param id the id of the photoTravailDTO to save.
     * @param photoTravailDTO the photoTravailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated photoTravailDTO,
     * or with status {@code 400 (Bad Request)} if the photoTravailDTO is not valid,
     * or with status {@code 404 (Not Found)} if the photoTravailDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the photoTravailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PhotoTravailDTO> partialUpdatePhotoTravail(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody PhotoTravailDTO photoTravailDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PhotoTravail partially : {}, {}", id, photoTravailDTO);
        if (photoTravailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, photoTravailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!photoTravailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PhotoTravailDTO> result = photoTravailService.partialUpdate(photoTravailDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, photoTravailDTO.getId())
        );
    }

    /**
     * {@code GET  /photo-travails} : get all the photoTravails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of photoTravails in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PhotoTravailDTO>> getAllPhotoTravails(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of PhotoTravails");
        Page<PhotoTravailDTO> page = photoTravailService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /photo-travails/:id} : get the "id" photoTravail.
     *
     * @param id the id of the photoTravailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the photoTravailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PhotoTravailDTO> getPhotoTravail(@PathVariable("id") String id) {
        LOG.debug("REST request to get PhotoTravail : {}", id);
        Optional<PhotoTravailDTO> photoTravailDTO = photoTravailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(photoTravailDTO);
    }

    /**
     * {@code DELETE  /photo-travails/:id} : delete the "id" photoTravail.
     *
     * @param id the id of the photoTravailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhotoTravail(@PathVariable("id") String id) {
        LOG.debug("REST request to delete PhotoTravail : {}", id);
        photoTravailService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
