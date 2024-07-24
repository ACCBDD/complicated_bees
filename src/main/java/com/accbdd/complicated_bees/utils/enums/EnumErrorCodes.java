package com.accbdd.complicated_bees.utils.enums;

public enum EnumErrorCodes {
    NO_FLOWER("no_flower", (byte)1),
    WRONG_TEMP("wrong_temp", (byte)2),
    WRONG_HUMIDITY("wrong_humidity", (byte)4),
    OUTPUT_FULL("output_full", (byte)8),
    WRONG_TIME("wrong_time", (byte)16),
    UNDERGROUND("underground", (byte)32),
    WEATHER("weather", (byte)64);

    public final String name;
    public final byte value;


    EnumErrorCodes(String name, byte value) {
        this.name = name;
        this.value = value;
    }
}
