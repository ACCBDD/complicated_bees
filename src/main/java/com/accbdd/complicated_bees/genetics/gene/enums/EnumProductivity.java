package com.accbdd.complicated_bees.genetics.gene.enums;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public enum EnumProductivity {
    SLOWEST("slowest", 0.5f),
    SLOWER("slower", 0.7f),
    SLOW("slow", 0.9f),
    AVERAGE("average", 1f),
    FAST("fast", 1.1f),
    FASTER("faster", 1.3f),
    FASTEST("fastest", 1.5f);

    public final String name;
    public final float value;

    EnumProductivity(String name, float value) {
        this.name = name;
        this.value = value;
    }

    public static EnumProductivity getFromString(String name) {
        for (EnumProductivity value : values()) {
            if (value.name.equals(name.toLowerCase()))
                return value;
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public MutableComponent getTranslationKey() {
        return Component.translatable("gui.complicated_bees.productivity." + this.toString());
    }
}
