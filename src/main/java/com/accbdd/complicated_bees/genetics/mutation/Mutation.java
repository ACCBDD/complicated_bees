package com.accbdd.complicated_bees.genetics.mutation;

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

    public ResourceLocation getSecond() {
        return second;
    }

    public ResourceLocation getResult() {
        return result;
    }

    public float getChance() {
        return chance;
    }
}
