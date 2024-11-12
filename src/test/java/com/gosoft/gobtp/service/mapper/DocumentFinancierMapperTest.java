package com.gosoft.gobtp.service.mapper;

import static com.gosoft.gobtp.domain.DocumentFinancierAsserts.*;
import static com.gosoft.gobtp.domain.DocumentFinancierTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DocumentFinancierMapperTest {

    private DocumentFinancierMapper documentFinancierMapper;

    @BeforeEach
    void setUp() {
        documentFinancierMapper = new DocumentFinancierMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDocumentFinancierSample1();
        var actual = documentFinancierMapper.toEntity(documentFinancierMapper.toDto(expected));
        assertDocumentFinancierAllPropertiesEquals(expected, actual);
    }
}
