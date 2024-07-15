package com.accbdd.complicated_bees.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;


public record ComplicatedHiveFeatureConfiguration(String speciesKey) implements FeatureConfiguration {
    public static final Codec<ComplicatedHiveFeatureConfiguration> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(Codec.STRING.fieldOf("species").forGetter(ComplicatedHiveFeatureConfiguration::speciesKey))
                    .apply(instance, ComplicatedHiveFeatureConfiguration::new)
    );
}
