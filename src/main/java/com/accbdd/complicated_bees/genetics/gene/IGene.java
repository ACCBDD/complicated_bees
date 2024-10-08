package com.accbdd.complicated_bees.genetics.gene;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;

/**
 * An abstract class for a Gene representing some arbitrary information.
 */
public interface IGene<T> {
    /**
     * @return the value of this gene
     */
    T get();

    /**
     * sets the value of this gene
     */
    IGene<T> set(T value);

    /**
     * @return whether this gene is dominant
     */
    boolean isDominant();

    /**
     * Serializes this gene into an NBT tag.
     *
     * @return a serialized version of this gene
     */
    CompoundTag serialize();

    /**
     * Creates a deserialized gene from a serialized NBT tag.
     *
     * @param tag the nbt serialized version of this gene
     * @return a deserialized version of this gene from the specified tag
     */
    IGene<T> deserialize(CompoundTag tag);

    /**
     * @return a translation key for the value inside this gene
     */
    MutableComponent getTranslationKey();
}
