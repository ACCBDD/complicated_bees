package com.accbdd.complicated_bees.datagen;

import com.accbdd.complicated_bees.registry.BlocksRegistration;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class DataGenerators {
    public static final BlockFamily HONEYED_PLANK_FAMILY = new BlockFamily.Builder(BlocksRegistration.HONEYED_PLANKS.get())
            .stairs(BlocksRegistration.HONEYED_STAIRS.get())
            .slab(BlocksRegistration.HONEYED_SLAB.get())
            .fence(BlocksRegistration.HONEYED_FENCE.get())
            .fenceGate(BlocksRegistration.HONEYED_FENCE_GATE.get())
            .button(BlocksRegistration.HONEYED_BUTTON.get())
            .pressurePlate(BlocksRegistration.HONEYED_PRESSURE_PLATE.get())
            .door(BlocksRegistration.HONEYED_DOOR.get())
            .trapdoor(BlocksRegistration.HONEYED_TRAPDOOR.get())
            .getFamily();

    public static final BlockFamily WAX_BLOCK_FAMILY = new BlockFamily.Builder(BlocksRegistration.WAX_BLOCK.get())
            .stairs(BlocksRegistration.WAX_BLOCK_STAIRS.get())
            .slab(BlocksRegistration.WAX_BLOCK_SLAB.get())
            .wall(BlocksRegistration.WAX_BLOCK_WALL.get())
            .getFamily();

    public static final BlockFamily WAX_BRICK_FAMILY = new BlockFamily.Builder(BlocksRegistration.WAX_BRICKS.get())
            .stairs(BlocksRegistration.WAX_BRICK_STAIRS.get())
            .slab(BlocksRegistration.WAX_BRICK_SLAB.get())
            .wall(BlocksRegistration.WAX_BRICK_WALL.get())
            .getFamily();

    public static final BlockFamily SMOOTH_WAX_FAMILY = new BlockFamily.Builder(BlocksRegistration.SMOOTH_WAX.get())
            .stairs(BlocksRegistration.SMOOTH_WAX_STAIRS.get())
            .slab(BlocksRegistration.SMOOTH_WAX_SLAB.get())
            .wall(BlocksRegistration.SMOOTH_WAX_WALL.get())
            .chiseled(BlocksRegistration.CHISELED_WAX.get())
            .getFamily();

    public static void generate(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new BlockStateGenerator(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ItemModelGenerator(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ParticleDescriptionGenerator(packOutput, existingFileHelper));

        BlockTagGenerator blockTagGenerator = new BlockTagGenerator(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagGenerator);
        generator.addProvider(event.includeServer(), new ItemTagGenerator(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), (DataProvider.Factory<LootTableGenerator>) pOutput -> new LootTableGenerator(packOutput));
        generator.addProvider(event.includeServer(), new RecipeGenerator(packOutput));
    }
}
