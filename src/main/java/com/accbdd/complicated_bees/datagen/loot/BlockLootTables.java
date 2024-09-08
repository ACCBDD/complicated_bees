package com.accbdd.complicated_bees.datagen.loot;

import com.accbdd.complicated_bees.loot.InheritHiveCombFunction;
import com.accbdd.complicated_bees.loot.InheritHiveSpeciesFunction;
import com.accbdd.complicated_bees.registry.BlocksRegistration;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import net.minecraft.core.Holder;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Collections;

public class BlockLootTables extends BlockLootSubProvider {
    public BlockLootTables() {
        super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(BlocksRegistration.APIARY.get());
        dropSelf(BlocksRegistration.CENTRIFUGE.get());
        dropSelf(BlocksRegistration.GENERATOR.get());
        this.add(BlocksRegistration.BEE_NEST.get(), nestLootTable(BlocksRegistration.BEE_NEST.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BlocksRegistration.BLOCKS.getEntries()
                .stream()
                .map(Holder::value)
                .toList();
    }

    protected static LootTable.Builder nestLootTable(Block beehive) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .when(HAS_SILK_TOUCH)
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(
                                LootItem.lootTableItem(beehive).apply(CopyNbtFunction
                                        .copyData(ContextNbtProvider.BLOCK_ENTITY)
                                        .copy("species", "BlockEntityTag.species", CopyNbtFunction.MergeStrategy.REPLACE)
                                )
                        ))
                .withPool(LootPool.lootPool()
                        .when(HAS_NO_SILK_TOUCH)
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(
                                LootItem.lootTableItem(ItemsRegistration.PRINCESS).apply(InheritHiveSpeciesFunction.set())
                        ))
                .withPool(LootPool.lootPool()
                        .when(HAS_NO_SILK_TOUCH)
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(
                                LootItem.lootTableItem(ItemsRegistration.DRONE)
                                        .apply(InheritHiveSpeciesFunction.set())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))
                                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 1))
                        ))
                .withPool(LootPool.lootPool()
                        .when(HAS_NO_SILK_TOUCH)
                        .setRolls(BinomialDistributionGenerator.binomial(1, 0.35f))
                        .add(
                                LootItem.lootTableItem(ItemsRegistration.COMB)
                                        .apply(InheritHiveCombFunction.set())
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f)))
                                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 1))
                        )
                );
    }
}
