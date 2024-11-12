package com.gosoft.gobtp.domain;

import static com.gosoft.gobtp.domain.ChantierTestSamples.*;
import static com.gosoft.gobtp.domain.DocumentFinancierTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gosoft.gobtp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocumentFinancierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentFinancier.class);
        DocumentFinancier documentFinancier1 = getDocumentFinancierSample1();
        DocumentFinancier documentFinancier2 = new DocumentFinancier();
        assertThat(documentFinancier1).isNotEqualTo(documentFinancier2);

        documentFinancier2.setId(documentFinancier1.getId());
        assertThat(documentFinancier1).isEqualTo(documentFinancier2);

        documentFinancier2 = getDocumentFinancierSample2();
        assertThat(documentFinancier1).isNotEqualTo(documentFinancier2);
    }

    @Test
    void chantierTest() {
        DocumentFinancier documentFinancier = getDocumentFinancierRandomSampleGenerator();
        Chantier chantierBack = getChantierRandomSampleGenerator();

        documentFinancier.setChantier(chantierBack);
        assertThat(documentFinancier.getChantier()).isEqualTo(chantierBack);

        documentFinancier.chantier(null);
        assertThat(documentFinancier.getChantier()).isNull();
    }
}
