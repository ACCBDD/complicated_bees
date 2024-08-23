package com.accbdd.complicated_bees.genetics;


import com.accbdd.complicated_bees.genetics.gene.GeneSpecies;
import com.accbdd.complicated_bees.registry.ItemsRegistration;
import com.accbdd.complicated_bees.registry.SpeciesRegistration;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;

import static com.accbdd.complicated_bees.util.ComplicatedBeesCodecs.HEX_STRING_CODEC;

/**
 * Defines the color and products of a bee, as well as the default genes for things like JEI display and world drops.
 */
public class Species {
    private final int color;
    private final int nest_color;
    private final List<Product> products;
    private final List<Product> specialty_products;
    private final Chromosome default_chromosome;
    private final boolean dominant;
    private final boolean foil;

    public static final Species INVALID = new Species();

    public static final Codec<Species> SPECIES_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.optionalFieldOf("dominant", true).forGetter(Species::isDominant),
                    Codec.BOOL.optionalFieldOf("foil", false).forGetter(Species::isFoil),
                    HEX_STRING_CODEC.fieldOf("color").forGetter(Species::getColor),
                    HEX_STRING_CODEC.optionalFieldOf("nest_color", -1).forGetter(Species::getNestColor),
                    Product.CODEC.listOf().optionalFieldOf("products", new ArrayList<>()).forGetter(Species::getProducts),
                    Product.CODEC.listOf().optionalFieldOf("specialty_products", new ArrayList<>()).forGetter(Species::getSpecialtyProducts),
                    CompoundTag.CODEC.optionalFieldOf("default_chromosome", new Chromosome().serialize()).forGetter(species -> species.getDefaultChromosome().serialize())
            ).apply(instance, Species::new)
    );

    public Species() {
        this(false, false, 0xFFFFFF, 0xFFFFFF, new ArrayList<>(), new ArrayList<>(), new Chromosome());
    }

    public Species(boolean dominant, boolean foil, int color, int nest_color, List<Product> products, List<Product> specialtyProducts, Chromosome default_chromosome) {
        this.dominant = dominant;
        this.color = color;
        this.nest_color = nest_color;
        this.products = products;
        this.specialty_products = specialtyProducts;
        this.foil = foil;
        this.default_chromosome = default_chromosome.setGene(GeneSpecies.ID, new GeneSpecies(this, dominant));
    }

    public Species(boolean dominant, boolean foil, int color, int nest_color, List<Product> products, List<Product> specialtyProducts, CompoundTag defaultGenomeAsTag) {
        this(dominant, foil, color, nest_color, products, specialtyProducts, new Chromosome(defaultGenomeAsTag));
        default_chromosome.setGene(GeneSpecies.ID, new GeneSpecies(this, dominant));
    }

    public static Species getFromResourceLocation(ResourceLocation loc) {
        RegistryAccess registryAccess = (Minecraft.getInstance().getConnection() == null) ? ServerLifecycleHooks.getCurrentServer().registryAccess() : Minecraft.getInstance().getConnection().registryAccess();
        return registryAccess.registry(SpeciesRegistration.SPECIES_REGISTRY_KEY).get().get(loc);
    }

    public boolean isFoil() {
        return this.foil;
    }

    public boolean isDominant() {
        return this.dominant;
    }

    public int getColor() {
        return this.color;
    }

    public int getNestColor() {
        return this.nest_color == -1 ? this.color : this.nest_color;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Product> getSpecialtyProducts() {
        return specialty_products;
    }

    public Chromosome getDefaultChromosome() {
        return default_chromosome.copy();
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
