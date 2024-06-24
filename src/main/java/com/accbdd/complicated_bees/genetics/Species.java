package com.accbdd.complicated_bees.genetics;


import com.accbdd.complicated_bees.ComplicatedBees;
import com.accbdd.complicated_bees.genetics.gene.GeneSpecies;
import net.minecraft.nbt.CompoundTag;

/**
 * Defines the color and products of a bee, as well as the default genes for things like JEI display.
 */
public class Species {
    private final int color;
    private final BeeProducts products;
    private final Genome defaultGenome;

    public static final Species INVALID = new Species(
            0,
            BeeProducts.EMPTY,
            new Genome());

    public Species(int color, BeeProducts products, Genome defaultGenome) {
        this.color = color;
        this.products = products;
        this.defaultGenome = defaultGenome.setGene(GeneSpecies.ID, new GeneSpecies(this));
    }

    public Species(int color, BeeProducts products, CompoundTag defaultGenomeAsTag) {
        this(color, products, new Genome(defaultGenomeAsTag));
        ComplicatedBees.LOGGER.debug("creating new species using genome from compoundtag: {}", defaultGenomeAsTag);
    }

    public int getColor() {
        return this.color;
    }

    public BeeProducts getProducts() {
        return products;
    }

    public Genome getDefaultGenome() {
        return defaultGenome;
    }
}
