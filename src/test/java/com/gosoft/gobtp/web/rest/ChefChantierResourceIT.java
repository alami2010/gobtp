package com.gosoft.gobtp.web.rest;

import static com.gosoft.gobtp.domain.ChefChantierAsserts.*;
import static com.gosoft.gobtp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gosoft.gobtp.IntegrationTest;
import com.gosoft.gobtp.domain.ChefChantier;
import com.gosoft.gobtp.domain.User;
import com.gosoft.gobtp.repository.ChefChantierRepository;
import com.gosoft.gobtp.repository.UserRepository;
import com.gosoft.gobtp.service.ChefChantierService;
import com.gosoft.gobtp.service.dto.ChefChantierDTO;
import com.gosoft.gobtp.service.mapper.ChefChantierMapper;
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
 * Integration tests for the {@link ChefChantierResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ChefChantierResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/chef-chantiers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ChefChantierRepository chefChantierRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private ChefChantierRepository chefChantierRepositoryMock;

    @Autowired
    private ChefChantierMapper chefChantierMapper;

    @Mock
    private ChefChantierService chefChantierServiceMock;

    @Autowired
    private MockMvc restChefChantierMockMvc;

    private ChefChantier chefChantier;

    private ChefChantier insertedChefChantier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChefChantier createEntity() {
        ChefChantier chefChantier = new ChefChantier().name(DEFAULT_NAME).email(DEFAULT_EMAIL).phone(DEFAULT_PHONE);
        // Add required entity
        User user = UserResourceIT.createEntity();
        user.setId("fixed-id-for-tests");
        chefChantier.setInternalUser(user);
        return chefChantier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChefChantier createUpdatedEntity() {
        ChefChantier updatedChefChantier = new ChefChantier().name(UPDATED_NAME).email(UPDATED_EMAIL).phone(UPDATED_PHONE);
        // Add required entity
        User user = UserResourceIT.createEntity();
        user.setId("fixed-id-for-tests");
        updatedChefChantier.setInternalUser(user);
        return updatedChefChantier;
    }

    @BeforeEach
    public void initTest() {
        chefChantier = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedChefChantier != null) {
            chefChantierRepository.delete(insertedChefChantier);
            insertedChefChantier = null;
        }
        userRepository.deleteAll();
    }

    @Test
    void createChefChantier() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ChefChantier
        ChefChantierDTO chefChantierDTO = chefChantierMapper.toDto(chefChantier);
        var returnedChefChantierDTO = om.readValue(
            restChefChantierMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chefChantierDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ChefChantierDTO.class
        );

        // Validate the ChefChantier in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedChefChantier = chefChantierMapper.toEntity(returnedChefChantierDTO);
        assertChefChantierUpdatableFieldsEquals(returnedChefChantier, getPersistedChefChantier(returnedChefChantier));

        assertChefChantierMapsIdRelationshipPersistedValue(chefChantier, returnedChefChantier);

        insertedChefChantier = returnedChefChantier;
    }

    @Test
    void createChefChantierWithExistingId() throws Exception {
        // Create the ChefChantier with an existing ID
        chefChantier.setId("existing_id");
        ChefChantierDTO chefChantierDTO = chefChantierMapper.toDto(chefChantier);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChefChantierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chefChantierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ChefChantier in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        chefChantier.setName(null);

        // Create the ChefChantier, which fails.
        ChefChantierDTO chefChantierDTO = chefChantierMapper.toDto(chefChantier);

        restChefChantierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chefChantierDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        chefChantier.setEmail(null);

        // Create the ChefChantier, which fails.
        ChefChantierDTO chefChantierDTO = chefChantierMapper.toDto(chefChantier);

        restChefChantierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chefChantierDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllChefChantiers() throws Exception {
        // Initialize the database
        insertedChefChantier = chefChantierRepository.save(chefChantier);

        // Get all the chefChantierList
        restChefChantierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chefChantier.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllChefChantiersWithEagerRelationshipsIsEnabled() throws Exception {
        when(chefChantierServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restChefChantierMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(chefChantierServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllChefChantiersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(chefChantierServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restChefChantierMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(chefChantierRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getChefChantier() throws Exception {
        // Initialize the database
        insertedChefChantier = chefChantierRepository.save(chefChantier);

        // Get the chefChantier
        restChefChantierMockMvc
            .perform(get(ENTITY_API_URL_ID, chefChantier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chefChantier.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    void getNonExistingChefChantier() throws Exception {
        // Get the chefChantier
        restChefChantierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingChefChantier() throws Exception {
        // Initialize the database
        insertedChefChantier = chefChantierRepository.save(chefChantier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the chefChantier
        ChefChantier updatedChefChantier = chefChantierRepository.findById(chefChantier.getId()).orElseThrow();
        updatedChefChantier.name(UPDATED_NAME).email(UPDATED_EMAIL).phone(UPDATED_PHONE);
        ChefChantierDTO chefChantierDTO = chefChantierMapper.toDto(updatedChefChantier);

        restChefChantierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chefChantierDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(chefChantierDTO))
            )
            .andExpect(status().isOk());

        // Validate the ChefChantier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedChefChantierToMatchAllProperties(updatedChefChantier);
    }

    @Test
    void putNonExistingChefChantier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chefChantier.setId(UUID.randomUUID().toString());

        // Create the ChefChantier
        ChefChantierDTO chefChantierDTO = chefChantierMapper.toDto(chefChantier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChefChantierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chefChantierDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(chefChantierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChefChantier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchChefChantier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chefChantier.setId(UUID.randomUUID().toString());

        // Create the ChefChantier
        ChefChantierDTO chefChantierDTO = chefChantierMapper.toDto(chefChantier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChefChantierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(chefChantierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChefChantier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamChefChantier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chefChantier.setId(UUID.randomUUID().toString());

        // Create the ChefChantier
        ChefChantierDTO chefChantierDTO = chefChantierMapper.toDto(chefChantier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChefChantierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chefChantierDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChefChantier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateChefChantierWithPatch() throws Exception {
        // Initialize the database
        insertedChefChantier = chefChantierRepository.save(chefChantier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the chefChantier using partial update
        ChefChantier partialUpdatedChefChantier = new ChefChantier();
        partialUpdatedChefChantier.setId(chefChantier.getId());

        partialUpdatedChefChantier.name(UPDATED_NAME).phone(UPDATED_PHONE);

        restChefChantierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChefChantier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedChefChantier))
            )
            .andExpect(status().isOk());

        // Validate the ChefChantier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertChefChantierUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedChefChantier, chefChantier),
            getPersistedChefChantier(chefChantier)
        );
    }

    @Test
    void fullUpdateChefChantierWithPatch() throws Exception {
        // Initialize the database
        insertedChefChantier = chefChantierRepository.save(chefChantier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the chefChantier using partial update
        ChefChantier partialUpdatedChefChantier = new ChefChantier();
        partialUpdatedChefChantier.setId(chefChantier.getId());

        partialUpdatedChefChantier.name(UPDATED_NAME).email(UPDATED_EMAIL).phone(UPDATED_PHONE);

        restChefChantierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChefChantier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedChefChantier))
            )
            .andExpect(status().isOk());

        // Validate the ChefChantier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertChefChantierUpdatableFieldsEquals(partialUpdatedChefChantier, getPersistedChefChantier(partialUpdatedChefChantier));
    }

    @Test
    void patchNonExistingChefChantier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chefChantier.setId(UUID.randomUUID().toString());

        // Create the ChefChantier
        ChefChantierDTO chefChantierDTO = chefChantierMapper.toDto(chefChantier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChefChantierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chefChantierDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(chefChantierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChefChantier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchChefChantier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chefChantier.setId(UUID.randomUUID().toString());

        // Create the ChefChantier
        ChefChantierDTO chefChantierDTO = chefChantierMapper.toDto(chefChantier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChefChantierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(chefChantierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChefChantier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamChefChantier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chefChantier.setId(UUID.randomUUID().toString());

        // Create the ChefChantier
        ChefChantierDTO chefChantierDTO = chefChantierMapper.toDto(chefChantier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChefChantierMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(chefChantierDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChefChantier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteChefChantier() throws Exception {
        // Initialize the database
        insertedChefChantier = chefChantierRepository.save(chefChantier);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the chefChantier
        restChefChantierMockMvc
            .perform(delete(ENTITY_API_URL_ID, chefChantier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return chefChantierRepository.count();
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

    protected ChefChantier getPersistedChefChantier(ChefChantier chefChantier) {
        return chefChantierRepository.findById(chefChantier.getId()).orElseThrow();
    }

    protected void assertPersistedChefChantierToMatchAllProperties(ChefChantier expectedChefChantier) {
        assertChefChantierAllPropertiesEquals(expectedChefChantier, getPersistedChefChantier(expectedChefChantier));
    }

    protected void assertPersistedChefChantierToMatchUpdatableProperties(ChefChantier expectedChefChantier) {
        assertChefChantierAllUpdatablePropertiesEquals(expectedChefChantier, getPersistedChefChantier(expectedChefChantier));
    }
}
