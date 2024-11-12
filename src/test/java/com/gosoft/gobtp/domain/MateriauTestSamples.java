package com.gosoft.gobtp.domain;

import java.util.UUID;

public class MateriauTestSamples {

    public static Materiau getMateriauSample1() {
        return new Materiau().id("id1").name("name1");
    }

    public static Materiau getMateriauSample2() {
        return new Materiau().id("id2").name("name2");
    }

    public static Materiau getMateriauRandomSampleGenerator() {
        return new Materiau().id(UUID.randomUUID().toString()).name(UUID.randomUUID().toString());
    }
}
