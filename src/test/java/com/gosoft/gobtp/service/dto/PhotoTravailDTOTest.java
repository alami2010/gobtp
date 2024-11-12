package com.gosoft.gobtp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gosoft.gobtp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PhotoTravailDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhotoTravailDTO.class);
        PhotoTravailDTO photoTravailDTO1 = new PhotoTravailDTO();
        photoTravailDTO1.setId("id1");
        PhotoTravailDTO photoTravailDTO2 = new PhotoTravailDTO();
        assertThat(photoTravailDTO1).isNotEqualTo(photoTravailDTO2);
        photoTravailDTO2.setId(photoTravailDTO1.getId());
        assertThat(photoTravailDTO1).isEqualTo(photoTravailDTO2);
        photoTravailDTO2.setId("id2");
        assertThat(photoTravailDTO1).isNotEqualTo(photoTravailDTO2);
        photoTravailDTO1.setId(null);
        assertThat(photoTravailDTO1).isNotEqualTo(photoTravailDTO2);
    }
}
