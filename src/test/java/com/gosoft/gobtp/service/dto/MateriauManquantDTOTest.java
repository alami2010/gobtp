package com.gosoft.gobtp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gosoft.gobtp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MateriauManquantDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MateriauManquantDTO.class);
        MateriauManquantDTO materiauManquantDTO1 = new MateriauManquantDTO();
        materiauManquantDTO1.setId("id1");
        MateriauManquantDTO materiauManquantDTO2 = new MateriauManquantDTO();
        assertThat(materiauManquantDTO1).isNotEqualTo(materiauManquantDTO2);
        materiauManquantDTO2.setId(materiauManquantDTO1.getId());
        assertThat(materiauManquantDTO1).isEqualTo(materiauManquantDTO2);
        materiauManquantDTO2.setId("id2");
        assertThat(materiauManquantDTO1).isNotEqualTo(materiauManquantDTO2);
        materiauManquantDTO1.setId(null);
        assertThat(materiauManquantDTO1).isNotEqualTo(materiauManquantDTO2);
    }
}
