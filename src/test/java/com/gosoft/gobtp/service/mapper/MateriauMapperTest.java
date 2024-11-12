package com.gosoft.gobtp.service.mapper;

import static com.gosoft.gobtp.domain.MateriauAsserts.*;
import static com.gosoft.gobtp.domain.MateriauTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MateriauMapperTest {

    private MateriauMapper materiauMapper;

    @BeforeEach
    void setUp() {
        materiauMapper = new MateriauMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMateriauSample1();
        var actual = materiauMapper.toEntity(materiauMapper.toDto(expected));
        assertMateriauAllPropertiesEquals(expected, actual);
    }
}
