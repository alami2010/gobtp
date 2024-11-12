package com.gosoft.gobtp.domain;

import java.util.UUID;

public class ChantierTestSamples {

    public static Chantier getChantierSample1() {
        return new Chantier().id("id1").name("name1").adresse("adresse1").desc("desc1").status("status1");
    }

    public static Chantier getChantierSample2() {
        return new Chantier().id("id2").name("name2").adresse("adresse2").desc("desc2").status("status2");
    }

    public static Chantier getChantierRandomSampleGenerator() {
        return new Chantier()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .adresse(UUID.randomUUID().toString())
            .desc(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString());
    }
}
