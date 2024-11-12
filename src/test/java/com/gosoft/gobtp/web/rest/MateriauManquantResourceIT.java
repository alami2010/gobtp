package com.gosoft.gobtp.web.rest;

import static com.gosoft.gobtp.domain.MateriauManquantAsserts.*;
import static com.gosoft.gobtp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gosoft.gobtp.IntegrationTest;
import com.gosoft.gobtp.domain.MateriauManquant;
import com.gosoft.gobtp.repository.MateriauManquantRepository;
import com.gosoft.gobtp.service.dto.MateriauManquantDTO;
import com.gosoft.gobtp.service.mapper.MateriauManquantMapper;
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
 * Integration tests for the {@link MateriauManquantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MateriauManquantResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/materiau-manquants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MateriauManquantRepository materiauManquantRepository;

    @Autowired
    private MateriauManquantMapper materiauManquantMapper;

    @Autowired
    private MockMvc restMateriauManquantMockMvc;

    private MateriauManquant materiauManquant;

    private MateriauManquant insertedMateriauManquant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MateriauManquant createEntity() {
        return new MateriauManquant().name(DEFAULT_NAME).quantity(DEFAULT_QUANTITY).date(DEFAULT_DATE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MateriauManquant createUpdatedEntity() {
        return new MateriauManquant().name(UPDATED_NAME).quantity(UPDATED_QUANTITY).date(UPDATED_DATE);
    }

    @BeforeEach
    public void initTest() {
        materiauManquant = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedMateriauManquant != null) {
            materiauManquantRepository.delete(insertedMateriauManquant);
            insertedMateriauManquant = null;
        }
    }

    @Test
    void createMateriauManquant() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MateriauManquant
        MateriauManquantDTO materiauManquantDTO = materiauManquantMapper.toDto(materiauManquant);
        var returnedMateriauManquantDTO = om.readValue(
            restMateriauManquantMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(materiauManquantDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MateriauManquantDTO.class
        );

        // Validate the MateriauManquant in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMateriauManquant = materiauManquantMapper.toEntity(returnedMateriauManquantDTO);
        assertMateriauManquantUpdatableFieldsEquals(returnedMateriauManquant, getPersistedMateriauManquant(returnedMateriauManquant));

        insertedMateriauManquant = returnedMateriauManquant;
    }

    @Test
    void createMateriauManquantWithExistingId() throws Exception {
        // Create the MateriauManquant with an existing ID
        materiauManquant.setId("existing_id");
        MateriauManquantDTO materiauManquantDTO = materiauManquantMapper.toDto(materiauManquant);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMateriauManquantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(materiauManquantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MateriauManquant in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        materiauManquant.setName(null);

        // Create the MateriauManquant, which fails.
        MateriauManquantDTO materiauManquantDTO = materiauManquantMapper.toDto(materiauManquant);

        restMateriauManquantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(materiauManquantDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkQuantityIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        materiauManquant.setQuantity(null);

        // Create the MateriauManquant, which fails.
        MateriauManquantDTO materiauManquantDTO = materiauManquantMapper.toDto(materiauManquant);

        restMateriauManquantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(materiauManquantDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        materiauManquant.setDate(null);

        // Create the MateriauManquant, which fails.
        MateriauManquantDTO materiauManquantDTO = materiauManquantMapper.toDto(materiauManquant);

        restMateriauManquantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(materiauManquantDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllMateriauManquants() throws Exception {
        // Initialize the database
        insertedMateriauManquant = materiauManquantRepository.save(materiauManquant);

        // Get all the materiauManquantList
        restMateriauManquantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materiauManquant.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    void getMateriauManquant() throws Exception {
        // Initialize the database
        insertedMateriauManquant = materiauManquantRepository.save(materiauManquant);

        // Get the materiauManquant
        restMateriauManquantMockMvc
            .perform(get(ENTITY_API_URL_ID, materiauManquant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materiauManquant.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    void getNonExistingMateriauManquant() throws Exception {
        // Get the materiauManquant
        restMateriauManquantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingMateriauManquant() throws Exception {
        // Initialize the database
        insertedMateriauManquant = materiauManquantRepository.save(materiauManquant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the materiauManquant
        MateriauManquant updatedMateriauManquant = materiauManquantRepository.findById(materiauManquant.getId()).orElseThrow();
        updatedMateriauManquant.name(UPDATED_NAME).quantity(UPDATED_QUANTITY).date(UPDATED_DATE);
        MateriauManquantDTO materiauManquantDTO = materiauManquantMapper.toDto(updatedMateriauManquant);

        restMateriauManquantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, materiauManquantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(materiauManquantDTO))
            )
            .andExpect(status().isOk());

        // Validate the MateriauManquant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMateriauManquantToMatchAllProperties(updatedMateriauManquant);
    }

    @Test
    void putNonExistingMateriauManquant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materiauManquant.setId(UUID.randomUUID().toString());

        // Create the MateriauManquant
        MateriauManquantDTO materiauManquantDTO = materiauManquantMapper.toDto(materiauManquant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMateriauManquantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, materiauManquantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(materiauManquantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MateriauManquant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchMateriauManquant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materiauManquant.setId(UUID.randomUUID().toString());

        // Create the MateriauManquant
        MateriauManquantDTO materiauManquantDTO = materiauManquantMapper.toDto(materiauManquant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriauManquantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(materiauManquantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MateriauManquant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamMateriauManquant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materiauManquant.setId(UUID.randomUUID().toString());

        // Create the MateriauManquant
        MateriauManquantDTO materiauManquantDTO = materiauManquantMapper.toDto(materiauManquant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriauManquantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(materiauManquantDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MateriauManquant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateMateriauManquantWithPatch() throws Exception {
        // Initialize the database
        insertedMateriauManquant = materiauManquantRepository.save(materiauManquant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the materiauManquant using partial update
        MateriauManquant partialUpdatedMateriauManquant = new MateriauManquant();
        partialUpdatedMateriauManquant.setId(materiauManquant.getId());

        partialUpdatedMateriauManquant.quantity(UPDATED_QUANTITY);

        restMateriauManquantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMateriauManquant.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMateriauManquant))
            )
            .andExpect(status().isOk());

        // Validate the MateriauManquant in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMateriauManquantUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMateriauManquant, materiauManquant),
            getPersistedMateriauManquant(materiauManquant)
        );
    }

    @Test
    void fullUpdateMateriauManquantWithPatch() throws Exception {
        // Initialize the database
        insertedMateriauManquant = materiauManquantRepository.save(materiauManquant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the materiauManquant using partial update
        MateriauManquant partialUpdatedMateriauManquant = new MateriauManquant();
        partialUpdatedMateriauManquant.setId(materiauManquant.getId());

        partialUpdatedMateriauManquant.name(UPDATED_NAME).quantity(UPDATED_QUANTITY).date(UPDATED_DATE);

        restMateriauManquantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMateriauManquant.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMateriauManquant))
            )
            .andExpect(status().isOk());

        // Validate the MateriauManquant in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMateriauManquantUpdatableFieldsEquals(
            partialUpdatedMateriauManquant,
            getPersistedMateriauManquant(partialUpdatedMateriauManquant)
        );
    }

    @Test
    void patchNonExistingMateriauManquant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materiauManquant.setId(UUID.randomUUID().toString());

        // Create the MateriauManquant
        MateriauManquantDTO materiauManquantDTO = materiauManquantMapper.toDto(materiauManquant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMateriauManquantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, materiauManquantDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(materiauManquantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MateriauManquant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchMateriauManquant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materiauManquant.setId(UUID.randomUUID().toString());

        // Create the MateriauManquant
        MateriauManquantDTO materiauManquantDTO = materiauManquantMapper.toDto(materiauManquant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriauManquantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(materiauManquantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MateriauManquant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamMateriauManquant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materiauManquant.setId(UUID.randomUUID().toString());

        // Create the MateriauManquant
        MateriauManquantDTO materiauManquantDTO = materiauManquantMapper.toDto(materiauManquant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriauManquantMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(materiauManquantDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MateriauManquant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteMateriauManquant() throws Exception {
        // Initialize the database
        insertedMateriauManquant = materiauManquantRepository.save(materiauManquant);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the materiauManquant
        restMateriauManquantMockMvc
            .perform(delete(ENTITY_API_URL_ID, materiauManquant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return materiauManquantRepository.count();
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

    protected MateriauManquant getPersistedMateriauManquant(MateriauManquant materiauManquant) {
        return materiauManquantRepository.findById(materiauManquant.getId()).orElseThrow();
    }

    protected void assertPersistedMateriauManquantToMatchAllProperties(MateriauManquant expectedMateriauManquant) {
        assertMateriauManquantAllPropertiesEquals(expectedMateriauManquant, getPersistedMateriauManquant(expectedMateriauManquant));
    }

    protected void assertPersistedMateriauManquantToMatchUpdatableProperties(MateriauManquant expectedMateriauManquant) {
        assertMateriauManquantAllUpdatablePropertiesEquals(
            expectedMateriauManquant,
            getPersistedMateriauManquant(expectedMateriauManquant)
        );
    }
}
