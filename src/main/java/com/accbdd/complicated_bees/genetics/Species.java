package com.accbdd.complicated_bees.genetics;


import com.accbdd.complicated_bees.ComplicatedBees;
import com.accbdd.complicated_bees.genetics.gene.GeneSpecies;
import net.minecraft.nbt.CompoundTag;

/**
 * Defines the color and products of a bee, as well as the default genes for things like JEI display.
 */
public class Species {
    private final String id;
    private final int color;
    private final BeeProducts products;
    private final Genome defaultGenome;

    public static final Species INVALID = new Species(
            "INVALID",
            0,
            BeeProducts.EMPTY,
            new Genome());

    public Species(String id, int color, BeeProducts products, Genome defaultGenome) {
        this.id = id;
        this.color = color;
        this.products = products;
        this.defaultGenome = defaultGenome.setGene(GeneSpecies.getId(), new GeneSpecies(this));
    }

    public Species(String id, int color, BeeProducts products, CompoundTag defaultGenomeAsTag) {
        this(id, color, products, new Genome(defaultGenomeAsTag));
        ComplicatedBees.LOGGER.debug("creating new species using genome from compoundtag");
    }

    public int getColor() {
        return this.color;
    }

    public String getId() {
        return id;
    }

    public BeeProducts getProducts() {
        return products;
    }

    public Genome getDefaultGenome() {
        return defaultGenome;
    }
}
