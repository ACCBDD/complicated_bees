package com.accbdd.complicated_bees.genetics.gene;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

/**
 * An abstract class for a Gene representing some arbitrary information.
 */
public interface IGene<T> {
    /**
     * Returns the value of this gene
     *
     * @return the value of this gene
     */
    T get();

    boolean isDominant();

    /**
     * Serializes this gene into an NBT tag.
     * @return a serialized version of this gene
     */
    CompoundTag serialize();

    /**
     * Creates a deserialized gene from a serialized NBT tag.
     * @param tag the serialized version of the data this gene stores
     * @return a deserialized version of this gene from the specified tag
     */
    IGene<T> deserialize(CompoundTag tag);
}
