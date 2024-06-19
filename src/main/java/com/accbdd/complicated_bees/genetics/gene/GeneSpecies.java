package com.accbdd.complicated_bees.genetics.gene;

import com.accbdd.complicated_bees.genetics.Genome;
import com.accbdd.complicated_bees.genetics.Species;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

import static com.accbdd.complicated_bees.ComplicatedBees.MODID;

public class GeneSpecies extends Gene {
    public static final String TAG = "species";

    private Species species;

    public GeneSpecies(Species species) {
        this.species = species;
    }

    @Override
    public GeneSpecies get(Genome genome) {
        return (GeneSpecies) genome.getGene(getId());
    }

    @Override
    public ResourceLocation getId() {
        return new ResourceLocation(MODID, TAG);
    }

    @Override
    public Gene setFromTag(Tag tag) {
        return null;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }
}
