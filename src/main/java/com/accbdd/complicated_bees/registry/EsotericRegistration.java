package com.accbdd.complicated_bees.registry;

import com.accbdd.complicated_bees.datagen.loot.InheritHiveSpeciesFunction;
import com.accbdd.complicated_bees.worldgen.ComplicatedBeenestDecorator;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class EsotericRegistration {
    public static final DeferredRegister<LootItemFunctionType> LOOT_ITEM_FUNCTION_REGISTER = DeferredRegister.create(BuiltInRegistries.LOOT_FUNCTION_TYPE, MODID);
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_REGISTER = DeferredRegister.create(BuiltInRegistries.TREE_DECORATOR_TYPE, MODID);

    public static final Supplier<LootItemFunctionType> INHERIT_HIVE = LOOT_ITEM_FUNCTION_REGISTER.register("inherit_hive_species",
            () -> new LootItemFunctionType(InheritHiveSpeciesFunction.CODEC));

    public static final Supplier<TreeDecoratorType<ComplicatedBeenestDecorator>> COMPLICATED_BEENEST_DECORATOR = TREE_DECORATOR_REGISTER.register("bee_nest",
            () -> new TreeDecoratorType<>(ComplicatedBeenestDecorator.CODEC));
}
