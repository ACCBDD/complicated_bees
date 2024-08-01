package com.accbdd.complicated_bees.genetics.gene.enums;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public enum EnumLifespan {
    SHORTEST("shortest", 10),
    SHORTER("shorter", 20),
    SHORT("short", 30),
    AVERAGE("average", 40),
    LONG("long", 50),
    LONGER("longer", 60),
    LONGEST("longest", 70);

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
