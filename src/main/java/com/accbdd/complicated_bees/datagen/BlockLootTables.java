package com.accbdd.complicated_bees.datagen;

import com.accbdd.complicated_bees.genetics.GeneticHelper;
import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.registry.BlocksRegistration;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Collections;

public class BlockLootTables extends BlockLootSubProvider {
    protected BlockLootTables() {
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
                .withPool(LootPool.lootPool().when(HAS_SILK_TOUCH).setRolls(ConstantValue.exactly(1.0f)).add(LootItem.lootTableItem(beehive)))
                .withPool(LootPool.lootPool()
                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.create(new ResourceLocation("complicated_bees:scoop_tool")))))
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(
                                LootItem.lootTableItem(ItemsRegistration.PRINCESS)//.apply(SetNbtFunction.setTag(tag))
                        )
                );
    }
}
