package com.gosoft.gobtp.domain;

import java.util.UUID;

public class ChefChantierTestSamples {

    public static ChefChantier getChefChantierSample1() {
        return new ChefChantier().id("id1").name("name1").email("email1").phone("phone1");
    }

    public static ChefChantier getChefChantierSample2() {
        return new ChefChantier().id("id2").name("name2").email("email2").phone("phone2");
    }

    public static ChefChantier getChefChantierRandomSampleGenerator() {
        return new ChefChantier()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString());
    }
}
