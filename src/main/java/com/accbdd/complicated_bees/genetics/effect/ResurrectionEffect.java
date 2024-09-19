package com.accbdd.complicated_bees.genetics.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.List;

public class ResurrectionEffect extends BeeEffect {
    @Override
    public void runEffect(BlockEntity apiary, ItemStack queen, int cycleProgress) {
        if (apiary.getLevel() == null) return;

        if (cycleProgress == 0) {
            ServerLevel level = (ServerLevel) apiary.getLevel();
            List<ItemEntity> itemEntities = new ArrayList<>();
            for (Entity entity : getTerritoryEntities(apiary, queen)) {
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
                ItemEntity chosen = itemEntities.get(apiary.getLevel().random.nextInt(itemEntities.size()));
                ItemStack chosenStack = chosen.getItem();
                if (chosenStack.is(Items.BONE)) {
                    EntityType.SKELETON.spawn(level, chosen.blockPosition(), MobSpawnType.SPAWNER);
                } else if (chosenStack.is(Items.ROTTEN_FLESH)) {
                    EntityType.ZOMBIE.spawn(level, chosen.blockPosition(), MobSpawnType.SPAWNER);
                } else if (chosenStack.is(Items.GUNPOWDER)) {
                    EntityType.CREEPER.spawn(level, chosen.blockPosition(), MobSpawnType.SPAWNER);
                } else if (chosenStack.is(Items.PORKCHOP)) {
                    EntityType.PIG.spawn(level, chosen.blockPosition(), MobSpawnType.SPAWNER);
                } else if (chosenStack.is(Items.BEEF)) {
                    EntityType.COW.spawn(level, chosen.blockPosition(), MobSpawnType.SPAWNER);
                } else if (chosenStack.is(Items.CHICKEN)) {
                    EntityType.CHICKEN.spawn(level, chosen.blockPosition(), MobSpawnType.SPAWNER);
                } else if (chosenStack.is(Items.MUTTON)) {
                    EntityType.SHEEP.spawn(level, chosen.blockPosition(), MobSpawnType.SPAWNER);
                } else if (chosenStack.is(Items.SLIME_BALL)) {
                    EntityType.SLIME.spawn(level, chosen.blockPosition(), MobSpawnType.SPAWNER);
                } else if (chosenStack.is(Items.RABBIT)) {
                    EntityType.RABBIT.spawn(level, chosen.blockPosition(), MobSpawnType.SPAWNER);
                } else if (chosenStack.is(Items.ENDER_PEARL)) {
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
