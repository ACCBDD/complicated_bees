package com.accbdd.complicated_bees.genetics.mutation;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public interface IMutationCondition {
    /**
     * @param level the level to check this condition in
     * @param pos   the position to check this condition in
     * @return whether this mutation condition is satisfied
     */
    boolean check(Level level, BlockPos pos);

    /**
     * Serializes this condition into a compound tag.
     *
     * @return a serialized version of this condition
     */
    CompoundTag serialize();

    /**
     * Creates a deserialized condition from a serialized compound tag.
     *
     * @param tag the nbt serialized version of this condition
     * @return a deserialized version of this condition from the specified tag
     */
    IMutationCondition deserialize(CompoundTag tag);
}
