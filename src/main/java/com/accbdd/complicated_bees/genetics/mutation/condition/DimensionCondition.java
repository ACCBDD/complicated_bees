package com.accbdd.complicated_bees.genetics.mutation.condition;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class DimensionCondition extends MutationCondition {
    public static String ID = "dimension";
    private final ResourceLocation dimension;

    public DimensionCondition(ResourceLocation dimension) {
        this.dimension = dimension;
    }

    @Override
    public boolean check(Level level, BlockPos pos) {
        return level.dimension().location().equals(dimension);
    }

    @Override
    public Component getDescription() {
        return Component.translatable("gui.complicated_bees.mutations.dimension", dimension);
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        tag.put("dimension", StringTag.valueOf(dimension.toString()));
        return tag;
    }

    @Override
    public DimensionCondition deserialize(CompoundTag tag) {
        return new DimensionCondition(ResourceLocation.tryParse(tag.getString("dimension")));
    }
}
