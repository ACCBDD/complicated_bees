package com.accbdd.complicated_bees.genetics.mutation.condition;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public class DaytimeCondition extends MutationCondition {
    public static String ID = "daytime";

    public DaytimeCondition() {

    }

    @Override
    public boolean check(Level level, BlockPos pos) {
        return level.isDay();
    }

    @Override
    public Component getDescription() {
        return Component.translatable("gui.complicated_bees.mutations.daytime");
    }

    @Override
    public CompoundTag serialize() {
        return new CompoundTag();
    }

    @Override
    public DaytimeCondition deserialize(CompoundTag tag) {
        return new DaytimeCondition();
    }
}
