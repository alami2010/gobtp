package com.gosoft.gobtp.domain;

import java.util.UUID;

public class DocumentFinancierTestSamples {

    public static DocumentFinancier getDocumentFinancierSample1() {
        return new DocumentFinancier().id("id1").nom("nom1").file("file1");
    }

    public static DocumentFinancier getDocumentFinancierSample2() {
        return new DocumentFinancier().id("id2").nom("nom2").file("file2");
    }

    public static DocumentFinancier getDocumentFinancierRandomSampleGenerator() {
        return new DocumentFinancier()
            .id(UUID.randomUUID().toString())
            .nom(UUID.randomUUID().toString())
            .file(UUID.randomUUID().toString());
    }
}
