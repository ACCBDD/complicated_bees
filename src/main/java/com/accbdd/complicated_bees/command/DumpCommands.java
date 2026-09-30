package com.accbdd.complicated_bees.command;

import com.accbdd.complicated_bees.ComplicatedBees;
import com.accbdd.complicated_bees.bees.Chromosome;
import com.accbdd.complicated_bees.bees.GeneticHelper;
import com.accbdd.complicated_bees.bees.Species;
import com.accbdd.complicated_bees.bees.gene.IGene;
import com.accbdd.complicated_bees.bees.gene.enums.EnumHumidity;
import com.accbdd.complicated_bees.bees.gene.enums.EnumTemperature;
import com.accbdd.complicated_bees.registry.GeneRegistration;
import com.accbdd.complicated_bees.registry.SpeciesRegistration;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.Biome;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class DumpCommands {
    public static void register(LiteralArgumentBuilder<CommandSourceStack> root, CommandDispatcher<CommandSourceStack> pDispatcher, CommandBuildContext buildContext) {
        pDispatcher.register(root
                .then(Commands.literal("dump")
                        .then(Commands.literal("biomes").executes(DumpCommands::dumpBiomes))
                        .then(Commands.literal("effects").executes(DumpCommands::dumpEffects))
                        .then(Commands.literal("species").executes(DumpCommands::dumpSpecies)))
        );
    }

    private static int dumpBiomes(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        RegistryAccess registryAccess = GeneticHelper.getRegistryAccess();
        if (player == null || registryAccess == null)
            return 0;

        Registry<Biome> biomes = registryAccess.registryOrThrow(Registries.BIOME);
        biomes.holders().forEach(holder ->
                player.sendSystemMessage(Component.literal(holder.getKey().location() + ": " + EnumTemperature.getFromBiome(holder) + ", " + EnumHumidity.getFromBiome(holder))));
        return 1;
    }

    private static int dumpEffects(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null)
            return 0;

        ComplicatedBees.BEE_EFFECT_REGISTRY.get().entrySet().forEach(entry ->
                player.sendSystemMessage(Component.translatable("effect.complicated_bees." + entry.getKey().location()).append(": ").append(Component.translatable("effect.complicated_bees." + entry.getKey().location() + ".desc"))));
        return 1;
    }

    private static int dumpSpecies(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        RegistryAccess registryAccess = GeneticHelper.getRegistryAccess();
        if (player == null || registryAccess == null)
            return 0;

        Registry<Species> speciesRegistry = registryAccess.registryOrThrow(SpeciesRegistration.SPECIES_REGISTRY_KEY);
        speciesRegistry.stream().forEach(species -> {
            Chromosome chromosome = species.getDefaultChromosome();
            player.sendSystemMessage(GeneticHelper.getTranslationKey(species)
                    .append(",")
                    .append(geneValue(chromosome, GeneRegistration.LIFESPAN.get())).append(",")
                    .append(geneValue(chromosome, GeneRegistration.PRODUCTIVITY.get())).append(",")
                    .append(geneValue(chromosome, GeneRegistration.FLOWER.get())).append(",")
                    .append(geneValue(chromosome, GeneRegistration.HUMIDITY.get())).append(",")
                    .append(geneValue(chromosome, GeneRegistration.TEMPERATURE.get())).append(",")
                    .append(geneValue(chromosome, GeneRegistration.TERRITORY.get())).append(",")
                    .append(geneValue(chromosome, GeneRegistration.EFFECT.get())).append(",")
                    .append(geneValue(chromosome, GeneRegistration.FERTILITY.get())).append(",")
                    .append(geneValue(chromosome, GeneRegistration.ACTIVE_TIME.get())).append(",")
                    .append(geneValue(chromosome, GeneRegistration.CAVE_DWELLING.get())).append(",")
                    .append(geneValue(chromosome, GeneRegistration.WEATHERPROOF.get())));
        });

        Path outputPath = Path.of("complicated_bees", "genes.csv");
        try {
            Files.createDirectories(outputPath.getParent());
        } catch (IOException e) {
            e.printStackTrace();
            player.sendSystemMessage(Component.literal("Failed to create output directory: " + e.getMessage()));
            return 0;
        }
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write("Species,Lifespan,Productivity,Flower,Humidity,Temperature,Territory,Effect,Fertility,ActiveTime,CaveDwelling,Weatherproof");
            writer.newLine();

            for (Species species : speciesRegistry) {
                Chromosome chromosome = species.getDefaultChromosome();

                String line = String.join(",",
                        GeneticHelper.getTranslationKey(species).getString(),
                        geneValue(chromosome, GeneRegistration.LIFESPAN.get()).getString(),
                        geneValue(chromosome, GeneRegistration.PRODUCTIVITY.get()).getString(),
                        geneValue(chromosome, GeneRegistration.FLOWER.get()).getString(),
                        geneValue(chromosome, GeneRegistration.HUMIDITY.get()).getString(),
                        geneValue(chromosome, GeneRegistration.TEMPERATURE.get()).getString(),
                        geneValue(chromosome, GeneRegistration.TERRITORY.get()).getString(),
                        geneValue(chromosome, GeneRegistration.EFFECT.get()).getString(),
                        geneValue(chromosome, GeneRegistration.FERTILITY.get()).getString(),
                        geneValue(chromosome, GeneRegistration.ACTIVE_TIME.get()).getString(),
                        geneValue(chromosome, GeneRegistration.CAVE_DWELLING.get()).getString(),
                        geneValue(chromosome, GeneRegistration.WEATHERPROOF.get()).getString()
                );

                writer.write(line);
                writer.newLine();
            }
            player.sendSystemMessage(Component.literal("Genes dumped to .minecraft/complicated_bees/genes.csv"));
        } catch (IOException e) {
            e.printStackTrace();
            player.sendSystemMessage(Component.literal("Error writing genes.csv: " + e.getMessage()));
        }
        return 1;
    }

    private static Component geneValue(Chromosome chromosome, IGene<?> gene) {
        return chromosome.getGene(ComplicatedBees.GENE_REGISTRY.get().getKey(gene)).getTranslationKey();
    }
}
