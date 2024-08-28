package com.accbdd.complicated_bees.genetics.effect;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ChampionEffect implements IBeeEffect {
    @Override
    public void runEffect(BlockEntity blockEntity, ItemStack queen, int cycleProgress) {
        if (cycleProgress == 0) {
            Vec3 center = blockEntity.getBlockPos().getCenter();
            Vec3 offset = new Vec3(3, 3, 3);
            for (Entity entity : blockEntity.getLevel().getEntities(null, new AABB(center.add(offset), center.subtract(offset)))) {
                if (entity instanceof Monster monster) {
                    monster.hurt(blockEntity.getLevel().damageSources().generic(), 2);
                }
            }
        }
    }
}
