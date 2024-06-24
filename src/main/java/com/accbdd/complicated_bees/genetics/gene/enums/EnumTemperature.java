package com.accbdd.complicated_bees.genetics.gene.enums;

import com.accbdd.complicated_bees.ComplicatedBees;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

public enum EnumTemperature {
    FROZEN("frozen"),
    ICY("icy"),
    COLD("cold"),
    NORMAL("normal"),
    WARM("warm"),
    HOT("hot"),
    HELLISH("hellish");

    public static final EnumTemperature[] VALUES = values();

    public final String name;

    EnumTemperature(String name) {
        this.name = name;
    }

    public static EnumTemperature getFromValue(float temp) {
        if (temp > 1f) {
            return HOT;
        } else if (temp > 0.85f) {
            return WARM;
        } else if (temp > 0.35f) {
            return NORMAL;
        } else if (temp > 0.0f) {
            return COLD;
        } else if (temp > -0.35f){
            return ICY;
        } else {
            return FROZEN;
        }
    }

    public static EnumTemperature getFromBiome(Holder<Biome> biome) {
        return biome.is(BiomeTags.IS_NETHER) ? HELLISH : getFromValue(biome.value().getBaseTemperature());
    }

    public static EnumTemperature getFromPosition(Level level, BlockPos pos) {
        return getFromBiome(level.getBiome(pos));
    }

    public static EnumTemperature getFromString(String str) {
        return switch (str.toLowerCase()) {
            case "hellish" -> HELLISH;
            case "hot" -> HOT;
            case "warm" -> WARM;
            case "normal" -> NORMAL;
            case "cold" -> COLD;
            case "icy" -> ICY;
            case "frozen" -> FROZEN;
            default -> {
                ComplicatedBees.LOGGER.warn("tried to convert unknown string {} to temperature; returning normal", str);
                yield NORMAL;
            }
        };
    }

    @Override
    public String toString() {
        return this.name;
    }
}
