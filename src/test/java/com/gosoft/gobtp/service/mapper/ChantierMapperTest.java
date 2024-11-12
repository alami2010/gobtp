package com.gosoft.gobtp.service.mapper;

import static com.gosoft.gobtp.domain.ChantierAsserts.*;
import static com.gosoft.gobtp.domain.ChantierTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChantierMapperTest {

    private ChantierMapper chantierMapper;

    @BeforeEach
    void setUp() {
        chantierMapper = new ChantierMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getChantierSample1();
        var actual = chantierMapper.toEntity(chantierMapper.toDto(expected));
        assertChantierAllPropertiesEquals(expected, actual);
    }
}
