package com.accbdd.complicated_bees.genetics.mutation;

import com.accbdd.complicated_bees.ComplicatedBees;
import com.accbdd.complicated_bees.genetics.Species;
import com.accbdd.complicated_bees.registry.MutationRegistration;
import com.accbdd.complicated_bees.registry.SpeciesRegistration;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class Mutation {
    private final ResourceLocation first, second, result;
    private final float chance;
    private final List<IMutationCondition> conditions;

    public static final Codec<Mutation> MUTATION_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceLocation.CODEC.fieldOf("first").forGetter(Mutation::getFirst),
                    ResourceLocation.CODEC.fieldOf("second").forGetter(Mutation::getSecond),
                    ResourceLocation.CODEC.fieldOf("result").forGetter(Mutation::getResult),
                    Codec.FLOAT.fieldOf("chance").forGetter(Mutation::getChance),
                    CompoundTag.CODEC.optionalFieldOf("conditions", new CompoundTag()).forGetter(mutation -> Mutation.getSerializedConditions(mutation.getConditions()))
            ).apply(instance, Mutation::new)
    );

    public Mutation(ResourceLocation first, ResourceLocation second, ResourceLocation result, float chance, List<IMutationCondition> conditions) {
        this.first = first;
        this.second = second;
        this.result = result;
        this.chance = chance;
        this.conditions = conditions;
    }

    public Mutation(ResourceLocation first, ResourceLocation second, ResourceLocation result, float chance, CompoundTag conditions) {
        this(first, second, result, chance, new ArrayList<>());
        List<IMutationCondition> list = getConditions();
        for (String key : conditions.getAllKeys()) {
            IMutationCondition condition = MutationRegistration.MUTATION_CONDITION_REGISTRY.get(ResourceLocation.tryParse(key));
            if (condition != null)
                list.add(condition.deserialize(conditions.getCompound(key)));
            else
                ComplicatedBees.LOGGER.error("could not find condition {}", key);
        }
    }

    public ResourceLocation getFirst() {
        return first;
    }

    public Species getFirstSpecies() {
        return SpeciesRegistration.getFromResourceLocation(first);
    }

    public ResourceLocation getSecond() {
        return second;
    }

    public Species getSecondSpecies() {
        return SpeciesRegistration.getFromResourceLocation(second);
    }

    public ResourceLocation getResult() {
        return result;
    }

    public Species getResultSpecies() {
        return SpeciesRegistration.getFromResourceLocation(result);
    }

    public float getChance() {
        return chance;
    }

    public List<IMutationCondition> getConditions() {
        return conditions;
    }

    public static CompoundTag getSerializedConditions(List<IMutationCondition> conditions) {
        CompoundTag tag = new CompoundTag();
        for (IMutationCondition condition : conditions) {
            ResourceLocation loc = MutationRegistration.MUTATION_CONDITION_REGISTRY.getKey(condition);
            if (loc == null) {
                ComplicatedBees.LOGGER.error("tried to serialize non-registered mutation condition!");
            } else {
                tag.put(loc.toString(), condition.serialize());
            }
        }
        return tag;
    }
}
