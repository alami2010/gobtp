package com.gosoft.gobtp.domain;

import static com.gosoft.gobtp.domain.ChantierTestSamples.*;
import static com.gosoft.gobtp.domain.MateriauManquantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gosoft.gobtp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MateriauManquantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MateriauManquant.class);
        MateriauManquant materiauManquant1 = getMateriauManquantSample1();
        MateriauManquant materiauManquant2 = new MateriauManquant();
        assertThat(materiauManquant1).isNotEqualTo(materiauManquant2);

        materiauManquant2.setId(materiauManquant1.getId());
        assertThat(materiauManquant1).isEqualTo(materiauManquant2);

        materiauManquant2 = getMateriauManquantSample2();
        assertThat(materiauManquant1).isNotEqualTo(materiauManquant2);
    }

    @Test
    void chantierTest() {
        MateriauManquant materiauManquant = getMateriauManquantRandomSampleGenerator();
        Chantier chantierBack = getChantierRandomSampleGenerator();

        materiauManquant.setChantier(chantierBack);
        assertThat(materiauManquant.getChantier()).isEqualTo(chantierBack);

        materiauManquant.chantier(null);
        assertThat(materiauManquant.getChantier()).isNull();
    }
}
