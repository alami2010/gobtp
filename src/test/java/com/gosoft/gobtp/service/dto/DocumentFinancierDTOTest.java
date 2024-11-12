package com.gosoft.gobtp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gosoft.gobtp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocumentFinancierDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentFinancierDTO.class);
        DocumentFinancierDTO documentFinancierDTO1 = new DocumentFinancierDTO();
        documentFinancierDTO1.setId("id1");
        DocumentFinancierDTO documentFinancierDTO2 = new DocumentFinancierDTO();
        assertThat(documentFinancierDTO1).isNotEqualTo(documentFinancierDTO2);
        documentFinancierDTO2.setId(documentFinancierDTO1.getId());
        assertThat(documentFinancierDTO1).isEqualTo(documentFinancierDTO2);
        documentFinancierDTO2.setId("id2");
        assertThat(documentFinancierDTO1).isNotEqualTo(documentFinancierDTO2);
        documentFinancierDTO1.setId(null);
        assertThat(documentFinancierDTO1).isNotEqualTo(documentFinancierDTO2);
    }
}
