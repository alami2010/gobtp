package com.gosoft.gobtp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class PhotoTravailAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPhotoTravailAllPropertiesEquals(PhotoTravail expected, PhotoTravail actual) {
        assertPhotoTravailAutoGeneratedPropertiesEquals(expected, actual);
        assertPhotoTravailAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPhotoTravailAllUpdatablePropertiesEquals(PhotoTravail expected, PhotoTravail actual) {
        assertPhotoTravailUpdatableFieldsEquals(expected, actual);
        assertPhotoTravailUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPhotoTravailAutoGeneratedPropertiesEquals(PhotoTravail expected, PhotoTravail actual) {
        assertThat(expected)
            .as("Verify PhotoTravail auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPhotoTravailUpdatableFieldsEquals(PhotoTravail expected, PhotoTravail actual) {
        assertThat(expected)
            .as("Verify PhotoTravail relevant properties")
            .satisfies(e -> assertThat(e.getDescription()).as("check description").isEqualTo(actual.getDescription()))
            .satisfies(e -> assertThat(e.getDate()).as("check date").isEqualTo(actual.getDate()))
            .satisfies(e -> assertThat(e.getPhoto()).as("check photo").isEqualTo(actual.getPhoto()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPhotoTravailUpdatableRelationshipsEquals(PhotoTravail expected, PhotoTravail actual) {
        assertThat(expected)
            .as("Verify PhotoTravail relationships")
            .satisfies(e -> assertThat(e.getChantier()).as("check chantier").isEqualTo(actual.getChantier()));
    }
}