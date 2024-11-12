package com.gosoft.gobtp.web.rest;

import static com.gosoft.gobtp.domain.TravailSupplementaireAsserts.*;
import static com.gosoft.gobtp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gosoft.gobtp.IntegrationTest;
import com.gosoft.gobtp.domain.TravailSupplementaire;
import com.gosoft.gobtp.repository.TravailSupplementaireRepository;
import com.gosoft.gobtp.service.dto.TravailSupplementaireDTO;
import com.gosoft.gobtp.service.mapper.TravailSupplementaireMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link TravailSupplementaireResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TravailSupplementaireResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/travail-supplementaires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TravailSupplementaireRepository travailSupplementaireRepository;

    @Autowired
    private TravailSupplementaireMapper travailSupplementaireMapper;

    @Autowired
    private MockMvc restTravailSupplementaireMockMvc;

    private TravailSupplementaire travailSupplementaire;

    private TravailSupplementaire insertedTravailSupplementaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TravailSupplementaire createEntity() {
        return new TravailSupplementaire().name(DEFAULT_NAME).label(DEFAULT_LABEL).date(DEFAULT_DATE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TravailSupplementaire createUpdatedEntity() {
        return new TravailSupplementaire().name(UPDATED_NAME).label(UPDATED_LABEL).date(UPDATED_DATE);
    }

    @BeforeEach
    public void initTest() {
        travailSupplementaire = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedTravailSupplementaire != null) {
            travailSupplementaireRepository.delete(insertedTravailSupplementaire);
            insertedTravailSupplementaire = null;
        }
    }

    @Test
    void createTravailSupplementaire() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TravailSupplementaire
        TravailSupplementaireDTO travailSupplementaireDTO = travailSupplementaireMapper.toDto(travailSupplementaire);
        var returnedTravailSupplementaireDTO = om.readValue(
            restTravailSupplementaireMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(travailSupplementaireDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TravailSupplementaireDTO.class
        );

        // Validate the TravailSupplementaire in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTravailSupplementaire = travailSupplementaireMapper.toEntity(returnedTravailSupplementaireDTO);
        assertTravailSupplementaireUpdatableFieldsEquals(
            returnedTravailSupplementaire,
            getPersistedTravailSupplementaire(returnedTravailSupplementaire)
        );

        insertedTravailSupplementaire = returnedTravailSupplementaire;
    }

    @Test
    void createTravailSupplementaireWithExistingId() throws Exception {
        // Create the TravailSupplementaire with an existing ID
        travailSupplementaire.setId("existing_id");
        TravailSupplementaireDTO travailSupplementaireDTO = travailSupplementaireMapper.toDto(travailSupplementaire);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTravailSupplementaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(travailSupplementaireDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TravailSupplementaire in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        travailSupplementaire.setName(null);

        // Create the TravailSupplementaire, which fails.
        TravailSupplementaireDTO travailSupplementaireDTO = travailSupplementaireMapper.toDto(travailSupplementaire);

        restTravailSupplementaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(travailSupplementaireDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllTravailSupplementaires() throws Exception {
        // Initialize the database
        insertedTravailSupplementaire = travailSupplementaireRepository.save(travailSupplementaire);

        // Get all the travailSupplementaireList
        restTravailSupplementaireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(travailSupplementaire.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    void getTravailSupplementaire() throws Exception {
        // Initialize the database
        insertedTravailSupplementaire = travailSupplementaireRepository.save(travailSupplementaire);

        // Get the travailSupplementaire
        restTravailSupplementaireMockMvc
            .perform(get(ENTITY_API_URL_ID, travailSupplementaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(travailSupplementaire.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    void getNonExistingTravailSupplementaire() throws Exception {
        // Get the travailSupplementaire
        restTravailSupplementaireMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingTravailSupplementaire() throws Exception {
        // Initialize the database
        insertedTravailSupplementaire = travailSupplementaireRepository.save(travailSupplementaire);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the travailSupplementaire
        TravailSupplementaire updatedTravailSupplementaire = travailSupplementaireRepository
            .findById(travailSupplementaire.getId())
            .orElseThrow();
        updatedTravailSupplementaire.name(UPDATED_NAME).label(UPDATED_LABEL).date(UPDATED_DATE);
        TravailSupplementaireDTO travailSupplementaireDTO = travailSupplementaireMapper.toDto(updatedTravailSupplementaire);

        restTravailSupplementaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, travailSupplementaireDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(travailSupplementaireDTO))
            )
            .andExpect(status().isOk());

        // Validate the TravailSupplementaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTravailSupplementaireToMatchAllProperties(updatedTravailSupplementaire);
    }

    @Test
    void putNonExistingTravailSupplementaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        travailSupplementaire.setId(UUID.randomUUID().toString());

        // Create the TravailSupplementaire
        TravailSupplementaireDTO travailSupplementaireDTO = travailSupplementaireMapper.toDto(travailSupplementaire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTravailSupplementaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, travailSupplementaireDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(travailSupplementaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TravailSupplementaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTravailSupplementaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        travailSupplementaire.setId(UUID.randomUUID().toString());

        // Create the TravailSupplementaire
        TravailSupplementaireDTO travailSupplementaireDTO = travailSupplementaireMapper.toDto(travailSupplementaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTravailSupplementaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(travailSupplementaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TravailSupplementaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTravailSupplementaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        travailSupplementaire.setId(UUID.randomUUID().toString());

        // Create the TravailSupplementaire
        TravailSupplementaireDTO travailSupplementaireDTO = travailSupplementaireMapper.toDto(travailSupplementaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTravailSupplementaireMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(travailSupplementaireDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TravailSupplementaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTravailSupplementaireWithPatch() throws Exception {
        // Initialize the database
        insertedTravailSupplementaire = travailSupplementaireRepository.save(travailSupplementaire);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the travailSupplementaire using partial update
        TravailSupplementaire partialUpdatedTravailSupplementaire = new TravailSupplementaire();
        partialUpdatedTravailSupplementaire.setId(travailSupplementaire.getId());

        restTravailSupplementaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTravailSupplementaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTravailSupplementaire))
            )
            .andExpect(status().isOk());

        // Validate the TravailSupplementaire in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTravailSupplementaireUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTravailSupplementaire, travailSupplementaire),
            getPersistedTravailSupplementaire(travailSupplementaire)
        );
    }

    @Test
    void fullUpdateTravailSupplementaireWithPatch() throws Exception {
        // Initialize the database
        insertedTravailSupplementaire = travailSupplementaireRepository.save(travailSupplementaire);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the travailSupplementaire using partial update
        TravailSupplementaire partialUpdatedTravailSupplementaire = new TravailSupplementaire();
        partialUpdatedTravailSupplementaire.setId(travailSupplementaire.getId());

        partialUpdatedTravailSupplementaire.name(UPDATED_NAME).label(UPDATED_LABEL).date(UPDATED_DATE);

        restTravailSupplementaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTravailSupplementaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTravailSupplementaire))
            )
            .andExpect(status().isOk());

        // Validate the TravailSupplementaire in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTravailSupplementaireUpdatableFieldsEquals(
            partialUpdatedTravailSupplementaire,
            getPersistedTravailSupplementaire(partialUpdatedTravailSupplementaire)
        );
    }

    @Test
    void patchNonExistingTravailSupplementaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        travailSupplementaire.setId(UUID.randomUUID().toString());

        // Create the TravailSupplementaire
        TravailSupplementaireDTO travailSupplementaireDTO = travailSupplementaireMapper.toDto(travailSupplementaire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTravailSupplementaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, travailSupplementaireDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(travailSupplementaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TravailSupplementaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTravailSupplementaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        travailSupplementaire.setId(UUID.randomUUID().toString());

        // Create the TravailSupplementaire
        TravailSupplementaireDTO travailSupplementaireDTO = travailSupplementaireMapper.toDto(travailSupplementaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTravailSupplementaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(travailSupplementaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TravailSupplementaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTravailSupplementaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        travailSupplementaire.setId(UUID.randomUUID().toString());

        // Create the TravailSupplementaire
        TravailSupplementaireDTO travailSupplementaireDTO = travailSupplementaireMapper.toDto(travailSupplementaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTravailSupplementaireMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(travailSupplementaireDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TravailSupplementaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTravailSupplementaire() throws Exception {
        // Initialize the database
        insertedTravailSupplementaire = travailSupplementaireRepository.save(travailSupplementaire);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the travailSupplementaire
        restTravailSupplementaireMockMvc
            .perform(delete(ENTITY_API_URL_ID, travailSupplementaire.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return travailSupplementaireRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected TravailSupplementaire getPersistedTravailSupplementaire(TravailSupplementaire travailSupplementaire) {
        return travailSupplementaireRepository.findById(travailSupplementaire.getId()).orElseThrow();
    }

    protected void assertPersistedTravailSupplementaireToMatchAllProperties(TravailSupplementaire expectedTravailSupplementaire) {
        assertTravailSupplementaireAllPropertiesEquals(
            expectedTravailSupplementaire,
            getPersistedTravailSupplementaire(expectedTravailSupplementaire)
        );
    }

    protected void assertPersistedTravailSupplementaireToMatchUpdatableProperties(TravailSupplementaire expectedTravailSupplementaire) {
        assertTravailSupplementaireAllUpdatablePropertiesEquals(
            expectedTravailSupplementaire,
            getPersistedTravailSupplementaire(expectedTravailSupplementaire)
        );
    }
}
