package com.gosoft.gobtp.domain;

import java.util.UUID;

public class PlanTestSamples {

    public static Plan getPlanSample1() {
        return new Plan().id("id1").name("name1").file("file1");
    }

    public static Plan getPlanSample2() {
        return new Plan().id("id2").name("name2").file("file2");
    }

    public static Plan getPlanRandomSampleGenerator() {
        return new Plan().id(UUID.randomUUID().toString()).name(UUID.randomUUID().toString()).file(UUID.randomUUID().toString());
    }
}
