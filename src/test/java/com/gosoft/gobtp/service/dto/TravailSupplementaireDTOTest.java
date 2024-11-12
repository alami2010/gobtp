package com.gosoft.gobtp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gosoft.gobtp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TravailSupplementaireDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TravailSupplementaireDTO.class);
        TravailSupplementaireDTO travailSupplementaireDTO1 = new TravailSupplementaireDTO();
        travailSupplementaireDTO1.setId("id1");
        TravailSupplementaireDTO travailSupplementaireDTO2 = new TravailSupplementaireDTO();
        assertThat(travailSupplementaireDTO1).isNotEqualTo(travailSupplementaireDTO2);
        travailSupplementaireDTO2.setId(travailSupplementaireDTO1.getId());
        assertThat(travailSupplementaireDTO1).isEqualTo(travailSupplementaireDTO2);
        travailSupplementaireDTO2.setId("id2");
        assertThat(travailSupplementaireDTO1).isNotEqualTo(travailSupplementaireDTO2);
        travailSupplementaireDTO1.setId(null);
        assertThat(travailSupplementaireDTO1).isNotEqualTo(travailSupplementaireDTO2);
    }
}
