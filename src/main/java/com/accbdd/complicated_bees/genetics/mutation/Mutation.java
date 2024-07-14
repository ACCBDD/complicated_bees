package com.accbdd.complicated_bees.genetics.mutation;

import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.registry.SpeciesRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public class Mutation {
    private final ResourceLocation first, second, result;
    private final float chance;

    public static final Codec<Mutation> MUTATION_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceLocation.CODEC.fieldOf("first").forGetter(Mutation::getFirst),
                    ResourceLocation.CODEC.fieldOf("second").forGetter(Mutation::getSecond),
                    ResourceLocation.CODEC.fieldOf("result").forGetter(Mutation::getResult),
                    Codec.FLOAT.fieldOf("chance").forGetter(Mutation::getChance)
            ).apply(instance, Mutation::new)
    );

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
