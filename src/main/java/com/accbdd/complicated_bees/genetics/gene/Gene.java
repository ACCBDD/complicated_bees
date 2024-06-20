package com.accbdd.complicated_bees.genetics.gene;

import com.accbdd.complicated_bees.genetics.Genome;
import com.mojang.serialization.Codec;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

/**
 * An abstract class for a Gene representing some arbitrary information.
 */
public abstract class Gene {
    /**
     * Returns the version of this gene present in the given genome.
     *
     * @param genome a genome
     * @return the version of this gene present in the genome
     */
    public static Gene get(Genome genome) {
        throw new RuntimeException("This needs to be implemented!");
    }

    /**
     * Gets the ResourceLocation of this gene, used in serialization and registry lookups.
     *
     * @return the ResourceLocation of this gene
     */
    public static ResourceLocation getId() {
        throw new RuntimeException("This needs to be implemented!");
    }

    /**
     * Serializes this gene into an NBT tag.
     * @return a serialized version of this gene
     */
    public abstract Tag serialize();

    /**
     * Creates a deserialized gene from a serialized NBT tag.
     * @param tag the serialized version of the data this gene stores
     * @return a deserialized version of this gene from the specified tag
     */
    public abstract Gene deserialize(Tag tag);
}
