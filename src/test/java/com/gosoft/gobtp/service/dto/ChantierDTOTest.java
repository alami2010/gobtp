package com.gosoft.gobtp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gosoft.gobtp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChantierDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChantierDTO.class);
        ChantierDTO chantierDTO1 = new ChantierDTO();
        chantierDTO1.setId("id1");
        ChantierDTO chantierDTO2 = new ChantierDTO();
        assertThat(chantierDTO1).isNotEqualTo(chantierDTO2);
        chantierDTO2.setId(chantierDTO1.getId());
        assertThat(chantierDTO1).isEqualTo(chantierDTO2);
        chantierDTO2.setId("id2");
        assertThat(chantierDTO1).isNotEqualTo(chantierDTO2);
        chantierDTO1.setId(null);
        assertThat(chantierDTO1).isNotEqualTo(chantierDTO2);
    }
}
