package com.accbdd.complicated_bees.genetics.mutation.condition;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public class DownfallCondition extends MutationCondition {
    public static String ID = "downfall";

    public DownfallCondition() {

    }

    @Override
    public boolean check(Level level, BlockPos pos) {
        return level.isRainingAt(pos);
    }

    @Override
    public Component getDescription() {
        return Component.translatable("gui.complicated_bees.mutations.downfall");
    }

    @Override
    public CompoundTag serialize() {
        return new CompoundTag();
    }

    @Override
    public DownfallCondition deserialize(CompoundTag tag) {
        return new DownfallCondition();
    }
}
