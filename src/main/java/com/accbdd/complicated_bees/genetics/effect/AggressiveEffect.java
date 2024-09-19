package com.accbdd.complicated_bees.genetics.effect;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class AggressiveEffect extends BeeEffect {
    @Override
    public void runEffect(BlockEntity apiary, ItemStack queen, int cycleProgress) {
        if (apiary.getLevel() == null) return;

        if (cycleProgress % 80 == 0) {
            for (Entity entity : getTerritoryEntities(apiary, queen)) {
                if (entity instanceof LivingEntity living) {
                    living.hurt(apiary.getLevel().damageSources().generic(), 2);
                }
            }
        }
    }
}
