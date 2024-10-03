package com.accbdd.complicated_bees.datagen;

import com.accbdd.complicated_bees.datagen.condition.ItemEnabledCondition;
import com.accbdd.complicated_bees.registry.BlocksRegistration;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {

    public RecipeGenerator(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> output) {
        frameRecipe(output, ItemsRegistration.FRAME.get(), Ingredient.of(Tags.Items.STRING), Ingredient.of(Tags.Items.RODS_WOODEN));
        frameRecipe(output, ItemsRegistration.WAXED_FRAME.get(), Ingredient.of(Tags.Items.STRING), Ingredient.of(ItemsRegistration.WAXED_STICK.get()), ItemsRegistration.WAXED_STICK.get());
        frameRecipe(output, ItemsRegistration.HONEYED_FRAME.get(), Ingredient.of(Tags.Items.STRING), Ingredient.of(ItemsRegistration.HONEYED_STICK.get()), ItemsRegistration.HONEYED_STICK.get());
        frameRecipe(output, ItemsRegistration.TWISTING_FRAME.get(), Ingredient.of(Items.SOUL_SAND, Items.SOUL_SOIL), Ingredient.of(ItemsRegistration.WAXED_STICK.get()), ItemsRegistration.WAXED_STICK.get());
        frameRecipe(output, ItemsRegistration.SOOTHING_FRAME.get(), Ingredient.of(ItemsRegistration.ROYAL_JELLY.get()), Ingredient.of(ItemsRegistration.HONEYED_STICK.get()), ItemsRegistration.HONEYED_STICK.get());
        frameRecipe(output, ItemsRegistration.RESTRICTIVE_FRAME.get(), Ingredient.of(Items.CHAIN), Ingredient.of(ItemsRegistration.WAXED_STICK.get()), ItemsRegistration.WAXED_STICK.get());
        frameRecipe(output, ItemsRegistration.DRY_FRAME.get(), Ingredient.of(ItemTags.SAND), Ingredient.of(ItemsRegistration.WAXED_STICK.get()), ItemsRegistration.WAXED_STICK.get());
        frameRecipe(output, ItemsRegistration.WET_FRAME.get(), Ingredient.of(Items.WATER_BUCKET), Ingredient.of(ItemsRegistration.WAXED_STICK.get()), ItemsRegistration.WAXED_STICK.get());
        frameRecipe(output, ItemsRegistration.HOT_FRAME.get(), Ingredient.of(Items.MAGMA_BLOCK), Ingredient.of(Items.NETHER_BRICK), ItemsRegistration.WAXED_STICK.get());
        frameRecipe(output, ItemsRegistration.COLD_FRAME.get(), Ingredient.of(Items.BLUE_ICE), Ingredient.of(ItemsRegistration.WAXED_STICK.get()), ItemsRegistration.WAXED_STICK.get());
        deadlyFrame(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STRING)
                .requires(ItemsRegistration.SILK_WISP.get(), 3)
                .unlockedBy(getHasName(ItemsRegistration.SILK_WISP.get()), has(ItemsRegistration.SILK_WISP.get()))
                .save(output, "complicated_bees:string_from_silk");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemsRegistration.HONEYED_STICK.get())
                .pattern("###")
                .pattern("#H#")
                .pattern("###")
                .define('H', ItemsRegistration.HONEY_DROPLET.get())
                .define('#', Tags.Items.RODS_WOODEN)
                .unlockedBy(getHasName(ItemsRegistration.HONEY_DROPLET.get()), has(ItemsRegistration.HONEY_DROPLET.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemsRegistration.WAXED_STICK.get())
                .requires(ItemsRegistration.BEESWAX.get(), 1)
                .requires(Ingredient.of(Tags.Items.RODS_WOODEN), 1)
                .unlockedBy(getHasName(ItemsRegistration.BEESWAX.get()), has(ItemsRegistration.BEESWAX.get()))
                .save(output);
        enabledRecipe(ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemsRegistration.HONEY_BREAD.get())
                .requires(Items.BREAD)
                .requires(ItemsRegistration.HONEY_DROPLET.get(), 4)
                .unlockedBy(getHasName(ItemsRegistration.HONEY_DROPLET.get()), has(ItemsRegistration.HONEY_DROPLET.get())), output);
        enabledRecipe(ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemsRegistration.HONEY_PORKCHOP.get())
                .requires(Items.COOKED_PORKCHOP)
                .requires(ItemsRegistration.HONEY_DROPLET.get(), 4)
                .unlockedBy(getHasName(ItemsRegistration.HONEY_DROPLET.get()), has(ItemsRegistration.HONEY_DROPLET.get())), output);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ItemsRegistration.AMBROSIA.get())
                .pattern("HHH")
                .pattern("PRP")
                .pattern("HHH")
                .define('H', ItemsRegistration.HONEY_DROPLET.get())
                .define('P', ItemsRegistration.POLLEN.get())
                .define('R', ItemsRegistration.ROYAL_JELLY.get())
                .unlockedBy(getHasName(ItemsRegistration.HONEY_DROPLET.get()), has(ItemsRegistration.HONEY_DROPLET.get()));
        enabledRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemsRegistration.BEE_STAFF.get())
                .pattern(" LJ")
                .pattern("PZL")
                .pattern("ZP ")
                .define('Z', Items.BLAZE_ROD)
                .define('P', ItemsRegistration.PROPOLIS.get())
                .define('J', ItemsRegistration.ROYAL_JELLY.get())
                .define('L', ItemsRegistration.POLLEN.get())
                .unlockedBy(getHasName(ItemsRegistration.ROYAL_JELLY.get()), has(ItemsRegistration.ROYAL_JELLY.get())), output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemsRegistration.ANALYZER.get())
                .pattern("IGI")
                .pattern("RWR")
                .pattern("ITI")
                .define('G', Tags.Items.GLASS_PANES)
                .define('I', Items.GOLD_INGOT)
                .define('W', Items.DIAMOND)
                .define('R', Items.REDSTONE)
                .define('T', Items.TRIPWIRE_HOOK)
                .unlockedBy("has_bee", has(ItemsRegistration.PRINCESS.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemsRegistration.METER.get())
                .pattern("IGI")
                .pattern("RWR")
                .pattern("ITI")
                .define('G', Tags.Items.GLASS_PANES)
                .define('I', Items.IRON_INGOT)
                .define('W', Items.REDSTONE_TORCH)
                .define('R', Items.REDSTONE)
                .define('T', Items.TRIPWIRE_HOOK)
                .unlockedBy("has_bee", has(ItemsRegistration.PRINCESS.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemsRegistration.SCOOP.get())
                .pattern("SWS")
                .pattern("SSS")
                .pattern(" S ")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('W', ItemTags.WOOL)
                .unlockedBy("has_wool", has(ItemTags.WOOL)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemsRegistration.APIARY.get())
                .pattern("PPP")
                .pattern("ICI")
                .pattern("PPP")
                .define('P', ItemTags.PLANKS)
                .define('I', Items.IRON_INGOT)
                .define('C', ItemsRegistration.COMB.get())
                .unlockedBy("has_bee", has(ItemsRegistration.PRINCESS.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemsRegistration.CENTRIFUGE.get())
                .pattern("III")
                .pattern("ICI")
                .pattern("SRS")
                .define('C', Items.CHEST)
                .define('I', Items.IRON_INGOT)
                .define('S', Items.SMOOTH_STONE)
                .define('R', Items.REDSTONE)
                .unlockedBy(getHasName(ItemsRegistration.COMB.get()), has(ItemsRegistration.COMB.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemsRegistration.GENERATOR.get())
                .pattern("CIC")
                .pattern("IBI")
                .pattern("SRS")
                .define('C', Items.COPPER_INGOT)
                .define('I', Items.IRON_INGOT)
                .define('B', Items.BLAST_FURNACE)
                .define('S', Items.SMOOTH_STONE)
                .define('R', Items.REDSTONE)
                .unlockedBy(getHasName(ItemsRegistration.COMB.get()), has(ItemsRegistration.COMB.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemsRegistration.WOVEN_MESH.get())
                .pattern("WWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('W', ItemsRegistration.SILK_WISP.get())
                .unlockedBy(getHasName(ItemsRegistration.SILK_WISP.get()), has(ItemsRegistration.SILK_WISP.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemsRegistration.APIARIST_HELMET.get())
                .pattern("MMM")
                .pattern("M M")
                .define('M', ItemsRegistration.WOVEN_MESH.get())
                .unlockedBy(getHasName(ItemsRegistration.SILK_WISP.get()), has(ItemsRegistration.SILK_WISP.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemsRegistration.APIARIST_CHESTPLATE.get())
                .pattern("M M")
                .pattern("MMM")
                .pattern("MMM")
                .define('M', ItemsRegistration.WOVEN_MESH.get())
                .unlockedBy(getHasName(ItemsRegistration.SILK_WISP.get()), has(ItemsRegistration.SILK_WISP.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemsRegistration.APIARIST_LEGGINGS.get())
                .pattern("MMM")
                .pattern("M M")
                .pattern("M M")
                .define('M', ItemsRegistration.WOVEN_MESH.get())
                .unlockedBy(getHasName(ItemsRegistration.SILK_WISP.get()), has(ItemsRegistration.SILK_WISP.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemsRegistration.APIARIST_BOOTS.get())
                .pattern("M M")
                .pattern("M M")
                .define('M', ItemsRegistration.WOVEN_MESH.get())
                .unlockedBy(getHasName(ItemsRegistration.SILK_WISP.get()), has(ItemsRegistration.SILK_WISP.get())).save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ItemsRegistration.HONEYED_PLANKS.get(), 8)
                .pattern("SSS")
                .pattern("SBS")
                .pattern("SSS")
                .define('S', ItemTags.PLANKS)
                .define('B', ItemsRegistration.HONEY_DROPLET.get())
                .unlockedBy(getHasName(ItemsRegistration.HONEY_DROPLET.get()), has(ItemsRegistration.HONEY_DROPLET.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ItemsRegistration.WAX_BLOCK.get())
                .pattern("SS")
                .pattern("SS")
                .define('S', ItemsRegistration.BEESWAX.get())
                .unlockedBy(getHasName(ItemsRegistration.BEESWAX.get()), has(ItemsRegistration.BEESWAX.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ItemsRegistration.WAX_BRICKS.get(), 4)
                .pattern("SS")
                .pattern("SS")
                .define('S', ItemsRegistration.WAX_BLOCK.get())
                .unlockedBy(getHasName(ItemsRegistration.WAX_BLOCK.get()), has(ItemsRegistration.WAX_BLOCK.get())).save(output);
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(ItemsRegistration.WAX_BLOCK.get()),
                        RecipeCategory.BUILDING_BLOCKS,
                        ItemsRegistration.SMOOTH_WAX.get(),
                        0.1f,
                        200)
                .unlockedBy(getHasName(ItemsRegistration.WAX_BLOCK.get()), has(ItemsRegistration.WAX_BLOCK.get())).save(output);
        generateRecipes(output, DataGenerators.HONEYED_PLANK_FAMILY);
        generateRecipes(output, DataGenerators.WAX_BLOCK_FAMILY);
        generateRecipes(output, DataGenerators.WAX_BRICK_FAMILY);
        generateRecipes(output, DataGenerators.SMOOTH_WAX_FAMILY);
        stonecutterFor(output, DataGenerators.WAX_BLOCK_FAMILY);
        stonecutterFor(output, DataGenerators.WAX_BRICK_FAMILY);
        stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, BlocksRegistration.WAX_BRICKS.get(), BlocksRegistration.WAX_BLOCK.get());
        stonecutterFor(output, DataGenerators.WAX_BRICK_FAMILY, BlocksRegistration.WAX_BLOCK.get());
        stonecutterFor(output, DataGenerators.SMOOTH_WAX_FAMILY);
        stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, BlocksRegistration.CHISELED_WAX.get(), BlocksRegistration.SMOOTH_WAX.get());
    }

    protected static void frameRecipe(Consumer<FinishedRecipe> output, ItemLike result, Ingredient center, Ingredient outside) {
        frameRecipe(output, result, center, outside, ItemsRegistration.APIARY.get());
    }

    protected static void frameRecipe(Consumer<FinishedRecipe> output, ItemLike result, Ingredient center, Ingredient outside, ItemLike unlockedBy) {
        var recipe = ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .pattern("OOO")
                .pattern("OXO")
                .pattern("OOO")
                .define('O', outside)
                .define('X', center)
                .unlockedBy(getHasName(unlockedBy), has(unlockedBy));
        ConditionalRecipe.builder()
                .addCondition(new ItemEnabledCondition(BuiltInRegistries.ITEM.getKey(result.asItem())))
                .addRecipe(recipe::save)
                .generateAdvancement()
                .build(output, ForgeRegistries.ITEMS.getKey(result.asItem()));
    }

    protected static void deadlyFrame(Consumer<FinishedRecipe> output) {
        var recipe = ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemsRegistration.DEADLY_FRAME.get())
                .pattern("OCO")
                .pattern("OXO")
                .pattern("OOO")
                .define('O', Items.OBSIDIAN)
                .define('X', Items.SKELETON_SKULL)
                .define('C', Items.CRYING_OBSIDIAN)
                .unlockedBy("has_apiary", has(ItemsRegistration.APIARY.get()));
        ConditionalRecipe.builder()
                .addCondition(new ItemEnabledCondition(ForgeRegistries.ITEMS.getKey(ItemsRegistration.DEADLY_FRAME.get().asItem())))
                .addRecipe(recipe::save)
                .generateAdvancement()
                .build(output, ForgeRegistries.ITEMS.getKey(ItemsRegistration.DEADLY_FRAME.get().asItem()));
    }

    protected static void enabledRecipe(RecipeBuilder builder, Consumer<FinishedRecipe> output) {
        ConditionalRecipe.builder()
                .addCondition(new ItemEnabledCondition(ForgeRegistries.ITEMS.getKey(builder.getResult())))
                .addRecipe(builder::save)
                .generateAdvancement()
                .build(output, ForgeRegistries.ITEMS.getKey(builder.getResult().asItem()));
    }

    protected static void stonecutterFor(Consumer<FinishedRecipe> output, BlockFamily family) {
        stonecutterFor(output, family, family.getBaseBlock());
    }

    protected static void stonecutterFor(Consumer<FinishedRecipe> output, BlockFamily family, Block base) {
        stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, family.get(BlockFamily.Variant.SLAB), base, 2);
        stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, family.get(BlockFamily.Variant.STAIRS), base);
        stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, family.get(BlockFamily.Variant.WALL), base);
    }
}
