package com.accbdd.complicated_bees.genetics.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IBeeEffect {

    void runEffect(Level level, BlockPos pos, ItemStack queen, int cycleProgress);
}
