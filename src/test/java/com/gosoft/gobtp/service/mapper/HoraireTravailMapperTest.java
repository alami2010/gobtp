package com.gosoft.gobtp.service.mapper;

import static com.gosoft.gobtp.domain.HoraireTravailAsserts.*;
import static com.gosoft.gobtp.domain.HoraireTravailTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HoraireTravailMapperTest {

    private HoraireTravailMapper horaireTravailMapper;

    @BeforeEach
    void setUp() {
        horaireTravailMapper = new HoraireTravailMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getHoraireTravailSample1();
        var actual = horaireTravailMapper.toEntity(horaireTravailMapper.toDto(expected));
        assertHoraireTravailAllPropertiesEquals(expected, actual);
    }
}
