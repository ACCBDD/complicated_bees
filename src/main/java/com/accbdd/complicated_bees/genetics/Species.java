package com.accbdd.complicated_bees.genetics;


import com.accbdd.complicated_bees.genetics.gene.GeneSpecies;
import net.minecraft.nbt.CompoundTag;

/**
 * Defines the color and products of a bee, as well as the default genes for things like JEI display.
 */
public class Species {
    private final int color;
    private final BeeProducts products;
    private final Chromosome defaultChromosome;
    private final boolean dominant;

    public static final Species INVALID = new Species(
            true,
            0,
            BeeProducts.EMPTY,
            new Chromosome());

    public Species(boolean dominant, int color, BeeProducts products, Chromosome defaultChromosome) {
        this.dominant = dominant;
        this.color = color;
        this.products = products;
        this.defaultChromosome = defaultChromosome.setGene(GeneSpecies.ID, new GeneSpecies(this, dominant));
    }

    public Species(boolean dominant, int color, BeeProducts products, CompoundTag defaultGenomeAsTag) {
        this(dominant, color, products, new Chromosome(defaultGenomeAsTag));
    }

    public boolean isDominant() {
        return this.dominant;
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
