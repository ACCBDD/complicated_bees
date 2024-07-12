package com.accbdd.complicated_bees.genetics;


import com.accbdd.complicated_bees.genetics.gene.GeneSpecies;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines the color and products of a bee, as well as the default genes for things like JEI display.
 */
public class Species {
    private final int color;
    private final List<Product> products;
    private final List<Product> specialty_products;
    private final Chromosome default_chromosome;
    private final boolean dominant;

    public static final Species INVALID = new Species(
            true,
            0xFFFFFF,
            new ArrayList<>(),
            new ArrayList<>(),
            new Chromosome());

    public Species(boolean dominant, int color, List<Product> products, List<Product> specialtyProducts, Chromosome default_chromosome) {
        this.dominant = dominant;
        this.color = color;
        this.products = products;
        this.specialty_products = specialtyProducts;
        this.default_chromosome = default_chromosome.setGene(GeneSpecies.ID, new GeneSpecies(this, dominant));
    }

    public Species(boolean dominant, int color, List<Product> products, List<Product> specialtyProducts, CompoundTag defaultGenomeAsTag) {
        this(dominant, color, products, specialtyProducts, new Chromosome(defaultGenomeAsTag));
    }

    public boolean isDominant() {
        return this.dominant;
    }

    public int getColor() {
        return this.color;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Product> getSpecialtyProducts() {
        return specialty_products;
    }

    public Chromosome getDefaultChromosome() {
        return default_chromosome;
    }

    public ItemStack toStack(Item item) {
        ItemStack stack = new ItemStack(item);
        return GeneticHelper.setGenome(stack, new Genome(getDefaultChromosome(), getDefaultChromosome()));
    }

    public List<ItemStack> toMembers() {
        List<ItemStack> members = new ArrayList<>();
        members.add(this.toStack(ItemsRegistration.QUEEN.get()));
        members.add(this.toStack(ItemsRegistration.PRINCESS.get()));
        members.add(this.toStack(ItemsRegistration.DRONE.get()));
        return members;
    }
}
