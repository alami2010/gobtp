package com.gosoft.gobtp.web.rest;

import static com.gosoft.gobtp.domain.OuvrierAsserts.*;
import static com.gosoft.gobtp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gosoft.gobtp.IntegrationTest;
import com.gosoft.gobtp.domain.Ouvrier;
import com.gosoft.gobtp.domain.User;
import com.gosoft.gobtp.domain.enumeration.TypeOuvrier;
import com.gosoft.gobtp.repository.OuvrierRepository;
import com.gosoft.gobtp.repository.UserRepository;
import com.gosoft.gobtp.service.OuvrierService;
import com.gosoft.gobtp.service.dto.OuvrierDTO;
import com.gosoft.gobtp.service.mapper.OuvrierMapper;
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
 * Integration tests for the {@link OuvrierResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class OuvrierResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final TypeOuvrier DEFAULT_TYPE = TypeOuvrier.Ouvrier;
    private static final TypeOuvrier UPDATED_TYPE = TypeOuvrier.Manoeuvre;

    private static final String ENTITY_API_URL = "/api/ouvriers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OuvrierRepository ouvrierRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private OuvrierRepository ouvrierRepositoryMock;

    @Autowired
    private OuvrierMapper ouvrierMapper;

    @Mock
    private OuvrierService ouvrierServiceMock;

    @Autowired
    private MockMvc restOuvrierMockMvc;

    private Ouvrier ouvrier;

    private Ouvrier insertedOuvrier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ouvrier createEntity() {
        Ouvrier ouvrier = new Ouvrier().name(DEFAULT_NAME).type(DEFAULT_TYPE);
        // Add required entity
        User user = UserResourceIT.createEntity();
        user.setId("fixed-id-for-tests");
        ouvrier.setInternalUser(user);
        return ouvrier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ouvrier createUpdatedEntity() {
        Ouvrier updatedOuvrier = new Ouvrier().name(UPDATED_NAME).type(UPDATED_TYPE);
        // Add required entity
        User user = UserResourceIT.createEntity();
        user.setId("fixed-id-for-tests");
        updatedOuvrier.setInternalUser(user);
        return updatedOuvrier;
    }

    @BeforeEach
    public void initTest() {
        ouvrier = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedOuvrier != null) {
            ouvrierRepository.delete(insertedOuvrier);
            insertedOuvrier = null;
        }
        userRepository.deleteAll();
    }

    @Test
    void createOuvrier() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ouvrier
        OuvrierDTO ouvrierDTO = ouvrierMapper.toDto(ouvrier);
        var returnedOuvrierDTO = om.readValue(
            restOuvrierMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ouvrierDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OuvrierDTO.class
        );

        // Validate the Ouvrier in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedOuvrier = ouvrierMapper.toEntity(returnedOuvrierDTO);
        assertOuvrierUpdatableFieldsEquals(returnedOuvrier, getPersistedOuvrier(returnedOuvrier));

        assertOuvrierMapsIdRelationshipPersistedValue(ouvrier, returnedOuvrier);

        insertedOuvrier = returnedOuvrier;
    }

    @Test
    void createOuvrierWithExistingId() throws Exception {
        // Create the Ouvrier with an existing ID
        ouvrier.setId("existing_id");
        OuvrierDTO ouvrierDTO = ouvrierMapper.toDto(ouvrier);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOuvrierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ouvrierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ouvrier in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ouvrier.setName(null);

        // Create the Ouvrier, which fails.
        OuvrierDTO ouvrierDTO = ouvrierMapper.toDto(ouvrier);

        restOuvrierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ouvrierDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ouvrier.setType(null);

        // Create the Ouvrier, which fails.
        OuvrierDTO ouvrierDTO = ouvrierMapper.toDto(ouvrier);

        restOuvrierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ouvrierDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllOuvriers() throws Exception {
        // Initialize the database
        insertedOuvrier = ouvrierRepository.save(ouvrier);

        // Get all the ouvrierList
        restOuvrierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ouvrier.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOuvriersWithEagerRelationshipsIsEnabled() throws Exception {
        when(ouvrierServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOuvrierMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(ouvrierServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOuvriersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(ouvrierServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOuvrierMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(ouvrierRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getOuvrier() throws Exception {
        // Initialize the database
        insertedOuvrier = ouvrierRepository.save(ouvrier);

        // Get the ouvrier
        restOuvrierMockMvc
            .perform(get(ENTITY_API_URL_ID, ouvrier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ouvrier.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    void getNonExistingOuvrier() throws Exception {
        // Get the ouvrier
        restOuvrierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingOuvrier() throws Exception {
        // Initialize the database
        insertedOuvrier = ouvrierRepository.save(ouvrier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ouvrier
        Ouvrier updatedOuvrier = ouvrierRepository.findById(ouvrier.getId()).orElseThrow();
        updatedOuvrier.name(UPDATED_NAME).type(UPDATED_TYPE);
        OuvrierDTO ouvrierDTO = ouvrierMapper.toDto(updatedOuvrier);

        restOuvrierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ouvrierDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ouvrierDTO))
            )
            .andExpect(status().isOk());

        // Validate the Ouvrier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOuvrierToMatchAllProperties(updatedOuvrier);
    }

    @Test
    void putNonExistingOuvrier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ouvrier.setId(UUID.randomUUID().toString());

        // Create the Ouvrier
        OuvrierDTO ouvrierDTO = ouvrierMapper.toDto(ouvrier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOuvrierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ouvrierDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ouvrierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ouvrier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchOuvrier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ouvrier.setId(UUID.randomUUID().toString());

        // Create the Ouvrier
        OuvrierDTO ouvrierDTO = ouvrierMapper.toDto(ouvrier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOuvrierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ouvrierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ouvrier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamOuvrier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ouvrier.setId(UUID.randomUUID().toString());

        // Create the Ouvrier
        OuvrierDTO ouvrierDTO = ouvrierMapper.toDto(ouvrier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOuvrierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ouvrierDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ouvrier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateOuvrierWithPatch() throws Exception {
        // Initialize the database
        insertedOuvrier = ouvrierRepository.save(ouvrier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ouvrier using partial update
        Ouvrier partialUpdatedOuvrier = new Ouvrier();
        partialUpdatedOuvrier.setId(ouvrier.getId());

        restOuvrierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOuvrier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOuvrier))
            )
            .andExpect(status().isOk());

        // Validate the Ouvrier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOuvrierUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedOuvrier, ouvrier), getPersistedOuvrier(ouvrier));
    }

    @Test
    void fullUpdateOuvrierWithPatch() throws Exception {
        // Initialize the database
        insertedOuvrier = ouvrierRepository.save(ouvrier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ouvrier using partial update
        Ouvrier partialUpdatedOuvrier = new Ouvrier();
        partialUpdatedOuvrier.setId(ouvrier.getId());

        partialUpdatedOuvrier.name(UPDATED_NAME).type(UPDATED_TYPE);

        restOuvrierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOuvrier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOuvrier))
            )
            .andExpect(status().isOk());

        // Validate the Ouvrier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOuvrierUpdatableFieldsEquals(partialUpdatedOuvrier, getPersistedOuvrier(partialUpdatedOuvrier));
    }

    @Test
    void patchNonExistingOuvrier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ouvrier.setId(UUID.randomUUID().toString());

        // Create the Ouvrier
        OuvrierDTO ouvrierDTO = ouvrierMapper.toDto(ouvrier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOuvrierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ouvrierDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ouvrierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ouvrier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchOuvrier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ouvrier.setId(UUID.randomUUID().toString());

        // Create the Ouvrier
        OuvrierDTO ouvrierDTO = ouvrierMapper.toDto(ouvrier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOuvrierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ouvrierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ouvrier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamOuvrier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ouvrier.setId(UUID.randomUUID().toString());

        // Create the Ouvrier
        OuvrierDTO ouvrierDTO = ouvrierMapper.toDto(ouvrier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOuvrierMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ouvrierDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ouvrier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteOuvrier() throws Exception {
        // Initialize the database
        insertedOuvrier = ouvrierRepository.save(ouvrier);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ouvrier
        restOuvrierMockMvc
            .perform(delete(ENTITY_API_URL_ID, ouvrier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ouvrierRepository.count();
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

    protected Ouvrier getPersistedOuvrier(Ouvrier ouvrier) {
        return ouvrierRepository.findById(ouvrier.getId()).orElseThrow();
    }

    protected void assertPersistedOuvrierToMatchAllProperties(Ouvrier expectedOuvrier) {
        assertOuvrierAllPropertiesEquals(expectedOuvrier, getPersistedOuvrier(expectedOuvrier));
    }

    protected void assertPersistedOuvrierToMatchUpdatableProperties(Ouvrier expectedOuvrier) {
        assertOuvrierAllUpdatablePropertiesEquals(expectedOuvrier, getPersistedOuvrier(expectedOuvrier));
    }
}
