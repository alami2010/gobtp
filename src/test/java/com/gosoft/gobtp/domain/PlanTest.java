package com.gosoft.gobtp.domain;

import static com.gosoft.gobtp.domain.ChantierTestSamples.*;
import static com.gosoft.gobtp.domain.PlanTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gosoft.gobtp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Plan.class);
        Plan plan1 = getPlanSample1();
        Plan plan2 = new Plan();
        assertThat(plan1).isNotEqualTo(plan2);

        plan2.setId(plan1.getId());
        assertThat(plan1).isEqualTo(plan2);

        plan2 = getPlanSample2();
        assertThat(plan1).isNotEqualTo(plan2);
    }

    @Test
    void chantierTest() {
        Plan plan = getPlanRandomSampleGenerator();
        Chantier chantierBack = getChantierRandomSampleGenerator();

        plan.setChantier(chantierBack);
        assertThat(plan.getChantier()).isEqualTo(chantierBack);

        plan.chantier(null);
        assertThat(plan.getChantier()).isNull();
    }
}
