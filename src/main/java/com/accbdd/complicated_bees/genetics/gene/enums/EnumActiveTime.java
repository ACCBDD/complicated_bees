package com.accbdd.complicated_bees.genetics.gene.enums;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public enum EnumActiveTime {
    DIURNAL("diurnal", new ImmutablePair<>(0, 12000)),
    NOCTURNAL("nocturnal", new ImmutablePair<>(13000, 24000)),
    MATUTINAL("matutinal", new ImmutablePair<>(22300, 24000)),
    VESPERTINE("vespertine", new ImmutablePair<>(12000, 13702)),
    CREPUSCULAR("crepuscular", new ImmutablePair<>(12000, 13702), new ImmutablePair<>(22300, 24000)),
    CATHEMERAL("cathemeral", new ImmutablePair<>(-1, -1)),
    NEVER_SLEEPS("never_sleeps", new ImmutablePair<>(-1, 24001));

    public final String name;
    public final Pair<Integer, Integer>[] activeTimes;

    @SafeVarargs
    EnumActiveTime(String name, Pair<Integer, Integer>... activeTimes) {
        this.name = name;
        this.activeTimes = activeTimes;
    }

    public static EnumActiveTime getFromString(String name) {
        for (EnumActiveTime value : values()) {
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
        return Component.translatable("gene.complicated_bees.active_time." + this.toString());
    }
}
