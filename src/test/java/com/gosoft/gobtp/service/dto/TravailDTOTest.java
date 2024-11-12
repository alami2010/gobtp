package com.gosoft.gobtp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gosoft.gobtp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TravailDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TravailDTO.class);
        TravailDTO travailDTO1 = new TravailDTO();
        travailDTO1.setId("id1");
        TravailDTO travailDTO2 = new TravailDTO();
        assertThat(travailDTO1).isNotEqualTo(travailDTO2);
        travailDTO2.setId(travailDTO1.getId());
        assertThat(travailDTO1).isEqualTo(travailDTO2);
        travailDTO2.setId("id2");
        assertThat(travailDTO1).isNotEqualTo(travailDTO2);
        travailDTO1.setId(null);
        assertThat(travailDTO1).isNotEqualTo(travailDTO2);
    }
}
