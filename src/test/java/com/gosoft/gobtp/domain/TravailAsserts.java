package com.gosoft.gobtp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class TravailAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTravailAllPropertiesEquals(Travail expected, Travail actual) {
        assertTravailAutoGeneratedPropertiesEquals(expected, actual);
        assertTravailAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTravailAllUpdatablePropertiesEquals(Travail expected, Travail actual) {
        assertTravailUpdatableFieldsEquals(expected, actual);
        assertTravailUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTravailAutoGeneratedPropertiesEquals(Travail expected, Travail actual) {
        assertThat(expected)
            .as("Verify Travail auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTravailUpdatableFieldsEquals(Travail expected, Travail actual) {
        assertThat(expected)
            .as("Verify Travail relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getLabel()).as("check label").isEqualTo(actual.getLabel()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTravailUpdatableRelationshipsEquals(Travail expected, Travail actual) {
        assertThat(expected)
            .as("Verify Travail relationships")
            .satisfies(e -> assertThat(e.getChantier()).as("check chantier").isEqualTo(actual.getChantier()));
    }
}