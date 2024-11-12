package com.gosoft.gobtp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MateriauManquantTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MateriauManquant getMateriauManquantSample1() {
        return new MateriauManquant().id("id1").name("name1").quantity(1);
    }

    public static MateriauManquant getMateriauManquantSample2() {
        return new MateriauManquant().id("id2").name("name2").quantity(2);
    }

    public static MateriauManquant getMateriauManquantRandomSampleGenerator() {
        return new MateriauManquant()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .quantity(intCount.incrementAndGet());
    }
}
