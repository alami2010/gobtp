package com.gosoft.gobtp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gosoft.gobtp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MateriauDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MateriauDTO.class);
        MateriauDTO materiauDTO1 = new MateriauDTO();
        materiauDTO1.setId("id1");
        MateriauDTO materiauDTO2 = new MateriauDTO();
        assertThat(materiauDTO1).isNotEqualTo(materiauDTO2);
        materiauDTO2.setId(materiauDTO1.getId());
        assertThat(materiauDTO1).isEqualTo(materiauDTO2);
        materiauDTO2.setId("id2");
        assertThat(materiauDTO1).isNotEqualTo(materiauDTO2);
        materiauDTO1.setId(null);
        assertThat(materiauDTO1).isNotEqualTo(materiauDTO2);
    }
}
