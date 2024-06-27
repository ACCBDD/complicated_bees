package com.accbdd.complicated_bees.genetics;

import com.accbdd.complicated_bees.genetics.gene.Gene;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class GeneticHelper {
    public static final String GENOME_A = "genome_a";
    public static final String GENOME_B = "genome_b";

    public static Genome getGenome(ItemStack stack, boolean primary) {
        CompoundTag serializedGenome = stack.getOrCreateTag().getCompound(primary ? GENOME_A : GENOME_B);
        return Genome.deserialize(serializedGenome);
    }

    public static ItemStack setGenome(ItemStack stack, Genome genome, boolean primary) {
        stack.getOrCreateTag().put(primary ? GENOME_A : GENOME_B, genome.serialize());
        return stack;
    }

    public static ItemStack setGenome(ItemStack stack, Genome genome_a, Genome genome_b) {
        stack.getOrCreateTag().put(GENOME_A, genome_a.serialize());
        stack.getOrCreateTag().put(GENOME_B, genome_b.serialize());
        return stack;
    }

    public static ItemStack setBothGenome(ItemStack stack, Genome genome) {
        stack.getOrCreateTag().put(GENOME_A, genome.serialize());
        stack.getOrCreateTag().put(GENOME_B, genome.serialize());
        return stack;
    }

    public static Gene<?> getGene(ItemStack stack, ResourceLocation id, boolean primary) {
        return getGenome(stack, primary).getGene(id);
    }

    public static Object getGeneValue(ItemStack stack, ResourceLocation id, boolean primary) {
        return getGene(stack, id, primary).get();
    }

    public static boolean hasGenome(ItemStack stack, boolean primary) {
        return !Objects.equals(getGenome(stack, primary).serialize(), new CompoundTag());
    }
}
