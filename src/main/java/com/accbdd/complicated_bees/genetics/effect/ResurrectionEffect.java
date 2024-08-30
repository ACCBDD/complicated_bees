package com.accbdd.complicated_bees.genetics.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class ResurrectionEffect implements IBeeEffect {
    @Override
    public void runEffect(BlockEntity blockEntity, ItemStack queen, int cycleProgress) {
        if (cycleProgress == 0) {
            ServerLevel level = (ServerLevel) blockEntity.getLevel();
            if (level == null) return;
            List<ItemEntity> itemEntities = new ArrayList<>();
            Vec3 center = blockEntity.getBlockPos().getCenter();
            Vec3 offset = new Vec3(3, 3, 3);
            for (Entity entity : level.getEntities(null, new AABB(center.add(offset), center.subtract(offset)))) {
                if (entity instanceof ItemEntity itemEntity) {
                    ItemStack stack = itemEntity.getItem();
                    if (stack.is(Items.BONE) ||
                            stack.is(Items.ROTTEN_FLESH) ||
                            stack.is(Items.GUNPOWDER) ||
                            stack.is(Items.PORKCHOP) ||
                            stack.is(Items.BEEF) ||
                            stack.is(Items.CHICKEN) ||
                            stack.is(Items.MUTTON) ||
                            stack.is(Items.SLIME_BALL) ||
                            stack.is(Items.RABBIT) ||
                            stack.is(Items.ENDER_PEARL) ||
                            stack.is(Items.BLAZE_ROD) ||
                            stack.is(Items.GHAST_TEAR))
                        itemEntities.add(itemEntity);
                }
            }
            if (!itemEntities.isEmpty()) {
                ItemEntity chosen = itemEntities.get(blockEntity.getLevel().random.nextInt(itemEntities.size()));
                ItemStack chosenStack = chosen.getItem();
                if (chosenStack.is(Items.BONE)) {
                    EntityType.SKELETON.spawn(level, chosen.blockPosition(), MobSpawnType.SPAWNER);
                } else if (chosenStack.is(Items.ROTTEN_FLESH)){
                    EntityType.ZOMBIE.spawn(level, chosen.blockPosition(), MobSpawnType.SPAWNER);
                } else if (chosenStack.is(Items.GUNPOWDER)) {
                    EntityType.CREEPER.spawn(level, chosen.blockPosition(), MobSpawnType.SPAWNER);
                } else if (chosenStack.is(Items.PORKCHOP)) {
                    EntityType.PIG.spawn(level, chosen.blockPosition(), MobSpawnType.SPAWNER);
                } else if (chosenStack.is(Items.BEEF)) {
                    EntityType.COW.spawn(level, chosen.blockPosition(), MobSpawnType.SPAWNER);
                } else if (chosenStack.is(Items.CHICKEN)) {
                    EntityType.CHICKEN.spawn(level, chosen.blockPosition(), MobSpawnType.SPAWNER);
                } else if (chosenStack.is(Items.MUTTON)){
                    EntityType.SHEEP.spawn(level, chosen.blockPosition(), MobSpawnType.SPAWNER);
                } else if (chosenStack.is(Items.SLIME_BALL)){
                    EntityType.SLIME.spawn(level, chosen.blockPosition(), MobSpawnType.SPAWNER);
                } else if (chosenStack.is(Items.RABBIT)) {
                    EntityType.RABBIT.spawn(level, chosen.blockPosition(), MobSpawnType.SPAWNER);
                } else if (chosenStack.is(Items.ENDER_PEARL)){
                    EntityType.ENDERMAN.spawn(level, chosen.blockPosition(), MobSpawnType.SPAWNER);
                } else if (chosenStack.is(Items.BLAZE_ROD)) {
                    EntityType.BLAZE.spawn(level, chosen.blockPosition(), MobSpawnType.SPAWNER);
                } else if (chosenStack.is(Items.GHAST_TEAR)) {
                    EntityType.GHAST.spawn(level, chosen.blockPosition(), MobSpawnType.SPAWNER);
                }
                chosenStack.shrink(1);
            }
        }
    }
}
