package com.gosoft.gobtp.web.rest;

import static com.gosoft.gobtp.domain.DocumentFinancierAsserts.*;
import static com.gosoft.gobtp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gosoft.gobtp.IntegrationTest;
import com.gosoft.gobtp.domain.DocumentFinancier;
import com.gosoft.gobtp.repository.DocumentFinancierRepository;
import com.gosoft.gobtp.service.dto.DocumentFinancierDTO;
import com.gosoft.gobtp.service.mapper.DocumentFinancierMapper;
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
 * Integration tests for the {@link DocumentFinancierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocumentFinancierResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_FILE = "AAAAAAAAAA";
    private static final String UPDATED_FILE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/document-financiers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DocumentFinancierRepository documentFinancierRepository;

    @Autowired
    private DocumentFinancierMapper documentFinancierMapper;

    @Autowired
    private MockMvc restDocumentFinancierMockMvc;

    private DocumentFinancier documentFinancier;

    private DocumentFinancier insertedDocumentFinancier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentFinancier createEntity() {
        return new DocumentFinancier().nom(DEFAULT_NOM).file(DEFAULT_FILE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentFinancier createUpdatedEntity() {
        return new DocumentFinancier().nom(UPDATED_NOM).file(UPDATED_FILE);
    }

    @BeforeEach
    public void initTest() {
        documentFinancier = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedDocumentFinancier != null) {
            documentFinancierRepository.delete(insertedDocumentFinancier);
            insertedDocumentFinancier = null;
        }
    }

    @Test
    void createDocumentFinancier() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DocumentFinancier
        DocumentFinancierDTO documentFinancierDTO = documentFinancierMapper.toDto(documentFinancier);
        var returnedDocumentFinancierDTO = om.readValue(
            restDocumentFinancierMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentFinancierDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DocumentFinancierDTO.class
        );

        // Validate the DocumentFinancier in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDocumentFinancier = documentFinancierMapper.toEntity(returnedDocumentFinancierDTO);
        assertDocumentFinancierUpdatableFieldsEquals(returnedDocumentFinancier, getPersistedDocumentFinancier(returnedDocumentFinancier));

        insertedDocumentFinancier = returnedDocumentFinancier;
    }

    @Test
    void createDocumentFinancierWithExistingId() throws Exception {
        // Create the DocumentFinancier with an existing ID
        documentFinancier.setId("existing_id");
        DocumentFinancierDTO documentFinancierDTO = documentFinancierMapper.toDto(documentFinancier);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentFinancierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentFinancierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentFinancier in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNomIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        documentFinancier.setNom(null);

        // Create the DocumentFinancier, which fails.
        DocumentFinancierDTO documentFinancierDTO = documentFinancierMapper.toDto(documentFinancier);

        restDocumentFinancierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentFinancierDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkFileIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        documentFinancier.setFile(null);

        // Create the DocumentFinancier, which fails.
        DocumentFinancierDTO documentFinancierDTO = documentFinancierMapper.toDto(documentFinancier);

        restDocumentFinancierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentFinancierDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllDocumentFinanciers() throws Exception {
        // Initialize the database
        insertedDocumentFinancier = documentFinancierRepository.save(documentFinancier);

        // Get all the documentFinancierList
        restDocumentFinancierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentFinancier.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(DEFAULT_FILE)));
    }

    @Test
    void getDocumentFinancier() throws Exception {
        // Initialize the database
        insertedDocumentFinancier = documentFinancierRepository.save(documentFinancier);

        // Get the documentFinancier
        restDocumentFinancierMockMvc
            .perform(get(ENTITY_API_URL_ID, documentFinancier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentFinancier.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.file").value(DEFAULT_FILE));
    }

    @Test
    void getNonExistingDocumentFinancier() throws Exception {
        // Get the documentFinancier
        restDocumentFinancierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingDocumentFinancier() throws Exception {
        // Initialize the database
        insertedDocumentFinancier = documentFinancierRepository.save(documentFinancier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentFinancier
        DocumentFinancier updatedDocumentFinancier = documentFinancierRepository.findById(documentFinancier.getId()).orElseThrow();
        updatedDocumentFinancier.nom(UPDATED_NOM).file(UPDATED_FILE);
        DocumentFinancierDTO documentFinancierDTO = documentFinancierMapper.toDto(updatedDocumentFinancier);

        restDocumentFinancierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentFinancierDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentFinancierDTO))
            )
            .andExpect(status().isOk());

        // Validate the DocumentFinancier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDocumentFinancierToMatchAllProperties(updatedDocumentFinancier);
    }

    @Test
    void putNonExistingDocumentFinancier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentFinancier.setId(UUID.randomUUID().toString());

        // Create the DocumentFinancier
        DocumentFinancierDTO documentFinancierDTO = documentFinancierMapper.toDto(documentFinancier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentFinancierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentFinancierDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentFinancierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentFinancier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDocumentFinancier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentFinancier.setId(UUID.randomUUID().toString());

        // Create the DocumentFinancier
        DocumentFinancierDTO documentFinancierDTO = documentFinancierMapper.toDto(documentFinancier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentFinancierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentFinancierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentFinancier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDocumentFinancier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentFinancier.setId(UUID.randomUUID().toString());

        // Create the DocumentFinancier
        DocumentFinancierDTO documentFinancierDTO = documentFinancierMapper.toDto(documentFinancier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentFinancierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentFinancierDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentFinancier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDocumentFinancierWithPatch() throws Exception {
        // Initialize the database
        insertedDocumentFinancier = documentFinancierRepository.save(documentFinancier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentFinancier using partial update
        DocumentFinancier partialUpdatedDocumentFinancier = new DocumentFinancier();
        partialUpdatedDocumentFinancier.setId(documentFinancier.getId());

        partialUpdatedDocumentFinancier.file(UPDATED_FILE);

        restDocumentFinancierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentFinancier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocumentFinancier))
            )
            .andExpect(status().isOk());

        // Validate the DocumentFinancier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumentFinancierUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDocumentFinancier, documentFinancier),
            getPersistedDocumentFinancier(documentFinancier)
        );
    }

    @Test
    void fullUpdateDocumentFinancierWithPatch() throws Exception {
        // Initialize the database
        insertedDocumentFinancier = documentFinancierRepository.save(documentFinancier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentFinancier using partial update
        DocumentFinancier partialUpdatedDocumentFinancier = new DocumentFinancier();
        partialUpdatedDocumentFinancier.setId(documentFinancier.getId());

        partialUpdatedDocumentFinancier.nom(UPDATED_NOM).file(UPDATED_FILE);

        restDocumentFinancierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentFinancier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocumentFinancier))
            )
            .andExpect(status().isOk());

        // Validate the DocumentFinancier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumentFinancierUpdatableFieldsEquals(
            partialUpdatedDocumentFinancier,
            getPersistedDocumentFinancier(partialUpdatedDocumentFinancier)
        );
    }

    @Test
    void patchNonExistingDocumentFinancier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentFinancier.setId(UUID.randomUUID().toString());

        // Create the DocumentFinancier
        DocumentFinancierDTO documentFinancierDTO = documentFinancierMapper.toDto(documentFinancier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentFinancierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documentFinancierDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(documentFinancierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentFinancier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDocumentFinancier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentFinancier.setId(UUID.randomUUID().toString());

        // Create the DocumentFinancier
        DocumentFinancierDTO documentFinancierDTO = documentFinancierMapper.toDto(documentFinancier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentFinancierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(documentFinancierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentFinancier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDocumentFinancier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentFinancier.setId(UUID.randomUUID().toString());

        // Create the DocumentFinancier
        DocumentFinancierDTO documentFinancierDTO = documentFinancierMapper.toDto(documentFinancier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentFinancierMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(documentFinancierDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentFinancier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDocumentFinancier() throws Exception {
        // Initialize the database
        insertedDocumentFinancier = documentFinancierRepository.save(documentFinancier);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the documentFinancier
        restDocumentFinancierMockMvc
            .perform(delete(ENTITY_API_URL_ID, documentFinancier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return documentFinancierRepository.count();
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

    protected DocumentFinancier getPersistedDocumentFinancier(DocumentFinancier documentFinancier) {
        return documentFinancierRepository.findById(documentFinancier.getId()).orElseThrow();
    }

    protected void assertPersistedDocumentFinancierToMatchAllProperties(DocumentFinancier expectedDocumentFinancier) {
        assertDocumentFinancierAllPropertiesEquals(expectedDocumentFinancier, getPersistedDocumentFinancier(expectedDocumentFinancier));
    }

    protected void assertPersistedDocumentFinancierToMatchUpdatableProperties(DocumentFinancier expectedDocumentFinancier) {
        assertDocumentFinancierAllUpdatablePropertiesEquals(
            expectedDocumentFinancier,
            getPersistedDocumentFinancier(expectedDocumentFinancier)
        );
    }
}
