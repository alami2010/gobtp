package com.gosoft.gobtp.domain;

import static com.gosoft.gobtp.domain.ChantierTestSamples.*;
import static com.gosoft.gobtp.domain.HoraireTravailTestSamples.*;
import static com.gosoft.gobtp.domain.OuvrierTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gosoft.gobtp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class OuvrierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ouvrier.class);
        Ouvrier ouvrier1 = getOuvrierSample1();
        Ouvrier ouvrier2 = new Ouvrier();
        assertThat(ouvrier1).isNotEqualTo(ouvrier2);

        ouvrier2.setId(ouvrier1.getId());
        assertThat(ouvrier1).isEqualTo(ouvrier2);

        ouvrier2 = getOuvrierSample2();
        assertThat(ouvrier1).isNotEqualTo(ouvrier2);
    }

    @Test
    void ouvrierTest() {
        Ouvrier ouvrier = getOuvrierRandomSampleGenerator();
        HoraireTravail horaireTravailBack = getHoraireTravailRandomSampleGenerator();

        ouvrier.addOuvrier(horaireTravailBack);
        assertThat(ouvrier.getOuvriers()).containsOnly(horaireTravailBack);
        assertThat(horaireTravailBack.getOuvrier()).isEqualTo(ouvrier);

        ouvrier.removeOuvrier(horaireTravailBack);
        assertThat(ouvrier.getOuvriers()).doesNotContain(horaireTravailBack);
        assertThat(horaireTravailBack.getOuvrier()).isNull();

        ouvrier.ouvriers(new HashSet<>(Set.of(horaireTravailBack)));
        assertThat(ouvrier.getOuvriers()).containsOnly(horaireTravailBack);
        assertThat(horaireTravailBack.getOuvrier()).isEqualTo(ouvrier);

        ouvrier.setOuvriers(new HashSet<>());
        assertThat(ouvrier.getOuvriers()).doesNotContain(horaireTravailBack);
        assertThat(horaireTravailBack.getOuvrier()).isNull();
    }

    @Test
    void chantiersTest() {
        Ouvrier ouvrier = getOuvrierRandomSampleGenerator();
        Chantier chantierBack = getChantierRandomSampleGenerator();

        ouvrier.addChantiers(chantierBack);
        assertThat(ouvrier.getChantiers()).containsOnly(chantierBack);
        assertThat(chantierBack.getOuvriers()).containsOnly(ouvrier);

        ouvrier.removeChantiers(chantierBack);
        assertThat(ouvrier.getChantiers()).doesNotContain(chantierBack);
        assertThat(chantierBack.getOuvriers()).doesNotContain(ouvrier);

        ouvrier.chantiers(new HashSet<>(Set.of(chantierBack)));
        assertThat(ouvrier.getChantiers()).containsOnly(chantierBack);
        assertThat(chantierBack.getOuvriers()).containsOnly(ouvrier);

        ouvrier.setChantiers(new HashSet<>());
        assertThat(ouvrier.getChantiers()).doesNotContain(chantierBack);
        assertThat(chantierBack.getOuvriers()).doesNotContain(ouvrier);
    }
}
