package com.gosoft.gobtp.domain;

import java.util.UUID;

public class TravailSupplementaireTestSamples {

    public static TravailSupplementaire getTravailSupplementaireSample1() {
        return new TravailSupplementaire().id("id1").name("name1").label("label1");
    }

    public static TravailSupplementaire getTravailSupplementaireSample2() {
        return new TravailSupplementaire().id("id2").name("name2").label("label2");
    }

    public static TravailSupplementaire getTravailSupplementaireRandomSampleGenerator() {
        return new TravailSupplementaire()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .label(UUID.randomUUID().toString());
    }
}
