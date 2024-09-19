package com.accbdd.complicated_bees.genetics.effect;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IBeeEffect {

    /**
     * Runs a bee effect.
     *
     * @param apiary        the BlockEntity generating this effect
     * @param queen         the queen generating this effect
     * @param cycleProgress the number of ticks since the last bee cycle
     */
    void runEffect(BlockEntity apiary, ItemStack queen, int cycleProgress);
}
