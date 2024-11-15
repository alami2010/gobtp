package com.gosoft.gobtp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class MateriauAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMateriauAllPropertiesEquals(Materiau expected, Materiau actual) {
        assertMateriauAutoGeneratedPropertiesEquals(expected, actual);
        assertMateriauAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMateriauAllUpdatablePropertiesEquals(Materiau expected, Materiau actual) {
        assertMateriauUpdatableFieldsEquals(expected, actual);
        assertMateriauUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMateriauAutoGeneratedPropertiesEquals(Materiau expected, Materiau actual) {
        assertThat(expected)
            .as("Verify Materiau auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMateriauUpdatableFieldsEquals(Materiau expected, Materiau actual) {
        assertThat(expected)
            .as("Verify Materiau relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getDate()).as("check date").isEqualTo(actual.getDate()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMateriauUpdatableRelationshipsEquals(Materiau expected, Materiau actual) {
        assertThat(expected)
            .as("Verify Materiau relationships")
            .satisfies(e -> assertThat(e.getChantier()).as("check chantier").isEqualTo(actual.getChantier()));
    }
}
