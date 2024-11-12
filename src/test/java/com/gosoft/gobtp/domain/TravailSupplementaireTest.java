package com.gosoft.gobtp.domain;

import static com.gosoft.gobtp.domain.ChantierTestSamples.*;
import static com.gosoft.gobtp.domain.TravailSupplementaireTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gosoft.gobtp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TravailSupplementaireTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TravailSupplementaire.class);
        TravailSupplementaire travailSupplementaire1 = getTravailSupplementaireSample1();
        TravailSupplementaire travailSupplementaire2 = new TravailSupplementaire();
        assertThat(travailSupplementaire1).isNotEqualTo(travailSupplementaire2);

        travailSupplementaire2.setId(travailSupplementaire1.getId());
        assertThat(travailSupplementaire1).isEqualTo(travailSupplementaire2);

        travailSupplementaire2 = getTravailSupplementaireSample2();
        assertThat(travailSupplementaire1).isNotEqualTo(travailSupplementaire2);
    }

    @Test
    void chantierTest() {
        TravailSupplementaire travailSupplementaire = getTravailSupplementaireRandomSampleGenerator();
        Chantier chantierBack = getChantierRandomSampleGenerator();

        travailSupplementaire.setChantier(chantierBack);
        assertThat(travailSupplementaire.getChantier()).isEqualTo(chantierBack);

        travailSupplementaire.chantier(null);
        assertThat(travailSupplementaire.getChantier()).isNull();
    }
}
