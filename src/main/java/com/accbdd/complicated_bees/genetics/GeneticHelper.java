package com.accbdd.complicated_bees.genetics;

import com.accbdd.complicated_bees.ComplicatedBees;
import com.accbdd.complicated_bees.genetics.gene.Gene;
import com.accbdd.complicated_bees.genetics.gene.GeneSpecies;
import com.accbdd.complicated_bees.genetics.gene.GeneTolerant;
import com.accbdd.complicated_bees.genetics.gene.enums.EnumTolerance;
import com.accbdd.complicated_bees.registry.SpeciesRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class GeneticHelper {
    public static final String GENOME_A = "genome_a";
    public static final String GENOME_B = "genome_b";
    public static final String BRED = "bred";
    private static final Random rand = new Random();

    public static Chromosome getChromosome(ItemStack stack, boolean primary) {
        CompoundTag serializedGenome = stack.getOrCreateTag().getCompound(primary ? GENOME_A : GENOME_B);
        return Chromosome.deserialize(serializedGenome);
    }

    public static Genome getGenome(ItemStack stack) {
        CompoundTag genome_a = stack.getOrCreateTag().getCompound(GENOME_A);
        CompoundTag genome_b = stack.getOrCreateTag().getCompound(GENOME_B);

        return new Genome(Chromosome.deserialize(genome_a), Chromosome.deserialize(genome_b));
    }

    public static ItemStack setGenome(ItemStack stack, Chromosome chromosome, boolean primary) {
        stack.getOrCreateTag().put(primary ? GENOME_A : GENOME_B, chromosome.serialize());
        return stack;
    }

    public static ItemStack setGenome(ItemStack stack, Genome genome) {
        stack.getOrCreateTag().put(GENOME_A, genome.getPrimary().serialize());
        stack.getOrCreateTag().put(GENOME_B, genome.getSecondary().serialize());
        return stack;
    }

    public static ItemStack setBred(ItemStack stack, Genome genome) {
        CompoundTag tag = new CompoundTag();
        tag.put(GENOME_A, genome.getPrimary().serialize());
        tag.put(GENOME_B, genome.getSecondary().serialize());
        stack.getOrCreateTag().put(BRED, tag);
        return stack;
    }

    public static ItemStack setBothGenome(ItemStack stack, Chromosome chromosome) {
        stack.getOrCreateTag().put(GENOME_A, chromosome.serialize());
        stack.getOrCreateTag().put(GENOME_B, chromosome.serialize());
        return stack;
    }

    public static Component getTranslationKey(Species species) {
        RegistryAccess registryAccess = (Minecraft.getInstance().getConnection() == null) ? ServerLifecycleHooks.getCurrentServer().registryAccess() : Minecraft.getInstance().getConnection().registryAccess();
        return Component.translatable("species.complicated_bees." + registryAccess.registry(SpeciesRegistry.SPECIES_REGISTRY_KEY).get().getKey(species));
    }

    public static Gene<?> getGene(ItemStack stack, ResourceLocation id, boolean primary) {
        return getChromosome(stack, primary).getGene(id);
    }

    public static Species getSpecies(ItemStack stack, boolean primary) {
        return (Species) getGene(stack, GeneSpecies.ID, primary).get();
    }

    public static Object getGeneValue(ItemStack stack, ResourceLocation id, boolean primary) {
        return getGene(stack, id, primary).get();
    }

    public static boolean hasGenome(ItemStack stack, boolean primary) {
        return !Objects.equals(getChromosome(stack, primary).serialize(), new CompoundTag());
    }

    private static Genome mixGenomes(Genome left, Genome right) {
        //todo: THIS IS NOT MENDELIAN - FIX THIS
        Chromosome chromosome_a = new Chromosome(), chromosome_b = new Chromosome();

        for (Map.Entry<ResourceLocation, Gene<?>> entry : chromosome_a.getGenes().entrySet()) {
            ResourceLocation key = entry.getKey();
            Gene<?> geneA = (rand.nextFloat() < 0.5 ? left.getPrimary() : left.getSecondary()).getGene(key);
            Gene<?> geneB = (rand.nextFloat() < 0.5 ? right.getPrimary() : right.getSecondary()).getGene(key);

//            if (entry.getValue() instanceof GeneTolerant) {
//                //mix tolerances as well
//                geneA = ((GeneTolerant<?>)geneA).setTolerance();
//                geneB = ((GeneTolerant<?>)geneB).setTolerance();
//            }

            chromosome_a.setGene(key, geneA);
            chromosome_b.setGene(key, geneB);
        }

        //sort genome so that dominant genes are always in a
        for (Map.Entry<ResourceLocation, Gene<?>> entry : chromosome_a.getGenes().entrySet()) {
            Gene<?> gene = entry.getValue();
            if (!entry.getValue().isDominant()) {
                ComplicatedBees.LOGGER.debug("swapping values for {}", entry.getKey());
                chromosome_a.setGene(entry.getKey(), chromosome_b.getGene(entry.getKey()));
                chromosome_b.setGene(entry.getKey(), gene);
            }
        }

        return new Genome(chromosome_a, chromosome_b);
    }

    private static Chromosome mixChromosomes(Chromosome first, Chromosome second) {
        Chromosome result = new Chromosome();

        for (Map.Entry<ResourceLocation, Gene<?>> entry : first.getGenes().entrySet()) {
            ResourceLocation geneType = entry.getKey();
            //todo: implement random mutations
            result.setGene(geneType, rand.nextFloat() < 0.5 ? entry.getValue() :  second.getGene(geneType));
            if (entry.getValue() instanceof GeneTolerant) {
                //mix tolerances as well
                EnumTolerance tolerance = rand.nextFloat() < 0.5 ? ((GeneTolerant<?>) entry.getValue()).getTolerance() : ((GeneTolerant<?>)second.getGene(geneType)).getTolerance();
                result.setGene(geneType, ((GeneTolerant<?>)result.getGene(geneType)).setTolerance(tolerance));
            }
        }

        return result;
    }

    public static ItemStack getFromEggs(ItemStack stack, Item resultType) {
        ItemStack result = new ItemStack(resultType);
        CompoundTag eggs = stack.getOrCreateTag().getCompound(BRED);

        Genome genome = getGenome(stack);
        Genome bred = new Genome(Chromosome.deserialize(eggs.getCompound(GENOME_A)), Chromosome.deserialize(eggs.getCompound(GENOME_B)));
        if (!eggs.equals(new CompoundTag())) {
            setGenome(result, mixGenomes(genome, bred));
        } else {
            setGenome(result, genome);
        }
        return result;
    }
}
