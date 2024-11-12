package com.gosoft.gobtp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gosoft.gobtp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChefChantierDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChefChantierDTO.class);
        ChefChantierDTO chefChantierDTO1 = new ChefChantierDTO();
        chefChantierDTO1.setId("id1");
        ChefChantierDTO chefChantierDTO2 = new ChefChantierDTO();
        assertThat(chefChantierDTO1).isNotEqualTo(chefChantierDTO2);
        chefChantierDTO2.setId(chefChantierDTO1.getId());
        assertThat(chefChantierDTO1).isEqualTo(chefChantierDTO2);
        chefChantierDTO2.setId("id2");
        assertThat(chefChantierDTO1).isNotEqualTo(chefChantierDTO2);
        chefChantierDTO1.setId(null);
        assertThat(chefChantierDTO1).isNotEqualTo(chefChantierDTO2);
    }
}
