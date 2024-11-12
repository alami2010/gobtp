package com.gosoft.gobtp.service.mapper;

import static com.gosoft.gobtp.domain.ChefChantierAsserts.*;
import static com.gosoft.gobtp.domain.ChefChantierTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChefChantierMapperTest {

    private ChefChantierMapper chefChantierMapper;

    @BeforeEach
    void setUp() {
        chefChantierMapper = new ChefChantierMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getChefChantierSample1();
        var actual = chefChantierMapper.toEntity(chefChantierMapper.toDto(expected));
        assertChefChantierAllPropertiesEquals(expected, actual);
    }
}
