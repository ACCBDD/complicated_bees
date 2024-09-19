package com.accbdd.complicated_bees.genetics.effect;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class PotionEffect extends BeeEffect {
    private final MobEffectInstance effect;
    private final int rate;

    public PotionEffect(MobEffectInstance effect, int rate) {
        this.effect = effect;
        this.rate = rate;
    }

    @Override
    public void runEffect(BlockEntity apiary, ItemStack queen, int cycleProgress) {
        if (apiary.getLevel() == null) return;

        if (cycleProgress % Math.max(0, rate) == 0) {
            for (Entity entity : getTerritoryEntities(apiary, queen)) {
                if (entity instanceof LivingEntity living) {
                    living.addEffect(new MobEffectInstance(effect));
                }
            }
        }
    }
}
