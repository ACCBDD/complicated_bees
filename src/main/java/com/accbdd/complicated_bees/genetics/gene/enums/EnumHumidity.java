package com.accbdd.complicated_bees.genetics.gene.enums;

import com.accbdd.complicated_bees.ComplicatedBees;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

public enum EnumHumidity {
    DRY("dry"),
    NORMAL("normal"),
    WET("wet");

    public final String name;

    EnumHumidity(String name) {
        this.name = name;
    }

    public static EnumHumidity getFromValue(float downfall) {
        if (downfall > 0.85f) {
            return WET;
        } else if (downfall > 0.3f) {
            return NORMAL;
        } else {
            return DRY;
        }
    }

    public static EnumHumidity getFromBiome(Holder<Biome> biome) {
        return getFromValue(biome.value().getModifiedClimateSettings().downfall());
    }

    public static EnumHumidity getFromPosition(Level level, BlockPos pos) {
        return getFromBiome(level.getBiome(pos));
    }

    public static EnumHumidity getFromString(String str) {
        return switch (str.toLowerCase()) {
            case "wet" -> WET;
            case "normal" -> NORMAL;
            case "dry" -> DRY;
            default -> {
                ComplicatedBees.LOGGER.warn("tried to convert unknown string {} to humidity; returning normal", str);
                yield NORMAL;
            }
        };
    }

    @Override
    public String toString() {
        return this.name;
    }

    public MutableComponent getTranslationKey() {
        return Component.translatable("gui.complicated_bees.humidity." + this.toString());
    }
}
