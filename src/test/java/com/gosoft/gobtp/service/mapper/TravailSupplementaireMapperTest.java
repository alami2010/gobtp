package com.gosoft.gobtp.service.mapper;

import static com.gosoft.gobtp.domain.TravailSupplementaireAsserts.*;
import static com.gosoft.gobtp.domain.TravailSupplementaireTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TravailSupplementaireMapperTest {

    private TravailSupplementaireMapper travailSupplementaireMapper;

    @BeforeEach
    void setUp() {
        travailSupplementaireMapper = new TravailSupplementaireMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTravailSupplementaireSample1();
        var actual = travailSupplementaireMapper.toEntity(travailSupplementaireMapper.toDto(expected));
        assertTravailSupplementaireAllPropertiesEquals(expected, actual);
    }
}
