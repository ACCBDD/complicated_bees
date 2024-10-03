package com.accbdd.complicated_bees.datagen.loot;

import com.accbdd.complicated_bees.loot.InheritHiveCombFunction;
import com.accbdd.complicated_bees.loot.InheritHiveSpeciesFunction;
import com.accbdd.complicated_bees.registry.BlocksRegistration;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
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
import net.minecraftforge.registries.RegistryObject;

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
        dropSelf(BlocksRegistration.WAX_BLOCK.get());
        dropSelf(BlocksRegistration.WAX_BLOCK_STAIRS.get());
        this.add(BlocksRegistration.WAX_BLOCK_SLAB.get(), createSlabItemTable(BlocksRegistration.WAX_BLOCK_SLAB.get()));
        dropSelf(BlocksRegistration.WAX_BLOCK_WALL.get());
        dropSelf(BlocksRegistration.SMOOTH_WAX.get());
        dropSelf(BlocksRegistration.SMOOTH_WAX_STAIRS.get());
        this.add(BlocksRegistration.SMOOTH_WAX_SLAB.get(), createSlabItemTable(BlocksRegistration.SMOOTH_WAX_SLAB.get()));
        dropSelf(BlocksRegistration.SMOOTH_WAX_WALL.get());
        dropSelf(BlocksRegistration.WAX_BRICKS.get());
        dropSelf(BlocksRegistration.WAX_BRICK_STAIRS.get());
        this.add(BlocksRegistration.WAX_BRICK_SLAB.get(), createSlabItemTable(BlocksRegistration.WAX_BRICK_SLAB.get()));
        dropSelf(BlocksRegistration.WAX_BRICK_WALL.get());
        dropSelf(BlocksRegistration.CHISELED_WAX.get());
        dropSelf(BlocksRegistration.HONEYED_PLANKS.get());
        dropSelf(BlocksRegistration.HONEYED_STAIRS.get());
        this.add(BlocksRegistration.HONEYED_SLAB.get(), createSlabItemTable(BlocksRegistration.HONEYED_SLAB.get()));
        dropSelf(BlocksRegistration.HONEYED_FENCE.get());
        dropSelf(BlocksRegistration.HONEYED_FENCE_GATE.get());
        dropSelf(BlocksRegistration.HONEYED_BUTTON.get());
        dropSelf(BlocksRegistration.HONEYED_PRESSURE_PLATE.get());
        this.add(BlocksRegistration.HONEYED_DOOR.get(), createDoorTable(BlocksRegistration.HONEYED_DOOR.get()));
        dropSelf(BlocksRegistration.HONEYED_TRAPDOOR.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BlocksRegistration.BLOCKS.getEntries()
                .stream()
                .map(RegistryObject::get)
                .toList();
    }

    public LootTable.Builder nestLootTable(Block beenest) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .when(HAS_SILK_TOUCH)
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(
                                LootItem.lootTableItem(beenest).apply(CopyNbtFunction
                                        .copyData(ContextNbtProvider.BLOCK_ENTITY)
                                        .copy("species", "BlockEntityTag.species", CopyNbtFunction.MergeStrategy.REPLACE)
                                )
                        ))
                .withPool(LootPool.lootPool()
                        .when(HAS_NO_SILK_TOUCH)
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(
                                LootItem.lootTableItem(ItemsRegistration.PRINCESS.get()).apply(InheritHiveSpeciesFunction.set())
                        ))
                .withPool(LootPool.lootPool()
                        .when(HAS_NO_SILK_TOUCH)
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(
                                LootItem.lootTableItem(ItemsRegistration.DRONE.get())
                                        .apply(InheritHiveSpeciesFunction.set())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))
                                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 1))
                        ))
                .withPool(LootPool.lootPool()
                        .when(HAS_NO_SILK_TOUCH)
                        .setRolls(BinomialDistributionGenerator.binomial(1, 0.35f))
                        .add(
                                LootItem.lootTableItem(ItemsRegistration.COMB.get())
                                        .apply(InheritHiveCombFunction.set())
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f)))
                                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 1))
                        )
                );
    }
}
