package com.gosoft.gobtp.web.rest;

import static com.gosoft.gobtp.domain.HoraireTravailAsserts.*;
import static com.gosoft.gobtp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gosoft.gobtp.IntegrationTest;
import com.gosoft.gobtp.domain.HoraireTravail;
import com.gosoft.gobtp.repository.HoraireTravailRepository;
import com.gosoft.gobtp.service.dto.HoraireTravailDTO;
import com.gosoft.gobtp.service.mapper.HoraireTravailMapper;
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
 * Integration tests for the {@link HoraireTravailResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HoraireTravailResourceIT {

    private static final Instant DEFAULT_DEBUT_MATIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEBUT_MATIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FIN_MATIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIN_MATIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DEBUT_SOIR = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEBUT_SOIR = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FIN_SOIR = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIN_SOIR = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_JOUR = "AAAAAAAAAA";
    private static final String UPDATED_JOUR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/horaire-travails";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HoraireTravailRepository horaireTravailRepository;

    @Autowired
    private HoraireTravailMapper horaireTravailMapper;

    @Autowired
    private MockMvc restHoraireTravailMockMvc;

    private HoraireTravail horaireTravail;

    private HoraireTravail insertedHoraireTravail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HoraireTravail createEntity() {
        return new HoraireTravail()
            .debutMatin(DEFAULT_DEBUT_MATIN)
            .finMatin(DEFAULT_FIN_MATIN)
            .debutSoir(DEFAULT_DEBUT_SOIR)
            .finSoir(DEFAULT_FIN_SOIR)
            .date(DEFAULT_DATE)
            .jour(DEFAULT_JOUR);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HoraireTravail createUpdatedEntity() {
        return new HoraireTravail()
            .debutMatin(UPDATED_DEBUT_MATIN)
            .finMatin(UPDATED_FIN_MATIN)
            .debutSoir(UPDATED_DEBUT_SOIR)
            .finSoir(UPDATED_FIN_SOIR)
            .date(UPDATED_DATE)
            .jour(UPDATED_JOUR);
    }

    @BeforeEach
    public void initTest() {
        horaireTravail = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedHoraireTravail != null) {
            horaireTravailRepository.delete(insertedHoraireTravail);
            insertedHoraireTravail = null;
        }
    }

    @Test
    void createHoraireTravail() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the HoraireTravail
        HoraireTravailDTO horaireTravailDTO = horaireTravailMapper.toDto(horaireTravail);
        var returnedHoraireTravailDTO = om.readValue(
            restHoraireTravailMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(horaireTravailDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            HoraireTravailDTO.class
        );

        // Validate the HoraireTravail in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedHoraireTravail = horaireTravailMapper.toEntity(returnedHoraireTravailDTO);
        assertHoraireTravailUpdatableFieldsEquals(returnedHoraireTravail, getPersistedHoraireTravail(returnedHoraireTravail));

        insertedHoraireTravail = returnedHoraireTravail;
    }

    @Test
    void createHoraireTravailWithExistingId() throws Exception {
        // Create the HoraireTravail with an existing ID
        horaireTravail.setId("existing_id");
        HoraireTravailDTO horaireTravailDTO = horaireTravailMapper.toDto(horaireTravail);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHoraireTravailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(horaireTravailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HoraireTravail in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        horaireTravail.setDate(null);

        // Create the HoraireTravail, which fails.
        HoraireTravailDTO horaireTravailDTO = horaireTravailMapper.toDto(horaireTravail);

        restHoraireTravailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(horaireTravailDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkJourIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        horaireTravail.setJour(null);

        // Create the HoraireTravail, which fails.
        HoraireTravailDTO horaireTravailDTO = horaireTravailMapper.toDto(horaireTravail);

        restHoraireTravailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(horaireTravailDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllHoraireTravails() throws Exception {
        // Initialize the database
        insertedHoraireTravail = horaireTravailRepository.save(horaireTravail);

        // Get all the horaireTravailList
        restHoraireTravailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horaireTravail.getId())))
            .andExpect(jsonPath("$.[*].debutMatin").value(hasItem(DEFAULT_DEBUT_MATIN.toString())))
            .andExpect(jsonPath("$.[*].finMatin").value(hasItem(DEFAULT_FIN_MATIN.toString())))
            .andExpect(jsonPath("$.[*].debutSoir").value(hasItem(DEFAULT_DEBUT_SOIR.toString())))
            .andExpect(jsonPath("$.[*].finSoir").value(hasItem(DEFAULT_FIN_SOIR.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].jour").value(hasItem(DEFAULT_JOUR)));
    }

    @Test
    void getHoraireTravail() throws Exception {
        // Initialize the database
        insertedHoraireTravail = horaireTravailRepository.save(horaireTravail);

        // Get the horaireTravail
        restHoraireTravailMockMvc
            .perform(get(ENTITY_API_URL_ID, horaireTravail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(horaireTravail.getId()))
            .andExpect(jsonPath("$.debutMatin").value(DEFAULT_DEBUT_MATIN.toString()))
            .andExpect(jsonPath("$.finMatin").value(DEFAULT_FIN_MATIN.toString()))
            .andExpect(jsonPath("$.debutSoir").value(DEFAULT_DEBUT_SOIR.toString()))
            .andExpect(jsonPath("$.finSoir").value(DEFAULT_FIN_SOIR.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.jour").value(DEFAULT_JOUR));
    }

    @Test
    void getNonExistingHoraireTravail() throws Exception {
        // Get the horaireTravail
        restHoraireTravailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingHoraireTravail() throws Exception {
        // Initialize the database
        insertedHoraireTravail = horaireTravailRepository.save(horaireTravail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the horaireTravail
        HoraireTravail updatedHoraireTravail = horaireTravailRepository.findById(horaireTravail.getId()).orElseThrow();
        updatedHoraireTravail
            .debutMatin(UPDATED_DEBUT_MATIN)
            .finMatin(UPDATED_FIN_MATIN)
            .debutSoir(UPDATED_DEBUT_SOIR)
            .finSoir(UPDATED_FIN_SOIR)
            .date(UPDATED_DATE)
            .jour(UPDATED_JOUR);
        HoraireTravailDTO horaireTravailDTO = horaireTravailMapper.toDto(updatedHoraireTravail);

        restHoraireTravailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, horaireTravailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(horaireTravailDTO))
            )
            .andExpect(status().isOk());

        // Validate the HoraireTravail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHoraireTravailToMatchAllProperties(updatedHoraireTravail);
    }

    @Test
    void putNonExistingHoraireTravail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        horaireTravail.setId(UUID.randomUUID().toString());

        // Create the HoraireTravail
        HoraireTravailDTO horaireTravailDTO = horaireTravailMapper.toDto(horaireTravail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoraireTravailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, horaireTravailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(horaireTravailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoraireTravail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchHoraireTravail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        horaireTravail.setId(UUID.randomUUID().toString());

        // Create the HoraireTravail
        HoraireTravailDTO horaireTravailDTO = horaireTravailMapper.toDto(horaireTravail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoraireTravailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(horaireTravailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoraireTravail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamHoraireTravail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        horaireTravail.setId(UUID.randomUUID().toString());

        // Create the HoraireTravail
        HoraireTravailDTO horaireTravailDTO = horaireTravailMapper.toDto(horaireTravail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoraireTravailMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(horaireTravailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HoraireTravail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateHoraireTravailWithPatch() throws Exception {
        // Initialize the database
        insertedHoraireTravail = horaireTravailRepository.save(horaireTravail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the horaireTravail using partial update
        HoraireTravail partialUpdatedHoraireTravail = new HoraireTravail();
        partialUpdatedHoraireTravail.setId(horaireTravail.getId());

        partialUpdatedHoraireTravail.finMatin(UPDATED_FIN_MATIN).debutSoir(UPDATED_DEBUT_SOIR).finSoir(UPDATED_FIN_SOIR);

        restHoraireTravailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHoraireTravail.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHoraireTravail))
            )
            .andExpect(status().isOk());

        // Validate the HoraireTravail in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHoraireTravailUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHoraireTravail, horaireTravail),
            getPersistedHoraireTravail(horaireTravail)
        );
    }

    @Test
    void fullUpdateHoraireTravailWithPatch() throws Exception {
        // Initialize the database
        insertedHoraireTravail = horaireTravailRepository.save(horaireTravail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the horaireTravail using partial update
        HoraireTravail partialUpdatedHoraireTravail = new HoraireTravail();
        partialUpdatedHoraireTravail.setId(horaireTravail.getId());

        partialUpdatedHoraireTravail
            .debutMatin(UPDATED_DEBUT_MATIN)
            .finMatin(UPDATED_FIN_MATIN)
            .debutSoir(UPDATED_DEBUT_SOIR)
            .finSoir(UPDATED_FIN_SOIR)
            .date(UPDATED_DATE)
            .jour(UPDATED_JOUR);

        restHoraireTravailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHoraireTravail.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHoraireTravail))
            )
            .andExpect(status().isOk());

        // Validate the HoraireTravail in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHoraireTravailUpdatableFieldsEquals(partialUpdatedHoraireTravail, getPersistedHoraireTravail(partialUpdatedHoraireTravail));
    }

    @Test
    void patchNonExistingHoraireTravail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        horaireTravail.setId(UUID.randomUUID().toString());

        // Create the HoraireTravail
        HoraireTravailDTO horaireTravailDTO = horaireTravailMapper.toDto(horaireTravail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoraireTravailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, horaireTravailDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(horaireTravailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoraireTravail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchHoraireTravail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        horaireTravail.setId(UUID.randomUUID().toString());

        // Create the HoraireTravail
        HoraireTravailDTO horaireTravailDTO = horaireTravailMapper.toDto(horaireTravail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoraireTravailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(horaireTravailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoraireTravail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamHoraireTravail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        horaireTravail.setId(UUID.randomUUID().toString());

        // Create the HoraireTravail
        HoraireTravailDTO horaireTravailDTO = horaireTravailMapper.toDto(horaireTravail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoraireTravailMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(horaireTravailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HoraireTravail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteHoraireTravail() throws Exception {
        // Initialize the database
        insertedHoraireTravail = horaireTravailRepository.save(horaireTravail);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the horaireTravail
        restHoraireTravailMockMvc
            .perform(delete(ENTITY_API_URL_ID, horaireTravail.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return horaireTravailRepository.count();
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

    protected HoraireTravail getPersistedHoraireTravail(HoraireTravail horaireTravail) {
        return horaireTravailRepository.findById(horaireTravail.getId()).orElseThrow();
    }

    protected void assertPersistedHoraireTravailToMatchAllProperties(HoraireTravail expectedHoraireTravail) {
        assertHoraireTravailAllPropertiesEquals(expectedHoraireTravail, getPersistedHoraireTravail(expectedHoraireTravail));
    }

    protected void assertPersistedHoraireTravailToMatchUpdatableProperties(HoraireTravail expectedHoraireTravail) {
        assertHoraireTravailAllUpdatablePropertiesEquals(expectedHoraireTravail, getPersistedHoraireTravail(expectedHoraireTravail));
    }
}
