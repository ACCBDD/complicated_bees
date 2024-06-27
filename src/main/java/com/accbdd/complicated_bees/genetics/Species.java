package com.accbdd.complicated_bees.genetics;


import com.accbdd.complicated_bees.ComplicatedBees;
import com.accbdd.complicated_bees.genetics.gene.GeneSpecies;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

/**
 * Defines the color and products of a bee, as well as the default genes for things like JEI display.
 */
public class Species {
    private final int color;
    private final BeeProducts products;
    private final Chromosome defaultChromosome;

    public static final Species INVALID = new Species(
            0,
            BeeProducts.EMPTY,
            new Chromosome());

    public Species(int color, BeeProducts products, Chromosome defaultChromosome) {
        this.color = color;
        this.products = products;
        this.defaultChromosome = defaultChromosome.setGene(GeneSpecies.ID, new GeneSpecies(this));
    }

    public Species(int color, BeeProducts products, CompoundTag defaultGenomeAsTag) {
        this(color, products, new Chromosome(defaultGenomeAsTag));
        ComplicatedBees.LOGGER.debug("creating new species using genome from compoundtag: {}", defaultGenomeAsTag);
    }

    public int getColor() {
        return this.color;
    }

    public BeeProducts getProducts() {
        return products;
    }

    public Chromosome getDefaultChromosome() {
        return defaultChromosome;
    }
}
