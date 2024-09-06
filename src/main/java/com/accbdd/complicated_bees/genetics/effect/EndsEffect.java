package com.accbdd.complicated_bees.genetics.effect;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EndsEffect implements IBeeEffect {
    @Override
    public void runEffect(BlockEntity blockEntity, ItemStack queen, int cycleProgress) {
        if (cycleProgress % 80 == 0) {
            Vec3 center = blockEntity.getBlockPos().getCenter();
            Vec3 offset = new Vec3(3, 3, 3);
            for (Entity entity : blockEntity.getLevel().getEntities(null, new AABB(center.add(offset), center.subtract(offset)))) {
                if (entity instanceof Player player) {
                    player.hurt(blockEntity.getLevel().damageSources().generic(), 6);
                }
            }
        }
    }
}
