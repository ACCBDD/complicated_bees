package com.accbdd.complicated_bees.registry;

import com.accbdd.complicated_bees.loot.InheritHiveSpeciesFunction;
import com.accbdd.complicated_bees.recipe.CentrifugeRecipe;
import com.accbdd.complicated_bees.util.SpeciesDiscoveredSerializer;
import com.accbdd.complicated_bees.worldgen.ComplicatedBeenestDecorator;
import com.accbdd.complicated_bees.worldgen.ComplicatedHiveFeature;
import com.accbdd.complicated_bees.worldgen.ComplicatedHiveFeatureConfiguration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class EsotericRegistration {
    public static final DeferredRegister<LootItemFunctionType> LOOT_ITEM_FUNCTION_REGISTER = DeferredRegister.create(BuiltInRegistries.LOOT_FUNCTION_TYPE, MODID);
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_REGISTER = DeferredRegister.create(BuiltInRegistries.TREE_DECORATOR_TYPE, MODID);
    public static final DeferredRegister<Feature<?>> FEATURE_REGISTER = DeferredRegister.create(BuiltInRegistries.FEATURE, MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPE_REGISTER = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER_REGISTER = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, MODID);
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPE_REGISTER = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MODID);

    public static final Supplier<LootItemFunctionType> INHERIT_HIVE = LOOT_ITEM_FUNCTION_REGISTER.register("inherit_hive_species",
            () -> new LootItemFunctionType(InheritHiveSpeciesFunction.CODEC));

    public static final Supplier<TreeDecoratorType<ComplicatedBeenestDecorator>> COMPLICATED_BEENEST_DECORATOR = TREE_DECORATOR_REGISTER.register("bee_nest_decorator",
            () -> new TreeDecoratorType<>(ComplicatedBeenestDecorator.CODEC));

    public static final Supplier<ComplicatedHiveFeature> COMPLICATED_HIVE_FEATURE = FEATURE_REGISTER.register("complicated_bee_nest",
            () -> new ComplicatedHiveFeature(ComplicatedHiveFeatureConfiguration.CODEC));

    public static final Supplier<RecipeType<CentrifugeRecipe>> CENTRIFUGE_RECIPE = RECIPE_TYPE_REGISTER.register("centrifuge",
            () -> RecipeType.simple(new ResourceLocation(MODID, "centrifuge")));

    public static final Supplier<RecipeSerializer<CentrifugeRecipe>> CENTRIFUGE_RECIPE_SERIALIZER = RECIPE_SERIALIZER_REGISTER.register("centrifuge",
            () -> CentrifugeRecipe.SERIALIZER);

    //todo: implement?
    public static final Supplier<AttachmentType<HashSet<String>>> SPECIES_DISCOVERED_ATTACHMENT = ATTACHMENT_TYPE_REGISTER.register("species_discovered",
            () -> AttachmentType.builder(() -> new HashSet<String>()).serialize(new SpeciesDiscoveredSerializer()).build());
}
