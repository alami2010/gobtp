package com.gosoft.gobtp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class DocumentFinancierAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDocumentFinancierAllPropertiesEquals(DocumentFinancier expected, DocumentFinancier actual) {
        assertDocumentFinancierAutoGeneratedPropertiesEquals(expected, actual);
        assertDocumentFinancierAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDocumentFinancierAllUpdatablePropertiesEquals(DocumentFinancier expected, DocumentFinancier actual) {
        assertDocumentFinancierUpdatableFieldsEquals(expected, actual);
        assertDocumentFinancierUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDocumentFinancierAutoGeneratedPropertiesEquals(DocumentFinancier expected, DocumentFinancier actual) {
        assertThat(expected)
            .as("Verify DocumentFinancier auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDocumentFinancierUpdatableFieldsEquals(DocumentFinancier expected, DocumentFinancier actual) {
        assertThat(expected)
            .as("Verify DocumentFinancier relevant properties")
            .satisfies(e -> assertThat(e.getNom()).as("check nom").isEqualTo(actual.getNom()))
            .satisfies(e -> assertThat(e.getFile()).as("check file").isEqualTo(actual.getFile()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDocumentFinancierUpdatableRelationshipsEquals(DocumentFinancier expected, DocumentFinancier actual) {
        assertThat(expected)
            .as("Verify DocumentFinancier relationships")
            .satisfies(e -> assertThat(e.getChantier()).as("check chantier").isEqualTo(actual.getChantier()));
    }
}