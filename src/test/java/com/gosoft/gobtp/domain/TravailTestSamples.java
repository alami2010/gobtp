package com.gosoft.gobtp.domain;

import java.util.UUID;

public class TravailTestSamples {

    public static Travail getTravailSample1() {
        return new Travail().id("id1").name("name1").label("label1");
    }

    public static Travail getTravailSample2() {
        return new Travail().id("id2").name("name2").label("label2");
    }

    public static Travail getTravailRandomSampleGenerator() {
        return new Travail().id(UUID.randomUUID().toString()).name(UUID.randomUUID().toString()).label(UUID.randomUUID().toString());
    }
}
