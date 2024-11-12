package com.gosoft.gobtp.service.mapper;

import static com.gosoft.gobtp.domain.OuvrierAsserts.*;
import static com.gosoft.gobtp.domain.OuvrierTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OuvrierMapperTest {

    private OuvrierMapper ouvrierMapper;

    @BeforeEach
    void setUp() {
        ouvrierMapper = new OuvrierMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOuvrierSample1();
        var actual = ouvrierMapper.toEntity(ouvrierMapper.toDto(expected));
        assertOuvrierAllPropertiesEquals(expected, actual);
    }
}
