package com.gosoft.gobtp.service.mapper;

import static com.gosoft.gobtp.domain.MateriauManquantAsserts.*;
import static com.gosoft.gobtp.domain.MateriauManquantTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MateriauManquantMapperTest {

    private MateriauManquantMapper materiauManquantMapper;

    @BeforeEach
    void setUp() {
        materiauManquantMapper = new MateriauManquantMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMateriauManquantSample1();
        var actual = materiauManquantMapper.toEntity(materiauManquantMapper.toDto(expected));
        assertMateriauManquantAllPropertiesEquals(expected, actual);
    }
}
