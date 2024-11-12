package com.gosoft.gobtp.web.rest;

import static com.gosoft.gobtp.domain.TravailAsserts.*;
import static com.gosoft.gobtp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gosoft.gobtp.IntegrationTest;
import com.gosoft.gobtp.domain.Travail;
import com.gosoft.gobtp.repository.TravailRepository;
import com.gosoft.gobtp.service.dto.TravailDTO;
import com.gosoft.gobtp.service.mapper.TravailMapper;
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
 * Integration tests for the {@link TravailResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TravailResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/travails";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TravailRepository travailRepository;

    @Autowired
    private TravailMapper travailMapper;

    @Autowired
    private MockMvc restTravailMockMvc;

    private Travail travail;

    private Travail insertedTravail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Travail createEntity() {
        return new Travail().name(DEFAULT_NAME).label(DEFAULT_LABEL);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Travail createUpdatedEntity() {
        return new Travail().name(UPDATED_NAME).label(UPDATED_LABEL);
    }

    @BeforeEach
    public void initTest() {
        travail = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedTravail != null) {
            travailRepository.delete(insertedTravail);
            insertedTravail = null;
        }
    }

    @Test
    void createTravail() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Travail
        TravailDTO travailDTO = travailMapper.toDto(travail);
        var returnedTravailDTO = om.readValue(
            restTravailMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(travailDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TravailDTO.class
        );

        // Validate the Travail in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTravail = travailMapper.toEntity(returnedTravailDTO);
        assertTravailUpdatableFieldsEquals(returnedTravail, getPersistedTravail(returnedTravail));

        insertedTravail = returnedTravail;
    }

    @Test
    void createTravailWithExistingId() throws Exception {
        // Create the Travail with an existing ID
        travail.setId("existing_id");
        TravailDTO travailDTO = travailMapper.toDto(travail);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTravailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(travailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Travail in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        travail.setName(null);

        // Create the Travail, which fails.
        TravailDTO travailDTO = travailMapper.toDto(travail);

        restTravailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(travailDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllTravails() throws Exception {
        // Initialize the database
        insertedTravail = travailRepository.save(travail);

        // Get all the travailList
        restTravailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(travail.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)));
    }

    @Test
    void getTravail() throws Exception {
        // Initialize the database
        insertedTravail = travailRepository.save(travail);

        // Get the travail
        restTravailMockMvc
            .perform(get(ENTITY_API_URL_ID, travail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(travail.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL));
    }

    @Test
    void getNonExistingTravail() throws Exception {
        // Get the travail
        restTravailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingTravail() throws Exception {
        // Initialize the database
        insertedTravail = travailRepository.save(travail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the travail
        Travail updatedTravail = travailRepository.findById(travail.getId()).orElseThrow();
        updatedTravail.name(UPDATED_NAME).label(UPDATED_LABEL);
        TravailDTO travailDTO = travailMapper.toDto(updatedTravail);

        restTravailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, travailDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(travailDTO))
            )
            .andExpect(status().isOk());

        // Validate the Travail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTravailToMatchAllProperties(updatedTravail);
    }

    @Test
    void putNonExistingTravail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        travail.setId(UUID.randomUUID().toString());

        // Create the Travail
        TravailDTO travailDTO = travailMapper.toDto(travail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTravailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, travailDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(travailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Travail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTravail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        travail.setId(UUID.randomUUID().toString());

        // Create the Travail
        TravailDTO travailDTO = travailMapper.toDto(travail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTravailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(travailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Travail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTravail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        travail.setId(UUID.randomUUID().toString());

        // Create the Travail
        TravailDTO travailDTO = travailMapper.toDto(travail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTravailMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(travailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Travail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTravailWithPatch() throws Exception {
        // Initialize the database
        insertedTravail = travailRepository.save(travail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the travail using partial update
        Travail partialUpdatedTravail = new Travail();
        partialUpdatedTravail.setId(travail.getId());

        partialUpdatedTravail.name(UPDATED_NAME);

        restTravailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTravail.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTravail))
            )
            .andExpect(status().isOk());

        // Validate the Travail in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTravailUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTravail, travail), getPersistedTravail(travail));
    }

    @Test
    void fullUpdateTravailWithPatch() throws Exception {
        // Initialize the database
        insertedTravail = travailRepository.save(travail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the travail using partial update
        Travail partialUpdatedTravail = new Travail();
        partialUpdatedTravail.setId(travail.getId());

        partialUpdatedTravail.name(UPDATED_NAME).label(UPDATED_LABEL);

        restTravailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTravail.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTravail))
            )
            .andExpect(status().isOk());

        // Validate the Travail in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTravailUpdatableFieldsEquals(partialUpdatedTravail, getPersistedTravail(partialUpdatedTravail));
    }

    @Test
    void patchNonExistingTravail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        travail.setId(UUID.randomUUID().toString());

        // Create the Travail
        TravailDTO travailDTO = travailMapper.toDto(travail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTravailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, travailDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(travailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Travail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTravail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        travail.setId(UUID.randomUUID().toString());

        // Create the Travail
        TravailDTO travailDTO = travailMapper.toDto(travail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTravailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(travailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Travail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTravail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        travail.setId(UUID.randomUUID().toString());

        // Create the Travail
        TravailDTO travailDTO = travailMapper.toDto(travail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTravailMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(travailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Travail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTravail() throws Exception {
        // Initialize the database
        insertedTravail = travailRepository.save(travail);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the travail
        restTravailMockMvc
            .perform(delete(ENTITY_API_URL_ID, travail.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return travailRepository.count();
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

    protected Travail getPersistedTravail(Travail travail) {
        return travailRepository.findById(travail.getId()).orElseThrow();
    }

    protected void assertPersistedTravailToMatchAllProperties(Travail expectedTravail) {
        assertTravailAllPropertiesEquals(expectedTravail, getPersistedTravail(expectedTravail));
    }

    protected void assertPersistedTravailToMatchUpdatableProperties(Travail expectedTravail) {
        assertTravailAllUpdatablePropertiesEquals(expectedTravail, getPersistedTravail(expectedTravail));
    }
}
