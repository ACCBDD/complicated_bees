package com.accbdd.complicated_bees.genetics.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class PotionEffect implements IBeeEffect {
    private final MobEffect effect;
    private final int duration;

    public PotionEffect(MobEffect effect, int duration) {
        this.effect = effect;
        this.duration = duration;
    }

    @Override
    public void runEffect(BlockEntity blockEntity, ItemStack queen, int cycleProgress) {
        if (cycleProgress % Math.max(0, duration - 20) == 0) {
            Vec3 center = blockEntity.getBlockPos().getCenter();
            Vec3 offset = new Vec3(3, 3, 3);
            for (Entity entity : blockEntity.getLevel().getEntities(null, new AABB(center.add(offset), center.subtract(offset)))) {
                if (entity instanceof LivingEntity living) {
                    living.addEffect(new MobEffectInstance(effect, duration));
                }
            }
        }
    }
}
