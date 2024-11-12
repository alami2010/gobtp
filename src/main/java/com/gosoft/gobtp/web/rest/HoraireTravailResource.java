package com.gosoft.gobtp.web.rest;

import com.gosoft.gobtp.repository.HoraireTravailRepository;
import com.gosoft.gobtp.service.HoraireTravailService;
import com.gosoft.gobtp.service.dto.HoraireTravailDTO;
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
 * REST controller for managing {@link com.gosoft.gobtp.domain.HoraireTravail}.
 */
@RestController
@RequestMapping("/api/horaire-travails")
public class HoraireTravailResource {

    private static final Logger LOG = LoggerFactory.getLogger(HoraireTravailResource.class);

    private static final String ENTITY_NAME = "horaireTravail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HoraireTravailService horaireTravailService;

    private final HoraireTravailRepository horaireTravailRepository;

    public HoraireTravailResource(HoraireTravailService horaireTravailService, HoraireTravailRepository horaireTravailRepository) {
        this.horaireTravailService = horaireTravailService;
        this.horaireTravailRepository = horaireTravailRepository;
    }

    /**
     * {@code POST  /horaire-travails} : Create a new horaireTravail.
     *
     * @param horaireTravailDTO the horaireTravailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new horaireTravailDTO, or with status {@code 400 (Bad Request)} if the horaireTravail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<HoraireTravailDTO> createHoraireTravail(@Valid @RequestBody HoraireTravailDTO horaireTravailDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save HoraireTravail : {}", horaireTravailDTO);
        if (horaireTravailDTO.getId() != null) {
            throw new BadRequestAlertException("A new horaireTravail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        horaireTravailDTO = horaireTravailService.save(horaireTravailDTO);
        return ResponseEntity.created(new URI("/api/horaire-travails/" + horaireTravailDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, horaireTravailDTO.getId()))
            .body(horaireTravailDTO);
    }

    /**
     * {@code PUT  /horaire-travails/:id} : Updates an existing horaireTravail.
     *
     * @param id the id of the horaireTravailDTO to save.
     * @param horaireTravailDTO the horaireTravailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated horaireTravailDTO,
     * or with status {@code 400 (Bad Request)} if the horaireTravailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the horaireTravailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HoraireTravailDTO> updateHoraireTravail(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody HoraireTravailDTO horaireTravailDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update HoraireTravail : {}, {}", id, horaireTravailDTO);
        if (horaireTravailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, horaireTravailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!horaireTravailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        horaireTravailDTO = horaireTravailService.update(horaireTravailDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, horaireTravailDTO.getId()))
            .body(horaireTravailDTO);
    }

    /**
     * {@code PATCH  /horaire-travails/:id} : Partial updates given fields of an existing horaireTravail, field will ignore if it is null
     *
     * @param id the id of the horaireTravailDTO to save.
     * @param horaireTravailDTO the horaireTravailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated horaireTravailDTO,
     * or with status {@code 400 (Bad Request)} if the horaireTravailDTO is not valid,
     * or with status {@code 404 (Not Found)} if the horaireTravailDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the horaireTravailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HoraireTravailDTO> partialUpdateHoraireTravail(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody HoraireTravailDTO horaireTravailDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update HoraireTravail partially : {}, {}", id, horaireTravailDTO);
        if (horaireTravailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, horaireTravailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!horaireTravailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HoraireTravailDTO> result = horaireTravailService.partialUpdate(horaireTravailDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, horaireTravailDTO.getId())
        );
    }

    /**
     * {@code GET  /horaire-travails} : get all the horaireTravails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of horaireTravails in body.
     */
    @GetMapping("")
    public ResponseEntity<List<HoraireTravailDTO>> getAllHoraireTravails(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of HoraireTravails");
        Page<HoraireTravailDTO> page = horaireTravailService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /horaire-travails/:id} : get the "id" horaireTravail.
     *
     * @param id the id of the horaireTravailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the horaireTravailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HoraireTravailDTO> getHoraireTravail(@PathVariable("id") String id) {
        LOG.debug("REST request to get HoraireTravail : {}", id);
        Optional<HoraireTravailDTO> horaireTravailDTO = horaireTravailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(horaireTravailDTO);
    }

    /**
     * {@code DELETE  /horaire-travails/:id} : delete the "id" horaireTravail.
     *
     * @param id the id of the horaireTravailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHoraireTravail(@PathVariable("id") String id) {
        LOG.debug("REST request to delete HoraireTravail : {}", id);
        horaireTravailService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
