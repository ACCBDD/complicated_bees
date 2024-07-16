package com.accbdd.complicated_bees.datagen.loot;

import com.accbdd.complicated_bees.datagen.ItemTagGenerator;
import com.accbdd.complicated_bees.loot.InheritHiveSpeciesFunction;
import com.accbdd.complicated_bees.registry.BlocksRegistration;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Collections;

public class BlockLootTables extends BlockLootSubProvider {
    public BlockLootTables() {
        super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(BlocksRegistration.APIARY.get());
        dropSelf(BlocksRegistration.CENTRIFUGE.get());
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
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                        .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5f, 1))
                        )
                );
    }
}
