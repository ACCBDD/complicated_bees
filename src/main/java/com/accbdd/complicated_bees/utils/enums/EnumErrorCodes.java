package com.accbdd.complicated_bees.utils.enums;

public enum EnumErrorCodes {
    NO_FLOWER("no_flower", 1),
    WRONG_TEMP("wrong_temp", 2),
    WRONG_HUMIDITY("wrong_humidity", 4),
    OUTPUT_FULL("output_full", 8),
    WRONG_TIME("wrong_time", 16),
    UNDERGROUND("underground", 32),
    WEATHER("weather", 64),
    ECSTATIC("ecstatic", 128);

    public final String name;
    public final int value;


    EnumErrorCodes(String name, int value) {
        this.name = name;
        this.value = value;
    }
}
