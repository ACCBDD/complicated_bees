package com.accbdd.complicated_bees.genetics.gene;

import com.accbdd.complicated_bees.genetics.Genome;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

public abstract class Gene {


    /**
     * Returns the version of this gene present in the given genome.
     * @param genome a genome
     * @return the version of this gene present in the genome
     */
    public abstract Gene get(Genome genome);

    /**
     * Gets the ResourceLocation of this gene, used in serialization and registry lookups.
     * @return the ResourceLocation of this gene
     */
    public abstract ResourceLocation getId();

    /**
     * Creates a deserialized gene from a serialized NBT tag.
     * @param tag the serialized version of the data this gene stores
     * @return a deserialized version of this gene from the specified tag
     */
    public abstract Gene setFromTag(Tag tag);
}
