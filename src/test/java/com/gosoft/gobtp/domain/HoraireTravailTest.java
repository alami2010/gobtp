package com.gosoft.gobtp.domain;

import static com.gosoft.gobtp.domain.ChantierTestSamples.*;
import static com.gosoft.gobtp.domain.HoraireTravailTestSamples.*;
import static com.gosoft.gobtp.domain.OuvrierTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gosoft.gobtp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HoraireTravailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HoraireTravail.class);
        HoraireTravail horaireTravail1 = getHoraireTravailSample1();
        HoraireTravail horaireTravail2 = new HoraireTravail();
        assertThat(horaireTravail1).isNotEqualTo(horaireTravail2);

        horaireTravail2.setId(horaireTravail1.getId());
        assertThat(horaireTravail1).isEqualTo(horaireTravail2);

        horaireTravail2 = getHoraireTravailSample2();
        assertThat(horaireTravail1).isNotEqualTo(horaireTravail2);
    }

    @Test
    void chantierTest() {
        HoraireTravail horaireTravail = getHoraireTravailRandomSampleGenerator();
        Chantier chantierBack = getChantierRandomSampleGenerator();

        horaireTravail.setChantier(chantierBack);
        assertThat(horaireTravail.getChantier()).isEqualTo(chantierBack);

        horaireTravail.chantier(null);
        assertThat(horaireTravail.getChantier()).isNull();
    }

    @Test
    void ouvrierTest() {
        HoraireTravail horaireTravail = getHoraireTravailRandomSampleGenerator();
        Ouvrier ouvrierBack = getOuvrierRandomSampleGenerator();

        horaireTravail.setOuvrier(ouvrierBack);
        assertThat(horaireTravail.getOuvrier()).isEqualTo(ouvrierBack);

        horaireTravail.ouvrier(null);
        assertThat(horaireTravail.getOuvrier()).isNull();
    }
}
