package com.gosoft.gobtp.domain;

import static com.gosoft.gobtp.domain.ChantierTestSamples.*;
import static com.gosoft.gobtp.domain.MateriauTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gosoft.gobtp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MateriauTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Materiau.class);
        Materiau materiau1 = getMateriauSample1();
        Materiau materiau2 = new Materiau();
        assertThat(materiau1).isNotEqualTo(materiau2);

        materiau2.setId(materiau1.getId());
        assertThat(materiau1).isEqualTo(materiau2);

        materiau2 = getMateriauSample2();
        assertThat(materiau1).isNotEqualTo(materiau2);
    }

    @Test
    void chantierTest() {
        Materiau materiau = getMateriauRandomSampleGenerator();
        Chantier chantierBack = getChantierRandomSampleGenerator();

        materiau.setChantier(chantierBack);
        assertThat(materiau.getChantier()).isEqualTo(chantierBack);

        materiau.chantier(null);
        assertThat(materiau.getChantier()).isNull();
    }
}
