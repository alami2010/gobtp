package com.gosoft.gobtp.domain;

import java.util.UUID;

public class PhotoTravailTestSamples {

    public static PhotoTravail getPhotoTravailSample1() {
        return new PhotoTravail().id("id1").description("description1");
    }

    public static PhotoTravail getPhotoTravailSample2() {
        return new PhotoTravail().id("id2").description("description2");
    }

    public static PhotoTravail getPhotoTravailRandomSampleGenerator() {
        return new PhotoTravail().id(UUID.randomUUID().toString()).description(UUID.randomUUID().toString());
    }
}
