package com.gosoft.gobtp.web.rest;

import static com.gosoft.gobtp.domain.MateriauAsserts.*;
import static com.gosoft.gobtp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gosoft.gobtp.IntegrationTest;
import com.gosoft.gobtp.domain.Materiau;
import com.gosoft.gobtp.repository.MateriauRepository;
import com.gosoft.gobtp.service.dto.MateriauDTO;
import com.gosoft.gobtp.service.mapper.MateriauMapper;
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
 * Integration tests for the {@link MateriauResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MateriauResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/materiaus";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MateriauRepository materiauRepository;

    @Autowired
    private MateriauMapper materiauMapper;

    @Autowired
    private MockMvc restMateriauMockMvc;

    private Materiau materiau;

    private Materiau insertedMateriau;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Materiau createEntity() {
        return new Materiau().name(DEFAULT_NAME).date(DEFAULT_DATE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Materiau createUpdatedEntity() {
        return new Materiau().name(UPDATED_NAME).date(UPDATED_DATE);
    }

    @BeforeEach
    public void initTest() {
        materiau = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedMateriau != null) {
            materiauRepository.delete(insertedMateriau);
            insertedMateriau = null;
        }
    }

    @Test
    void createMateriau() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Materiau
        MateriauDTO materiauDTO = materiauMapper.toDto(materiau);
        var returnedMateriauDTO = om.readValue(
            restMateriauMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(materiauDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MateriauDTO.class
        );

        // Validate the Materiau in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMateriau = materiauMapper.toEntity(returnedMateriauDTO);
        assertMateriauUpdatableFieldsEquals(returnedMateriau, getPersistedMateriau(returnedMateriau));

        insertedMateriau = returnedMateriau;
    }

    @Test
    void createMateriauWithExistingId() throws Exception {
        // Create the Materiau with an existing ID
        materiau.setId("existing_id");
        MateriauDTO materiauDTO = materiauMapper.toDto(materiau);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMateriauMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(materiauDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Materiau in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        materiau.setName(null);

        // Create the Materiau, which fails.
        MateriauDTO materiauDTO = materiauMapper.toDto(materiau);

        restMateriauMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(materiauDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllMateriaus() throws Exception {
        // Initialize the database
        insertedMateriau = materiauRepository.save(materiau);

        // Get all the materiauList
        restMateriauMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materiau.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    void getMateriau() throws Exception {
        // Initialize the database
        insertedMateriau = materiauRepository.save(materiau);

        // Get the materiau
        restMateriauMockMvc
            .perform(get(ENTITY_API_URL_ID, materiau.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materiau.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    void getNonExistingMateriau() throws Exception {
        // Get the materiau
        restMateriauMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingMateriau() throws Exception {
        // Initialize the database
        insertedMateriau = materiauRepository.save(materiau);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the materiau
        Materiau updatedMateriau = materiauRepository.findById(materiau.getId()).orElseThrow();
        updatedMateriau.name(UPDATED_NAME).date(UPDATED_DATE);
        MateriauDTO materiauDTO = materiauMapper.toDto(updatedMateriau);

        restMateriauMockMvc
            .perform(
                put(ENTITY_API_URL_ID, materiauDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(materiauDTO))
            )
            .andExpect(status().isOk());

        // Validate the Materiau in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMateriauToMatchAllProperties(updatedMateriau);
    }

    @Test
    void putNonExistingMateriau() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materiau.setId(UUID.randomUUID().toString());

        // Create the Materiau
        MateriauDTO materiauDTO = materiauMapper.toDto(materiau);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMateriauMockMvc
            .perform(
                put(ENTITY_API_URL_ID, materiauDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(materiauDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materiau in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchMateriau() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materiau.setId(UUID.randomUUID().toString());

        // Create the Materiau
        MateriauDTO materiauDTO = materiauMapper.toDto(materiau);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriauMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(materiauDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materiau in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamMateriau() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materiau.setId(UUID.randomUUID().toString());

        // Create the Materiau
        MateriauDTO materiauDTO = materiauMapper.toDto(materiau);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriauMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(materiauDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Materiau in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateMateriauWithPatch() throws Exception {
        // Initialize the database
        insertedMateriau = materiauRepository.save(materiau);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the materiau using partial update
        Materiau partialUpdatedMateriau = new Materiau();
        partialUpdatedMateriau.setId(materiau.getId());

        partialUpdatedMateriau.name(UPDATED_NAME).date(UPDATED_DATE);

        restMateriauMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMateriau.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMateriau))
            )
            .andExpect(status().isOk());

        // Validate the Materiau in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMateriauUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedMateriau, materiau), getPersistedMateriau(materiau));
    }

    @Test
    void fullUpdateMateriauWithPatch() throws Exception {
        // Initialize the database
        insertedMateriau = materiauRepository.save(materiau);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the materiau using partial update
        Materiau partialUpdatedMateriau = new Materiau();
        partialUpdatedMateriau.setId(materiau.getId());

        partialUpdatedMateriau.name(UPDATED_NAME).date(UPDATED_DATE);

        restMateriauMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMateriau.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMateriau))
            )
            .andExpect(status().isOk());

        // Validate the Materiau in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMateriauUpdatableFieldsEquals(partialUpdatedMateriau, getPersistedMateriau(partialUpdatedMateriau));
    }

    @Test
    void patchNonExistingMateriau() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materiau.setId(UUID.randomUUID().toString());

        // Create the Materiau
        MateriauDTO materiauDTO = materiauMapper.toDto(materiau);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMateriauMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, materiauDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(materiauDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materiau in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchMateriau() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materiau.setId(UUID.randomUUID().toString());

        // Create the Materiau
        MateriauDTO materiauDTO = materiauMapper.toDto(materiau);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriauMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(materiauDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materiau in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamMateriau() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materiau.setId(UUID.randomUUID().toString());

        // Create the Materiau
        MateriauDTO materiauDTO = materiauMapper.toDto(materiau);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriauMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(materiauDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Materiau in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteMateriau() throws Exception {
        // Initialize the database
        insertedMateriau = materiauRepository.save(materiau);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the materiau
        restMateriauMockMvc
            .perform(delete(ENTITY_API_URL_ID, materiau.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return materiauRepository.count();
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

    protected Materiau getPersistedMateriau(Materiau materiau) {
        return materiauRepository.findById(materiau.getId()).orElseThrow();
    }

    protected void assertPersistedMateriauToMatchAllProperties(Materiau expectedMateriau) {
        assertMateriauAllPropertiesEquals(expectedMateriau, getPersistedMateriau(expectedMateriau));
    }

    protected void assertPersistedMateriauToMatchUpdatableProperties(Materiau expectedMateriau) {
        assertMateriauAllUpdatablePropertiesEquals(expectedMateriau, getPersistedMateriau(expectedMateriau));
    }
}
