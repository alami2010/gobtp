package com.gosoft.gobtp.service.mapper;

import static com.gosoft.gobtp.domain.PlanAsserts.*;
import static com.gosoft.gobtp.domain.PlanTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlanMapperTest {

    private PlanMapper planMapper;

    @BeforeEach
    void setUp() {
        planMapper = new PlanMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPlanSample1();
        var actual = planMapper.toEntity(planMapper.toDto(expected));
        assertPlanAllPropertiesEquals(expected, actual);
    }
}
