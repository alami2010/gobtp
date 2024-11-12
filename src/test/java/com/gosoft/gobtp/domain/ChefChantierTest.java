package com.gosoft.gobtp.domain;

import static com.gosoft.gobtp.domain.ChantierTestSamples.*;
import static com.gosoft.gobtp.domain.ChefChantierTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gosoft.gobtp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ChefChantierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChefChantier.class);
        ChefChantier chefChantier1 = getChefChantierSample1();
        ChefChantier chefChantier2 = new ChefChantier();
        assertThat(chefChantier1).isNotEqualTo(chefChantier2);

        chefChantier2.setId(chefChantier1.getId());
        assertThat(chefChantier1).isEqualTo(chefChantier2);

        chefChantier2 = getChefChantierSample2();
        assertThat(chefChantier1).isNotEqualTo(chefChantier2);
    }

    @Test
    void chantiersTest() {
        ChefChantier chefChantier = getChefChantierRandomSampleGenerator();
        Chantier chantierBack = getChantierRandomSampleGenerator();

        chefChantier.addChantiers(chantierBack);
        assertThat(chefChantier.getChantiers()).containsOnly(chantierBack);
        assertThat(chantierBack.getChefChantier()).isEqualTo(chefChantier);

        chefChantier.removeChantiers(chantierBack);
        assertThat(chefChantier.getChantiers()).doesNotContain(chantierBack);
        assertThat(chantierBack.getChefChantier()).isNull();

        chefChantier.chantiers(new HashSet<>(Set.of(chantierBack)));
        assertThat(chefChantier.getChantiers()).containsOnly(chantierBack);
        assertThat(chantierBack.getChefChantier()).isEqualTo(chefChantier);

        chefChantier.setChantiers(new HashSet<>());
        assertThat(chefChantier.getChantiers()).doesNotContain(chantierBack);
        assertThat(chantierBack.getChefChantier()).isNull();
    }
}
