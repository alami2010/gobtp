package com.gosoft.gobtp.service.mapper;

import static com.gosoft.gobtp.domain.PhotoTravailAsserts.*;
import static com.gosoft.gobtp.domain.PhotoTravailTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PhotoTravailMapperTest {

    private PhotoTravailMapper photoTravailMapper;

    @BeforeEach
    void setUp() {
        photoTravailMapper = new PhotoTravailMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPhotoTravailSample1();
        var actual = photoTravailMapper.toEntity(photoTravailMapper.toDto(expected));
        assertPhotoTravailAllPropertiesEquals(expected, actual);
    }
}
