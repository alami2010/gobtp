package com.gosoft.gobtp.domain;

import java.util.UUID;

public class HoraireTravailTestSamples {

    public static HoraireTravail getHoraireTravailSample1() {
        return new HoraireTravail().id("id1").jour("jour1");
    }

    public static HoraireTravail getHoraireTravailSample2() {
        return new HoraireTravail().id("id2").jour("jour2");
    }

    public static HoraireTravail getHoraireTravailRandomSampleGenerator() {
        return new HoraireTravail().id(UUID.randomUUID().toString()).jour(UUID.randomUUID().toString());
    }
}
