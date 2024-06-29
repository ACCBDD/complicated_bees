package com.accbdd.complicated_bees.genetics.mutation;

import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.registry.SpeciesRegistry;
import net.minecraft.resources.ResourceLocation;

public class Mutation {
    private final ResourceLocation first, second, result;
    private final float chance;

    public Mutation(ResourceLocation first, ResourceLocation second, ResourceLocation result, float chance) {
        this.first = first;
        this.second = second;
        this.result = result;
        this.chance = chance;
    }

    public ResourceLocation getFirst() {
        return first;
    }

    public Species getFirstSpecies() {
        return SpeciesRegistry.getFromResourceLocation(first);
    }

    public ResourceLocation getSecond() {
        return second;
    }

    public Species getSecondSpecies() {
        return SpeciesRegistry.getFromResourceLocation(second);
    }

    public ResourceLocation getResult() {
        return result;
    }

    public Species getResultSpecies() {
        return SpeciesRegistry.getFromResourceLocation(result);
    }

    public float getChance() {
        return chance;
    }


}
