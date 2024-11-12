package com.gosoft.gobtp.domain;

import static com.gosoft.gobtp.domain.ChantierTestSamples.*;
import static com.gosoft.gobtp.domain.PhotoTravailTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gosoft.gobtp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PhotoTravailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhotoTravail.class);
        PhotoTravail photoTravail1 = getPhotoTravailSample1();
        PhotoTravail photoTravail2 = new PhotoTravail();
        assertThat(photoTravail1).isNotEqualTo(photoTravail2);

        photoTravail2.setId(photoTravail1.getId());
        assertThat(photoTravail1).isEqualTo(photoTravail2);

        photoTravail2 = getPhotoTravailSample2();
        assertThat(photoTravail1).isNotEqualTo(photoTravail2);
    }

    @Test
    void chantierTest() {
        PhotoTravail photoTravail = getPhotoTravailRandomSampleGenerator();
        Chantier chantierBack = getChantierRandomSampleGenerator();

        photoTravail.setChantier(chantierBack);
        assertThat(photoTravail.getChantier()).isEqualTo(chantierBack);

        photoTravail.chantier(null);
        assertThat(photoTravail.getChantier()).isNull();
    }
}
