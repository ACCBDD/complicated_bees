package com.accbdd.complicated_bees.worldgen;

import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.registry.BlockEntitiesRegistration;
import com.accbdd.complicated_bees.registry.BlocksRegistration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class ComplicatedHiveFeature extends Feature<ComplicatedHiveFeatureConfiguration> {
    public ComplicatedHiveFeature(Codec<ComplicatedHiveFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ComplicatedHiveFeatureConfiguration> context) {
        ComplicatedHiveFeatureConfiguration config = context.config();
        WorldGenLevel worldgenlevel = context.level();
        BlockPos blockpos = context.origin();

        worldgenlevel.setBlock(blockpos, BlocksRegistration.BEE_NEST.get().defaultBlockState(), 2);
        context.level().getBlockEntity(blockpos, BlockEntitiesRegistration.BEE_NEST_ENTITY.get()).ifPresent(be -> {
            be.setSpecies(Species.getFromResourceLocation(ResourceLocation.tryParse(config.speciesKey())));
        });

        return true;
    }
}
