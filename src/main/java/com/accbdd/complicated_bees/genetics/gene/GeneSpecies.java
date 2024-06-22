package com.accbdd.complicated_bees.genetics.gene;

import com.accbdd.complicated_bees.genetics.Genome;
import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.registry.SpeciesRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.logging.Level;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class GeneSpecies extends Gene {
    public static final String TAG = "species";

    private Species species;

    public GeneSpecies() {
        this(Species.INVALID);
    }

    public GeneSpecies(Species species) {
        this.species = species;
    }

    public static GeneSpecies get(Genome genome) {
        return genome.getGene(getId()) == null ? new GeneSpecies() : (GeneSpecies) genome.getGene(getId());
    }

    public static ResourceLocation getId() {
        return new ResourceLocation(MODID, TAG);
    }

    @Override
    public StringTag serialize() {
        RegistryAccess registryAccess = (Minecraft.getInstance().getConnection() == null) ? ServerLifecycleHooks.getCurrentServer().registryAccess() : Minecraft.getInstance().getConnection().registryAccess();
        return StringTag.valueOf(registryAccess.registry(SpeciesRegistry.SPECIES_REGISTRY_KEY).get().getKey(this.species).toString());
    }

    @Override
    public Gene deserialize(Tag tag) {
        RegistryAccess registryAccess = (Minecraft.getInstance().getConnection() == null) ? ServerLifecycleHooks.getCurrentServer().registryAccess() : Minecraft.getInstance().getConnection().registryAccess();
        Registry<Species> registry = registryAccess.registry(SpeciesRegistry.SPECIES_REGISTRY_KEY).get();

        return new GeneSpecies(registry.get(ResourceLocation.tryParse(tag.getAsString())));
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }
}
