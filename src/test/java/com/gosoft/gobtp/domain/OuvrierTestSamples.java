package com.gosoft.gobtp.domain;

import java.util.UUID;

public class OuvrierTestSamples {

    public static Ouvrier getOuvrierSample1() {
        return new Ouvrier().id("id1").name("name1");
    }

    public static Ouvrier getOuvrierSample2() {
        return new Ouvrier().id("id2").name("name2");
    }

    public static Ouvrier getOuvrierRandomSampleGenerator() {
        return new Ouvrier().id(UUID.randomUUID().toString()).name(UUID.randomUUID().toString());
    }
}
