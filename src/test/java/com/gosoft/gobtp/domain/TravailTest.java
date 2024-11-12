package com.gosoft.gobtp.domain;

import static com.gosoft.gobtp.domain.ChantierTestSamples.*;
import static com.gosoft.gobtp.domain.TravailTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gosoft.gobtp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TravailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Travail.class);
        Travail travail1 = getTravailSample1();
        Travail travail2 = new Travail();
        assertThat(travail1).isNotEqualTo(travail2);

        travail2.setId(travail1.getId());
        assertThat(travail1).isEqualTo(travail2);

        travail2 = getTravailSample2();
        assertThat(travail1).isNotEqualTo(travail2);
    }

    @Test
    void chantierTest() {
        Travail travail = getTravailRandomSampleGenerator();
        Chantier chantierBack = getChantierRandomSampleGenerator();

        travail.setChantier(chantierBack);
        assertThat(travail.getChantier()).isEqualTo(chantierBack);

        travail.chantier(null);
        assertThat(travail.getChantier()).isNull();
    }
}
