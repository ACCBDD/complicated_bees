package com.accbdd.complicated_bees.genetics.gene;

import com.accbdd.complicated_bees.ComplicatedBees;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public enum EnumTemperature {
    FROZEN("Frozen"),
    ICY("Icy"),
    COLD("Cold"),
    NORMAL("Normal"),
    WARM("Warm"),
    HOT("Hot"),
    HELLISH("Hellish");

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
        switch (str.toLowerCase()) {
            case "hellish":
                return HELLISH;
            case "hot":
                return HOT;
            case "warm":
                return WARM;
            case "normal":
                return NORMAL;
            case "cold":
                return COLD;
            case "icy":
                return ICY;
            case "frozen":
                return FROZEN;
            default:
                ComplicatedBees.LOGGER.warn("tried to convert unknown string {} to temperature; returning normal", str);
                return NORMAL;
        }
    }

    @Override
    public String toString() {
        return this.name;
    }
}
