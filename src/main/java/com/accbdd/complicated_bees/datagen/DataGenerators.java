package com.accbdd.complicated_bees.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DataGenerators {
    public static void generate(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new BlockStateGenerator(packOutput, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new ItemModelGenerator(packOutput, event.getExistingFileHelper()));

        generator.addProvider(event.includeServer(), (DataProvider.Factory<LootTableGenerator>) pOutput -> new LootTableGenerator(packOutput));
    }
}
