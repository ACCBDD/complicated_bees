package com.accbdd.complicated_bees.genetics.effect;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IBeeEffect {

    void runEffect(BlockEntity entity, ItemStack queen, int cycleProgress);
}
