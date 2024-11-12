package com.gosoft.gobtp.web.rest;

import static com.gosoft.gobtp.domain.PhotoTravailAsserts.*;
import static com.gosoft.gobtp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gosoft.gobtp.IntegrationTest;
import com.gosoft.gobtp.domain.PhotoTravail;
import com.gosoft.gobtp.repository.PhotoTravailRepository;
import com.gosoft.gobtp.service.dto.PhotoTravailDTO;
import com.gosoft.gobtp.service.mapper.PhotoTravailMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
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
 * Integration tests for the {@link PhotoTravailResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PhotoTravailResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/photo-travails";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PhotoTravailRepository photoTravailRepository;

    @Autowired
    private PhotoTravailMapper photoTravailMapper;

    @Autowired
    private MockMvc restPhotoTravailMockMvc;

    private PhotoTravail photoTravail;

    private PhotoTravail insertedPhotoTravail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhotoTravail createEntity() {
        return new PhotoTravail()
            .description(DEFAULT_DESCRIPTION)
            .date(DEFAULT_DATE)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhotoTravail createUpdatedEntity() {
        return new PhotoTravail()
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @BeforeEach
    public void initTest() {
        photoTravail = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedPhotoTravail != null) {
            photoTravailRepository.delete(insertedPhotoTravail);
            insertedPhotoTravail = null;
        }
    }

    @Test
    void createPhotoTravail() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PhotoTravail
        PhotoTravailDTO photoTravailDTO = photoTravailMapper.toDto(photoTravail);
        var returnedPhotoTravailDTO = om.readValue(
            restPhotoTravailMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(photoTravailDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PhotoTravailDTO.class
        );

        // Validate the PhotoTravail in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPhotoTravail = photoTravailMapper.toEntity(returnedPhotoTravailDTO);
        assertPhotoTravailUpdatableFieldsEquals(returnedPhotoTravail, getPersistedPhotoTravail(returnedPhotoTravail));

        insertedPhotoTravail = returnedPhotoTravail;
    }

    @Test
    void createPhotoTravailWithExistingId() throws Exception {
        // Create the PhotoTravail with an existing ID
        photoTravail.setId("existing_id");
        PhotoTravailDTO photoTravailDTO = photoTravailMapper.toDto(photoTravail);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhotoTravailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(photoTravailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PhotoTravail in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        photoTravail.setDate(null);

        // Create the PhotoTravail, which fails.
        PhotoTravailDTO photoTravailDTO = photoTravailMapper.toDto(photoTravail);

        restPhotoTravailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(photoTravailDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllPhotoTravails() throws Exception {
        // Initialize the database
        insertedPhotoTravail = photoTravailRepository.save(photoTravail);

        // Get all the photoTravailList
        restPhotoTravailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(photoTravail.getId())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_PHOTO))));
    }

    @Test
    void getPhotoTravail() throws Exception {
        // Initialize the database
        insertedPhotoTravail = photoTravailRepository.save(photoTravail);

        // Get the photoTravail
        restPhotoTravailMockMvc
            .perform(get(ENTITY_API_URL_ID, photoTravail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(photoTravail.getId()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64.getEncoder().encodeToString(DEFAULT_PHOTO)));
    }

    @Test
    void getNonExistingPhotoTravail() throws Exception {
        // Get the photoTravail
        restPhotoTravailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingPhotoTravail() throws Exception {
        // Initialize the database
        insertedPhotoTravail = photoTravailRepository.save(photoTravail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the photoTravail
        PhotoTravail updatedPhotoTravail = photoTravailRepository.findById(photoTravail.getId()).orElseThrow();
        updatedPhotoTravail
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);
        PhotoTravailDTO photoTravailDTO = photoTravailMapper.toDto(updatedPhotoTravail);

        restPhotoTravailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, photoTravailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(photoTravailDTO))
            )
            .andExpect(status().isOk());

        // Validate the PhotoTravail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPhotoTravailToMatchAllProperties(updatedPhotoTravail);
    }

    @Test
    void putNonExistingPhotoTravail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        photoTravail.setId(UUID.randomUUID().toString());

        // Create the PhotoTravail
        PhotoTravailDTO photoTravailDTO = photoTravailMapper.toDto(photoTravail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhotoTravailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, photoTravailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(photoTravailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhotoTravail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPhotoTravail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        photoTravail.setId(UUID.randomUUID().toString());

        // Create the PhotoTravail
        PhotoTravailDTO photoTravailDTO = photoTravailMapper.toDto(photoTravail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhotoTravailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(photoTravailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhotoTravail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPhotoTravail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        photoTravail.setId(UUID.randomUUID().toString());

        // Create the PhotoTravail
        PhotoTravailDTO photoTravailDTO = photoTravailMapper.toDto(photoTravail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhotoTravailMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(photoTravailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhotoTravail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePhotoTravailWithPatch() throws Exception {
        // Initialize the database
        insertedPhotoTravail = photoTravailRepository.save(photoTravail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the photoTravail using partial update
        PhotoTravail partialUpdatedPhotoTravail = new PhotoTravail();
        partialUpdatedPhotoTravail.setId(photoTravail.getId());

        restPhotoTravailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhotoTravail.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPhotoTravail))
            )
            .andExpect(status().isOk());

        // Validate the PhotoTravail in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPhotoTravailUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPhotoTravail, photoTravail),
            getPersistedPhotoTravail(photoTravail)
        );
    }

    @Test
    void fullUpdatePhotoTravailWithPatch() throws Exception {
        // Initialize the database
        insertedPhotoTravail = photoTravailRepository.save(photoTravail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the photoTravail using partial update
        PhotoTravail partialUpdatedPhotoTravail = new PhotoTravail();
        partialUpdatedPhotoTravail.setId(photoTravail.getId());

        partialUpdatedPhotoTravail
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restPhotoTravailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhotoTravail.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPhotoTravail))
            )
            .andExpect(status().isOk());

        // Validate the PhotoTravail in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPhotoTravailUpdatableFieldsEquals(partialUpdatedPhotoTravail, getPersistedPhotoTravail(partialUpdatedPhotoTravail));
    }

    @Test
    void patchNonExistingPhotoTravail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        photoTravail.setId(UUID.randomUUID().toString());

        // Create the PhotoTravail
        PhotoTravailDTO photoTravailDTO = photoTravailMapper.toDto(photoTravail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhotoTravailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, photoTravailDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(photoTravailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhotoTravail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPhotoTravail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        photoTravail.setId(UUID.randomUUID().toString());

        // Create the PhotoTravail
        PhotoTravailDTO photoTravailDTO = photoTravailMapper.toDto(photoTravail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhotoTravailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(photoTravailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhotoTravail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPhotoTravail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        photoTravail.setId(UUID.randomUUID().toString());

        // Create the PhotoTravail
        PhotoTravailDTO photoTravailDTO = photoTravailMapper.toDto(photoTravail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhotoTravailMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(photoTravailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhotoTravail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePhotoTravail() throws Exception {
        // Initialize the database
        insertedPhotoTravail = photoTravailRepository.save(photoTravail);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the photoTravail
        restPhotoTravailMockMvc
            .perform(delete(ENTITY_API_URL_ID, photoTravail.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return photoTravailRepository.count();
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

    protected PhotoTravail getPersistedPhotoTravail(PhotoTravail photoTravail) {
        return photoTravailRepository.findById(photoTravail.getId()).orElseThrow();
    }

    protected void assertPersistedPhotoTravailToMatchAllProperties(PhotoTravail expectedPhotoTravail) {
        assertPhotoTravailAllPropertiesEquals(expectedPhotoTravail, getPersistedPhotoTravail(expectedPhotoTravail));
    }

    protected void assertPersistedPhotoTravailToMatchUpdatableProperties(PhotoTravail expectedPhotoTravail) {
        assertPhotoTravailAllUpdatablePropertiesEquals(expectedPhotoTravail, getPersistedPhotoTravail(expectedPhotoTravail));
    }
}
