package com.gosoft.gobtp.web.rest;

import static com.gosoft.gobtp.domain.ChantierAsserts.*;
import static com.gosoft.gobtp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gosoft.gobtp.IntegrationTest;
import com.gosoft.gobtp.domain.Chantier;
import com.gosoft.gobtp.repository.ChantierRepository;
import com.gosoft.gobtp.service.ChantierService;
import com.gosoft.gobtp.service.dto.ChantierDTO;
import com.gosoft.gobtp.service.mapper.ChantierMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ChantierResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ChantierResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/chantiers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ChantierRepository chantierRepository;

    @Mock
    private ChantierRepository chantierRepositoryMock;

    @Autowired
    private ChantierMapper chantierMapper;

    @Mock
    private ChantierService chantierServiceMock;

    @Autowired
    private MockMvc restChantierMockMvc;

    private Chantier chantier;

    private Chantier insertedChantier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chantier createEntity() {
        return new Chantier().name(DEFAULT_NAME).adresse(DEFAULT_ADRESSE).desc(DEFAULT_DESC).status(DEFAULT_STATUS).date(DEFAULT_DATE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chantier createUpdatedEntity() {
        return new Chantier().name(UPDATED_NAME).adresse(UPDATED_ADRESSE).desc(UPDATED_DESC).status(UPDATED_STATUS).date(UPDATED_DATE);
    }

    @BeforeEach
    public void initTest() {
        chantier = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedChantier != null) {
            chantierRepository.delete(insertedChantier);
            insertedChantier = null;
        }
    }

    @Test
    void createChantier() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Chantier
        ChantierDTO chantierDTO = chantierMapper.toDto(chantier);
        var returnedChantierDTO = om.readValue(
            restChantierMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chantierDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ChantierDTO.class
        );

        // Validate the Chantier in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedChantier = chantierMapper.toEntity(returnedChantierDTO);
        assertChantierUpdatableFieldsEquals(returnedChantier, getPersistedChantier(returnedChantier));

        insertedChantier = returnedChantier;
    }

    @Test
    void createChantierWithExistingId() throws Exception {
        // Create the Chantier with an existing ID
        chantier.setId("existing_id");
        ChantierDTO chantierDTO = chantierMapper.toDto(chantier);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChantierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chantierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chantier in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        chantier.setName(null);

        // Create the Chantier, which fails.
        ChantierDTO chantierDTO = chantierMapper.toDto(chantier);

        restChantierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chantierDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkAdresseIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        chantier.setAdresse(null);

        // Create the Chantier, which fails.
        ChantierDTO chantierDTO = chantierMapper.toDto(chantier);

        restChantierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chantierDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        chantier.setStatus(null);

        // Create the Chantier, which fails.
        ChantierDTO chantierDTO = chantierMapper.toDto(chantier);

        restChantierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chantierDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        chantier.setDate(null);

        // Create the Chantier, which fails.
        ChantierDTO chantierDTO = chantierMapper.toDto(chantier);

        restChantierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chantierDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllChantiers() throws Exception {
        // Initialize the database
        insertedChantier = chantierRepository.save(chantier);

        // Get all the chantierList
        restChantierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chantier.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllChantiersWithEagerRelationshipsIsEnabled() throws Exception {
        when(chantierServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restChantierMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(chantierServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllChantiersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(chantierServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restChantierMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(chantierRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getChantier() throws Exception {
        // Initialize the database
        insertedChantier = chantierRepository.save(chantier);

        // Get the chantier
        restChantierMockMvc
            .perform(get(ENTITY_API_URL_ID, chantier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chantier.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    void getNonExistingChantier() throws Exception {
        // Get the chantier
        restChantierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingChantier() throws Exception {
        // Initialize the database
        insertedChantier = chantierRepository.save(chantier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the chantier
        Chantier updatedChantier = chantierRepository.findById(chantier.getId()).orElseThrow();
        updatedChantier.name(UPDATED_NAME).adresse(UPDATED_ADRESSE).desc(UPDATED_DESC).status(UPDATED_STATUS).date(UPDATED_DATE);
        ChantierDTO chantierDTO = chantierMapper.toDto(updatedChantier);

        restChantierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chantierDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(chantierDTO))
            )
            .andExpect(status().isOk());

        // Validate the Chantier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedChantierToMatchAllProperties(updatedChantier);
    }

    @Test
    void putNonExistingChantier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chantier.setId(UUID.randomUUID().toString());

        // Create the Chantier
        ChantierDTO chantierDTO = chantierMapper.toDto(chantier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChantierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chantierDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(chantierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Chantier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchChantier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chantier.setId(UUID.randomUUID().toString());

        // Create the Chantier
        ChantierDTO chantierDTO = chantierMapper.toDto(chantier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChantierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(chantierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Chantier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamChantier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chantier.setId(UUID.randomUUID().toString());

        // Create the Chantier
        ChantierDTO chantierDTO = chantierMapper.toDto(chantier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChantierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chantierDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Chantier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateChantierWithPatch() throws Exception {
        // Initialize the database
        insertedChantier = chantierRepository.save(chantier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the chantier using partial update
        Chantier partialUpdatedChantier = new Chantier();
        partialUpdatedChantier.setId(chantier.getId());

        partialUpdatedChantier.name(UPDATED_NAME).desc(UPDATED_DESC).date(UPDATED_DATE);

        restChantierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChantier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedChantier))
            )
            .andExpect(status().isOk());

        // Validate the Chantier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertChantierUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedChantier, chantier), getPersistedChantier(chantier));
    }

    @Test
    void fullUpdateChantierWithPatch() throws Exception {
        // Initialize the database
        insertedChantier = chantierRepository.save(chantier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the chantier using partial update
        Chantier partialUpdatedChantier = new Chantier();
        partialUpdatedChantier.setId(chantier.getId());

        partialUpdatedChantier.name(UPDATED_NAME).adresse(UPDATED_ADRESSE).desc(UPDATED_DESC).status(UPDATED_STATUS).date(UPDATED_DATE);

        restChantierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChantier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedChantier))
            )
            .andExpect(status().isOk());

        // Validate the Chantier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertChantierUpdatableFieldsEquals(partialUpdatedChantier, getPersistedChantier(partialUpdatedChantier));
    }

    @Test
    void patchNonExistingChantier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chantier.setId(UUID.randomUUID().toString());

        // Create the Chantier
        ChantierDTO chantierDTO = chantierMapper.toDto(chantier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChantierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chantierDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(chantierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Chantier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchChantier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chantier.setId(UUID.randomUUID().toString());

        // Create the Chantier
        ChantierDTO chantierDTO = chantierMapper.toDto(chantier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChantierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(chantierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Chantier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamChantier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chantier.setId(UUID.randomUUID().toString());

        // Create the Chantier
        ChantierDTO chantierDTO = chantierMapper.toDto(chantier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChantierMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(chantierDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Chantier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteChantier() throws Exception {
        // Initialize the database
        insertedChantier = chantierRepository.save(chantier);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the chantier
        restChantierMockMvc
            .perform(delete(ENTITY_API_URL_ID, chantier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return chantierRepository.count();
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

    protected Chantier getPersistedChantier(Chantier chantier) {
        return chantierRepository.findById(chantier.getId()).orElseThrow();
    }

    protected void assertPersistedChantierToMatchAllProperties(Chantier expectedChantier) {
        assertChantierAllPropertiesEquals(expectedChantier, getPersistedChantier(expectedChantier));
    }

    protected void assertPersistedChantierToMatchUpdatableProperties(Chantier expectedChantier) {
        assertChantierAllUpdatablePropertiesEquals(expectedChantier, getPersistedChantier(expectedChantier));
    }
}
