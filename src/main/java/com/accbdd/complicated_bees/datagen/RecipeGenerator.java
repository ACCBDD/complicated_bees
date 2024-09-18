package com.accbdd.complicated_bees.datagen;

import com.accbdd.complicated_bees.registry.BlocksRegistration;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

public class RecipeGenerator extends RecipeProvider {

    public RecipeGenerator(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        frameRecipe(output, ItemsRegistration.FRAME, Ingredient.of(Tags.Items.STRING), Ingredient.of(Tags.Items.RODS_WOODEN));
        frameRecipe(output, ItemsRegistration.THICK_FRAME, Ingredient.of(ItemTags.WOOL), Ingredient.of(Tags.Items.RODS_WOODEN));
        frameRecipe(output, ItemsRegistration.DRY_FRAME, Ingredient.of(ItemTags.SAND), Ingredient.of(Tags.Items.RODS_WOODEN));
        frameRecipe(output, ItemsRegistration.MOIST_FRAME, Ingredient.of(Items.WATER_BUCKET), Ingredient.of(Tags.Items.RODS_WOODEN));
        frameRecipe(output, ItemsRegistration.HOT_FRAME, Ingredient.of(Items.MAGMA_BLOCK), Ingredient.of(Items.NETHER_BRICK));
        frameRecipe(output, ItemsRegistration.COLD_FRAME, Ingredient.of(Items.BLUE_ICE), Ingredient.of(Tags.Items.RODS_WOODEN));
        deadlyFrame(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemsRegistration.ANALYZER)
                .pattern("IGI")
                .pattern("RWR")
                .pattern("ITI")
                .define('G', Tags.Items.GLASS_PANES)
                .define('I', Items.GOLD_INGOT)
                .define('W', Items.DIAMOND)
                .define('R', Items.REDSTONE)
                .define('T', Items.TRIPWIRE_HOOK)
                .unlockedBy("has_bee", has(ItemsRegistration.PRINCESS)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemsRegistration.METER)
                .pattern("IGI")
                .pattern("RWR")
                .pattern("ITI")
                .define('G', Tags.Items.GLASS_PANES)
                .define('I', Items.IRON_INGOT)
                .define('W', Items.REDSTONE_TORCH)
                .define('R', Items.REDSTONE)
                .define('T', Items.TRIPWIRE_HOOK)
                .unlockedBy("has_bee", has(ItemsRegistration.PRINCESS)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemsRegistration.SCOOP)
                .pattern("SWS")
                .pattern("SSS")
                .pattern(" S ")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('W', ItemTags.WOOL)
                .unlockedBy("has_wool", has(ItemTags.WOOL)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemsRegistration.APIARY)
                .pattern("PPP")
                .pattern("ICI")
                .pattern("PPP")
                .define('P', ItemTags.PLANKS)
                .define('I', Items.IRON_INGOT)
                .define('C', ItemsRegistration.COMB)
                .unlockedBy(getHasName(ItemsRegistration.COMB), has(ItemsRegistration.COMB)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemsRegistration.CENTRIFUGE)
                .pattern("III")
                .pattern("ICI")
                .pattern("SRS")
                .define('C', Items.CHEST)
                .define('I', Items.IRON_INGOT)
                .define('S', Items.SMOOTH_STONE)
                .define('R', Items.REDSTONE)
                .unlockedBy(getHasName(ItemsRegistration.COMB), has(ItemsRegistration.COMB)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemsRegistration.GENERATOR)
                .pattern("CIC")
                .pattern("IBI")
                .pattern("SRS")
                .define('C', Items.COPPER_INGOT)
                .define('I', Items.IRON_INGOT)
                .define('B', Items.BLAST_FURNACE)
                .define('S', Items.SMOOTH_STONE)
                .define('R', Items.REDSTONE)
                .unlockedBy(getHasName(ItemsRegistration.COMB), has(ItemsRegistration.COMB)).save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ItemsRegistration.HONEYED_PLANKS, 8)
                .pattern("SSS")
                .pattern("SBS")
                .pattern("SSS")
                .define('S', ItemTags.PLANKS)
                .define('B', ItemsRegistration.HONEY_DROPLET)
                .unlockedBy(getHasName(ItemsRegistration.HONEY_DROPLET), has(ItemsRegistration.HONEY_DROPLET)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ItemsRegistration.WAX_BLOCK)
                .pattern("SS")
                .pattern("SS")
                .define('S', ItemsRegistration.BEESWAX)
                .unlockedBy(getHasName(ItemsRegistration.BEESWAX), has(ItemsRegistration.BEESWAX)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ItemsRegistration.WAX_BRICKS, 4)
                .pattern("SS")
                .pattern("SS")
                .define('S', ItemsRegistration.WAX_BLOCK)
                .unlockedBy(getHasName(ItemsRegistration.WAX_BLOCK), has(ItemsRegistration.WAX_BLOCK)).save(output);
        SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(ItemsRegistration.WAX_BLOCK),
                RecipeCategory.BUILDING_BLOCKS,
                ItemsRegistration.SMOOTH_WAX,
                0.1f,
                200)
                .unlockedBy(getHasName(ItemsRegistration.WAX_BLOCK), has(ItemsRegistration.WAX_BLOCK)).save(output);
        generateRecipes(output, DataGenerators.HONEYED_PLANK_FAMILY, FeatureFlags.DEFAULT_FLAGS);
        generateRecipes(output, DataGenerators.WAX_BLOCK_FAMILY, FeatureFlags.DEFAULT_FLAGS);
        generateRecipes(output, DataGenerators.WAX_BRICK_FAMILY, FeatureFlags.DEFAULT_FLAGS);
        generateRecipes(output, DataGenerators.SMOOTH_WAX_FAMILY, FeatureFlags.DEFAULT_FLAGS);
        stonecutterFor(output, DataGenerators.WAX_BLOCK_FAMILY);
        stonecutterFor(output, DataGenerators.WAX_BRICK_FAMILY);
        stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, BlocksRegistration.WAX_BRICKS.get(), BlocksRegistration.WAX_BLOCK.get());
        stonecutterFor(output, DataGenerators.WAX_BRICK_FAMILY, BlocksRegistration.WAX_BLOCK.get());
        stonecutterFor(output, DataGenerators.SMOOTH_WAX_FAMILY);
        stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, BlocksRegistration.CHISELED_WAX.get(), BlocksRegistration.SMOOTH_WAX.get());
    }

    protected static void frameRecipe(RecipeOutput output, ItemLike result, Ingredient center, Ingredient outside) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .pattern("OOO")
                .pattern("OXO")
                .pattern("OOO")
                .define('O', outside)
                .define('X', center)
                .unlockedBy("has_apiary", has(ItemsRegistration.APIARY)).save(output);
    }

    protected static void deadlyFrame(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemsRegistration.DEADLY_FRAME)
                .pattern("OCO")
                .pattern("OXO")
                .pattern("OOO")
                .define('O', Items.OBSIDIAN)
                .define('X', Items.SKELETON_SKULL)
                .define('C', Items.CRYING_OBSIDIAN)
                .unlockedBy("has_apiary", has(ItemsRegistration.APIARY)).save(output);
    }

    protected static void stonecutterFor(RecipeOutput output, BlockFamily family) {
        stonecutterFor(output, family, family.getBaseBlock());
    }

    protected static void stonecutterFor(RecipeOutput output, BlockFamily family, Block base) {
        stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, family.get(BlockFamily.Variant.SLAB), base, 2);
        stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, family.get(BlockFamily.Variant.STAIRS), base);
        stonecutterResultFromBase(output, RecipeCategory.BUILDING_BLOCKS, family.get(BlockFamily.Variant.WALL), base);
    }
}
