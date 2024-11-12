package com.gosoft.gobtp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gosoft.gobtp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OuvrierDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OuvrierDTO.class);
        OuvrierDTO ouvrierDTO1 = new OuvrierDTO();
        ouvrierDTO1.setId("id1");
        OuvrierDTO ouvrierDTO2 = new OuvrierDTO();
        assertThat(ouvrierDTO1).isNotEqualTo(ouvrierDTO2);
        ouvrierDTO2.setId(ouvrierDTO1.getId());
        assertThat(ouvrierDTO1).isEqualTo(ouvrierDTO2);
        ouvrierDTO2.setId("id2");
        assertThat(ouvrierDTO1).isNotEqualTo(ouvrierDTO2);
        ouvrierDTO1.setId(null);
        assertThat(ouvrierDTO1).isNotEqualTo(ouvrierDTO2);
    }
}
