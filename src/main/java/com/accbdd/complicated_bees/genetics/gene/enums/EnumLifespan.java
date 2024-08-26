package com.accbdd.complicated_bees.genetics.gene.enums;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public enum EnumLifespan {
    SHORTEST("shortest", 15),
    SHORTER("shorter", 25),
    SHORT("short", 35),
    AVERAGE("average", 50),
    LONG("long", 65),
    LONGER("longer", 75),
    LONGEST("longest", 90);

    public final String name;
    public final int value;

    EnumLifespan(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static EnumLifespan getFromString(String name) {
        for (EnumLifespan value : values()) {
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
        return Component.translatable("gui.complicated_bees.lifespan." + this.toString());
    }
}
