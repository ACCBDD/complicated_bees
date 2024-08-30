package com.accbdd.complicated_bees.genetics.effect;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class PotionEffect implements IBeeEffect {
    private final MobEffectInstance effect;
    private final int rate;

    public PotionEffect(MobEffectInstance effect, int rate) {
        this.effect = effect;
        this.rate = rate;
    }

    @Override
    public void runEffect(BlockEntity blockEntity, ItemStack queen, int cycleProgress) {
        if (cycleProgress % Math.max(0, rate) == 0) {
            Vec3 center = blockEntity.getBlockPos().getCenter();
            Vec3 offset = new Vec3(3, 3, 3);
            for (Entity entity : blockEntity.getLevel().getEntities(null, new AABB(center.add(offset), center.subtract(offset)))) {
                if (entity instanceof LivingEntity living) {
                    living.addEffect(new MobEffectInstance(effect));
                }
            }
        }
    }
}
