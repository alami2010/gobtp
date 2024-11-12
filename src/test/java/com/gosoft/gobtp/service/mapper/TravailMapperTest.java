package com.gosoft.gobtp.service.mapper;

import static com.gosoft.gobtp.domain.TravailAsserts.*;
import static com.gosoft.gobtp.domain.TravailTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TravailMapperTest {

    private TravailMapper travailMapper;

    @BeforeEach
    void setUp() {
        travailMapper = new TravailMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTravailSample1();
        var actual = travailMapper.toEntity(travailMapper.toDto(expected));
        assertTravailAllPropertiesEquals(expected, actual);
    }
}
