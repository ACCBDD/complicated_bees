package com.accbdd.complicated_bees.command;

import com.accbdd.complicated_bees.bees.Chromosome;
import com.accbdd.complicated_bees.bees.GeneticHelper;
import com.accbdd.complicated_bees.bees.Genome;
import com.accbdd.complicated_bees.bees.Species;
import com.accbdd.complicated_bees.bees.gene.IGene;
import com.accbdd.complicated_bees.bees.tracking.BreedingTracker;
import com.accbdd.complicated_bees.component.Bee;
import com.accbdd.complicated_bees.datagen.ItemTagGenerator;
import com.accbdd.complicated_bees.recipe.mutation.MutationRecipe;
import com.accbdd.complicated_bees.registry.EsotericRegistration;
import com.accbdd.complicated_bees.registry.GeneRegistration;
import com.accbdd.complicated_bees.registry.SpeciesRegistration;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class DiscoverCommands {
    public static void register(LiteralArgumentBuilder<CommandSourceStack> root, CommandDispatcher<CommandSourceStack> pDispatcher, CommandBuildContext buildContext) {
        pDispatcher.register(root
                .then(Commands.literal("tracking").requires(context -> context.hasPermission(2))
                        .then(Commands.argument("targets", EntityArgument.players())
                                .then(Commands.literal("species")
                                        .then(Commands.literal("clear").executes(context -> clearSpecies(context.getSource(), EntityArgument.getEntities(context, "targets"))))
                                        .then(Commands.literal("grant")
                                                .then(Commands.argument("species", ResourceArgument.resource(buildContext, SpeciesRegistration.SPECIES_REGISTRY_KEY)).executes(context -> discoverSpecies(context.getSource(), EntityArgument.getEntities(context, "targets"), ResourceArgument.getResource(context, "species", SpeciesRegistration.SPECIES_REGISTRY_KEY).value()))))
                                ).then(Commands.literal("mutation")
                                        .then(Commands.literal("clear").executes(context -> clearMutations(context.getSource(), EntityArgument.getEntities(context, "targets"))))
                                        .then(Commands.literal("grant")
                                            .then(Commands.argument("mutation", ResourceLocationArgument.id()).suggests(DiscoverCommands::getMutationSuggest).executes(context -> discoverMutation(context.getSource(), EntityArgument.getEntities(context, "targets"), ResourceLocationArgument.getRecipe(context, "mutation")))))
                                ).then(Commands.literal("research")
                                        .then(Commands.literal("clear").executes(context -> clearResearch(context.getSource(), EntityArgument.getEntities(context, "targets"))))
                                        .then(Commands.literal("grant")
                                            .then(Commands.argument("mutation", ResourceLocationArgument.id()).suggests(DiscoverCommands::getMutationSuggest).executes(context -> discoverResearch(context.getSource(), EntityArgument.getEntities(context, "targets"), ResourceLocationArgument.getRecipe(context, "mutation")))))
                )))
                .then(Commands.literal("setgene").requires(context -> context.hasPermission(2))
                        .then(Commands.argument("primary", BoolArgumentType.bool())
                                .then(Commands.argument("gene_name", ResourceArgument.resource(buildContext, GeneRegistration.GENE_REGISTRY_LOCATION))
                                        .then(Commands.argument("data", CompoundTagArgument.compoundTag()).suggests(DiscoverCommands::getGeneSuggest).executes(context -> setGeneData(context.getSource(), BoolArgumentType.getBool(context, "primary"), ResourceArgument.getResource(context, "gene_name", GeneRegistration.GENE_REGISTRY_LOCATION).key().location(), CompoundTagArgument.getCompoundTag(context, "data"))))))
        ));
    }

    private static CompletableFuture<Suggestions> getGeneSuggest(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        String[] suggestion = new String[] {""};
        if (context.getSource().getPlayer() != null) {
            ItemStack held = context.getSource().getPlayer().getMainHandItem();
            if (held.has(EsotericRegistration.BEE)) {
                Chromosome chromosome = BoolArgumentType.getBool(context, "primary") ? held.get(EsotericRegistration.BEE).genome().primary() : held.get(EsotericRegistration.BEE).genome().secondary();
                IGene<?> gene = chromosome.getGene(ResourceArgument.getResource(context, "gene_name", GeneRegistration.GENE_REGISTRY_LOCATION).key().location());
                suggestion[0] = gene.serialize().toString();
            }
        }
        return SharedSuggestionProvider.suggest(suggestion, builder);
    }

    private static CompletableFuture<Suggestions> getMutationSuggest(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        String[] suggestion = new String[] {""};
        if (context.getSource().getPlayer() != null) {
            return SharedSuggestionProvider.suggest(context.getSource().getPlayer().level().getRecipeManager().getAllRecipesFor(EsotericRegistration.MUTATION_RECIPE.get()).stream().map(holder -> holder.id().toString()), builder);
        }
        return SharedSuggestionProvider.suggest(suggestion, builder);
    }

    private static int setGeneData(CommandSourceStack source, boolean primary, ResourceLocation geneId, CompoundTag data) throws CommandSyntaxException {
        if (source.getPlayer() == null)
            return 0;
        ItemStack held = source.getPlayer().getMainHandItem();
        if (held.is(ItemTagGenerator.BEE)) {
            if (held.has(EsotericRegistration.BEE)) {
                Bee beeComponent = held.get(EsotericRegistration.BEE);
                Genome genome = beeComponent.genome();
                Chromosome chromosome = primary ? genome.primary() : genome.secondary();
                IGene<?> gene = chromosome.copy().getGene(geneId);
                if (gene != null) {
                    if (data.getAllKeys().equals(gene.serialize().getAllKeys())) {
                        chromosome.setGene(geneId, gene.deserialize(data));
                        held.set(EsotericRegistration.BEE, beeComponent.withGenome(genome));
                        source.sendSuccess(() -> Component.translatable("command.complicated_bees.set_gene.success"), true);
                    } else {
                        throw new SimpleCommandExceptionType(Component.translatable("command.complicated_bees.set_gene.invalid_data")).create();
                    }
                } else {
                    throw new SimpleCommandExceptionType(Component.translatable("command.complicated_bees.set_gene.invalid_gene")).create();
                }
            } else {
                throw new SimpleCommandExceptionType(Component.translatable("command.complicated_bees.set_gene.corrupt_tag")).create();
            }
        } else {
            throw new SimpleCommandExceptionType(Component.translatable("command.complicated_bees.not_bee")).create();
        }
        return 1;
    }

    private static int clearResearch(CommandSourceStack source, Collection<? extends Entity> targets) throws CommandSyntaxException {
        int i = 0;
        for (Entity entity : targets) {
            if (entity instanceof Player player) {
                ++i;
                BreedingTracker.getTracker(player).clearResearch();
            }
        }
        if (i == 0)
            throw new SimpleCommandExceptionType(Component.translatable("command.complicated_bees.no_players")).create();
        else {
            int finalI = i;
            source.sendSuccess(() -> Component.translatable("command.complicated_bees.discover.research.clear", finalI), true);
        }
        return i;
    }

    private static int discoverResearch(CommandSourceStack source, Collection<? extends Entity> targets, RecipeHolder<?> mutation) throws CommandSyntaxException {
        int i = 0;
        if (mutation.value() instanceof MutationRecipe) {
            for (Entity entity : targets) {
                if (entity instanceof Player player) {
                    var tracker = BreedingTracker.getTracker(player);
                    if (!tracker.isResearched((RecipeHolder<MutationRecipe>) mutation)) {
                        ++i;
                        BreedingTracker.getTracker(player).research((RecipeHolder<MutationRecipe>) mutation);
                    }
                }
            }
        }
        if (i == 0)
            throw new SimpleCommandExceptionType(Component.translatable("command.complicated_bees.discover.research.no_change")).create();
        else {
            int finalI = i;
            source.sendSuccess(() -> Component.translatable("command.complicated_bees.discover.research", mutation.id().toString(), finalI), true);
        }
        return i;
    }

    private static int clearMutations(CommandSourceStack source, Collection<? extends Entity> targets) throws CommandSyntaxException {
        int i = 0;
        for (Entity entity : targets) {
            if (entity instanceof Player player) {
                ++i;
                BreedingTracker.getTracker(player).clearMutations();
            }
        }
        if (i == 0)
            throw new SimpleCommandExceptionType(Component.translatable("command.complicated_bees.no_players")).create();
        else {
            int finalI = i;
            source.sendSuccess(() -> Component.translatable("command.complicated_bees.discover.mutation.clear", finalI), true);
        }
        return i;
    }

    private static int discoverMutation(CommandSourceStack source, Collection<? extends Entity> targets, RecipeHolder<?> mutation) throws CommandSyntaxException {
        int i = 0;
        if (mutation.value() instanceof MutationRecipe) {
            for (Entity entity : targets) {
                if (entity instanceof Player player) {
                    var tracker = BreedingTracker.getTracker(player);
                    if (!tracker.isDiscovered((RecipeHolder<MutationRecipe>) mutation)) {
                        ++i;
                        BreedingTracker.getTracker(player).discover((RecipeHolder<MutationRecipe>) mutation);
                    }
                }
            }
        }
        if (i == 0)
            throw new SimpleCommandExceptionType(Component.translatable("command.complicated_bees.discover.mutation.no_change")).create();
        else {
            int finalI = i;
            source.sendSuccess(() -> Component.translatable("command.complicated_bees.discover.mutation", mutation.id().toString(), finalI), true);
        }
        return i;
    }

    private static int clearSpecies(CommandSourceStack source, Collection<? extends Entity> targets) throws CommandSyntaxException {
        int i = 0;
        for (Entity entity : targets) {
            if (entity instanceof Player player) {
                ++i;
                BreedingTracker.getTracker(player).clearSpecies();
            }
        }
        if (i == 0)
            throw new SimpleCommandExceptionType(Component.translatable("command.complicated_bees.no_players")).create();
        else {
            int finalI = i;
            source.sendSuccess(() -> Component.translatable("command.complicated_bees.discover.species.clear", finalI), true);
        }
        return i;
    }

    private static int discoverSpecies(CommandSourceStack source, Collection<? extends Entity> targets, Species species) throws CommandSyntaxException {
        int i = 0;
        for (Entity entity : targets) {
            if (entity instanceof Player player) {
                var tracker = BreedingTracker.getTracker(player);
                if (!tracker.isDiscovered(species)) {
                    ++i;
                    BreedingTracker.getTracker(player).discover(species);
                }
            }
        }
        if (i == 0)
            throw new SimpleCommandExceptionType(Component.translatable("command.complicated_bees.discover.species.no_change")).create();
        else {
            int finalI = i;
            source.sendSuccess(() -> Component.translatable("command.complicated_bees.discover.species", GeneticHelper.getTranslationKey(species), finalI), true);
        }
        return i;
    }
}
