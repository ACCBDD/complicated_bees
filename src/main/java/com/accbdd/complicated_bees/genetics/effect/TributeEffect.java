package com.accbdd.complicated_bees.genetics.effect;

import com.accbdd.complicated_bees.block.entity.ApiaryBlockEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public class TributeEffect implements IBeeEffect {
    @Override
    public void runEffect(BlockEntity blockEntity, ItemStack queen, int cycleProgress) {
        if (cycleProgress == 0) {
            Vec3 center = blockEntity.getBlockPos().getCenter();
            Vec3 offset = new Vec3(3, 3, 3);
            ArrayList<LivingEntity> list = new ArrayList<>();
            for (Entity entity : blockEntity.getLevel().getEntities(null, new AABB(center.add(offset), center.subtract(offset)))) {
                if (entity instanceof LivingEntity living && living instanceof Animal) {
                    list.add(living);
                }
            }
            if (!list.isEmpty() && blockEntity.getLevel().random.nextFloat() < 0.1f) {
                LivingEntity collectFrom = list.get(blockEntity.getLevel().random.nextInt(list.size()));
                ServerLevel level = (ServerLevel) collectFrom.level();
                LootTable lootTable = level.getServer().getLootData().getLootTable(collectFrom.getLootTable());
                LootParams params = new LootParams.Builder(level)
                        .withParameter(LootContextParams.THIS_ENTITY, collectFrom)
                        .withParameter(LootContextParams.ORIGIN, collectFrom.position())
                        .withParameter(LootContextParams.DAMAGE_SOURCE, level.damageSources().generic()).create(LootContextParamSets.ENTITY);
                ItemStack stack = lootTable.getRandomItems(params).get(0);
                stack.setCount(1);
                ((ApiaryBlockEntity) blockEntity).addToOutput(stack);
            }
        }
    }
}
