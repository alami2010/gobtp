package com.gosoft.gobtp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class TravailSupplementaireAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTravailSupplementaireAllPropertiesEquals(TravailSupplementaire expected, TravailSupplementaire actual) {
        assertTravailSupplementaireAutoGeneratedPropertiesEquals(expected, actual);
        assertTravailSupplementaireAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTravailSupplementaireAllUpdatablePropertiesEquals(
        TravailSupplementaire expected,
        TravailSupplementaire actual
    ) {
        assertTravailSupplementaireUpdatableFieldsEquals(expected, actual);
        assertTravailSupplementaireUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTravailSupplementaireAutoGeneratedPropertiesEquals(
        TravailSupplementaire expected,
        TravailSupplementaire actual
    ) {
        assertThat(expected)
            .as("Verify TravailSupplementaire auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTravailSupplementaireUpdatableFieldsEquals(TravailSupplementaire expected, TravailSupplementaire actual) {
        assertThat(expected)
            .as("Verify TravailSupplementaire relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getLabel()).as("check label").isEqualTo(actual.getLabel()))
            .satisfies(e -> assertThat(e.getDate()).as("check date").isEqualTo(actual.getDate()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTravailSupplementaireUpdatableRelationshipsEquals(
        TravailSupplementaire expected,
        TravailSupplementaire actual
    ) {
        assertThat(expected)
            .as("Verify TravailSupplementaire relationships")
            .satisfies(e -> assertThat(e.getChantier()).as("check chantier").isEqualTo(actual.getChantier()));
    }
}