package com.gosoft.gobtp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gosoft.gobtp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HoraireTravailDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HoraireTravailDTO.class);
        HoraireTravailDTO horaireTravailDTO1 = new HoraireTravailDTO();
        horaireTravailDTO1.setId("id1");
        HoraireTravailDTO horaireTravailDTO2 = new HoraireTravailDTO();
        assertThat(horaireTravailDTO1).isNotEqualTo(horaireTravailDTO2);
        horaireTravailDTO2.setId(horaireTravailDTO1.getId());
        assertThat(horaireTravailDTO1).isEqualTo(horaireTravailDTO2);
        horaireTravailDTO2.setId("id2");
        assertThat(horaireTravailDTO1).isNotEqualTo(horaireTravailDTO2);
        horaireTravailDTO1.setId(null);
        assertThat(horaireTravailDTO1).isNotEqualTo(horaireTravailDTO2);
    }
}
